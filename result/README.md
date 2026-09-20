# 网易云音乐儿童手表端收藏免费听歌逻辑逆向分析与实现

本项目对网易云音乐手表端/儿童手表版（包名为 `com.netease.xtc.cloudmusic`，具体版本 `2.9.105`）进行了深度的 DEX 反编译和逆向分析。通过分析，完整梳理了其专属的**红心收藏免费听歌**机制、EAPI 协议加密签名逻辑，并使用 Python 进行了完整还原。

有关反编译的 Java 核心代码，已按原始包名路径归档于仓库的 `source` 文件夹。

---

## 核心逆向成果分析

### 1. 收藏免费听歌（PLAY_LIST_STAR 86）机制

在手表版应用中，普通歌曲播放会受到会员或数字专辑购买限制，但在特定“试听/试玩”场景下，服务器会对特定客户端开放完整的免费播放特权。

通过分析 `com.netease.cloudmusic.utils.FreeTrialPrivilegeUtils` 和 `com.netease.cloudmusic.meta.virtual.freetrial.FreeTrialScene`，我们发现了以下关键逻辑：
- 当一首歌属于用户的“红心收藏歌单”（通过 `FreeTrialPrivilegeUtils.isMyStarPL()` 方法判定）时，客户端会将当前的播放场景认定为红心歌单场景。
- 对应的播放场景定义为：`FreeTrialScene.PLAY_LIST_STAR`，其整型数值（`trialMode`）为 **`86`**，服务器对应的标识 Key 为 `"star"`。
- 当客户端发起播放请求 `/api/song/enhance/player/url/v1` 时，如果传入参数 `trialMode=86`，网易云服务端对应该穿戴客户端的网关会解除普通歌曲的 VIP 试听限制，直接下发标准的 AAC/MP3 完整播放地址。

**核心检测条件**：
1. **是否需要登录**：是。必须登录（支持匿名游客登录，或手机号验证码登录），以确定当前用户的 `userId` 和红心歌单关联。
2. **红心收藏关联**：歌曲必须首先被该用户添加到红心歌单中（调用 `/api/song/like` 接口），否则服务端在校验 `trialMode=86` 时会因为在用户的红心歌单里找不到该歌曲而拒绝下发完整 URL。

---

### 2. 穿戴端网关与 EAPI 协议加密算法

儿童手表端的所有 API 调用（除部分特定流媒体、埋点外）均采用标准的网易云穿戴端 **EAPI（Encrypt API）** 协议进行网络传输：
- **请求地址改写**：HTTP 请求发送到 `https://interface3.music.163.com/eapi/...`。
- **签名路径改写**：进行加密签名和计算 MD5 校验和时，必须使用原始 `/api/...` 路径，而不是网络请求的 `/eapi/...` 路径。
- **AES 加密**：
  - 加密模式：**AES-128-ECB** 模式。
  - 加密 Key：静态密钥 **`e82ckenh8dichen8`**。
  - 填充方式：PKCS7 填充。
- **签名 Salt 结构**：
  - 签名盐值格式：`nobody{url}use{text}md5forencrypt`
  - 将上述格式中的 `{url}` 替换为 `/api/` 格式的路径，`{text}` 替换为待发送参数 JSON 字符串。
  - 对拼接好的盐值字符串进行 `MD5` 32位小写哈希，得到 `{digest}`。
- **最终加密内容拼接**：
  - 拼接格式：`{url}-36cd479b6b5-{text}-36cd479b6b5-{digest}`
  - 对拼接后的密文进行 AES-128-ECB 加密，并转换为**大写 16 进制字符串**。
  - 作为 HTTP POST 的 Form 参数 `params` 发送。

**响应解密**：
- 网易云 EAPI 服务器下发的也是经过 AES-128-ECB 加密后的原始二进制数据。
- 客户端通过 `deserialdata` 等 native 接口，使用相同的密钥 `e82ckenh8dichen8` 配合 PKCS7 反填充算法进行解密，得到最终的明文 JSON。

---

## 逆向解析的接口明细

所有接口均已在 Python 客户端中实现，并遵循 EAPI 协议打包：

### 1. 匿名游客登录 (Anonymous Login)
- **URL**: `/eapi/login/anon/device`
- **参数**: 无特殊参数，Payload 仅需包含 `"header": "{}"`。
- **作用**: 自动向网易云服务器注册并分配一个临时的穿戴端 `userId`，并在 `Set-Cookie` 中返回后续请求必不可少的 `MUSIC_U` 等 Session Cookie。

### 2. 发送手机验证码 (Send SMS Captcha)
- **URL**: `/eapi/sms/captcha/sent`
- **参数**: 
  - `cellphone`: 手机号
  - `ctcode`: 国家代码（默认 `"86"`）
  - `secrete`: 穿戴端专属密钥 `"music_middleuser_andrwarelogin"`

### 3. 手机号验证码登录 (Cellphone Login)
- **URL**: `/eapi/login/cellphone`
- **参数**:
  - `phone`: 手机号
  - `captcha`: 收到的 4 位或 6 位验证码
  - `countrycode`: 国家代码（默认 `"86"`）

### 4. 添加歌曲到红心歌单 (Like/Favorite Song)
- **URL**: `/eapi/song/like`
- **参数**:
  - `like`: `"true"` (表示收藏) / `"false"` (表示取消收藏)
  - `trackId`: 歌曲 ID
  - `userid`: 当前登录的 `userId`

### 5. 获取播放地址 (Get Playback URL)
- **URL**: `/eapi/song/enhance/player/url/v1`
- **参数**:
  - `ids`: 格式为 `["{song_id}_0"]`（例如 `["123456_0"]`）
  - `level`: 采样等级（默认 `"standard"`）
  - `encodeType`: 编码格式（默认 `"aac"`）
  - `trialMode`: 免费试听场景值 **`"86"`**

---

## 使用指南 (Quick Start)

我们提供了一步到位的自动化客户端脚本 `result/netease_free_trial.py`。

### 依赖安装

在使用前，请确保安装了运行所需的 Python 库：
```bash
pip install requests pycryptodome
```

### 1. 匿名一步到位模式 (推荐，全自动)

直接运行脚本，输入你想要听的歌曲 ID。脚本将：
1. 自动执行**匿名游客登录**，获取临时账户和 Cookie。
2. 自动将目标歌曲**添加至该临时账户的红心歌单**，以满足特权条件。
3. 自动传入 `trialMode=86` 发起播放请求。
4. 解密并输出高保真歌曲播放直链。

```bash
python3 result/netease_free_trial.py
```
**交互过程示例**：
```
[*] Initiating Anonymous Device Login...
[+] Guest Login Succeeded! Temp UID: 2048910482
[?] Enter song ID to fetch: 1813926551
[*] Adding song 1813926551 to favorites to satisfy PLAY_LIST_STAR condition...
[+] Successfully added to red heart favorites!
[*] Retrieving play URL with trialMode=86 for song 1813926551...

==================================================
REVERSE-ENGINEERING RESULTS:
==================================================
TRACK ID:   1813926551
PLAY URL:   https://m801.music.126.net/20260920102030/.../1813926551.aac
SIZE:       3214562 bytes
BR:         128000 kbps
TYPE:       aac
FREETRIAL:  None
==================================================
```

### 2. 手机登录与后期自动清理红心模式

如果你想在自己的真实账号上使用：
1. 传入 `--phone` 发送验证码：
   ```bash
   python3 result/netease_free_trial.py --phone 13800000000 --send-code
   ```
2. 输入验证码登录，获取歌曲，并在获取成功后自动将红心取消，保持歌单干净：
   ```bash
   python3 result/netease_free_trial.py --phone 13800000000 --captcha 1234 --song 1813926551 --unlike-after
   ```

### 命令行参数说明

| 参数项 | 说明 |
| :--- | :--- |
| `--song <ID>` | 目标歌曲的网易云歌曲 ID |
| `--phone <PHONE>` | 手机登录账号 |
| `--captcha <CODE>` | 手机登录收到的验证码 |
| `--send-code` | 发送手机验证码并退出 |
| `--no-like` | 跳过自动加红心步骤（若歌曲本就在红心中可使用） |
| `--unlike-after` | 成功获取播放直链后，自动将红心取消（保持歌单整洁） |

---

## 归档的 Java 源码参考 (`source` 目录)

以下为反编译出的核心 Java 类，可用作原理核对和扩展开发：
- `source/com/netease/cloudmusic/meta/virtual/freetrial/FreeTrialScene.java`: 定义了包含 `PLAY_LIST_STAR(86, "【手表】红心歌单", "star")` 的场景字典。
- `source/com/netease/cloudmusic/utils/FreeTrialPrivilegeUtils.java`: 包含判断歌曲是否满足试听、确定对应 trialMode 场景的客户端判定类。
- `source/com/netease/cloudmusic/module/playlist/PlaylistNoPrivilegeManager.java`: 手表端针对无播放特权歌曲在红心试听逻辑下的 UI 展示管理类。
- `source/com/netease/cloudmusic/c/a/a.java`: 实现核心网络请求生成、喜欢歌曲（`/api/song/like`）及播放直链（`/api/song/enhance/player/url/v1`）等 API 的拼装。
- `source/com/netease/cloudmusic/utils/NeteaseMusicUtils.java`: EAPI 底层加密 (`serialdata`) 及响应解密 (`deserialdata`) 的 native 方法声明。
- `source/com/netease/cloudmusic/wear/watch/account/WatchLoginApi.java`: 手表端设备匿名快速登录实现类。
- `source/com/netease/cloudmusic/wear/watch/login/captcha/WatchCaptchaLoginApi.java`: 手表端验证码发送、手机号验证登录、账号注册等逻辑。

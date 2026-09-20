package com.netease.cloudmusic.c.a;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Pair;
import androidx.collection.LongSparseArray;
import androidx.core.app.NotificationCompat;
import com.alibaba.fastjson.JSON;
import com.netease.cg.center.sdk.GameJsonKeys;
import com.netease.cloudmusic.NeteaseMusicApplication;
import com.netease.cloudmusic.asynctask.UpdateProfileTask;
import com.netease.cloudmusic.asynctask.UploadImageTask;
import com.netease.cloudmusic.asynctask.l;
import com.netease.cloudmusic.c.a;
import com.netease.cloudmusic.d;
import com.netease.cloudmusic.e;
import com.netease.cloudmusic.m.h;
import com.netease.cloudmusic.media.player.HttpStatusCode;
import com.netease.cloudmusic.meta.Account;
import com.netease.cloudmusic.meta.Album;
import com.netease.cloudmusic.meta.Artist;
import com.netease.cloudmusic.meta.IVideoAndMvResource;
import com.netease.cloudmusic.meta.LyricEntryInfo;
import com.netease.cloudmusic.meta.LyricInfo;
import com.netease.cloudmusic.meta.MusicInfo;
import com.netease.cloudmusic.meta.MusicRewardInfo;
import com.netease.cloudmusic.meta.PageValue;
import com.netease.cloudmusic.meta.PendantData;
import com.netease.cloudmusic.meta.PlayList;
import com.netease.cloudmusic.meta.PlayerSongShareInfo;
import com.netease.cloudmusic.meta.PlayerZoneEntryInfo;
import com.netease.cloudmusic.meta.PopUpListDialogData;
import com.netease.cloudmusic.meta.PopUpResultDialogData;
import com.netease.cloudmusic.meta.PrivateCloudSong;
import com.netease.cloudmusic.meta.Profile;
import com.netease.cloudmusic.meta.Program;
import com.netease.cloudmusic.meta.Radio;
import com.netease.cloudmusic.meta.ResExposureReq;
import com.netease.cloudmusic.meta.SongRelatedVideo;
import com.netease.cloudmusic.meta.UserPoint;
import com.netease.cloudmusic.meta.VipHint;
import com.netease.cloudmusic.meta.XiaoIceProgram;
import com.netease.cloudmusic.meta.virtual.AIPlayListMusicInfo;
import com.netease.cloudmusic.meta.virtual.LivingStatus;
import com.netease.cloudmusic.meta.virtual.MVUrlInfo;
import com.netease.cloudmusic.meta.virtual.MainDrawerConfig;
import com.netease.cloudmusic.meta.virtual.MusicExtraInfo;
import com.netease.cloudmusic.meta.virtual.MvPrivilege;
import com.netease.cloudmusic.meta.virtual.ProfileAuthType;
import com.netease.cloudmusic.meta.virtual.ProgramPlayRecord;
import com.netease.cloudmusic.meta.virtual.SongPrivilege;
import com.netease.cloudmusic.meta.virtual.SongUrlInfo;
import com.netease.cloudmusic.meta.virtual.UserPrivilege;
import com.netease.cloudmusic.meta.virtual.freetrial.FreeTrialPrivilege;
import com.netease.cloudmusic.meta.virtual.freetrial.FreeTrialScene;
import com.netease.cloudmusic.meta.virtual.profile.ProfilePlaylist;
import com.netease.cloudmusic.meta.virtual.programdetail.DjProgramH5;
import com.netease.cloudmusic.meta.virtual.programdetail.meta.DJDisplayUnit;
import com.netease.cloudmusic.module.player.e.n;
import com.netease.cloudmusic.module.satimode.meta.SatiScene;
import com.netease.cloudmusic.module.transfer.upload.a.b;
import com.netease.cloudmusic.network.b.j;
import com.netease.cloudmusic.network.c;
import com.netease.cloudmusic.network.exception.CMNetworkIOException;
import com.netease.cloudmusic.network.exception.i;
import com.netease.cloudmusic.plugin.Plugin;
import com.netease.cloudmusic.service.LocalMusicMatchService;
import com.netease.cloudmusic.service.PlayService;
import com.netease.cloudmusic.service.ServiceConst;
import com.netease.cloudmusic.service.upgrade.UpgradeManager;
import com.netease.cloudmusic.utils.BASE64Encoder;
import com.netease.cloudmusic.utils.FreeTrialPrivilegeUtils;
import com.netease.cloudmusic.utils.ai;
import com.netease.cloudmusic.utils.bf;
import com.netease.cloudmusic.utils.bg;
import com.netease.cloudmusic.utils.bh;
import com.netease.cloudmusic.utils.bo;
import com.netease.cloudmusic.utils.bp;
import com.netease.cloudmusic.utils.bt;
import com.netease.cloudmusic.wear.watch.player.fmmodescene.WatchFMModeManager;
import com.netease.cloudmusic.wear.watch.setting.rating.WatchAgeRatingHelper;
import com.netease.cloudmusic.wear.watch.task.util.TaskCenterHelper;
import com.netease.xtc.cloudmusic.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.xtc.shareapi.share.constant.OpenApiConstant;
import io.agora.rtc.Constants;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ProGuard */
/* loaded from: classes.dex */
public class a implements com.netease.cloudmusic.c.a {

    /* renamed from: a, reason: collision with root package name */
    private static com.netease.cloudmusic.c.a f2110a = null;

    /* renamed from: b, reason: collision with root package name */
    private static final String f2111b = "com.netease.cloudmusic.c.a.a";

    private a() {
    }

    private long a(Bitmap bitmap) {
        File a2 = a(bitmap, false);
        if (a2 != null) {
            return b.a(a2, ServiceConst.IMAGE_SERVICE, "image/jpeg", false);
        }
        return 0L;
    }

    public static LongSparseArray<SongPrivilege> a(JSONArray jSONArray, LongSparseArray<SongPrivilege> longSparseArray) throws JSONException {
        if (longSparseArray == null) {
            return longSparseArray;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            SongPrivilege a2 = a(jSONArray.getJSONObject(i));
            longSparseArray.put(a2.getId(), a2);
        }
        return longSparseArray;
    }

    private UploadImageTask.Result a(Bitmap bitmap, String str, String str2, HashMap<String, String> hashMap) {
        return a(str, str2, hashMap, a(bitmap));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private UploadImageTask.Result a(String str, String str2, HashMap<String, String> hashMap, long j) {
        if (j <= 0) {
            return new UploadImageTask.Result();
        }
        return (UploadImageTask.Result) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a(str).c(str2, j + "")).a(hashMap)).a(new j<UploadImageTask.Result>() { // from class: com.netease.cloudmusic.c.a.a.9
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public UploadImageTask.Result parse(JSONObject jSONObject) throws JSONException {
                String string = (jSONObject.optInt("code") == 200) & (true ^ jSONObject.isNull("url")) ? jSONObject.getString("url") : "";
                String string2 = !jSONObject.isNull("message") ? jSONObject.getString("message") : null;
                UploadImageTask.Result result = new UploadImageTask.Result();
                result.newUrl = string;
                result.message = string2;
                return result;
            }
        }, 405);
    }

    public static <T extends PlayList> T a(JSONObject jSONObject, boolean z, LinkedHashMap<Long, MusicExtraInfo> linkedHashMap, long j, Class<T> cls) throws JSONException {
        try {
            T newInstance = cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            newInstance.setId(jSONObject.optLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, Long.MIN_VALUE));
            newInstance.setName(jSONObject.optString("name", ""));
            if (jSONObject.isNull("creator")) {
                newInstance.setAnonimous(true);
                newInstance.getCreateUser().setNickname(NeteaseMusicApplication.a().getString(R.string.ahm));
                newInstance.getCreateUser().setUserId(jSONObject.getLong("userId"));
            } else {
                newInstance.setCreateUser(c(jSONObject.getJSONObject("creator")));
            }
            newInstance.setSubscribed(Boolean.valueOf(jSONObject.optBoolean("subscribed", false) && !newInstance.isMyCreatePl()));
            if (!jSONObject.isNull("subscribers")) {
                newInstance.setSubscribers(b(jSONObject.optJSONArray("subscribers")));
            }
            if (!jSONObject.isNull("backgroundCoverUrl")) {
                newInstance.setBackgroundCoverUrl(jSONObject.getString("backgroundCoverUrl"));
            }
            newInstance.setBookedCount(jSONObject.optInt("subscribedCount", 0));
            newInstance.setCommentCount(jSONObject.optInt("commentCount", 0));
            newInstance.setShareCount(jSONObject.optInt("shareCount", 0));
            newInstance.setCoverUrl(jSONObject.optString("coverImgUrl", ""));
            newInstance.setPlayCount(Math.max(jSONObject.optInt("playCount", 0), jSONObject.optInt("playedCount", 0)));
            newInstance.setTrackUpdateTime(jSONObject.optLong("trackUpdateTime", Long.MIN_VALUE));
            newInstance.setTrackNumberUpdateTime(jSONObject.optLong("trackNumberUpdateTime", 0L));
            newInstance.setUpdateTime(jSONObject.optLong("updateTime", Long.MIN_VALUE));
            newInstance.setSpecialType(jSONObject.optInt("specialType"));
            if (!jSONObject.isNull("highQuality")) {
                try {
                    newInstance.setHighQuality(jSONObject.getBoolean("highQuality"));
                } catch (JSONException unused) {
                    newInstance.setHighQuality(jSONObject.getInt("highQuality") == 1);
                }
            }
            if (!jSONObject.isNull("opRecommend")) {
                newInstance.setOpRecommend(jSONObject.getBoolean("opRecommend"));
            }
            if (!jSONObject.isNull("coverImgId")) {
                newInstance.setCoverDocId(jSONObject.optLong("coverImgId"));
            }
            if (!jSONObject.isNull("adType")) {
                newInstance.setAdType(jSONObject.getInt("adType"));
            }
            if (z && !jSONObject.isNull("trackIds") && j != newInstance.getTrackUpdateTime()) {
                a(linkedHashMap, newInstance.getTrackInfoMaps(), newInstance.getSpecialType() == 10, jSONObject.getJSONArray("trackIds"));
            }
            if (z) {
                newInstance.setMusicCount(jSONObject.optInt("trackCount", 0) + (linkedHashMap != null ? linkedHashMap.size() : 0) + (newInstance.isMyCreatePl() ? jSONObject.optInt("cloudTrackCount", 0) : 0));
            } else {
                newInstance.setMusicCount(jSONObject.optInt("trackCount", 0) + (newInstance.isMyCreatePl() ? jSONObject.optInt("cloudTrackCount", 0) : 0));
            }
            if (!jSONObject.isNull("tracks")) {
                newInstance.setMusics(!z ? c(jSONObject.getJSONArray("tracks")) : a(jSONObject.getJSONArray("tracks")));
            }
            if (!jSONObject.isNull("description")) {
                newInstance.setDescription(jSONObject.getString("description"));
            }
            if (!jSONObject.isNull("commentThreadId")) {
                newInstance.setThreadId(jSONObject.getString("commentThreadId"));
            }
            if (!jSONObject.isNull("updateFrequency")) {
                newInstance.setUpdateFrequency(jSONObject.getString("updateFrequency"));
            }
            newInstance.setStatus(jSONObject.optInt(NotificationCompat.CATEGORY_STATUS, 0));
            JSONArray optJSONArray = jSONObject.optJSONArray("tags");
            if (optJSONArray != null && optJSONArray.length() != 0) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    arrayList.add((String) optJSONArray.get(i));
                }
                newInstance.setTags(arrayList);
            }
            if (!jSONObject.isNull("alg")) {
                newInstance.setAlg(jSONObject.getString("alg"));
            }
            if (!jSONObject.isNull("scm")) {
                newInstance.setScm(jSONObject.getString("scm"));
            }
            if (!jSONObject.isNull("ToplistType")) {
                newInstance.setBillboardType(jSONObject.optString("ToplistType", ""));
            }
            if (!jSONObject.isNull("privacy")) {
                newInstance.setPrivacy(jSONObject.getInt("privacy"));
            }
            if (!jSONObject.isNull("officialTags")) {
                JSONArray jSONArray = jSONObject.getJSONArray("officialTags");
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    if (!jSONArray.isNull(i2)) {
                        String string = jSONArray.getString(i2);
                        if (string.length() > 10) {
                            string = string.substring(0, 10);
                        }
                        arrayList2.add(string);
                    }
                }
                newInstance.setOfficialTags(arrayList2);
            }
            if (!jSONObject.isNull("trialMode")) {
                newInstance.setTrialMode(jSONObject.optInt("trialMode"));
            }
            return newInstance;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.b("");
        } catch (InstantiationException e3) {
            e3.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.b("");
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.b("");
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.b("");
        }
    }

    public static Profile a(JSONObject jSONObject, boolean z) throws JSONException {
        Profile profile = new Profile();
        if (jSONObject == null) {
            return profile;
        }
        if (z) {
            profile.setCelebrities(true);
        } else {
            profile.setCelebrities(false);
        }
        if (!jSONObject.isNull("follows")) {
            profile.setFollows(jSONObject.getInt("follows"));
        }
        if (!jSONObject.isNull("newFollows")) {
            profile.setNewFollows(jSONObject.getLong("newFollows"));
        }
        if (!jSONObject.isNull("defaultAvatar")) {
            profile.setDefaultAvatar(jSONObject.getBoolean("defaultAvatar"));
        }
        if (!jSONObject.isNull("followeds")) {
            profile.setFolloweds(jSONObject.getInt("followeds"));
        } else if (!jSONObject.isNull("followedCount")) {
            profile.setFolloweds(jSONObject.getInt("followedCount"));
        }
        if (!jSONObject.isNull("playlistCount")) {
            profile.setPlaylist(jSONObject.getInt("playlistCount"));
        }
        if (!jSONObject.isNull("eventCount")) {
            profile.setTrackCount(jSONObject.getInt("eventCount"));
        }
        if (!jSONObject.isNull("score")) {
            profile.setScore(jSONObject.getInt("score"));
        }
        profile.setLastRank(jSONObject.isNull("lastRank") ? -1 : jSONObject.getInt("lastRank"));
        if (!jSONObject.isNull("alg")) {
            profile.setAlg(jSONObject.getString("alg"));
        }
        if (!jSONObject.isNull("djStatus")) {
            profile.setDjStatus(jSONObject.getInt("djStatus"));
        }
        profile.setMutual(jSONObject.optBoolean("mutual"));
        profile.setArtistId(jSONObject.optLong("artistId"));
        profile.setUserId(jSONObject.optLong("userId", Long.MIN_VALUE));
        profile.setUserType(jSONObject.optInt("userType", Integer.MIN_VALUE));
        if (!jSONObject.isNull("nickname")) {
            profile.setNickname(jSONObject.optString("nickname", ""));
        } else if (jSONObject.isNull("nickName")) {
            profile.setNickname("");
        } else {
            profile.setNickname(jSONObject.optString("nickName", ""));
        }
        if (jSONObject.isNull("remarkName")) {
            profile.setAlias("");
        } else {
            profile.setAlias(jSONObject.optString("remarkName", ""));
        }
        profile.setAvatarImgId(jSONObject.optLong("avatarImgId"));
        if (!jSONObject.isNull("signature")) {
            profile.setSignature(jSONObject.getString("signature"));
        }
        if (!jSONObject.isNull("detailDescription")) {
            profile.setDetailDesc(jSONObject.getString("detailDescription"));
        }
        profile.setUserName(jSONObject.optString("userName", ""));
        profile.setLastLoginTime(jSONObject.optLong("lastLoginTime", Long.MIN_VALUE));
        profile.setBirthday(jSONObject.optLong("birthday", Profile.BIRTHDAY_NOTSET_VALUE));
        profile.setGender(jSONObject.optInt("gender", 0));
        profile.setAccountStatus(jSONObject.optInt("accountStatus", Integer.MIN_VALUE));
        profile.setAvatarUrl(jSONObject.optString("avatarUrl", ""));
        profile.setFollowing(jSONObject.optBoolean("followed"));
        profile.setUrlAnalyze(jSONObject.optBoolean("urlAnalyze"));
        profile.setProvince(jSONObject.optInt("province", Integer.MIN_VALUE));
        profile.setCity(jSONObject.optInt("city", Integer.MIN_VALUE));
        profile.setAuthStatus(jSONObject.optInt("authStatus", 0));
        if (!jSONObject.isNull("description")) {
            profile.setDesc(jSONObject.getString("description"));
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("tags");
        if (optJSONArray != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < optJSONArray.length(); i++) {
                stringBuffer.append(optJSONArray.getString(i) + PlayService.SEP);
            }
            profile.setTags(stringBuffer.toString());
        }
        JSONArray optJSONArray2 = jSONObject.optJSONArray("expertTags");
        if (optJSONArray2 != null && optJSONArray2.length() > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                arrayList.add(optJSONArray2.getString(i2));
            }
            profile.setExpertTags(arrayList);
        }
        if (!jSONObject.isNull("experts")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("experts");
            String optString = jSONObject2.optString("1");
            String optString2 = jSONObject2.optString(OpenApiConstant.XTCShareSocialVideoType.INSTAGRAM);
            if (bt.a(optString)) {
                profile.setExpertsInfo(1, optString);
            }
            if (bt.a(optString2)) {
                profile.setExpertsInfo(2, optString2);
            }
        }
        if (!jSONObject.isNull("mainAuthType") && (jSONObject.get("mainAuthType") instanceof JSONObject)) {
            profile.setMainAuthType(ProfileAuthType.fromJson(jSONObject.getJSONObject("mainAuthType")));
        }
        if (!jSONObject.isNull("allAuthTypes")) {
            profile.setAllAuthTypes(ProfileAuthType.fromJsons(jSONObject.getJSONArray("allAuthTypes")));
        }
        if (!jSONObject.isNull("listenedSongs")) {
            profile.setListernedSongs(c(jSONObject.getJSONArray("listenedSongs")));
        }
        if (!jSONObject.isNull("eventCount")) {
            profile.setEventCount(jSONObject.getLong("eventCount"));
        }
        if (!jSONObject.isNull("followTime")) {
            profile.setFollowTime(jSONObject.getString("followTime"));
        }
        profile.setPinyin(jSONObject.optString("py", ""));
        profile.setTime(jSONObject.optLong(com.netease.mam.agent.d.d.a.dJ, Long.MIN_VALUE));
        if (!jSONObject.isNull("backgroundUrl")) {
            profile.setProfileBgUrl(jSONObject.optString("backgroundUrl"));
        }
        if (!jSONObject.isNull("blacklist")) {
            profile.setInBlacklist(jSONObject.getBoolean("blacklist"));
        }
        if (!jSONObject.isNull("locationInfo")) {
            profile.setLocation(jSONObject.getString("locationInfo"));
        }
        if (!jSONObject.isNull("canReward")) {
            boolean z2 = jSONObject.getBoolean("canReward");
            profile.setCanReward(z2);
            if (z2) {
                profile.setRewardCount(jSONObject.getLong("rewardCount"));
            }
        }
        if (!jSONObject.isNull("artistName")) {
            profile.setArtistName(jSONObject.getString("artistName"));
        }
        if (!jSONObject.isNull("vipRights")) {
            profile.setUserPrivilege(UserPrivilege.fromJsonForProfileList(jSONObject.getJSONObject("vipRights"), profile.getUserId()));
        }
        if (!jSONObject.isNull("rcmdReason")) {
            profile.setRcmdReason(jSONObject.getString("rcmdReason"));
        }
        if (!jSONObject.isNull("liveInfo")) {
            profile.setLivingStatus(LivingStatus.fromJson(jSONObject.optJSONObject("liveInfo")));
        }
        if (!jSONObject.isNull("fansSize")) {
            profile.setFansSize(jSONObject.getInt("fansSize"));
        }
        if (!jSONObject.isNull("relationTag")) {
            profile.setRelationTag(jSONObject.getString("relationTag"));
        }
        profile.setPendantData(PendantData.fromJson(jSONObject));
        return profile;
    }

    public static SongPrivilege a(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return SongPrivilege.getDefualtSongPrivilege(SongPrivilege.Type.MOCK_DEFAULT_SERVER);
        }
        SongPrivilege songPrivilege = new SongPrivilege();
        songPrivilege.setId(jSONObject.optLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID));
        songPrivilege.setFee(jSONObject.optInt("fee"));
        songPrivilege.setPayed(jSONObject.optInt("payed"));
        songPrivilege.setToast(jSONObject.optBoolean("toast"));
        songPrivilege.setOfflinestatus(jSONObject.optInt("st"));
        songPrivilege.setPlayMaxLevel(jSONObject.optInt("pl"));
        songPrivilege.setDownMaxLevel(jSONObject.optInt("dl"));
        songPrivilege.setSharePriv(jSONObject.optInt("sp"));
        songPrivilege.setCommentPriv(jSONObject.optInt("cp"));
        songPrivilege.setSubPriv(jSONObject.optInt("subp"));
        songPrivilege.setId(jSONObject.optLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID));
        songPrivilege.setMaxbr(jSONObject.optInt("maxbr"));
        songPrivilege.setFreeLevel(jSONObject.optInt("fl"));
        songPrivilege.setFlag(jSONObject.optInt("flag"));
        songPrivilege.setSpTypeForLog("SERVER");
        songPrivilege.setNow(System.currentTimeMillis());
        if (!jSONObject.isNull("cs")) {
            songPrivilege.setCloudSong(jSONObject.getBoolean("cs"));
        }
        if (!jSONObject.isNull("playMaxbr")) {
            songPrivilege.setPlayMaxbr(jSONObject.getInt("playMaxbr"));
        }
        if (!jSONObject.isNull("downloadMaxbr")) {
            songPrivilege.setDownloadMaxbr(jSONObject.getInt("downloadMaxbr"));
        }
        if (!jSONObject.isNull("freeTrialPrivilege")) {
            songPrivilege.setFreeTrialPrivilege(b(jSONObject.getJSONObject("freeTrialPrivilege")));
        }
        return songPrivilege;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.io.File a(android.graphics.Bitmap r6, boolean r7) {
        /*
            r5 = this;
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            java.lang.String r2 = com.netease.cloudmusic.e.ah     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            r2 = 1
            com.netease.cloudmusic.utils.y.a(r1, r2)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            r2.<init>()     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            java.lang.String r3 = "crop_"
            r2.append(r3)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            long r3 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            r2.append(r3)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            java.lang.String r3 = ".tmp"
            java.io.File r1 = java.io.File.createTempFile(r2, r3, r1)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L41 java.io.IOException -> L45
            r3 = 1048576(0x100000, double:5.180654E-318)
            byte[] r3 = com.netease.cloudmusic.utils.h.a(r6, r3)     // Catch: java.io.IOException -> L3f java.lang.Throwable -> L53
            r2.write(r3)     // Catch: java.io.IOException -> L3f java.lang.Throwable -> L53
            com.netease.cloudmusic.utils.ah.a(r2)
            if (r7 == 0) goto L3e
            r6.recycle()
        L3e:
            return r1
        L3f:
            r1 = move-exception
            goto L47
        L41:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L54
        L45:
            r1 = move-exception
            r2 = r0
        L47:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L53
            com.netease.cloudmusic.utils.ah.a(r2)
            if (r7 == 0) goto L52
            r6.recycle()
        L52:
            return r0
        L53:
            r0 = move-exception
        L54:
            com.netease.cloudmusic.utils.ah.a(r2)
            if (r7 == 0) goto L5c
            r6.recycle()
        L5c:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.cloudmusic.c.a.a.a(android.graphics.Bitmap, boolean):java.io.File");
    }

    private ArrayList<ProgramPlayRecord> a(String str, JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2;
        if (str == null || (jSONObject2 = jSONObject.getJSONObject(str)) == null || jSONObject2.getInt("code") != 200 || jSONObject2.getJSONObject("data") == null) {
            return null;
        }
        return h(jSONObject2.getJSONObject("data").getJSONArray("list"));
    }

    public static List<MusicInfo> a(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (jSONArray != null && jSONArray.length() != 0) {
            for (int i = 0; i < jSONArray.length(); i++) {
                MusicInfo l = l(jSONArray.optJSONObject(i));
                if (l != null) {
                    arrayList.add(l);
                }
            }
        }
        return arrayList;
    }

    public static <T extends PlayList> List<T> a(JSONArray jSONArray, Class<T> cls) throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (jSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(a(jSONArray.getJSONObject(i), false, (LinkedHashMap<Long, MusicExtraInfo>) null, 0L, (Class) cls));
        }
        return arrayList;
    }

    private static List<Profile> a(JSONArray jSONArray, boolean z) throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (jSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(a(jSONArray.getJSONObject(i), z));
        }
        return arrayList;
    }

    public static Map<Long, SongPrivilege> a(JSONArray jSONArray, Map<Long, SongPrivilege> map) throws JSONException {
        if (map == null) {
            return map;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            SongPrivilege a2 = a(jSONArray.getJSONObject(i));
            map.put(Long.valueOf(a2.getId()), a2);
        }
        return map;
    }

    private void a(int i, String str) {
        com.netease.cloudmusic.network.i.d.a.b(i, str);
    }

    private void a(int i, String str, String str2) {
        com.netease.cloudmusic.network.i.d.a.a(i, str, str2);
    }

    private void a(Profile profile, JSONObject jSONObject, MainDrawerConfig mainDrawerConfig) {
        try {
            profile.setLevel(jSONObject.optInt("level", 0));
            profile.setMobileSign(jSONObject.optBoolean("mobileSign", false));
            if (!jSONObject.isNull("userPoint")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("userPoint");
                UserPoint userPoint = new UserPoint();
                userPoint.setBalance(jSONObject2.optLong("balance", 0L));
                userPoint.setUserId(jSONObject2.optLong("userId", 0L));
                userPoint.setUpdateTime(jSONObject2.optLong("updateTime", 0L));
                userPoint.setVersion(jSONObject2.optInt("version", 0));
                userPoint.setStatus(jSONObject2.optInt(NotificationCompat.CATEGORY_STATUS, 0));
                userPoint.setBlockBalance(jSONObject2.optLong("blockBalance", 0L));
                profile.setUserPoint(userPoint);
            }
            if (mainDrawerConfig != null) {
                mainDrawerConfig.setConfigFromJson(jSONObject);
            }
            bp.a(!jSONObject.isNull("storeTitle") ? jSONObject.getString("storeTitle") : "");
        } catch (JSONException unused) {
        }
    }

    private static void a(LinkedHashMap<Long, MusicExtraInfo> linkedHashMap, LinkedHashMap<Long, MusicExtraInfo> linkedHashMap2, boolean z, JSONArray jSONArray) throws JSONException {
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        boolean z2 = linkedHashMap != null && linkedHashMap.size() > 0;
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            MusicExtraInfo musicExtraInfo = new MusicExtraInfo();
            musicExtraInfo.version = jSONObject.optInt("v");
            musicExtraInfo.index = i;
            musicExtraInfo.isLocal = false;
            if (z) {
                if (jSONObject.isNull("ratio")) {
                    musicExtraInfo.lastRank = jSONObject.optInt("lr", -1);
                } else {
                    musicExtraInfo.lastRank = jSONObject.optInt("ratio", 0);
                }
            }
            long optLong = jSONObject.optLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, 0L);
            if (z2) {
                linkedHashMap3.put(Long.valueOf(optLong), musicExtraInfo);
            } else {
                linkedHashMap2.put(Long.valueOf(optLong), musicExtraInfo);
            }
        }
        if (z2) {
            LinkedList linkedList = new LinkedList();
            linkedList.addAll(linkedHashMap3.keySet());
            for (Map.Entry<Long, MusicExtraInfo> entry : linkedHashMap.entrySet()) {
                MusicExtraInfo value = entry.getValue();
                if (!linkedList.contains(entry.getKey())) {
                    linkedList.add(value.index > linkedList.size() ? linkedList.size() : value.index, entry.getKey());
                }
            }
            for (int i2 = 0; i2 < linkedList.size(); i2++) {
                long longValue = ((Long) linkedList.get(i2)).longValue();
                MusicExtraInfo musicExtraInfo2 = linkedHashMap3.containsKey(Long.valueOf(longValue)) ? (MusicExtraInfo) linkedHashMap3.get(Long.valueOf(longValue)) : linkedHashMap.get(Long.valueOf(longValue));
                if (musicExtraInfo2 != null) {
                    musicExtraInfo2.index = i2;
                    linkedHashMap2.put(Long.valueOf(longValue), musicExtraInfo2);
                }
            }
        }
    }

    public static void a(JSONObject jSONObject, MusicInfo musicInfo) {
        if (jSONObject == null || musicInfo == null) {
            return;
        }
        try {
            if (jSONObject.isNull("ftype")) {
                return;
            }
            musicInfo.setFromType(jSONObject.getInt("ftype"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    private void a(JSONObject jSONObject, MainDrawerConfig mainDrawerConfig) {
        if (mainDrawerConfig != null) {
            mainDrawerConfig.getMainDrawerDynamicConfig().setListFromJson(jSONObject);
        }
    }

    private boolean a(List<Long> list, Map<Long, SongPrivilege> map) {
        if (list.size() == 0) {
            return true;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("ids", String.format("[%s]", bt.a(list, ",")));
        try {
            JSONObject jSONObject = new JSONObject(c.a("song/enhance/privilege", hashMap).g());
            if (jSONObject.getInt("code") == 200) {
                a(jSONObject.getJSONArray("data"), map);
                return true;
            }
        } catch (IllegalStateException | JSONException e2) {
            e2.printStackTrace();
        }
        return false;
    }

    private boolean a(boolean z, LyricInfo lyricInfo, JSONObject jSONObject, String str, int i) {
        if (jSONObject == null) {
            return false;
        }
        if (z) {
            z = jSONObject.optInt("version", 0) > i;
            if (z) {
                if ("tlyric".equals(str)) {
                    if (!jSONObject.isNull("lyric")) {
                        lyricInfo.setTranslateLyric(jSONObject.optString("lyric", lyricInfo.getTranslateLyric()));
                        lyricInfo.setTranslateVersion(jSONObject.optInt("version", i));
                    }
                } else if ("lrc".equals(str)) {
                    if (!jSONObject.isNull("lyric")) {
                        lyricInfo.setLyric(jSONObject.optString("lyric", lyricInfo.getLyric()));
                        lyricInfo.setLyricVersion(jSONObject.optInt("version", i));
                    }
                    lyricInfo.setLyricUserOffset(jSONObject.optInt("offset", -1));
                } else if ("klyric".equals(str) && !jSONObject.isNull("lyric")) {
                    lyricInfo.setKalaokLyric(jSONObject.optString("lyric", lyricInfo.getKalaokLyric()));
                    lyricInfo.setKalaokVersion(jSONObject.optInt("version", i));
                }
            }
        }
        if ("lrc".equals(str) && jSONObject.optInt("version", 0) != 0 && jSONObject.optInt("version", 0) == i) {
            lyricInfo.setLyricUserOffset(jSONObject.optInt("offset", -1));
        }
        return z;
    }

    public static Artist b(JSONObject jSONObject, boolean z) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        Artist artist = new Artist();
        artist.setId(jSONObject.getLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID));
        if (!jSONObject.isNull("name")) {
            artist.setName(jSONObject.getString("name"));
        }
        if (z) {
            if (!jSONObject.isNull("picUrl")) {
                artist.setCover(jSONObject.getString("picUrl"));
            }
        } else if (!jSONObject.isNull("img1v1Url")) {
            artist.setCover(jSONObject.getString("img1v1Url"));
        } else if (!jSONObject.isNull("picUrl")) {
            artist.setCover(jSONObject.getString("picUrl"));
        } else if (!jSONObject.isNull("cover")) {
            artist.setCover(jSONObject.getString("cover"));
        }
        if (!jSONObject.isNull("fansCount")) {
            artist.setFansNum(jSONObject.getLong("fansCount"));
        }
        if (!jSONObject.isNull("albumSize")) {
            artist.setAlbumSize(jSONObject.getInt("albumSize"));
        }
        if (!jSONObject.isNull("mvSize")) {
            artist.setMvSize(jSONObject.getInt("mvSize"));
        }
        if (!jSONObject.isNull("reason")) {
            artist.setRecommendReason(jSONObject.getString("reason"));
        }
        if (!jSONObject.isNull("alg")) {
            artist.setAlg(jSONObject.optString("alg"));
        }
        if (!jSONObject.isNull("accountId")) {
            artist.setAccountId(jSONObject.getLong("accountId"));
        }
        if (!jSONObject.isNull("musicSize")) {
            artist.setMusicSize(jSONObject.optInt("musicSize"));
        }
        if (!jSONObject.isNull("desc")) {
            artist.setDesc(jSONObject.optString("desc"));
        }
        if (!jSONObject.isNull("briefDesc")) {
            artist.setBriefDesc(jSONObject.optString("briefDesc"));
        }
        if (!jSONObject.isNull("transNames")) {
            artist.setTransNames(ai.a(jSONObject.getJSONArray("transNames")));
        }
        if (!jSONObject.isNull("identifyTag")) {
            artist.setIdentifyTags(ai.a(jSONObject.getJSONArray("identifyTag")));
        }
        artist.setSubscribed(jSONObject.optBoolean("followed"));
        if (!jSONObject.isNull("subed")) {
            artist.setSubscribed(jSONObject.getBoolean("subed"));
        }
        if (!jSONObject.isNull("identities")) {
            artist.setIdentities(ai.a(jSONObject.getJSONArray("identities")));
        }
        if (!jSONObject.isNull("rank")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("rank");
            Artist.RankBean rankBean = new Artist.RankBean();
            if (!jSONObject2.isNull("rank")) {
                rankBean.setRank(jSONObject2.getInt("rank"));
            }
            if (!jSONObject2.isNull("type")) {
                rankBean.setType(jSONObject2.getInt("type"));
            }
            artist.setRank(rankBean);
        }
        if (!jSONObject.isNull("alias")) {
            ArrayList arrayList = new ArrayList();
            JSONArray optJSONArray = jSONObject.optJSONArray("alias");
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    arrayList.add(jSONObject.getJSONArray("alias").getString(i));
                }
                if (arrayList.size() > 0) {
                    artist.setAlias(arrayList);
                }
            }
        }
        if (!jSONObject.isNull("officialTags")) {
            JSONArray jSONArray = jSONObject.getJSONArray("officialTags");
            artist.setOfficialTags(new ArrayList());
            if (jSONArray != null && jSONArray.length() > 0) {
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    artist.getOfficialTags().add(jSONArray.get(i2).toString());
                }
            }
        }
        if (!jSONObject.isNull("fansSize")) {
            artist.setFansSize(jSONObject.getInt("fansSize"));
        }
        if (!jSONObject.isNull("videoSize")) {
            artist.setVideoSize(jSONObject.getInt("videoSize"));
        }
        return artist;
    }

    public static FreeTrialPrivilege b(JSONObject jSONObject) throws JSONException {
        JSONArray optJSONArray;
        FreeTrialPrivilege freeTrialPrivilege = new FreeTrialPrivilege();
        if (!jSONObject.isNull("resConsumable")) {
            freeTrialPrivilege.setResConsumable(jSONObject.getBoolean("resConsumable"));
        }
        if (!jSONObject.isNull("userConsumable")) {
            freeTrialPrivilege.setUserConsumable(jSONObject.getBoolean("userConsumable"));
        }
        if (!jSONObject.isNull("freeLimitTagType")) {
            freeTrialPrivilege.setFreeLimitTagType(jSONObject.optString("freeLimitTagType"));
        }
        if (!jSONObject.isNull("playReason") && (optJSONArray = jSONObject.optJSONArray("playReason")) != null) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                arrayList.add(optJSONArray.optString(i));
            }
            freeTrialPrivilege.setPlayReason(arrayList);
        }
        return freeTrialPrivilege;
    }

    public static List<Profile> b(JSONArray jSONArray) throws JSONException {
        return a(jSONArray, false);
    }

    public static Profile c(JSONObject jSONObject) throws JSONException {
        return a(jSONObject, false);
    }

    public static ProfilePlaylist c(JSONObject jSONObject, boolean z) throws JSONException {
        ProfilePlaylist profilePlaylist = new ProfilePlaylist();
        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
        profilePlaylist.setMore(jSONObject2.getBoolean("more"));
        profilePlaylist.setCount(jSONObject2.getInt("count"));
        profilePlaylist.setPlaylist(g(jSONObject2.getJSONArray(ResExposureReq.ExposureRecord.RES_POS_PLAYLIST)));
        if (z) {
            profilePlaylist.setSubCount(jSONObject2.getInt("subCount"));
        }
        return profilePlaylist;
    }

    public static List<MusicInfo> c(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (jSONArray != null && jSONArray.length() != 0) {
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(d(jSONArray.getJSONObject(i)));
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.netease.cloudmusic.meta.MusicInfo d(org.json.JSONObject r5) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 436
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.cloudmusic.c.a.a.d(org.json.JSONObject):com.netease.cloudmusic.meta.MusicInfo");
    }

    private void d(int i) {
        c(i);
    }

    public static Radio e(JSONObject jSONObject) throws JSONException {
        JSONObject optJSONObject;
        if (jSONObject == null) {
            return null;
        }
        Radio radio = new Radio();
        radio.setRadioId(jSONObject.optLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID));
        radio.setName(jSONObject.optString("name"));
        if (!jSONObject.isNull("picUrl")) {
            radio.setPicUrl(jSONObject.optString("picUrl"));
        }
        radio.setProgramCount(jSONObject.optInt("programCount"));
        radio.setSubCount(jSONObject.optInt("subCount"));
        radio.setCreateTime(jSONObject.optLong("createTime"));
        radio.setCategoryId(jSONObject.optLong("categoryId"));
        radio.setCategory(jSONObject.optString("category"));
        radio.setLastProgramCreateTime(jSONObject.optLong("lastProgramCreateTime"));
        if (!jSONObject.isNull("lastProgramName")) {
            radio.setLastProgramName(jSONObject.getString("lastProgramName"));
        }
        if (!jSONObject.isNull("creatorName")) {
            radio.setCreatorName(jSONObject.getString("creatorName"));
        }
        if (!jSONObject.isNull("lastRank")) {
            radio.setLastRank(jSONObject.getInt("lastRank"));
        }
        if (!jSONObject.isNull("rank")) {
            radio.setRank(jSONObject.getInt("rank"));
        }
        if (!jSONObject.isNull("score")) {
            radio.setScore(jSONObject.getInt("score"));
        }
        if (!jSONObject.isNull("dj")) {
            radio.setDj(c(jSONObject.getJSONObject("dj")));
        }
        if (!jSONObject.isNull("copywriter")) {
            radio.setCopyWriter(jSONObject.optString("copywriter"));
        }
        if (!jSONObject.isNull("desc")) {
            radio.setDesc(jSONObject.optString("desc"));
        }
        if (!jSONObject.isNull("newProgramCount")) {
            radio.setNewProgramCount(jSONObject.optInt("newProgramCount"));
        }
        if (!jSONObject.isNull("rcmdtext")) {
            radio.setRcmdText(jSONObject.optString("rcmdtext"));
        }
        if (!jSONObject.isNull("rcmdText")) {
            radio.setRcmdText(jSONObject.optString("rcmdText"));
        }
        if (!jSONObject.isNull("playCount")) {
            radio.setPlayCount(jSONObject.optInt("playCount"));
        }
        if (!jSONObject.isNull("subed")) {
            radio.setSubscribed(jSONObject.optBoolean("subed", false));
        }
        if (!jSONObject.isNull("radioFeeType")) {
            radio.setRadioFeeType(jSONObject.optInt("radioFeeType"));
        }
        if (!jSONObject.isNull("feeScope")) {
            radio.setFeeScope(jSONObject.optInt("feeScope"));
        }
        if (!jSONObject.isNull("price")) {
            radio.setPrice(jSONObject.optLong("price"));
        }
        if (!jSONObject.isNull("originalPrice")) {
            radio.setPrice(jSONObject.optLong("originalPrice"));
        }
        if (!jSONObject.isNull("discountPrice")) {
            radio.setVipDiscountPrice(jSONObject.optLong("discountPrice"));
        }
        if (!jSONObject.isNull("videos") && (optJSONObject = jSONObject.optJSONObject("videos")) != null && (!optJSONObject.isNull("720P") || !optJSONObject.isNull("480P"))) {
            HashMap hashMap = new HashMap();
            hashMap.put(720, optJSONObject.optString("720P"));
            hashMap.put(Integer.valueOf(IVideoAndMvResource.Resolution.HIGH), optJSONObject.optString("480P"));
            radio.setVideoBrs(hashMap);
        }
        if (!jSONObject.isNull("purchaseCount")) {
            radio.setPurchaseCount(jSONObject.optLong("purchaseCount"));
        }
        if (!jSONObject.isNull("buyed")) {
            radio.setBuyed(jSONObject.optBoolean("buyed"));
        }
        if (!jSONObject.isNull("underShelf")) {
            radio.setUnderShelf(jSONObject.optBoolean("underShelf"));
        }
        if (!jSONObject.isNull("finished")) {
            radio.setFinished(jSONObject.optBoolean("finished"));
        }
        if (!jSONObject.isNull("feeDesc")) {
            List<DJDisplayUnit> f = f(jSONObject.optJSONArray("feeDesc"));
            if (f.size() > 0) {
                radio.setFeeDesc(f);
            }
        }
        if (!jSONObject.isNull("alg")) {
            radio.setAlg(jSONObject.optString("alg"));
        }
        if (!jSONObject.isNull("scm")) {
            radio.setScm(jSONObject.optString("scm"));
        }
        return radio;
    }

    public static List<Program> e(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (jSONArray != null && jSONArray.length() != 0) {
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(g(jSONArray.getJSONObject(i)));
            }
        }
        return arrayList;
    }

    public static synchronized com.netease.cloudmusic.c.a f() {
        com.netease.cloudmusic.c.a aVar;
        synchronized (a.class) {
            if (f2110a == null) {
                f2110a = new a();
            }
            aVar = f2110a;
        }
        return aVar;
    }

    public static PlayList f(JSONObject jSONObject) throws JSONException {
        return a(jSONObject, false, (LinkedHashMap<Long, MusicExtraInfo>) null, 0L, PlayList.class);
    }

    private static List<DJDisplayUnit> f(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        if (jSONArray != null && jSONArray.length() != 0) {
            for (int i = 0; i < jSONArray.length(); i++) {
                DJDisplayUnit dJDisplayUnit = new DJDisplayUnit();
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (!jSONObject.isNull("videoCoverImgUrl")) {
                    dJDisplayUnit.setType(10);
                    dJDisplayUnit.setContent(jSONObject.getString("videoCoverImgUrl"));
                    dJDisplayUnit.setVideoId(jSONObject.getString("videoEventId"));
                    dJDisplayUnit.setDuration(jSONObject.getInt("duration"));
                    arrayList.add(dJDisplayUnit);
                    DJDisplayUnit dJDisplayUnit2 = new DJDisplayUnit();
                    dJDisplayUnit2.setType(1);
                    dJDisplayUnit2.setContent(jSONObject.optString("text"));
                    arrayList.add(dJDisplayUnit2);
                } else {
                    String optString = jSONObject.optString("content");
                    if (bt.a(optString)) {
                        dJDisplayUnit.setType(jSONObject.getInt("type"));
                        dJDisplayUnit.setContent(optString);
                        dJDisplayUnit.setWidth(jSONObject.optInt(IjkMediaMeta.IJKM_KEY_WIDTH));
                        dJDisplayUnit.setHeight(jSONObject.optInt(IjkMediaMeta.IJKM_KEY_HEIGHT));
                        arrayList.add(dJDisplayUnit);
                    }
                }
            }
        }
        return arrayList;
    }

    public static Program g(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        Program program = new Program();
        program.setCreateTime(jSONObject.optLong("createTime"));
        program.setId(jSONObject.optLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID));
        if (!jSONObject.isNull("programDesc")) {
            program.setProgramDesc(f(jSONObject.optJSONArray("programDesc")));
        }
        if (!jSONObject.isNull("h5Links")) {
            ArrayList arrayList = new ArrayList();
            JSONArray optJSONArray = jSONObject.optJSONArray("h5Links");
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                arrayList.add(new DjProgramH5(jSONObject2.getString(GameJsonKeys.SHARE_TITLE), jSONObject2.getString("url")));
            }
            program.setH5Links(arrayList);
        }
        if (!jSONObject.isNull("programFeeType")) {
            program.setProgramFeeType(jSONObject.getInt("programFeeType"));
        }
        if (!jSONObject.isNull("buyed")) {
            program.setPurchased(jSONObject.getBoolean("buyed"));
        }
        if (!jSONObject.isNull("canReward")) {
            program.setReward(jSONObject.getBoolean("canReward"));
        }
        if (!jSONObject.isNull("name")) {
            program.setName(jSONObject.getString("name"));
        }
        if (!jSONObject.isNull("description")) {
            program.setIntroduction(jSONObject.getString("description"));
        }
        if (!jSONObject.isNull("coverUrl")) {
            program.setCoverUrl(jSONObject.getString("coverUrl"));
        }
        if (!jSONObject.isNull("alg")) {
            program.setAlg(jSONObject.getString("alg"));
        }
        if (!jSONObject.isNull("targetId")) {
            program.setTargetId(jSONObject.getLong("targetId"));
        }
        if (!jSONObject.isNull("score")) {
            program.setScore(jSONObject.getInt("score"));
        }
        if (!jSONObject.isNull("categoryName")) {
            program.setCategoryName(jSONObject.getString("categoryName"));
        }
        if (!jSONObject.isNull("secondCategoryName")) {
            program.setSecondCategoryName(jSONObject.getString("secondCategoryName"));
        }
        program.setCategoryId(jSONObject.optLong("categoryId", 0L));
        program.setSecondCategoryId(jSONObject.optLong("secondCategoryId", 0L));
        program.setSerial(jSONObject.optInt("serialNum"));
        program.setDuration(jSONObject.optLong("duration"));
        program.setListenerCount(jSONObject.optInt("listenerCount"));
        program.setLikedCount(jSONObject.optInt("likedCount", 0));
        program.setTrackCount(jSONObject.optInt("trackCount"));
        program.setPrivacy(jSONObject.optBoolean("privacy", false));
        program.setAuditDisPlayStatus(jSONObject.optInt("auditDisPlayStatus", 0));
        if (!jSONObject.isNull("songs")) {
            program.setMusics(c(jSONObject.getJSONArray("songs")));
        }
        if (!jSONObject.isNull("dj")) {
            program.setDj(c(jSONObject.getJSONObject("dj")));
            if (!jSONObject.getJSONObject("dj").isNull("brand")) {
                program.setBrand(jSONObject.getJSONObject("dj").getString("brand"));
            }
            if (program.getDj() != null && program.getDj().getLivingStatus() == null && !jSONObject.isNull("liveInfo")) {
                program.getDj().setLivingStatus(LivingStatus.fromJson(jSONObject.optJSONObject("liveInfo")));
            }
        }
        if (!jSONObject.isNull("mainSong")) {
            program.setMainSong(d(jSONObject.getJSONObject("mainSong")));
        }
        MusicInfo mainSong = program.getMainSong();
        if (mainSong != null && mainSong.getArtists().size() > 0 && program.getDj() != null) {
            mainSong.getArtists().get(0).setName(program.getDj().getNickname());
            if (program.getDj().getAlias() != null) {
                mainSong.getArtists().get(0).setAlias(Arrays.asList(program.getDj().getAlias()));
            }
            if (bt.a(program.getName())) {
                mainSong.setMusicName(program.getName());
            }
        }
        program.setCommentCount(jSONObject.optInt("commentCount"));
        program.setShareCount(jSONObject.optInt("shareCount"));
        if (!jSONObject.isNull("commentThreadId")) {
            program.setThreadId(jSONObject.optString("commentThreadId"));
        }
        if (!jSONObject.isNull("channels")) {
            JSONArray jSONArray = jSONObject.getJSONArray("channels");
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                arrayList2.add(jSONArray.getString(i2));
            }
            program.setChannels(arrayList2);
        }
        if (!jSONObject.isNull("radio")) {
            program.setRadio(e(jSONObject.getJSONObject("radio")));
        }
        if (!jSONObject.isNull("liked")) {
            program.setLiked(jSONObject.getBoolean("liked"));
        }
        if (!jSONObject.isNull("reason")) {
            program.setReason(jSONObject.optString("reason"));
        }
        if (!jSONObject.isNull("videoInfo")) {
            JSONObject optJSONObject = jSONObject.optJSONObject("videoInfo");
            program.setHasMoreVideo(optJSONObject.optBoolean("moreThanOne"));
            if (!optJSONObject.isNull("video")) {
                program.setRelatedVideoId(optJSONObject.optJSONObject("video").optString(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID));
            }
        }
        if (!jSONObject.isNull("disPlayStatus")) {
            program.setDisPlayStatus(jSONObject.optString("disPlayStatus"));
        }
        if (!jSONObject.isNull("djPlayRecordVo")) {
            program.setProgramPlayRecord(h(jSONObject.getJSONObject("djPlayRecordVo")));
        }
        return program;
    }

    public static List<PopUpResultDialogData> g() {
        SharedPreferences a2 = bh.a("popup_dialog_new", false);
        ArrayList arrayList = new ArrayList();
        try {
            Map<String, ?> all = a2.getAll();
            Iterator<String> it = all.keySet().iterator();
            while (it.hasNext()) {
                PopUpResultDialogData popUpResultDialogData = (PopUpResultDialogData) JSON.parseObject((String) all.get(it.next()), PopUpResultDialogData.class);
                if (popUpResultDialogData != null) {
                    arrayList.add(popUpResultDialogData);
                }
            }
        } catch (RuntimeException unused) {
            a2.edit().clear().commit();
        }
        Collections.sort(arrayList);
        return arrayList.subList(0, arrayList.size() <= 30 ? arrayList.size() : 30);
    }

    private static List<PlayList> g(JSONArray jSONArray) throws JSONException {
        return a(jSONArray, PlayList.class);
    }

    public static ProgramPlayRecord h(JSONObject jSONObject) throws JSONException {
        ProgramPlayRecord programPlayRecord = new ProgramPlayRecord();
        programPlayRecord.setComplete(jSONObject.getBoolean("isListened"));
        programPlayRecord.setPlayPostion(jSONObject.getInt("listenLocation"));
        programPlayRecord.setProgramId(jSONObject.getLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID));
        programPlayRecord.setUpdateTime(jSONObject.getLong("uploadTime"));
        return programPlayRecord;
    }

    private ArrayList<ProgramPlayRecord> h(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        ArrayList<ProgramPlayRecord> arrayList = new ArrayList<>();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            ProgramPlayRecord programPlayRecord = new ProgramPlayRecord();
            programPlayRecord.setComplete(jSONObject.getBoolean("isListened"));
            programPlayRecord.setPlayPostion(jSONObject.getInt("listenLocation"));
            programPlayRecord.setProgramId(jSONObject.getLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID));
            programPlayRecord.setUpdateTime(jSONObject.getLong("uploadTime"));
            arrayList.add(programPlayRecord);
        }
        return arrayList;
    }

    public static Album i(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        Album album = new Album();
        album.setId(jSONObject.getLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID));
        if (!jSONObject.isNull("onSale")) {
            album.setOnSale(jSONObject.optBoolean("onSale"));
        }
        if (!jSONObject.isNull("paid")) {
            album.setIsPaid(jSONObject.optBoolean("paid"));
        }
        if (!jSONObject.isNull("picUrl")) {
            album.setImage(jSONObject.optString("picUrl"));
        }
        if (jSONObject.optLong("picId") != 0) {
            album.setImageDocId(jSONObject.optLong("picId"));
        }
        if (!jSONObject.isNull("name")) {
            album.setName(jSONObject.getString("name"));
        }
        if (!jSONObject.isNull("artist")) {
            album.setArtist(j(jSONObject.optJSONObject("artist")));
        }
        if (!jSONObject.isNull("artists")) {
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray = jSONObject.getJSONArray("artists");
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(j(jSONArray.getJSONObject(i)));
            }
            album.setArtistsForIArtistList(arrayList);
        }
        if (!jSONObject.isNull("company")) {
            album.setCompany(jSONObject.optString("company"));
        }
        if (!jSONObject.isNull("copyrightId")) {
            album.setCopyrightId(jSONObject.optLong("copyrightId"));
        }
        if (!jSONObject.isNull("alias")) {
            album.setAlias(ai.a(jSONObject.getJSONArray("alias")));
        }
        if (!jSONObject.isNull("transNames")) {
            album.setTransNames(ai.a(jSONObject.getJSONArray("transNames")));
        }
        album.setTime(jSONObject.optLong("publishTime"));
        album.setSongSize(jSONObject.optInt("size"));
        if (!jSONObject.isNull("description")) {
            album.setDescription(jSONObject.optString("description"));
        }
        if (!jSONObject.isNull("commentThreadId")) {
            album.setThreadId(jSONObject.getString("commentThreadId"));
        }
        if (!jSONObject.isNull("songs")) {
            album.setMusics(c(jSONObject.getJSONArray("songs")));
        }
        if (!jSONObject.isNull("info")) {
            album.setCommentCount(jSONObject.getJSONObject("info").optInt("commentCount"));
            album.setShareCount(jSONObject.getJSONObject("info").optInt("shareCount"));
        }
        if (!jSONObject.isNull("exclusive")) {
            album.setExclusive(jSONObject.optBoolean("exclusive"));
        }
        if (!jSONObject.isNull("mark")) {
            album.setMark(jSONObject.getLong("mark"));
        }
        if (!jSONObject.isNull("briefDesc")) {
            album.setBriefDesc(jSONObject.getString("briefDesc"));
        }
        if (!jSONObject.isNull("type")) {
            album.setType(jSONObject.getString("type"));
        }
        if (!jSONObject.isNull("subType")) {
            album.setSubType(jSONObject.getString("subType"));
        }
        if (!jSONObject.isNull("alg")) {
            album.setAlg(jSONObject.getString("alg"));
        }
        if (!jSONObject.isNull("scm")) {
            album.setScm(jSONObject.getString("scm"));
        }
        if (!jSONObject.isNull("valid")) {
            album.setValid(jSONObject.optInt("valid", 0) == 99);
        }
        if (!jSONObject.isNull("containedSong")) {
            album.setContainedSong(jSONObject.getString("containedSong"));
        }
        album.setLocked(jSONObject.optBoolean("locked"));
        if (album.getSongSize() > 0 && album.getMusics() != null && album.getMusics().size() == 0) {
            album.setMusics(null);
        }
        if (album.getMusics() != null) {
            Iterator<MusicInfo> it = album.getMusics().iterator();
            while (it.hasNext()) {
                it.next().setAlbum(album);
            }
        }
        return album;
    }

    public static Artist j(JSONObject jSONObject) throws JSONException {
        return b(jSONObject, false);
    }

    public static SongUrlInfo k(JSONObject jSONObject) throws JSONException {
        return SongUrlInfo.parseJson(jSONObject);
    }

    public static MusicInfo l(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        MusicInfo musicInfo = new MusicInfo();
        musicInfo.setMusicName(jSONObject.isNull("name") ? null : jSONObject.optString("name"));
        musicInfo.setId(jSONObject.optLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, Long.MIN_VALUE));
        musicInfo.setVersion(jSONObject.optInt("v", 0));
        musicInfo.setMvId(jSONObject.optLong(MVUrlInfo.MV));
        musicInfo.setDuration(jSONObject.optInt("dt"));
        musicInfo.setTrackNo(jSONObject.optInt("no"));
        if (!jSONObject.isNull("cd")) {
            musicInfo.setTrackCd(jSONObject.optString("cd"));
        }
        musicInfo.setAlg(jSONObject.optString("alg"));
        if (!jSONObject.isNull("scm")) {
            musicInfo.setScm(jSONObject.optString("scm"));
        }
        musicInfo.setHearTime(jSONObject.optLong("hearTime", 0L));
        a(jSONObject, musicInfo);
        if (!jSONObject.isNull("cf")) {
            musicInfo.setCopyFrom(jSONObject.getString("cf"));
        }
        if (!jSONObject.isNull("mark")) {
            musicInfo.setMark(jSONObject.getLong("mark"));
        }
        if (!jSONObject.isNull("bpm")) {
            musicInfo.setSongBPM(jSONObject.getInt("bpm"));
        }
        musicInfo.setAlbum(Album.parseSimpleAlbum(jSONObject.optJSONObject("al")));
        if (!jSONObject.isNull("alia")) {
            musicInfo.setAlias(ai.a(jSONObject.getString("alia"), String.class));
        }
        if (!jSONObject.isNull("tns")) {
            musicInfo.setTransNames(ai.a(jSONObject.getString("tns"), String.class));
        }
        if (!jSONObject.isNull("ar")) {
            musicInfo.setArtistsForIArtistList(Artist.parseSimpleArtists(jSONObject.getJSONArray("ar")));
        }
        if (!jSONObject.isNull("originCoverType")) {
            musicInfo.setOriginCoverType(jSONObject.getInt("originCoverType"));
        }
        musicInfo.setMusicType(jSONObject.optInt("t", 0));
        if (!jSONObject.isNull("pc")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("pc");
            musicInfo.setCloudSong(new PrivateCloudSong());
            musicInfo.getCloudSong().setUserId(jSONObject2.optLong(Oauth2AccessToken.KEY_UID, 0L));
            musicInfo.getCloudSong().setBitrate(jSONObject2.optInt("br", 0));
            musicInfo.getCloudSong().setArtist(jSONObject2.optString("ar", ""));
            musicInfo.getCloudSong().setAlbum(jSONObject2.optString("alb", ""));
            musicInfo.getCloudSong().setSongName(jSONObject2.optString("sn", ""));
            musicInfo.getCloudSong().setNickName(jSONObject2.optString("nickname", ""));
            try {
                String optString = jSONObject2.optString("cid", "");
                if (bt.a(optString)) {
                    musicInfo.getCloudSong().setCover(Long.parseLong(optString));
                }
                if (musicInfo.getAlbum().getId() == 0) {
                    musicInfo.getAlbum().setImageDocId(musicInfo.getCloudSong().getCover());
                }
            } catch (NumberFormatException unused) {
            }
            if (!jSONObject2.isNull("fn")) {
                musicInfo.getCloudSong().setFileName(jSONObject2.getString("fn"));
            }
        }
        if (!jSONObject.isNull("privilege")) {
            musicInfo.setSp(a(jSONObject.getJSONObject("privilege")));
        }
        if (!jSONObject.isNull("videoInfo")) {
            JSONObject jSONObject3 = jSONObject.getJSONObject("videoInfo");
            if (!jSONObject3.isNull("video")) {
                JSONObject jSONObject4 = jSONObject3.getJSONObject("video");
                if (!jSONObject4.isNull("type")) {
                    int i = jSONObject4.getInt("type");
                    if (i == 0) {
                        musicInfo.setMvId(jSONObject4.getLong("vid"));
                    } else if (i == 1) {
                        musicInfo.setRelatedVideoId(jSONObject4.getString("vid"));
                    }
                }
            }
            musicInfo.setHasMoreVideo(jSONObject3.optBoolean("moreThanOne"));
        }
        return musicInfo;
    }

    private static SongRelatedVideo m(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        SongRelatedVideo songRelatedVideo = new SongRelatedVideo();
        if (!jSONObject.isNull("type")) {
            int i = jSONObject.getInt("type");
            songRelatedVideo.setType(i);
            if (i == 0) {
                songRelatedVideo.setId(jSONObject.getLong("vid"));
            } else if (i == 1) {
                songRelatedVideo.setUuid(jSONObject.getString("vid"));
            }
        }
        return songRelatedVideo;
    }

    private Profile n(JSONObject jSONObject) throws JSONException {
        int i = jSONObject.getInt("code");
        if (i != 200) {
            c(i);
            return null;
        }
        Profile c2 = c(jSONObject.getJSONObject("profile"));
        if (!jSONObject.isNull("listenedSongs")) {
            c2.setListernedSongs(c(jSONObject.getJSONArray("listenedSongs")));
        }
        if (!jSONObject.isNull("level")) {
            c2.setLevel(jSONObject.getInt("level"));
        }
        if (!jSONObject.isNull("listenSongs")) {
            c2.setListenSongs(jSONObject.getInt("listenSongs"));
        }
        if (!jSONObject.isNull("peopleCanSeeMyPlayRecord")) {
            c2.setPeopleCanSeeMyPlayRecord(jSONObject.getBoolean("peopleCanSeeMyPlayRecord"));
        }
        if (!jSONObject.isNull("mobileSign")) {
            c2.setMobileSign(jSONObject.getBoolean("mobileSign"));
        }
        if (!jSONObject.isNull("userPoint")) {
            c2.setUserPoint(o(jSONObject.getJSONObject("userPoint")));
        }
        if (!jSONObject.isNull("djStatus")) {
            c2.setDjStatus(jSONObject.getInt("djStatus"));
        }
        if (!jSONObject.isNull("createDays")) {
            c2.setCreateDays(jSONObject.getInt("createDays"));
        }
        if (!jSONObject.isNull("createTime")) {
            c2.setCreateTime(jSONObject.getLong("createTime"));
        }
        return c2;
    }

    private static UserPoint o(JSONObject jSONObject) {
        UserPoint userPoint = new UserPoint();
        if (jSONObject == null) {
            return userPoint;
        }
        userPoint.setUserId(jSONObject.optLong("userId", 0L));
        userPoint.setBalance(jSONObject.optLong("balance", 0L));
        userPoint.setUpdateTime(jSONObject.optLong("updateTime", 0L));
        userPoint.setBlockBalance(jSONObject.optLong("blockBalance", 0L));
        userPoint.setVersion(jSONObject.optInt("version", 0));
        userPoint.setStatus(jSONObject.optInt(NotificationCompat.CATEGORY_STATUS, 0));
        return userPoint;
    }

    private boolean p(JSONObject jSONObject) throws JSONException {
        if (jSONObject.getInt("code") != 200) {
            c(jSONObject.getInt("code"));
            return false;
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject("setting");
        int i = jSONObject2.getInt("shareSetting");
        int i2 = jSONObject2.getInt("playRecordSetting");
        int i3 = jSONObject2.getInt("ageSetting");
        int i4 = jSONObject2.getInt("areaSetting");
        int i5 = jSONObject2.getInt("collegeSetting");
        int i6 = jSONObject2.getInt("villageAgeSetting");
        int optInt = jSONObject2.optInt("socialSetting");
        int optInt2 = jSONObject2.optInt("concertSetting");
        int optInt3 = jSONObject2.optInt("broadcastSetting");
        boolean z = jSONObject2.getBoolean("allowOfflinePrivateMessageNotify");
        boolean z2 = jSONObject2.getBoolean("allowOfflineCommentNotify");
        boolean z3 = jSONObject2.getBoolean("allowOfflineForwardNotify");
        boolean z4 = jSONObject2.getBoolean("allowSubscriptionNotify");
        boolean z5 = jSONObject2.getBoolean("allowVideoSubscriptionNotify");
        boolean z6 = jSONObject2.getBoolean("allowPlaylistShareNotify");
        boolean z7 = jSONObject2.getBoolean("allowDJRadioSubscriptionNotify");
        boolean z8 = jSONObject2.getBoolean("allowNewFollowerNotify");
        boolean z9 = jSONObject2.getBoolean("allowImportDoubanPlaylist");
        boolean z10 = jSONObject2.getBoolean("allowImportXiamiPlaylist");
        boolean z11 = jSONObject2.getBoolean("peopleNearbyCanSeeMe");
        boolean z12 = jSONObject2.getBoolean("allowLikedNotify");
        boolean z13 = jSONObject2.getBoolean("allowOfflineNotify");
        boolean z14 = !jSONObject2.isNull("needRcmdEvent") ? jSONObject2.getBoolean("needRcmdEvent") : true;
        SharedPreferences.Editor edit = bh.a().edit();
        edit.putInt("shareSetting", i);
        edit.putInt("hearSongSetting", i2);
        edit.putInt("socialSetting", optInt);
        edit.putInt("ageSetting", i3);
        edit.putInt("areaSetting", i4);
        edit.putInt("collegeSetting", i5);
        edit.putInt("villageSetting", i6);
        edit.putInt("concertInfoSetting", optInt2);
        edit.putInt("liveRcmdSetting", optInt3);
        edit.putBoolean("allowOfflinePrivateMessageNotify", z);
        edit.putBoolean("allowOfflineCommentNotify", z2);
        edit.putBoolean("allowOfflineAtNotify", z3);
        edit.putBoolean("allowSubscriptionNotify", z4);
        edit.putBoolean("allowVideoSubscriptionNotify", z5);
        edit.putBoolean("allowPlayListSharedNotify", z6);
        edit.putBoolean("allowDJRadioSubscriptionNotify", z7);
        edit.putBoolean("allowNewFollowerNotify", z8);
        edit.putBoolean("allowImportDoubanPlaylist", z9);
        edit.putBoolean("allowImportXiamiPlaylist", z10);
        edit.putBoolean("peopleNearbyCanSeeMe", z11);
        edit.putBoolean("allowOfflineNotify", z13);
        edit.putBoolean("allowLikedNotify", z12);
        edit.putBoolean("allowTrackRcmd", z14);
        if (!jSONObject2.isNull("commentSetting")) {
            edit.putInt("profileCommentSetting", jSONObject2.getInt("commentSetting"));
        }
        bg.c(jSONObject2.optInt("followSingerSetting") == 0);
        bg.a(jSONObject2.optInt("personalServiceSetting") == 0, com.netease.cloudmusic.core.a.a());
        edit.commit();
        if (!jSONObject2.isNull("phoneFriendSetting")) {
            bp.a(jSONObject2.optBoolean("phoneFriendSetting"));
        }
        return true;
    }

    @Override // com.netease.cloudmusic.c.a
    public int a(long j, PageValue pageValue) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, j + "");
            hashMap.put("checkToken", bo.a());
            JSONObject jSONObject = new JSONObject(c.a("playlist/subscribe", hashMap).g());
            int i = jSONObject.getInt("code");
            if (i == 200) {
                TaskCenterHelper.f7374a.a("iot_trial_watch_playlist_collect");
                pageValue.setIntValue(jSONObject.optInt("point"));
                return 1;
            }
            if (i == 506) {
                return -2;
            }
            if (i == 404) {
                return -3;
            }
            if (i == 501) {
                return -4;
            }
            c(i);
            return -1;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public int a(String str, long j, long j2, boolean z, PageValue pageValue) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("like", z + "");
            hashMap.put("trackId", j + "");
            hashMap.put("userid", j2 + "");
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("source", str);
                hashMap.put("userActionMap", jSONObject.toString());
            }
            hashMap.put("checkToken", bo.a());
            JSONObject jSONObject2 = new JSONObject(c.a("song/like", hashMap).g());
            int i = jSONObject2.getInt("code");
            if (i == 200) {
                if (z) {
                    TaskCenterHelper.f7374a.a("iot_trial_watch_song_like_multi", (Long) null);
                }
                pageValue.setLongValue(jSONObject2.getLong("playlistId"));
                pageValue.setIntValue(jSONObject2.optInt("point"));
                return jSONObject2.optBoolean("privateCloudStored") ? 2 : 1;
            }
            if (i == 506) {
                return -8;
            }
            if (i == 511) {
                return -7;
            }
            if (i != 502 && i != 404) {
                if (i == 515) {
                    return -9;
                }
                if (i == 505) {
                    return -4;
                }
                if (i == 400) {
                    return -5;
                }
                if (i == 420 || i == 401) {
                    return -6;
                }
                if (i == 512) {
                    return -10;
                }
                c(i);
                return 0;
            }
            return -2;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public int a(List<Long> list, List<Long> list2, long j, String str, Map<Long, List<Long>> map) {
        List<Long> list3 = list2;
        String str2 = str;
        int i = 1;
        try {
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            while (i2 < list.size()) {
                StringBuilder sb = new StringBuilder();
                sb.append(list.get(i2));
                sb.append(list3.get(i2).longValue() == 0 ? "" : "_" + list3.get(i2));
                arrayList.add(sb.toString());
                if ((i2 != 0 && i2 % 100 == 0) || i2 == list.size() - i) {
                    if (i2 != 0 || list.size() == i) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("songIds", bt.a(arrayList, ";"));
                        hashMap.put("playlistId", String.valueOf(j));
                        if (str2 != null) {
                            hashMap.put("playlistName", str2);
                        }
                        hashMap.put("cleanBeforeImport", "false");
                        JSONObject jSONObject = new JSONObject(c.a("v1/playlist/importFromLocal", hashMap).g());
                        if (jSONObject.getInt("code") != 200) {
                            return jSONObject.getInt("code") == 507 ? -1 : -2;
                        }
                        for (int i3 = 0; i3 < jSONObject.getJSONArray("addedPlaylistIds").length(); i3++) {
                            long j2 = jSONObject.getJSONArray("addedPlaylistIds").getLong(i3);
                            if (!map.containsKey(Long.valueOf(j2))) {
                                map.put(Long.valueOf(j2), new ArrayList());
                            }
                            if (!jSONObject.isNull("addedMusicIds")) {
                                JSONArray jSONArray = jSONObject.getJSONObject("addedMusicIds").getJSONArray(j2 + "");
                                for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                                    map.get(Long.valueOf(j2)).add(Long.valueOf(jSONArray.getLong(i4)));
                                }
                            }
                        }
                    }
                    arrayList.clear();
                }
                i2++;
                list3 = list2;
                str2 = str;
                i = 1;
            }
            return map.size();
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public long a(String str, String str2, long j, ArrayList<Long> arrayList, long j2, long j3, ArrayList<Integer> arrayList2, long j4) {
        return ((Long) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("djprogram/v2/add/mobile").c("name", str)).c("desc", str2)).c("cover", j + "")).c("songIds", new JSONArray((Collection) arrayList).toString())).c("radioId", j2 + "")).c("publishTime", j3 + "")).c("shareTypes", new JSONArray((Collection) arrayList2).toString())).c("dfsId", j4 + "")).f(false)).a(new j<Long>() { // from class: com.netease.cloudmusic.c.a.a.8
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Long parse(JSONObject jSONObject) throws JSONException {
                return Long.valueOf(jSONObject.getJSONObject("program").getLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID));
            }
        }, new int[0])).longValue();
    }

    @Override // com.netease.cloudmusic.c.a
    public Pair<Integer, Long> a(long j, boolean z) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, j + "");
            if (z) {
                hashMap.put("checkToken", bo.a());
            }
            JSONObject jSONObject = new JSONObject(c.a(z ? "djradio/sub" : "djradio/unsub", hashMap).g());
            int i = jSONObject.getInt("code");
            if (i != 200 && i != 400 && i != 502) {
                a(i, jSONObject.isNull("message") ? null : jSONObject.optString("message"));
            }
            return new Pair<>(Integer.valueOf(i), Long.valueOf(jSONObject.optLong(com.netease.mam.agent.d.d.a.dJ)));
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public LongSparseArray<SongPrivilege> a(Collection<MusicInfo> collection) {
        return a(collection, (Map<String, String>) null);
    }

    @Override // com.netease.cloudmusic.c.a
    public LongSparseArray<SongPrivilege> a(Collection<MusicInfo> collection, Map<String, String> map) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (collection != null) {
            for (MusicInfo musicInfo : collection) {
                if (musicInfo != null && musicInfo.getMatchedMusicId() > 0) {
                    arrayList.add(Long.valueOf(musicInfo.getMatchedMusicId()));
                    arrayList2.add(Integer.valueOf(FreeTrialPrivilegeUtils.c(musicInfo)));
                }
            }
        }
        int value = FreeTrialScene.OTHER_SCENE.getValue();
        com.alibaba.fastjson.JSONObject jSONObject = new com.alibaba.fastjson.JSONObject();
        boolean z = true;
        int i = value;
        for (int i2 = 0; i2 < arrayList.size() && i2 < arrayList2.size(); i2++) {
            if (i2 == 0) {
                i = ((Integer) arrayList2.get(i2)).intValue();
            } else if (z && i != ((Integer) arrayList2.get(i2)).intValue()) {
                z = false;
            }
            jSONObject.put(String.valueOf(arrayList.get(i2)), (Object) String.valueOf(arrayList2.get(i2)));
        }
        if (z) {
            value = i;
        }
        return a(arrayList, value, z ? null : jSONObject.toJSONString(), map);
    }

    public LongSparseArray<SongPrivilege> a(List<Long> list, int i, String str, Map<String, String> map) {
        LongSparseArray<SongPrivilege> longSparseArray = new LongSparseArray<>();
        if (list.size() == 0) {
            return longSparseArray;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("ids", String.format("[%s]", bt.a(list, ",")));
        hashMap.put("trialMode", String.valueOf(i));
        if (str != null) {
            hashMap.put("trialModes", str);
        }
        if (map != null && !map.isEmpty()) {
            hashMap.put("extMap", ai.a(map));
        }
        try {
            JSONObject jSONObject = new JSONObject(c.a("song/enhance/privilege", hashMap).g());
            int i2 = jSONObject.getInt("code");
            if (i2 == 200) {
                a(jSONObject.getJSONArray("data"), longSparseArray);
            } else {
                c(i2);
            }
            return longSparseArray;
        } catch (IllegalStateException | JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public LongSparseArray<SongUrlInfo> a(List<Long> list, int i, List<Long> list2, boolean z, a.InterfaceC0044a interfaceC0044a, long j) {
        String format;
        LongSparseArray<SongUrlInfo> longSparseArray = new LongSparseArray<>();
        try {
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            while (true) {
                if (i2 >= list.size()) {
                    break;
                }
                if (list.get(i2).longValue() > 0) {
                    arrayList.add("\"" + list.get(i2) + "_" + list2.get(i2) + "\"");
                }
                i2++;
            }
            long j2 = FreeTrialPrivilegeUtils.f5533a.a() ? j : 0L;
            if (z) {
                boolean a2 = com.netease.cloudmusic.module.player.h.c.a();
                String b2 = d.b(i);
                Object[] objArr = new Object[4];
                objArr[0] = URLEncoder.encode("[" + bt.a(arrayList, ",") + "]");
                objArr[1] = b2;
                objArr[2] = a2 ? "aac" : "mp3";
                objArr[3] = Long.valueOf(j2);
                format = String.format("song/enhance/player/url/v1?ids=%s&level=%s&encodeType=%s&trialMode=%s", objArr);
            } else {
                format = String.format("song/enhance/download/url?br=%d&id=%d_%d", Integer.valueOf(i), list.get(0), list2.get(0));
            }
            String str = f2111b;
            com.netease.cloudmusic.log.a.a(str, (Object) ("url:" + format));
            String g = com.netease.cloudmusic.network.e.b.g();
            if (g != null) {
                format = format + "&sp=" + g;
            }
            com.netease.cloudmusic.network.i.d.a aVar = (com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a(format).c(5000)).b(5000);
            if (interfaceC0044a != null) {
                interfaceC0044a.a(aVar);
            }
            JSONObject f = aVar.f();
            int i3 = f.getInt("code");
            if (i3 != 200) {
                try {
                    c(i3);
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                    throw new com.netease.cloudmusic.network.exception.a(1, e);
                }
            } else {
                if (f.isNull("data")) {
                    return longSparseArray;
                }
                com.netease.cloudmusic.log.a.a(str, (Object) ("jsonResult:" + f.toString()));
                if (z) {
                    for (int i4 = 0; i4 < f.getJSONArray("data").length(); i4++) {
                        SongUrlInfo k = k(f.getJSONArray("data").getJSONObject(i4));
                        if (k != null) {
                            longSparseArray.put(k.getId(), k);
                        }
                    }
                } else {
                    SongUrlInfo k2 = k(f.getJSONObject("data"));
                    if (k2 != null) {
                        longSparseArray.put(k2.getId(), k2);
                    }
                }
            }
            return longSparseArray;
        } catch (JSONException e3) {
            e = e3;
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public UpdateProfileTask.Result a(String str, String str2, int i, int i2, int i3, long j, PageValue pageValue) {
        HashMap hashMap = new HashMap();
        if (str != null) {
            hashMap.put("nickname", str);
        }
        if (str2 != null) {
            hashMap.put("signature", str2);
        }
        if (i != Integer.MIN_VALUE) {
            hashMap.put("gender", i + "");
        }
        if (i2 != Integer.MIN_VALUE) {
            if (i2 == -1 || i2 == -2) {
                hashMap.put("province", i3 + "");
            } else {
                hashMap.put("province", i2 + "");
            }
        }
        if (i3 != Integer.MIN_VALUE) {
            if (i2 == -1 || i2 == -2) {
                hashMap.put("city", "0");
            } else {
                hashMap.put("city", i3 + "");
            }
        }
        if (j != 0) {
            hashMap.put("birthday", j + "");
        }
        try {
            JSONObject jSONObject = new JSONObject(c.a("user/profile/update", hashMap).g());
            int i4 = jSONObject.getInt("code");
            String string = jSONObject.isNull("message") ? null : jSONObject.getString("message");
            UpdateProfileTask.Result result = new UpdateProfileTask.Result();
            result.code = i4;
            result.message = string;
            if (i4 == 200) {
                pageValue.setIntValue(jSONObject.optInt("point"));
            }
            return result;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public UploadImageTask.Result a(Bitmap bitmap, PageValue pageValue) {
        return a(bitmap, "user/avatar/upload/v1", "imgid", (HashMap<String, String>) null);
    }

    @Override // com.netease.cloudmusic.c.a
    public l.d a(l.c cVar) {
        String str;
        l.d dVar = new l.d();
        dVar.a(cVar.b());
        try {
            HashMap hashMap = new HashMap(3);
            hashMap.put("threadId", cVar.b());
            String str2 = "";
            switch (cVar.a()) {
                case 10:
                case 30:
                case 40:
                case 50:
                case 60:
                case 70:
                case 80:
                case 170:
                case 190:
                    str2 = "resource/like";
                    hashMap.put("checkToken", bo.a());
                    break;
                case 20:
                    str = "v1/comment/like";
                    hashMap.put("commentId", cVar.c() + "");
                    hashMap.put("checkToken", bo.a());
                    str2 = str;
                    break;
                case 90:
                case 100:
                case 120:
                case Constants.ERR_ENCRYPTED_STREAM_NOT_ALLOWED_PUBLISHED /* 130 */:
                case 140:
                case 150:
                case 160:
                case 180:
                case 200:
                    str2 = "resource/unlike";
                    hashMap.put("checkToken", bo.a());
                    break;
                case 110:
                    str = "v1/comment/unlike";
                    hashMap.put("commentId", cVar.c() + "");
                    hashMap.put("checkToken", bo.a());
                    str2 = str;
                    break;
            }
            JSONObject jSONObject = new JSONObject(c.a(str2, hashMap).g());
            int i = jSONObject.getInt("code");
            if (i == 200) {
                if (cVar.a() == 10) {
                    cVar.b(jSONObject.optInt("point"));
                }
                dVar.a(1);
                return dVar;
            }
            if (i == 400) {
                dVar.a(2);
                return dVar;
            }
            if (i == 404) {
                dVar.a(4);
                return dVar;
            }
            c(i);
            dVar.a(3);
            return dVar;
        } catch (com.alibaba.fastjson.JSONException | JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public LyricInfo a(LyricInfo lyricInfo) {
        boolean z;
        boolean z2;
        boolean z3;
        JSONObject jSONObject;
        try {
            StringBuffer stringBuffer = new StringBuffer(String.format("song/lyric?id=%d&cp=false", Long.valueOf(lyricInfo.getMusicId())));
            z = lyricInfo.getLyricVersion() != -10000;
            z2 = lyricInfo.getTranslateVersion() != -10000;
            z3 = lyricInfo.getKalaokVersion() != -10000;
            if (z) {
                stringBuffer.append(String.format("&lv=%d", Integer.valueOf(lyricInfo.getLyricVersion())));
            }
            if (z2) {
                stringBuffer.append(String.format("&tv=%d", Integer.valueOf(lyricInfo.getTranslateVersion())));
            }
            if (z3) {
                stringBuffer.append(String.format("&kv=%d", Integer.valueOf(lyricInfo.getKalaokVersion())));
            }
            jSONObject = new JSONObject(c.a(stringBuffer.toString()).g());
        } catch (JSONException e2) {
            e = e2;
        }
        if (jSONObject.getInt("code") != 200) {
            try {
                c(jSONObject.getInt("code"));
            } catch (JSONException e3) {
                e = e3;
                e.printStackTrace();
                lyricInfo.setLyricInfoType(LyricInfo.LyricInfoType.Lyric_Error);
                return lyricInfo;
            }
            lyricInfo.setLyricInfoType(LyricInfo.LyricInfoType.Lyric_Error);
            return lyricInfo;
        }
        if (!jSONObject.isNull("nolyric") && jSONObject.getBoolean("nolyric")) {
            lyricInfo.setLyricInfoType(LyricInfo.LyricInfoType.Lyric_No_Lyrics);
        } else if (jSONObject.isNull("uncollected") || !jSONObject.getBoolean("uncollected")) {
            boolean a2 = a(z2, lyricInfo, jSONObject.optJSONObject("tlyric"), "tlyric", lyricInfo.getTranslateVersion());
            boolean a3 = a(z3, lyricInfo, jSONObject.optJSONObject("klyric"), "klyric", lyricInfo.getKalaokVersion());
            if (!a(z, lyricInfo, jSONObject.optJSONObject("lrc"), "lrc", lyricInfo.getLyricVersion()) && !a2 && !a3) {
                lyricInfo.setLyricInfoType(LyricInfo.LyricInfoType.Lyric_Version_Not_Update);
            }
            lyricInfo.setLyricInfoType(LyricInfo.LyricInfoType.Lyric_Loaded_Or_Update);
        } else {
            lyricInfo.setLyricInfoType(LyricInfo.LyricInfoType.Lyric_Not_Collected);
        }
        if (!jSONObject.isNull("briefDesc")) {
            lyricInfo.setBriefDesc(jSONObject.getString("briefDesc"));
        }
        if (!jSONObject.isNull("qfy")) {
            lyricInfo.setQfy(jSONObject.getBoolean("qfy"));
        }
        if (!jSONObject.isNull("transUser")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("transUser");
            lyricInfo.setTransUserId(jSONObject2.optLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, 0L));
            lyricInfo.setTransUserName(jSONObject2.optString("nickname", NeteaseMusicApplication.a().getString(R.string.ck0)));
            lyricInfo.setTransUserTime(jSONObject2.optLong(com.netease.mam.agent.d.d.a.dJ, 0L));
        }
        if (!jSONObject.isNull("lyricUser")) {
            JSONObject jSONObject3 = jSONObject.getJSONObject("lyricUser");
            lyricInfo.setLyricUserId(jSONObject3.optLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, 0L));
            lyricInfo.setLyricUserName(jSONObject3.optString("nickname", NeteaseMusicApplication.a().getString(R.string.ck0)));
            lyricInfo.setLyricUserTime(jSONObject3.optLong(com.netease.mam.agent.d.d.a.dJ, 0L));
        }
        return lyricInfo;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0062, code lost:
    
        r8.setKalaokLyric(r0);
        r8.setKalaokVersion(1);
     */
    @Override // com.netease.cloudmusic.c.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.netease.cloudmusic.meta.LyricInfo a(com.netease.cloudmusic.meta.LyricInfo r8, long r9) {
        /*
            r7 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = "code"
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            java.lang.String r3 = "cloud/lyric/get?songId=%d&userId=%d&kv=0&lv=0"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            long r5 = r8.getMusicId()     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r6 = 0
            r4[r6] = r5     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r10 = 1
            r4[r10] = r9     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            java.lang.String r9 = java.lang.String.format(r3, r4)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r2.<init>(r9)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            java.lang.String r9 = r2.toString()     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            com.netease.cloudmusic.network.i.d.a r9 = com.netease.cloudmusic.network.c.a(r9)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            java.lang.String r9 = r9.g()     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r2.<init>(r9)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            int r9 = r2.getInt(r1)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r3 = 200(0xc8, float:2.8E-43)
            if (r9 != r3) goto L76
            java.lang.String r9 = "lrc"
            java.lang.String r9 = r2.optString(r9, r0)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            java.lang.String r1 = "krc"
            java.lang.String r0 = r2.optString(r1, r0)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            boolean r1 = com.netease.cloudmusic.utils.bt.a(r9)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            if (r1 != 0) goto L50
            r6 = 1
        L50:
            boolean r1 = com.netease.cloudmusic.utils.bt.a(r0)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r1 = r1 ^ r10
            if (r6 != 0) goto L60
            if (r1 == 0) goto L5a
            goto L60
        L5a:
            com.netease.cloudmusic.meta.LyricInfo$LyricInfoType r9 = com.netease.cloudmusic.meta.LyricInfo.LyricInfoType.Lyric_No_Lyrics     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r8.setLyricInfoType(r9)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            goto L75
        L60:
            if (r1 == 0) goto L68
            r8.setKalaokLyric(r0)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r8.setKalaokVersion(r10)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
        L68:
            if (r6 == 0) goto L70
            r8.setLyric(r9)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r8.setLyricVersion(r10)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
        L70:
            com.netease.cloudmusic.meta.LyricInfo$LyricInfoType r9 = com.netease.cloudmusic.meta.LyricInfo.LyricInfoType.Lyric_Loaded_Or_Update     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r8.setLyricInfoType(r9)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
        L75:
            return r8
        L76:
            r10 = 404(0x194, float:5.66E-43)
            if (r9 != r10) goto L80
            com.netease.cloudmusic.meta.LyricInfo$LyricInfoType r9 = com.netease.cloudmusic.meta.LyricInfo.LyricInfoType.Lyric_No_Lyrics     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r8.setLyricInfoType(r9)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            return r8
        L80:
            int r9 = r2.getInt(r1)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            r7.c(r9)     // Catch: org.json.JSONException -> L88 com.netease.cloudmusic.network.exception.i -> L8a
            goto L8e
        L88:
            r9 = move-exception
            goto L8b
        L8a:
            r9 = move-exception
        L8b:
            r9.printStackTrace()
        L8e:
            com.netease.cloudmusic.meta.LyricInfo$LyricInfoType r9 = com.netease.cloudmusic.meta.LyricInfo.LyricInfoType.Lyric_Error
            r8.setLyricInfoType(r9)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.cloudmusic.c.a.a.a(com.netease.cloudmusic.meta.LyricInfo, long):com.netease.cloudmusic.meta.LyricInfo");
    }

    @Override // com.netease.cloudmusic.c.a
    public MusicInfo a(String str, String str2, String str3, int i, List<byte[]> list) {
        try {
            HashMap hashMap = new HashMap();
            JSONArray jSONArray = new JSONArray();
            Iterator<byte[]> it = list.iterator();
            while (it.hasNext()) {
                jSONArray.put(new BASE64Encoder().encode(it.next()));
            }
            hashMap.put("rawdatas", jSONArray.toString());
            hashMap.put("artist", str);
            hashMap.put(ResExposureReq.ExposureRecord.RES_POS_ALBUM, str2);
            hashMap.put("songName", str3);
            hashMap.put("duration", i + "");
            hashMap.put("fpver", "5");
            JSONObject jSONObject = new JSONObject(c.a("music/matcher/philips/file-matcher", hashMap).g());
            int i2 = jSONObject.getInt("code");
            if (i2 == 200) {
                if (jSONObject.isNull("song")) {
                    return null;
                }
                return d(jSONObject.getJSONObject("song"));
            }
            if (i2 == 404) {
                return null;
            }
            c(i2);
            return null;
        } catch (i | JSONException unused) {
            throw new h();
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public PageValue a(long j, int i, String str, boolean z, int i2, int i3) {
        PageValue pageValue = new PageValue();
        try {
            JSONObject jSONObject = new JSONObject(c.a(String.format("radio/sport/like?songId=%d&like=%s&alg=%s&time=%d&sbpm=%d&rbpm=%d", Long.valueOf(j), Boolean.valueOf(z), str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3))).g());
            int i4 = jSONObject.getInt("code");
            if (i4 == 200) {
                pageValue.setLongValue(jSONObject.getLong("playlistId"));
                pageValue.setIntValue(i4);
                if (z && !jSONObject.isNull("point")) {
                    pageValue.setObject(jSONObject.get("point"));
                }
            } else if (i4 == 500) {
                pageValue.setIntValue(i4);
            } else if (i4 == 400) {
                pageValue.setIntValue(i4);
            } else {
                c(i4);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return pageValue;
    }

    @Override // com.netease.cloudmusic.c.a
    public XiaoIceProgram a(long j, boolean z, String str) {
        com.netease.cloudmusic.network.i.d.a a2 = c.a(z ? "coop/xiaoice/demand/program/get" : "coop/xiaoice/program/get");
        if (!z) {
            a2.c("sharedSongId", j + "");
        }
        if (bt.c(str)) {
            a2.c("scene", str);
        }
        return (XiaoIceProgram) a2.a(new j<XiaoIceProgram>() { // from class: com.netease.cloudmusic.c.a.a.7
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public XiaoIceProgram parse(JSONObject jSONObject) throws JSONException {
                return XiaoIceProgram.fromJson(jSONObject.getJSONObject("data"));
            }
        }, new int[0]);
    }

    @Override // com.netease.cloudmusic.c.a
    public SongUrlInfo a(long j, int i, long j2, boolean z) {
        return a(Arrays.asList(Long.valueOf(j)), i, Arrays.asList(Long.valueOf(j2)), z, (a.InterfaceC0044a) null, FreeTrialScene.NORMAL.getValue()).get(j);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public Plugin a(String str) {
        return (Plugin) ((com.netease.cloudmusic.network.i.d.a) c.a("usertool/mobile/latestVersion").c("pluginName", str)).a(new j<Plugin>() { // from class: com.netease.cloudmusic.c.a.a.3
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Plugin parse(JSONObject jSONObject) throws JSONException {
                if (jSONObject.getInt("code") != 200 || jSONObject.isNull("data")) {
                    return null;
                }
                return Plugin.parsePlugin(jSONObject.getJSONObject("data"));
            }
        }, 400);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public ArrayList<SatiScene> a(final int i) {
        return (ArrayList) ((com.netease.cloudmusic.network.i.d.a) c.a("mindfulness/detail").c("model", i == 1 ? "FOCUS" : i == 3 ? "RELIEVE_STRESS" : "SLEEP")).a(new j<ArrayList<SatiScene>>() { // from class: com.netease.cloudmusic.c.a.a.6
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ArrayList<SatiScene> parse(JSONObject jSONObject) throws JSONException {
                return SatiScene.a(jSONObject.getJSONObject("data"), i);
            }
        }, new int[0]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public LinkedHashMap<Long, Pair<MusicInfo, String>> a(long j, long j2, boolean z, long j3, int i) {
        return (LinkedHashMap) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("music/multi/terminal/tv/intelligent/get").c("songId", String.valueOf(j2))).c("type", z ? "fromPlayAll" : "fromPlayOne")).c("playlistId", String.valueOf(j))).c("startMusicId", String.valueOf(j3))).c("count", String.valueOf(i))).d(5000)).a(new j<LinkedHashMap<Long, Pair<MusicInfo, String>>>() { // from class: com.netease.cloudmusic.c.a.a.13
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public LinkedHashMap<Long, Pair<MusicInfo, String>> parse(JSONObject jSONObject) throws JSONException {
                JSONArray optJSONArray = jSONObject.optJSONArray("data");
                if (optJSONArray == null) {
                    return null;
                }
                LinkedHashMap<Long, Pair<MusicInfo, String>> linkedHashMap = new LinkedHashMap<>();
                for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                    JSONObject jSONObject2 = optJSONArray.getJSONObject(i2);
                    MusicInfo l = a.l(jSONObject2.optJSONObject("songInfo"));
                    String optString = jSONObject2.optString("alg");
                    long j4 = jSONObject2.getLong(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID);
                    if (l != null) {
                        l.setAlg(optString);
                        l = new AIPlayListMusicInfo(l, jSONObject2.optBoolean("recommended"));
                    }
                    linkedHashMap.put(Long.valueOf(j4), Pair.create(l, optString));
                }
                return linkedHashMap;
            }
        }, new int[0]);
    }

    @Override // com.netease.cloudmusic.c.a
    public List<Long> a() {
        try {
            JSONObject jSONObject = new JSONObject(c.a("song/like/get").g());
            if (jSONObject.getInt("code") != 200) {
                c(jSONObject.getInt("code"));
                return null;
            }
            ArrayList arrayList = new ArrayList();
            if (jSONObject.isNull("ids")) {
                return arrayList;
            }
            bh.a().edit().putLong("starMusicIdCheckPoint", jSONObject.getLong("checkPoint")).commit();
            JSONArray jSONArray = jSONObject.getJSONArray("ids");
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(Long.valueOf(jSONArray.getLong(i)));
            }
            return arrayList;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public List<PlayList> a(long j, int i, int i2, PageValue pageValue) {
        try {
            JSONObject jSONObject = new JSONObject(c.a(String.format("user/playlist/?uid=%d&limit=%d&offset=%d", Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2))).g());
            if (jSONObject.getInt("code") == 200) {
                pageValue.setHasMore(jSONObject.optBoolean("more", false));
                return g(jSONObject.getJSONArray(ResExposureReq.ExposureRecord.RES_POS_PLAYLIST));
            }
            c(jSONObject.getInt("code"));
            return null;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> a(long j, int i, String str) {
        try {
            JSONObject jSONObject = new JSONObject(c.a(String.format("v1/radio/skip?songId=%d&time=%d&alg=%s", Long.valueOf(j), Integer.valueOf(i), str)).g());
            int i2 = jSONObject.getInt("code");
            if (i2 == 200) {
                return jSONObject.isNull("songs") ? Collections.emptyList() : c(jSONObject.getJSONArray("songs"));
            }
            c(i2);
            return Collections.emptyList();
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> a(long j, int i, String str, String str2) {
        return (List) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("zone/fm/trash/add").c("zone", str2)).c(com.netease.mam.agent.d.d.a.dJ, i + "")).c("alg", str)).c("songId", j + "")).a(new j<List<MusicInfo>>() { // from class: com.netease.cloudmusic.c.a.a.10
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public List<MusicInfo> parse(JSONObject jSONObject) throws JSONException {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                return jSONObject2.isNull("songs") ? Collections.emptyList() : a.c(jSONObject2.getJSONArray("songs"));
            }
        }, new int[0]);
    }

    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> a(long j, int i, String str, boolean z, PageValue pageValue) {
        try {
            JSONObject jSONObject = new JSONObject(c.a(String.format("v1/radio/like?trackId=%d&time=%d&alg=%s&like=%s", Long.valueOf(j), Integer.valueOf(i), str, Boolean.valueOf(z))).g());
            int i2 = jSONObject.getInt("code");
            if (i2 != 200 && i2 != 502) {
                if (i2 == 505) {
                    pageValue.setIntValue(i2);
                    return null;
                }
                c(i2);
                return null;
            }
            pageValue.setLongValue(jSONObject.getLong("playlistId"));
            pageValue.setIntValue(i2);
            if (z && !jSONObject.isNull("point")) {
                pageValue.setObject(jSONObject.get("point"));
            }
            return jSONObject.isNull("songs") ? Collections.emptyList() : c(jSONObject.getJSONArray("songs"));
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> a(long j, int i, String str, boolean z, PageValue pageValue, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(c.a(String.format("zone/fm/like?songId=%d&time=%d&alg=%s&like=%s&zone=%s", Long.valueOf(j), Integer.valueOf(i), str, Boolean.valueOf(z), str2)).g());
            int i2 = jSONObject.getInt("code");
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            if (i2 != 200 && i2 != 502) {
                if (i2 == 505) {
                    pageValue.setIntValue(i2);
                    return null;
                }
                c(i2);
                return null;
            }
            pageValue.setLongValue(jSONObject2.getLong("playlistId"));
            pageValue.setIntValue(i2);
            if (z && !jSONObject2.isNull("point")) {
                pageValue.setObject(jSONObject2.get("point"));
            }
            return jSONObject2.isNull("songs") ? Collections.emptyList() : c(jSONObject2.getJSONArray("songs"));
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> a(List<Long> list) {
        if (list == null || list.size() == 0) {
            return new ArrayList();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(list.size());
        Map<Long, SongPrivilege> hashMap = new HashMap<>(list.size());
        Iterator<Long> it = list.iterator();
        while (it.hasNext()) {
            linkedHashMap.put(it.next(), 0);
        }
        List<MusicInfo> b2 = b(linkedHashMap, hashMap, (LongSparseArray<Pair<Boolean, SongRelatedVideo>>) null);
        if (b2 != null && b2.size() != 0) {
            for (MusicInfo musicInfo : b2) {
                if (musicInfo != null) {
                    musicInfo.setSp(hashMap.get(Long.valueOf(musicInfo.getId())));
                }
            }
        }
        return b2;
    }

    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> a(List<Long> list, int i) throws JSONException {
        HashMap hashMap = new HashMap();
        hashMap.put(com.netease.cloudmusic.module.transfer.a.b.EXTRA_MAX, String.valueOf(i));
        if (list.size() > 0) {
            hashMap.put("song", ai.a((Object) list));
        }
        JSONObject jSONObject = new JSONObject(c.a("radio/sport/offline/song/get", hashMap).g());
        int i2 = jSONObject.getInt("code");
        if (i2 == 200) {
            return a(jSONObject.getJSONArray("data"));
        }
        c(i2);
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public List<Program> a(List<Long> list, String str, long j) {
        return (List) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("dj/program/list").c("sourceType", str)).c("sourceId", String.valueOf(j))).c("programIds", ai.b(list))).a(new j<List<Program>>() { // from class: com.netease.cloudmusic.c.a.a.5
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public List<Program> parse(JSONObject jSONObject) throws JSONException {
                if (jSONObject.isNull("data")) {
                    return null;
                }
                JSONArray jSONArray = jSONObject.getJSONArray("data");
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(a.g(jSONArray.getJSONObject(i)));
                }
                return arrayList;
            }
        }, new int[0]);
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x012e, code lost:
    
        c(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0131, code lost:
    
        return r6;
     */
    @Override // com.netease.cloudmusic.c.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<com.netease.cloudmusic.meta.MusicInfo> a(java.util.Map<java.lang.Long, java.lang.Integer> r19, java.util.Map<java.lang.Long, com.netease.cloudmusic.meta.virtual.SongPrivilege> r20, androidx.collection.LongSparseArray<android.util.Pair<java.lang.Boolean, com.netease.cloudmusic.meta.SongRelatedVideo>> r21) {
        /*
            Method dump skipped, instructions count: 320
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.cloudmusic.c.a.a.a(java.util.Map, java.util.Map, androidx.collection.LongSparseArray):java.util.List");
    }

    @Override // com.netease.cloudmusic.c.a
    public List<Object> a(boolean z, MainDrawerConfig mainDrawerConfig, VipHint vipHint) {
        return a(z, mainDrawerConfig, vipHint, (List<PopUpListDialogData>) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0476 A[Catch: JSONException -> 0x0077, TryCatch #1 {JSONException -> 0x0077, blocks: (B:7:0x007f, B:13:0x00a8, B:15:0x00b0, B:16:0x00b3, B:18:0x0111, B:21:0x011f, B:24:0x014e, B:27:0x0155, B:28:0x0158, B:30:0x0184, B:32:0x01a1, B:35:0x01ad, B:71:0x02ab, B:75:0x02bd, B:78:0x02ca, B:84:0x02e7, B:88:0x02f6, B:99:0x031d, B:101:0x032c, B:102:0x033a, B:104:0x0340, B:107:0x0355, B:108:0x0376, B:110:0x037d, B:111:0x039e, B:113:0x03a6, B:115:0x03c7, B:119:0x03d5, B:122:0x03dc, B:124:0x03ea, B:127:0x0404, B:128:0x040a, B:129:0x0410, B:131:0x0425, B:133:0x042b, B:134:0x0435, B:136:0x043a, B:138:0x0448, B:140:0x044e, B:141:0x046b, B:143:0x0476, B:145:0x0482, B:148:0x0490, B:150:0x049b, B:151:0x04a6, B:153:0x04b1, B:154:0x04bc, B:156:0x04c7, B:158:0x04e6, B:160:0x04ec, B:162:0x04f2, B:164:0x04fa, B:167:0x0508, B:169:0x050f, B:170:0x0512, B:172:0x051a, B:174:0x0526, B:176:0x052c, B:179:0x04d5, B:190:0x0224, B:196:0x024a), top: B:6:0x007f }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x04ec A[Catch: JSONException -> 0x0077, TryCatch #1 {JSONException -> 0x0077, blocks: (B:7:0x007f, B:13:0x00a8, B:15:0x00b0, B:16:0x00b3, B:18:0x0111, B:21:0x011f, B:24:0x014e, B:27:0x0155, B:28:0x0158, B:30:0x0184, B:32:0x01a1, B:35:0x01ad, B:71:0x02ab, B:75:0x02bd, B:78:0x02ca, B:84:0x02e7, B:88:0x02f6, B:99:0x031d, B:101:0x032c, B:102:0x033a, B:104:0x0340, B:107:0x0355, B:108:0x0376, B:110:0x037d, B:111:0x039e, B:113:0x03a6, B:115:0x03c7, B:119:0x03d5, B:122:0x03dc, B:124:0x03ea, B:127:0x0404, B:128:0x040a, B:129:0x0410, B:131:0x0425, B:133:0x042b, B:134:0x0435, B:136:0x043a, B:138:0x0448, B:140:0x044e, B:141:0x046b, B:143:0x0476, B:145:0x0482, B:148:0x0490, B:150:0x049b, B:151:0x04a6, B:153:0x04b1, B:154:0x04bc, B:156:0x04c7, B:158:0x04e6, B:160:0x04ec, B:162:0x04f2, B:164:0x04fa, B:167:0x0508, B:169:0x050f, B:170:0x0512, B:172:0x051a, B:174:0x0526, B:176:0x052c, B:179:0x04d5, B:190:0x0224, B:196:0x024a), top: B:6:0x007f }] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0507  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x050f A[Catch: JSONException -> 0x0077, TryCatch #1 {JSONException -> 0x0077, blocks: (B:7:0x007f, B:13:0x00a8, B:15:0x00b0, B:16:0x00b3, B:18:0x0111, B:21:0x011f, B:24:0x014e, B:27:0x0155, B:28:0x0158, B:30:0x0184, B:32:0x01a1, B:35:0x01ad, B:71:0x02ab, B:75:0x02bd, B:78:0x02ca, B:84:0x02e7, B:88:0x02f6, B:99:0x031d, B:101:0x032c, B:102:0x033a, B:104:0x0340, B:107:0x0355, B:108:0x0376, B:110:0x037d, B:111:0x039e, B:113:0x03a6, B:115:0x03c7, B:119:0x03d5, B:122:0x03dc, B:124:0x03ea, B:127:0x0404, B:128:0x040a, B:129:0x0410, B:131:0x0425, B:133:0x042b, B:134:0x0435, B:136:0x043a, B:138:0x0448, B:140:0x044e, B:141:0x046b, B:143:0x0476, B:145:0x0482, B:148:0x0490, B:150:0x049b, B:151:0x04a6, B:153:0x04b1, B:154:0x04bc, B:156:0x04c7, B:158:0x04e6, B:160:0x04ec, B:162:0x04f2, B:164:0x04fa, B:167:0x0508, B:169:0x050f, B:170:0x0512, B:172:0x051a, B:174:0x0526, B:176:0x052c, B:179:0x04d5, B:190:0x0224, B:196:0x024a), top: B:6:0x007f }] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x051a A[Catch: JSONException -> 0x0077, TryCatch #1 {JSONException -> 0x0077, blocks: (B:7:0x007f, B:13:0x00a8, B:15:0x00b0, B:16:0x00b3, B:18:0x0111, B:21:0x011f, B:24:0x014e, B:27:0x0155, B:28:0x0158, B:30:0x0184, B:32:0x01a1, B:35:0x01ad, B:71:0x02ab, B:75:0x02bd, B:78:0x02ca, B:84:0x02e7, B:88:0x02f6, B:99:0x031d, B:101:0x032c, B:102:0x033a, B:104:0x0340, B:107:0x0355, B:108:0x0376, B:110:0x037d, B:111:0x039e, B:113:0x03a6, B:115:0x03c7, B:119:0x03d5, B:122:0x03dc, B:124:0x03ea, B:127:0x0404, B:128:0x040a, B:129:0x0410, B:131:0x0425, B:133:0x042b, B:134:0x0435, B:136:0x043a, B:138:0x0448, B:140:0x044e, B:141:0x046b, B:143:0x0476, B:145:0x0482, B:148:0x0490, B:150:0x049b, B:151:0x04a6, B:153:0x04b1, B:154:0x04bc, B:156:0x04c7, B:158:0x04e6, B:160:0x04ec, B:162:0x04f2, B:164:0x04fa, B:167:0x0508, B:169:0x050f, B:170:0x0512, B:172:0x051a, B:174:0x0526, B:176:0x052c, B:179:0x04d5, B:190:0x0224, B:196:0x024a), top: B:6:0x007f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<java.lang.Object> a(boolean r38, com.netease.cloudmusic.meta.virtual.MainDrawerConfig r39, com.netease.cloudmusic.meta.VipHint r40, java.util.List<com.netease.cloudmusic.meta.PopUpListDialogData> r41) {
        /*
            Method dump skipped, instructions count: 1352
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.cloudmusic.c.a.a.a(boolean, com.netease.cloudmusic.meta.virtual.MainDrawerConfig, com.netease.cloudmusic.meta.VipHint, java.util.List):java.util.List");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public Map<Integer, PlayList> a(String str, int i) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("name", str);
            hashMap.put("privacy", i + "");
            hashMap.put("checkToken", bo.a());
            JSONObject f = ((com.netease.cloudmusic.network.i.d.a) c.a("playlist/create").a(hashMap)).f();
            HashMap hashMap2 = new HashMap();
            int i2 = f.getInt("code");
            if (i2 == 200) {
                TaskCenterHelper.f7374a.a("iot_trial_watch_playlist_create");
                hashMap2.put(1, f(f.getJSONObject(ResExposureReq.ExposureRecord.RES_POS_PLAYLIST)));
            } else {
                PlayList playList = null;
                if (i2 == 507) {
                    hashMap2.put(2, null);
                } else if (i2 == 400) {
                    hashMap2.put(4, null);
                } else if (i2 == 521) {
                    String string = f.isNull("message") ? null : f.getString("message");
                    if (string != null) {
                        playList = new PlayList();
                        playList.setDescription(string);
                    }
                    hashMap2.put(5, playList);
                } else {
                    c(i2);
                    hashMap2.put(3, null);
                }
            }
            return hashMap2;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public Map<Integer, MusicInfo> a(String str, boolean z) {
        HashMap hashMap = new HashMap();
        if (z) {
            hashMap.put(UpgradeManager.UpgradeConst.UPGRADE_HAS_RESULT, "true");
        } else {
            hashMap.put("type", "0");
        }
        hashMap.put(z ? "songdata" : "songs", str);
        try {
            JSONObject jSONObject = new JSONObject(c.a(z ? "user/init/playlist" : "search/match/new", hashMap).g());
            HashMap hashMap2 = new HashMap();
            int i = jSONObject.getInt("code");
            if (i == 200) {
                JSONArray jSONArray = jSONObject.getJSONObject(UpgradeManager.UpgradeConst.UPGRADE_HAS_RESULT).getJSONArray("ids");
                List<MusicInfo> c2 = c(jSONObject.getJSONObject(UpgradeManager.UpgradeConst.UPGRADE_HAS_RESULT).getJSONArray("songs"));
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    hashMap2.put(Integer.valueOf(jSONArray.getInt(i2)), c2.get(i2));
                }
            } else {
                c(i);
            }
            return hashMap2;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00f7 A[Catch: JSONException -> 0x02a2, TryCatch #0 {JSONException -> 0x02a2, blocks: (B:6:0x001e, B:9:0x0033, B:13:0x003b, B:15:0x0041, B:19:0x007d, B:20:0x0066, B:24:0x00bd, B:26:0x00f7, B:27:0x010c, B:29:0x0120, B:30:0x0129, B:32:0x0140, B:34:0x0146, B:36:0x0150, B:37:0x0156, B:42:0x0161, B:44:0x016a, B:46:0x0170, B:47:0x017f, B:49:0x0185, B:51:0x0193, B:52:0x0197, B:54:0x019d, B:57:0x01a9, B:62:0x01ad, B:63:0x01b3, B:65:0x01cb, B:66:0x01d2, B:68:0x01da, B:75:0x01e6, B:77:0x01f3, B:82:0x0201, B:87:0x020d, B:92:0x0219, B:97:0x0225, B:102:0x0231, B:104:0x0238, B:106:0x023e, B:107:0x0248, B:109:0x024e, B:115:0x0263, B:120:0x026f, B:125:0x027b, B:129:0x0285, B:133:0x028f, B:145:0x009b, B:147:0x00a1), top: B:5:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0120 A[Catch: JSONException -> 0x02a2, TryCatch #0 {JSONException -> 0x02a2, blocks: (B:6:0x001e, B:9:0x0033, B:13:0x003b, B:15:0x0041, B:19:0x007d, B:20:0x0066, B:24:0x00bd, B:26:0x00f7, B:27:0x010c, B:29:0x0120, B:30:0x0129, B:32:0x0140, B:34:0x0146, B:36:0x0150, B:37:0x0156, B:42:0x0161, B:44:0x016a, B:46:0x0170, B:47:0x017f, B:49:0x0185, B:51:0x0193, B:52:0x0197, B:54:0x019d, B:57:0x01a9, B:62:0x01ad, B:63:0x01b3, B:65:0x01cb, B:66:0x01d2, B:68:0x01da, B:75:0x01e6, B:77:0x01f3, B:82:0x0201, B:87:0x020d, B:92:0x0219, B:97:0x0225, B:102:0x0231, B:104:0x0238, B:106:0x023e, B:107:0x0248, B:109:0x024e, B:115:0x0263, B:120:0x026f, B:125:0x027b, B:129:0x0285, B:133:0x028f, B:145:0x009b, B:147:0x00a1), top: B:5:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0150 A[Catch: JSONException -> 0x02a2, TryCatch #0 {JSONException -> 0x02a2, blocks: (B:6:0x001e, B:9:0x0033, B:13:0x003b, B:15:0x0041, B:19:0x007d, B:20:0x0066, B:24:0x00bd, B:26:0x00f7, B:27:0x010c, B:29:0x0120, B:30:0x0129, B:32:0x0140, B:34:0x0146, B:36:0x0150, B:37:0x0156, B:42:0x0161, B:44:0x016a, B:46:0x0170, B:47:0x017f, B:49:0x0185, B:51:0x0193, B:52:0x0197, B:54:0x019d, B:57:0x01a9, B:62:0x01ad, B:63:0x01b3, B:65:0x01cb, B:66:0x01d2, B:68:0x01da, B:75:0x01e6, B:77:0x01f3, B:82:0x0201, B:87:0x020d, B:92:0x0219, B:97:0x0225, B:102:0x0231, B:104:0x0238, B:106:0x023e, B:107:0x0248, B:109:0x024e, B:115:0x0263, B:120:0x026f, B:125:0x027b, B:129:0x0285, B:133:0x028f, B:145:0x009b, B:147:0x00a1), top: B:5:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01e0  */
    @Override // com.netease.cloudmusic.c.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.Map<java.lang.String, java.lang.String> a(boolean r23, java.lang.String r24, java.util.List<java.lang.Long> r25, java.util.List<java.lang.Long> r26, long r27, java.util.Set<java.lang.Long> r29, boolean r30, boolean r31) {
        /*
            Method dump skipped, instructions count: 689
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.cloudmusic.c.a.a.a(boolean, java.lang.String, java.util.List, java.util.List, long, java.util.Set, boolean, boolean):java.util.Map");
    }

    @Override // com.netease.cloudmusic.c.a
    public void a(long j, int i, String str, int i2, int i3) {
        try {
            int i4 = new JSONObject(c.a(String.format("radio/sport/skip?songId=%d&alg=%s&time=%d&sbpm=%d&rbpm=%d", Long.valueOf(j), str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3))).g()).getInt("code");
            if (i4 == 200) {
                return;
            }
            c(i4);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public void a(long j, int i, boolean z) {
        ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("dj/playrecord/upload").c("isListened", String.valueOf(z))).c("listenLocation", String.valueOf(i))).c("programId", String.valueOf(j))).e();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public void a(long j, ArrayList<Long> arrayList) {
        ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("activity/attract/artist/song/listened").c("songId", j + "")).c("artistIds", TextUtils.join(",", arrayList))).e();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public void a(boolean z, String str, String str2) {
        ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("coop/xiaoice/program/like").c("like", z + "")).c("programItemId", str)).c("scene", str2)).e();
    }

    @Override // com.netease.cloudmusic.c.a
    public boolean a(long j) {
        try {
            JSONObject jSONObject = new JSONObject(c.a("playlist/delete/?pid=" + j).g());
            int i = jSONObject.getInt("code");
            if (i == 200) {
                return true;
            }
            if (i == 518) {
                a(i, jSONObject.isNull(NotificationCompat.CATEGORY_MESSAGE) ? null : jSONObject.optString(NotificationCompat.CATEGORY_MESSAGE));
                return false;
            }
            c(i);
            return false;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public boolean a(long j, List<Long> list, List<Long> list2) {
        try {
            JSONObject f = c.a("song/like/change?checkPoint=" + j).f();
            if (f.getInt("code") != 200) {
                c(f.getInt("code"));
                return false;
            }
            if (!f.isNull("reloadLiked") && f.getBoolean("reloadLiked")) {
                bh.a().edit().remove("starMusicIdCheckPoint").commit();
                return true;
            }
            bh.a().edit().putLong("starMusicIdCheckPoint", f.getLong("checkPoint")).commit();
            if (!f.isNull("likedAdded")) {
                JSONArray jSONArray = f.getJSONArray("likedAdded");
                for (int i = 0; i < jSONArray.length(); i++) {
                    list.add(Long.valueOf(jSONArray.getLong(i)));
                }
            }
            if (!f.isNull("likedCanceled")) {
                JSONArray jSONArray2 = f.getJSONArray("likedCanceled");
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    list2.add(Long.valueOf(jSONArray2.getLong(i2)));
                }
            }
            return false;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public int[] a(long j, int i) throws JSONException {
        JSONObject jSONObject = new JSONObject(c.a(String.format(Locale.CHINESE, "%s?resourceIds=[%d]&resourceType=%d&fixliked=true&needupgradedinfo=true", "resource/commentInfo/list", Long.valueOf(j), Integer.valueOf(i))).g());
        int i2 = jSONObject.getInt("code");
        JSONArray jSONArray = jSONObject.getJSONArray("data");
        if (i2 != 200) {
            c(i2);
        } else if (jSONArray.length() > 0) {
            return new int[]{jSONArray.getJSONObject(0).optInt("commentCount"), jSONArray.getJSONObject(0).optInt("likedCount"), jSONArray.getJSONObject(0).optBoolean("liked") ? 1 : 0, -1, -1, -1, jSONArray.getJSONObject(0).optBoolean("commentUpgraded") ? 1 : 0};
        }
        return new int[]{0, 0, 0, 0, 0, 0, 0};
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public int[] a(long j, int i, boolean z, boolean z2, boolean z3, List<Long> list, Map<Long, PlayerZoneEntryInfo> map, List<Long> list2, LyricEntryInfo lyricEntryInfo, PlayerSongShareInfo playerSongShareInfo) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        JSONObject optJSONObject;
        JSONObject optJSONObject2;
        JSONArray jSONArray;
        JSONObject jSONObject;
        String str8;
        JSONArray jSONArray2;
        String str9;
        String str10;
        int i2;
        List<Long> list3 = list2;
        String str11 = "entryConfig";
        String str12 = "code";
        try {
            HashMap hashMap = new HashMap();
            if (z2) {
                str = "linkText";
                str2 = com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID;
                hashMap.put("/api/springtone/exist", String.format("{'songid':%d}", Long.valueOf(j)));
            } else {
                str = "linkText";
                str2 = com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID;
            }
            String str13 = "iconType";
            if (z3) {
                hashMap.put("/api/usertool/ring/song/check", String.format("{'songId':%d}", Long.valueOf(j)));
            }
            if (z) {
                str3 = "animationType";
                str4 = "show";
                hashMap.put("/api/resource/commentInfo/list", String.format("{'resourceIds':[%d], 'resourceType':%d, 'fixliked':true, 'needupgradedinfo':true}", Long.valueOf(j), Integer.valueOf(i)));
            } else {
                str3 = "animationType";
                str4 = "show";
            }
            if (!bg.c() || list == null) {
                str5 = "biz";
            } else {
                str5 = "biz";
                hashMap.put("/api/mlivestream/entrance/playpage/v5/get", String.format("{'artistIds':[%s],'songId':%d}", bt.a(list, ","), Long.valueOf(j)));
            }
            if (list3 != null && list2.size() > 0) {
                hashMap.put("/api/zone/songplay/entry/get", String.format("{'songIds':[%s]}", bt.a(list3, ",")));
            }
            String str14 = "";
            if (lyricEntryInfo != null) {
                Object[] objArr = new Object[2];
                objArr[0] = Long.valueOf(j);
                StringBuilder sb = new StringBuilder();
                sb.append(!bg.b());
                sb.append("");
                objArr[1] = sb.toString();
                hashMap.put("/api/content/exposure/songplay/entrance/animation", String.format("{'songId':%d, 'isNewUser':%s}", objArr));
            }
            if (playerSongShareInfo != null) {
                hashMap.put("/api/music/songshare/text/recommend/get", String.format("{'songId':%d}", Long.valueOf(j)));
            }
            JSONObject f = ((com.netease.cloudmusic.network.i.d.a) c.b().a(hashMap)).f();
            int i3 = f.getInt("code");
            int[] iArr = new int[4];
            if (i3 != 200) {
                try {
                    c(i3);
                    return null;
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                    throw new com.netease.cloudmusic.network.exception.a(1, e);
                }
            }
            if (z2) {
                JSONObject jSONObject2 = f.getJSONObject("/api/springtone/exist");
                iArr[0] = (jSONObject2 == null || jSONObject2.getInt("code") != 200) ? -1 : jSONObject2.getInt("hasring");
            } else {
                iArr[0] = -1;
            }
            if (z3) {
                JSONObject jSONObject3 = f.getJSONObject("/api/usertool/ring/song/check");
                if (jSONObject3 == null || jSONObject3.getInt("code") != 200) {
                    iArr[3] = -1;
                } else {
                    JSONObject jSONObject4 = jSONObject3.getJSONObject("data");
                    if (jSONObject4 != null) {
                        iArr[3] = jSONObject4.optBoolean("canUse") ? 1 : 0;
                    } else {
                        iArr[3] = -1;
                    }
                }
            } else {
                iArr[3] = -1;
            }
            if (z) {
                JSONObject jSONObject5 = f.getJSONObject("/api/resource/commentInfo/list");
                if (jSONObject5 == null || jSONObject5.getInt("code") != 200) {
                    iArr[1] = -1;
                } else {
                    JSONArray jSONArray3 = jSONObject5.getJSONArray("data");
                    iArr[1] = jSONArray3.length() > 0 ? jSONArray3.getJSONObject(0).optInt("commentCount") : -1;
                    if (jSONArray3.length() > 0 && jSONArray3.getJSONObject(0).optBoolean("commentUpgraded")) {
                        i2 = 1;
                        iArr[2] = i2;
                    }
                    i2 = 0;
                    iArr[2] = i2;
                }
            } else {
                iArr[1] = -1;
            }
            if (list3 == null || list2.size() <= 0 || f.isNull("/api/zone/songplay/entry/get") || (jSONObject = f.getJSONObject("/api/zone/songplay/entry/get")) == null || jSONObject.getInt("code") != 200 || jSONObject.isNull("data")) {
                str6 = "code";
            } else {
                JSONArray jSONArray4 = jSONObject.getJSONArray("data");
                int i4 = 0;
                while (i4 < jSONArray4.length()) {
                    if (jSONArray4.isNull(i4)) {
                        jSONArray2 = jSONArray4;
                        str9 = str11;
                        str8 = str12;
                    } else {
                        JSONObject jSONObject6 = jSONArray4.getJSONObject(i4);
                        str8 = str12;
                        long optLong = jSONObject6.optLong("songId");
                        if (list3.contains(Long.valueOf(optLong))) {
                            list3.remove(Long.valueOf(optLong));
                            PlayerZoneEntryInfo playerZoneEntryInfo = new PlayerZoneEntryInfo();
                            if (!jSONObject6.isNull(str11)) {
                                JSONObject jSONObject7 = jSONObject6.getJSONObject(str11);
                                playerZoneEntryInfo.setTitle(jSONObject7.isNull(GameJsonKeys.SHARE_TITLE) ? "" : jSONObject7.optString(GameJsonKeys.SHARE_TITLE));
                                playerZoneEntryInfo.setPicUrl(jSONObject7.isNull("picUrl") ? "" : jSONObject7.optString("picUrl"));
                                playerZoneEntryInfo.setDescription(jSONObject7.isNull("description") ? "" : jSONObject7.optString("description"));
                                playerZoneEntryInfo.setTargetUrl(jSONObject7.isNull("targetUrl") ? "" : jSONObject7.optString("targetUrl"));
                                playerZoneEntryInfo.setZone(jSONObject7.isNull("zone") ? "" : jSONObject7.optString("zone"));
                                playerZoneEntryInfo.setMusicId(optLong);
                                if (!jSONObject7.isNull("logInfo")) {
                                    HashMap hashMap2 = new HashMap();
                                    jSONArray2 = jSONArray4;
                                    JSONObject jSONObject8 = jSONObject7.getJSONObject("logInfo");
                                    Iterator<String> keys = jSONObject8.keys();
                                    while (keys.hasNext()) {
                                        String next = keys.next();
                                        if (!bt.c(next) || jSONObject8.isNull(next)) {
                                            str10 = str11;
                                        } else {
                                            str10 = str11;
                                            hashMap2.put(next, jSONObject8.getString(next));
                                        }
                                        str11 = str10;
                                    }
                                    str9 = str11;
                                    playerZoneEntryInfo.setLogInfo(hashMap2);
                                    map.put(Long.valueOf(optLong), playerZoneEntryInfo);
                                }
                            }
                            jSONArray2 = jSONArray4;
                            str9 = str11;
                            map.put(Long.valueOf(optLong), playerZoneEntryInfo);
                        } else {
                            jSONArray2 = jSONArray4;
                            str9 = str11;
                        }
                    }
                    i4++;
                    jSONArray4 = jSONArray2;
                    list3 = list2;
                    str12 = str8;
                    str11 = str9;
                }
                str6 = str12;
                Iterator<Long> it = list2.iterator();
                while (it.hasNext()) {
                    map.put(it.next(), bf.a());
                }
                list2.clear();
            }
            if (lyricEntryInfo != null) {
                JSONObject jSONObject9 = f.getJSONObject("/api/content/exposure/songplay/entrance/animation");
                ArrayList arrayList = new ArrayList();
                str7 = str6;
                if (jSONObject9 != null && jSONObject9.getInt(str7) == 200 && !jSONObject9.isNull("data") && (jSONArray = jSONObject9.getJSONArray("data")) != null && jSONArray.length() > 0) {
                    int i5 = 0;
                    while (i5 < jSONArray.length()) {
                        LyricEntryInfo.LyricEntry lyricEntry = new LyricEntryInfo.LyricEntry();
                        JSONObject jSONObject10 = jSONArray.getJSONObject(i5);
                        String str15 = str5;
                        if (!jSONObject10.isNull(str15)) {
                            lyricEntry.setBiz(jSONObject10.getString(str15));
                        }
                        String str16 = str4;
                        if (!jSONObject10.isNull(str16)) {
                            lyricEntry.setShow(jSONObject10.getBoolean(str16));
                        }
                        String str17 = str3;
                        JSONArray jSONArray5 = jSONArray;
                        if (!jSONObject10.isNull(str17)) {
                            lyricEntry.setAnimationType(jSONObject10.getInt(str17));
                        }
                        String str18 = str13;
                        if (!jSONObject10.isNull(str18)) {
                            lyricEntry.setIconType(jSONObject10.getInt(str18));
                        }
                        arrayList.add(lyricEntry);
                        i5++;
                        str13 = str18;
                        str5 = str15;
                        str4 = str16;
                        str3 = str17;
                        jSONArray = jSONArray5;
                    }
                }
                lyricEntryInfo.setSongId(j);
                lyricEntryInfo.setLyricEntries(arrayList);
            } else {
                str7 = str6;
            }
            if (playerSongShareInfo != null && !f.isNull("/api/music/songshare/text/recommend/get") && (optJSONObject = f.optJSONObject("/api/music/songshare/text/recommend/get")) != null && optJSONObject.getInt(str7) == 200 && !optJSONObject.isNull("data") && (optJSONObject2 = optJSONObject.optJSONObject("data")) != null) {
                String str19 = str2;
                playerSongShareInfo.setId(optJSONObject2.isNull(str19) ? -1 : optJSONObject2.optInt(str19));
                String str20 = str;
                playerSongShareInfo.setLinkText(optJSONObject2.isNull(str20) ? "" : optJSONObject2.optString(str20));
                if (!optJSONObject2.isNull("rcmdText")) {
                    str14 = optJSONObject2.optString("rcmdText");
                }
                playerSongShareInfo.setRcmdText(str14);
            }
            return iArr;
        } catch (JSONException e3) {
            e = e3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public Object[] a(long j, int i, int i2, boolean z, boolean z2, PageValue pageValue) {
        String str;
        Object[] objArr = new Object[2];
        HashMap hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("limit", String.valueOf(i));
            jSONObject.put("offset", String.valueOf(i2));
            jSONObject.put("radioId", String.valueOf(j));
            jSONObject.put("asc", String.valueOf(z));
            jSONObject.put("updateOrder", String.valueOf(z2));
            hashMap.put("/api/v1/dj/program/byradio", jSONObject.toString());
            if (i != 0) {
                str = "/api/dj/playrecord/radio/get";
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("limit", String.valueOf(i));
                jSONObject2.put("offset", String.valueOf(i2));
                jSONObject2.put("radioId", String.valueOf(j));
                jSONObject2.put("asc", String.valueOf(z ? 1 : -1));
                hashMap.put("/api/dj/playrecord/radio/get", jSONObject2.toString());
            } else {
                str = null;
            }
            JSONObject f = ((com.netease.cloudmusic.network.i.d.a) c.b().a(hashMap)).f();
            int i3 = f.getInt("code");
            if (i3 != 200) {
                c(i3);
                return null;
            }
            JSONObject jSONObject3 = f.getJSONObject("/api/v1/dj/program/byradio");
            int i4 = jSONObject3.getInt("code");
            if (i4 != 200) {
                c(i4);
                return null;
            }
            List<Program> e2 = e(jSONObject3.getJSONArray("programs"));
            if (pageValue != null) {
                pageValue.setHasMore(jSONObject3.getBoolean("more"));
            }
            objArr[0] = e2;
            objArr[1] = a(str, f);
            return objArr;
        } catch (JSONException e3) {
            e3.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public Object[] a(long j, int i, long j2) throws JSONException {
        HashMap hashMap = new HashMap();
        hashMap.put(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, j + "_" + j2);
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append("");
        hashMap.put("br", sb.toString());
        String g = com.netease.cloudmusic.network.e.b.g();
        if (g != null) {
            hashMap.put("sp", g);
        }
        JSONObject f = ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("song/enhance/download/url").a(hashMap)).b(5000)).c(5000)).f();
        int i2 = f.getInt("code");
        if (i2 != 200) {
            d(i2);
            return null;
        }
        if (f.isNull("data")) {
            return null;
        }
        JSONObject jSONObject = f.getJSONObject("data");
        return new Object[]{Integer.valueOf(jSONObject.getInt("code")), jSONObject.getString("url"), Integer.valueOf(jSONObject.getInt("br")), Long.valueOf(jSONObject.getLong("size")), jSONObject.getString(GameJsonKeys.MD5), jSONObject.getString("type"), Integer.valueOf(jSONObject.getInt("fee")), Integer.valueOf(jSONObject.getInt("payed")), Integer.valueOf(jSONObject.getInt("flag"))};
    }

    @Override // com.netease.cloudmusic.c.a
    public MusicInfo b(long j) {
        List<MusicInfo> a2 = a(Arrays.asList(Long.valueOf(j)));
        if (a2 == null || a2.size() <= 0) {
            return null;
        }
        return a2.get(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public ArrayList<Plugin> b(String str) {
        return (ArrayList) ((com.netease.cloudmusic.network.i.d.a) c.a("usertool/mobile/versionCheck").c("versions", str)).a(new j<ArrayList<Plugin>>() { // from class: com.netease.cloudmusic.c.a.a.1
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public ArrayList<Plugin> parse(JSONObject jSONObject) throws JSONException {
                if (jSONObject.getInt("code") != 200 || jSONObject.isNull("data")) {
                    return null;
                }
                ArrayList<Plugin> arrayList = new ArrayList<>();
                JSONArray jSONArray = jSONObject.getJSONArray("data");
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(Plugin.parsePlugin(jSONArray.getJSONObject(i)));
                }
                return arrayList;
            }
        }, new int[0]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> b() {
        return (List) ((com.netease.cloudmusic.network.i.d.a) c.a("baby/fm/get").c("limit", "10")).a(new j<List<MusicInfo>>() { // from class: com.netease.cloudmusic.c.a.a.12
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public List<MusicInfo> parse(JSONObject jSONObject) throws JSONException {
                return a.c(jSONObject.getJSONArray("data"));
            }
        }, new int[0]);
    }

    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> b(int i) throws JSONException {
        JSONObject jSONObject = new JSONObject(c.a(String.format("radio/sport/get?bpm=%d", Integer.valueOf(i))).g());
        int i2 = jSONObject.getInt("code");
        if (i2 == 200) {
            return d(jSONObject.getJSONArray("data"));
        }
        c(i2);
        return Collections.emptyList();
    }

    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> b(long j, int i, String str) {
        try {
            JSONObject jSONObject = new JSONObject(c.a(String.format("v1/radio/trash/add?songId=%d&time=%d&alg=%s", Long.valueOf(j), Integer.valueOf(i), str)).g());
            int i2 = jSONObject.getInt("code");
            if (i2 == 200) {
                return jSONObject.isNull("songs") ? Collections.emptyList() : c(jSONObject.getJSONArray("songs"));
            }
            c(i2);
            return Collections.emptyList();
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> b(long j, int i, String str, String str2) {
        return (List) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("zone/fm/skip").c("zone", str2)).c(com.netease.mam.agent.d.d.a.dJ, i + "")).c("alg", str)).c("songId", j + "")).a(new j<List<MusicInfo>>() { // from class: com.netease.cloudmusic.c.a.a.14
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public List<MusicInfo> parse(JSONObject jSONObject) throws JSONException {
                return a.c(jSONObject.getJSONObject("data").getJSONArray("songs"));
            }
        }, new int[0]);
    }

    public List<MusicInfo> b(Map<Long, Integer> map, Map<Long, SongPrivilege> map2, LongSparseArray<Pair<Boolean, SongRelatedVideo>> longSparseArray) {
        ArrayList arrayList = new ArrayList();
        if (map == null || map.size() == 0) {
            return arrayList;
        }
        JSONArray jSONArray = new JSONArray();
        try {
            try {
                JSONArray jSONArray2 = jSONArray;
                for (Map.Entry<Long, Integer> entry : map.entrySet()) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, entry.getKey());
                        jSONObject.put("v", entry.getValue());
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    jSONArray2.put(jSONObject);
                    int i = (jSONArray2.length() == 1000 || i == map.size() - 1) ? 0 : i + 1;
                    if (jSONArray2.length() == 0) {
                        return arrayList;
                    }
                    HashMap hashMap = new HashMap();
                    hashMap.put(com.netease.mam.agent.b.a.a.ah, jSONArray2.toString());
                    if (longSparseArray != null) {
                        hashMap.put("rv", "true");
                    }
                    JSONObject jSONObject2 = new JSONObject(c.a("v3/song/detail", hashMap).g());
                    int i2 = jSONObject2.getInt("code");
                    if (i2 != 200) {
                        try {
                            c(i2);
                            return arrayList;
                        } catch (IllegalStateException e3) {
                            e = e3;
                            e.printStackTrace();
                            throw new com.netease.cloudmusic.network.exception.a(1, e);
                        } catch (JSONException e4) {
                            e = e4;
                            e.printStackTrace();
                            throw new com.netease.cloudmusic.network.exception.a(1, e);
                        }
                    }
                    List<MusicInfo> a2 = a(jSONObject2.getJSONArray("songs"));
                    a(jSONObject2.getJSONArray("privileges"), map2);
                    if (longSparseArray != null && !jSONObject2.isNull("relatedVideos")) {
                        JSONObject jSONObject3 = jSONObject2.getJSONObject("relatedVideos");
                        Iterator<String> keys = jSONObject3.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            JSONObject jSONObject4 = jSONObject3.getJSONObject(next);
                            boolean optBoolean = jSONObject4.optBoolean("moreThanOne");
                            SongRelatedVideo songRelatedVideo = null;
                            if (!jSONObject4.isNull("video")) {
                                songRelatedVideo = m(jSONObject4.optJSONObject("video"));
                            }
                            longSparseArray.put(Long.parseLong(next), Pair.create(Boolean.valueOf(optBoolean), songRelatedVideo));
                        }
                    }
                    if (a2 != null && a2.size() > 0) {
                        arrayList.addAll(a2);
                    }
                    jSONArray2 = new JSONArray();
                }
                return arrayList;
            } catch (JSONException e5) {
                e = e5;
                e.printStackTrace();
                throw new com.netease.cloudmusic.network.exception.a(1, e);
            }
        } catch (IllegalStateException e6) {
            e = e6;
            e.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public void b(long j, int i, String str, int i2, int i3) {
        try {
            int i4 = new JSONObject(c.a(String.format("radio/sport/trash/add?songId=%d&alg=%s&time=%d&sbpm=%d&rbpm=%d", Long.valueOf(j), str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3))).g()).getInt("code");
            if (i4 == 200) {
                return;
            }
            c(i4);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public boolean b(long j, int i, long j2) {
        JSONObject jSONObject;
        int i2;
        try {
            jSONObject = new JSONObject(c.a(String.format("userLyricOffset/add?songId=%d&lyricVersion=%d&offset=%d", Long.valueOf(j), Integer.valueOf(i), Long.valueOf(j2))).g());
            i2 = jSONObject.getInt("code");
        } catch (i | JSONException e2) {
            e2.printStackTrace();
        }
        if (i2 == 200) {
            return true;
        }
        if (i2 == 400 || i2 == 500) {
            return false;
        }
        c(jSONObject.getInt("code"));
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public boolean b(Collection<Long> collection) {
        return ((Boolean) ((com.netease.cloudmusic.network.i.d.a) c.a("search/localsong/upload").c("ids", "[" + bt.a(collection, ",") + "]")).a(new j<Boolean>() { // from class: com.netease.cloudmusic.c.a.a.15
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Boolean parse(JSONObject jSONObject) {
                return true;
            }
        }, new int[0])).booleanValue();
    }

    @Override // com.netease.cloudmusic.c.a
    public Object[] b(long j, int i) throws JSONException {
        HashMap hashMap = new HashMap();
        hashMap.put(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, j + "");
        hashMap.put("r", i + "");
        String g = com.netease.cloudmusic.network.e.b.g();
        if (g != null) {
            hashMap.put("sp", g);
        }
        JSONObject jSONObject = new JSONObject(c.a("song/enhance/download/mv/url", hashMap).g());
        int i2 = jSONObject.getInt("code");
        if (i2 != 200) {
            d(i2);
            return null;
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
        int i3 = jSONObject2.getInt("fee");
        MvPrivilege mvPrivilege = new MvPrivilege();
        mvPrivilege.setFee(i3);
        mvPrivilege.setMvFee(jSONObject2.getInt("mvFee"));
        mvPrivilege.setSt(jSONObject2.getInt("st"));
        return new Object[]{Integer.valueOf(jSONObject2.getInt("code")), jSONObject2.getString("url"), Integer.valueOf(jSONObject2.getInt("r")), Long.valueOf(jSONObject2.getLong("size")), Integer.valueOf(i3), mvPrivilege};
    }

    @Override // com.netease.cloudmusic.c.a
    public int c(long j, int i) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, j + "");
            hashMap.put("brs", i + "");
            int i2 = new JSONObject(c.a("mv/download/del", hashMap).g()).getInt("code");
            if (i2 != 200) {
                d(i2);
            }
            return i2;
        } catch (i | JSONException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public LongSparseArray<Pair<Integer, Integer>> c(Collection<Long> collection) {
        return (LongSparseArray) ((com.netease.cloudmusic.network.i.d.a) c.a("song/free/trial/info").c("ids", "[" + bt.a(collection, ",") + "]")).a(new j<LongSparseArray<Pair<Integer, Integer>>>() { // from class: com.netease.cloudmusic.c.a.a.16
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public LongSparseArray<Pair<Integer, Integer>> parse(JSONObject jSONObject) throws JSONException {
                LongSparseArray<Pair<Integer, Integer>> longSparseArray = new LongSparseArray<>();
                JSONArray jSONArray = jSONObject.getJSONArray("data");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    longSparseArray.put(jSONObject2.getLong("songId"), Pair.create(Integer.valueOf(jSONObject2.getInt(LocalMusicMatchService.ACTION_START) * 1000), Integer.valueOf(jSONObject2.getInt("end") * 1000)));
                }
                return longSparseArray;
            }
        }, new int[0]);
    }

    @Override // com.netease.cloudmusic.c.a
    public SongPrivilege c(long j) {
        HashMap hashMap = new HashMap();
        hashMap.put("ids", String.format("[%s]", Long.valueOf(j)));
        try {
            JSONObject jSONObject = new JSONObject(c.a("song/enhance/privilege", hashMap).g());
            int i = jSONObject.getInt("code");
            if (i == 200) {
                return a(jSONObject.getJSONArray("data").getJSONObject(0));
            }
            c(i);
            return SongPrivilege.getDefualtSongPrivilege(SongPrivilege.Type.MOCK_DEFAULT_SERVER);
        } catch (IllegalStateException | JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> c() {
        try {
            int value = FreeTrialPrivilegeUtils.f5533a.a() ? FreeTrialScene.PRIVATE_FM.getValue() : 0;
            String d2 = WatchAgeRatingHelper.f7176a.d();
            JSONObject jSONObject = new JSONObject(((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("watch/fm/get").c("trialMode", String.valueOf(value))).c("age", d2)).c("scene", WatchFMModeManager.f6602a.a().getScene())).c("tagIds", WatchFMModeManager.f6602a.a().getTagIds())).g());
            int i = jSONObject.getInt("code");
            if (i == 200) {
                return c(jSONObject.getJSONArray("data"));
            }
            a(i, (String) null, (String) null);
            return Collections.emptyList();
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    public void c(int i) {
        a(i, (String) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public boolean c(String str) throws CMNetworkIOException {
        HashMap hashMap = new HashMap();
        hashMap.put("username", str);
        try {
            JSONObject f = ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("register/anonimous").a(hashMap)).b(HttpStatusCode.DNS_ERROR_BASE)).c(HttpStatusCode.DNS_ERROR_BASE)).f();
            if (f.getInt("code") != 200) {
                return false;
            }
            Profile profile = new Profile();
            profile.setUserId(f.getLong("userId"));
            profile.setNickname(NeteaseMusicApplication.a().getResources().getString(R.string.ahm));
            com.netease.cloudmusic.g.a.a().a(e.a.f3149b, Long.valueOf(profile.getUserId()));
            com.netease.cloudmusic.g.a.a().a(profile);
            Account account = new Account();
            account.setId(f.getLong("userId"));
            com.netease.cloudmusic.g.a.a().a(account);
            return true;
        } catch (i e2) {
            e2.printStackTrace();
            throw new CMNetworkIOException(e2);
        } catch (JSONException e3) {
            e3.printStackTrace();
            throw new CMNetworkIOException(new com.netease.cloudmusic.network.exception.a(1, e3));
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public Program d(long j) {
        try {
            JSONObject jSONObject = new JSONObject(c.a(String.format("dj/program/detail?id=%d", Long.valueOf(j))).g());
            if (jSONObject.getInt("code") == 200) {
                return g(jSONObject.getJSONObject("program"));
            }
            c(jSONObject.getInt("code"));
            return null;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> d() {
        try {
            JSONObject jSONObject = new JSONObject(c.a("v1/radio/get").g());
            int i = jSONObject.getInt("code");
            if (i == 200) {
                return c(jSONObject.getJSONArray("data"));
            }
            c(i);
            return Collections.emptyList();
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public List<MusicInfo> d(String str) {
        return (List) ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) c.a("zone/fm/get").c("limit", "3")).c("zone", str)).a(new j<List<MusicInfo>>() { // from class: com.netease.cloudmusic.c.a.a.11
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public List<MusicInfo> parse(JSONObject jSONObject) throws JSONException {
                return a.c(jSONObject.getJSONArray("data"));
            }
        }, new int[0]);
    }

    public List<MusicInfo> d(JSONArray jSONArray) throws JSONException {
        return a(jSONArray);
    }

    @Override // com.netease.cloudmusic.c.a
    public Set<Long> d(Collection<Long> collection) {
        HashMap hashMap = new HashMap(1);
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        Iterator<Long> it = collection.iterator();
        while (it.hasNext()) {
            hashSet2.add(it.next());
            if (hashSet2.size() == 200 || !it.hasNext()) {
                hashMap.put("songIds", "[" + bt.a(hashSet2, ",") + "]");
                hashSet.addAll((Collection) c.a("song/cache/clean/get", hashMap).a(new j<Set<Long>>() { // from class: com.netease.cloudmusic.c.a.a.4
                    @Override // com.netease.cloudmusic.network.b.j
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public Set<Long> parse(JSONObject jSONObject) throws JSONException {
                        HashSet hashSet3 = new HashSet();
                        JSONArray jSONArray = jSONObject.getJSONObject("data").getJSONArray("songs");
                        for (int i = 0; i < jSONArray.length(); i++) {
                            hashSet3.add(Long.valueOf(jSONArray.getLong(i)));
                        }
                        return hashSet3;
                    }
                }, new int[0]));
                hashSet2.clear();
            }
        }
        return hashSet;
    }

    @Override // com.netease.cloudmusic.c.a
    public int e(long j) {
        try {
            JSONObject jSONObject = new JSONObject(c.a(String.format("playlist/unsubscribe/?id=%d", Long.valueOf(j))).g());
            if (jSONObject.getInt("code") == 200) {
                return 1;
            }
            c(jSONObject.getInt("code"));
            return -1;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public Map<String, Object> e() {
        HashMap hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(c.a("djradio/carfm/get").g());
            int i = jSONObject.getInt("code");
            if (i != 200) {
                c(i);
                return hashMap;
            }
            if (!jSONObject.isNull("radio1")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("radio1");
                boolean z = jSONObject2.getBoolean("more");
                hashMap.put("radio1", e(jSONObject2.getJSONArray("programs")));
                hashMap.put("radio1More", Boolean.valueOf(z));
            }
            if (!jSONObject.isNull("radio2")) {
                JSONObject jSONObject3 = jSONObject.getJSONObject("radio2");
                boolean z2 = jSONObject3.getBoolean("more");
                hashMap.put("radio2", e(jSONObject3.getJSONArray("programs")));
                hashMap.put("radio2More", Boolean.valueOf(z2));
            }
            return hashMap;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public Pair<ArrayList<n.c>, Long> f(long j) {
        return (Pair) ((com.netease.cloudmusic.network.i.d.a) c.a("djprogram/babyfm/get").c("lastRcmdId", j + "")).a(new j<Pair<ArrayList<n.c>, Long>>() { // from class: com.netease.cloudmusic.c.a.a.2
            @Override // com.netease.cloudmusic.network.b.j
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public Pair<ArrayList<n.c>, Long> parse(JSONObject jSONObject) throws JSONException {
                ArrayList arrayList = new ArrayList();
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                long optLong = jSONObject2.optLong("groupId");
                JSONArray optJSONArray = jSONObject2.optJSONArray("radios");
                if (optJSONArray == null) {
                    return new Pair<>(arrayList, Long.valueOf(optLong));
                }
                int length = optJSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject3 = optJSONArray.getJSONObject(i);
                    List<Program> e2 = a.e(jSONObject3.getJSONArray("programs"));
                    PageValue pageValue = new PageValue();
                    pageValue.setIntValue(e2.size());
                    pageValue.setHasMore(jSONObject3.optBoolean("more"));
                    arrayList.add(new n.c(e2, pageValue, jSONObject3.optLong("radioId")));
                }
                return new Pair<>(arrayList, Long.valueOf(optLong));
            }
        }, new int[0]);
    }

    @Override // com.netease.cloudmusic.c.a
    public void g(long j) {
        HashMap hashMap = new HashMap();
        hashMap.put(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, j + "");
        hashMap.put("checkToken", bo.a());
        c.a("playlist/update/playcount", hashMap).e();
    }

    @Override // com.netease.cloudmusic.c.a
    public void h(long j) {
        c.a(String.format("album/play?id=%d", Long.valueOf(j))).e();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public PlayList i(long j) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put(com.netease.cloudmusic.module.transfer.a.b.EXTRA_ID, j + "");
            hashMap.put("n", "1000");
            hashMap.put("s", "5");
            hashMap.put("e_r", com.netease.cloudmusic.network.utils.a.a() + "");
            hashMap.put("cache_key", com.netease.cloudmusic.network.utils.a.a(hashMap));
            JSONObject jSONObject = new JSONObject(((com.netease.cloudmusic.network.i.d.a) c.a("playlist/v4/detail", hashMap).i(true)).g());
            if (jSONObject.getInt("code") == 200) {
                return a(jSONObject.getJSONObject(ResExposureReq.ExposureRecord.RES_POS_PLAYLIST), true, (LinkedHashMap<Long, MusicExtraInfo>) null, 0L, PlayList.class);
            }
            a(jSONObject.getInt("code"), (String) null, "playlist/v4/detail");
            return null;
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public void j(long j) {
        ((com.netease.cloudmusic.network.i.d.a) c.a("activity/attract/fm/song/listened").c("songId", j + "")).e();
    }

    @Override // com.netease.cloudmusic.c.a
    public String k(long j) {
        try {
            JSONObject jSONObject = new JSONObject(c.a("song/toast?id=" + j).g());
            if (jSONObject.getInt("code") != 200 || jSONObject.isNull("toast")) {
                return null;
            }
            return jSONObject.getString("toast");
        } catch (i | JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.netease.cloudmusic.c.a
    public void l(long j) {
        c.a(String.format("dj/program/listen?id=%d", Long.valueOf(j))).e();
    }

    @Override // com.netease.cloudmusic.c.a
    public MusicRewardInfo m(long j) throws JSONException {
        HashMap hashMap = new HashMap();
        hashMap.put("programid", String.valueOf(j));
        JSONObject jSONObject = new JSONObject(c.a("reward/djprogram/rewardinfo", hashMap).g());
        int i = jSONObject.getInt("code");
        if (i != 200) {
            c(i);
            return null;
        }
        MusicRewardInfo musicRewardInfo = new MusicRewardInfo();
        musicRewardInfo.setRewardCount(jSONObject.getInt("rewardCount"));
        musicRewardInfo.setTargetId(j);
        musicRewardInfo.setRewardType(2);
        musicRewardInfo.setCanReward(jSONObject.getBoolean("canReward"));
        return musicRewardInfo;
    }

    @Override // com.netease.cloudmusic.c.a
    public void n(long j) {
        HashMap hashMap = new HashMap();
        hashMap.put("taskId", String.valueOf(j));
        c.a("vipcenter/task/finish", hashMap).e();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.netease.cloudmusic.c.a
    public Pair<Integer, Integer> o(long j) {
        int i;
        try {
            JSONObject f = ((com.netease.cloudmusic.network.i.d.a) c.a("usertool/ring/song/refrain/get").c("songId", j + "")).f();
            int i2 = f.getInt("code");
            if (i2 != 200) {
                c(i2);
                return null;
            }
            JSONObject jSONObject = f.getJSONObject("data");
            if (jSONObject == null) {
                return null;
            }
            int i3 = -1;
            if (jSONObject.isNull(LocalMusicMatchService.ACTION_START) || jSONObject.isNull("end")) {
                i = -1;
            } else {
                i3 = jSONObject.getInt(LocalMusicMatchService.ACTION_START);
                i = jSONObject.getInt("end");
            }
            return Pair.create(Integer.valueOf(i3), Integer.valueOf(i));
        } catch (JSONException e2) {
            e2.printStackTrace();
            throw new com.netease.cloudmusic.network.exception.a(1, e2);
        }
    }
}

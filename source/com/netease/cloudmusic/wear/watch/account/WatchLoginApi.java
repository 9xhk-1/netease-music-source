package com.netease.cloudmusic.wear.watch.account;

import com.netease.cloudmusic.NeteaseMusicApplication;
import com.netease.cloudmusic.e;
import com.netease.cloudmusic.media.player.HttpStatusCode;
import com.netease.cloudmusic.meta.Account;
import com.netease.cloudmusic.meta.Profile;
import com.netease.cloudmusic.network.exception.CMNetworkIOException;
import com.netease.cloudmusic.network.exception.i;
import com.netease.cloudmusic.wear.watch.LoginManager;
import com.netease.cloudmusic.wear.watch.utils.ActiveLogUtils;
import com.netease.xtc.cloudmusic.R;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, d2 = {"Lcom/netease/cloudmusic/wear/watch/account/WatchLoginApi;", "", "()V", "anonymousLogin", "", "neteaseMusic_userWatchXtcChildRelease"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* renamed from: com.netease.cloudmusic.wear.watch.account.d, reason: from Kotlin metadata */
/* loaded from: classes.dex */
public final class WatchLoginApi {

    /* renamed from: a, reason: collision with root package name */
    public static final WatchLoginApi f5851a = new WatchLoginApi();

    private WatchLoginApi() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JvmStatic
    public static final boolean a() throws CMNetworkIOException {
        try {
            JSONObject f = ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) com.netease.cloudmusic.network.c.a("login/anon/device").b(HttpStatusCode.DNS_ERROR_BASE)).c(HttpStatusCode.DNS_ERROR_BASE)).f();
            int i = f.getInt("code");
            JSONObject optJSONObject = f.optJSONObject("data");
            if (i == 200 && optJSONObject != null) {
                Profile profile = new Profile();
                profile.setUserId(optJSONObject.getLong("userId"));
                profile.setNickname(NeteaseMusicApplication.a().getResources().getString(R.string.ahm));
                com.netease.cloudmusic.g.a.a().a(e.a.f3149b, Long.valueOf(profile.getUserId()));
                com.netease.cloudmusic.g.a.a().a(profile);
                Account account = new Account();
                account.setId(optJSONObject.getLong("userId"));
                com.netease.cloudmusic.g.a.a().a(account);
                LoginManager.f5778a.b().postValue(false);
                LoginManager.f5778a.a().postValue(true);
                ActiveLogUtils.a(3);
                return true;
            }
            return false;
        } catch (i e2) {
            e2.printStackTrace();
            throw new CMNetworkIOException(e2);
        } catch (JSONException e3) {
            e3.printStackTrace();
            throw new CMNetworkIOException(new com.netease.cloudmusic.network.exception.a(1, e3));
        }
    }
}

package com.netease.cloudmusic.wear.watch.login.captcha;

import android.net.Uri;
import com.netease.cloudmusic.network.b.j;
import com.netease.cloudmusic.network.exception.i;
import com.netease.cloudmusic.wear.watch.account.WatchLoginMonitorHelper;
import com.xtc.shareapi.share.shareobject.ShareCloudFileResource;
import java.net.URLEncoder;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/* compiled from: ProGuard */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\nJ\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u000f\u001a\u0004\u0018\u00010\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\nJ\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\u000e2\u0006\u0010\u0015\u001a\u00020\u0016J:\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\u0010\u0011\u001a\u0004\u0018\u00010\nJ&\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\n0\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/netease/cloudmusic/wear/watch/login/captcha/WatchCaptchaLoginApi;", "", "()V", "RESULT_API_CODE_FAILED", "", "RESULT_API_CODE_SUCCESS", "RESULT_NO_PROFILE", "RESULT_ONEPASS_NO_PROFILE", "RESULT_ONEPASS_UNSAFE", "SECRETE", "", "errorCodes", "", "cellphoneLogin", "Lkotlin/Pair;", "cellphone", "captcha", "regionCode", "checkCellphone", "Lcom/netease/cloudmusic/wear/watch/login/captcha/PhoneAccountInfo;", "parseLoginResult", "jsonResult", "Lorg/json/JSONObject;", "registerCellphone", "allowDefaultNickname", "", "sendCaptcha", "neteaseMusic_userWatchXtcChildRelease"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* renamed from: com.netease.cloudmusic.wear.watch.login.captcha.c, reason: from Kotlin metadata */
/* loaded from: classes2.dex */
public final class WatchCaptchaLoginApi {

    /* renamed from: a, reason: collision with root package name */
    public static final WatchCaptchaLoginApi f6347a = new WatchCaptchaLoginApi();

    /* renamed from: b, reason: collision with root package name */
    private static final int[] f6348b = {410, 411, 250, 501, 502, 503, 504, 505, 506, 508, 300, 303, 304, ShareCloudFileResource.WIDTH, IMediaPlayer.MEDIA_INFO_BUFFERING_END, 10001, 10002, 10003, 10004, 11000};

    private WatchCaptchaLoginApi() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pair a(com.netease.cloudmusic.network.i.d.a api, JSONObject jsonResult) {
        WatchLoginMonitorHelper watchLoginMonitorHelper = WatchLoginMonitorHelper.f5852a;
        Intrinsics.checkNotNullExpressionValue(api, "api");
        Intrinsics.checkNotNullExpressionValue(jsonResult, "jsonResult");
        watchLoginMonitorHelper.a("cellphone_captcha_req", api, jsonResult);
        Integer valueOf = Integer.valueOf(jsonResult.optInt("code"));
        String optString = jsonResult.optString("message");
        if (optString == null) {
            optString = "";
        }
        return new Pair(valueOf, optString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PhoneAccountInfo b(JSONObject jSONObject) {
        return new PhoneAccountInfo(Integer.valueOf(jSONObject.optInt("exist")), jSONObject.optString("nickname"), jSONObject.optString("avatarUrl"), jSONObject.optString("cellphone"), jSONObject.optString("countryCode"), Boolean.valueOf(jSONObject.optBoolean("hasPassword")), Boolean.valueOf(jSONObject.optBoolean("hasSnsBinded")), Integer.valueOf(jSONObject.optInt("code")), jSONObject.optString("message"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pair b(com.netease.cloudmusic.network.i.d.a api, JSONObject jsonResult) {
        WatchLoginMonitorHelper watchLoginMonitorHelper = WatchLoginMonitorHelper.f5852a;
        Intrinsics.checkNotNullExpressionValue(api, "api");
        Intrinsics.checkNotNullExpressionValue(jsonResult, "jsonResult");
        watchLoginMonitorHelper.a("cellphone_login_req", api, jsonResult);
        return f6347a.a(jsonResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pair c(JSONObject jsonResult) {
        Intrinsics.checkNotNullParameter(jsonResult, "jsonResult");
        return f6347a.a(jsonResult);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Pair<Integer, String> a(String str, String str2) throws i {
        final com.netease.cloudmusic.network.i.d.a a2 = com.netease.cloudmusic.network.c.a("sms/captcha/sent");
        com.netease.cloudmusic.network.i.d.a aVar = (com.netease.cloudmusic.network.i.d.a) a2.a("cellphone", str);
        Object[] objArr = new Object[2];
        objArr[0] = "ctcode";
        String str3 = "86";
        if (str2 != null) {
            if (!(!StringsKt.isBlank(str2))) {
                str2 = null;
            }
            if (str2 != null) {
                str3 = str2;
            }
        }
        objArr[1] = str3;
        Object a3 = ((com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) aVar.a(objArr)).a("secrete", "music_middleuser_andrwarelogin")).a((j<Object>) new j() { // from class: com.netease.cloudmusic.wear.watch.login.captcha.-$$Lambda$c$tJLu_XWo92i-B0FvFgNaAMLzEFo
            @Override // com.netease.cloudmusic.network.b.j
            public final Object parse(JSONObject jSONObject) {
                Pair a4;
                a4 = WatchCaptchaLoginApi.a(com.netease.cloudmusic.network.i.d.a.this, jSONObject);
                return a4;
            }
        }, true, 400);
        Intrinsics.checkNotNullExpressionValue(a3, "api.paramsObject(\"cellph…            }, true, 400)");
        return (Pair) a3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Pair<Integer, String> a(String str, String str2, String str3) throws i {
        final com.netease.cloudmusic.network.i.d.a a2 = com.netease.cloudmusic.network.c.a("login/cellphone");
        String[] strArr = new String[6];
        strArr[0] = "phone";
        strArr[1] = str;
        strArr[2] = "captcha";
        strArr[3] = str2;
        strArr[4] = "countrycode";
        String str4 = "86";
        if (str3 != null) {
            if (!(!StringsKt.isBlank(str3))) {
                str3 = null;
            }
            if (str3 != null) {
                str4 = str3;
            }
        }
        strArr[5] = str4;
        com.netease.cloudmusic.network.i.d.a aVar = (com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) a2.a(strArr)).e(true);
        j jVar = new j() { // from class: com.netease.cloudmusic.wear.watch.login.captcha.-$$Lambda$c$1X63e-FV5RM84vKxl8nr94i6oGk
            @Override // com.netease.cloudmusic.network.b.j
            public final Object parse(JSONObject jSONObject) {
                Pair b2;
                b2 = WatchCaptchaLoginApi.b(com.netease.cloudmusic.network.i.d.a.this, jSONObject);
                return b2;
            }
        };
        int[] iArr = f6348b;
        Object a3 = aVar.a((j<Object>) jVar, true, Arrays.copyOf(iArr, iArr.length));
        Intrinsics.checkNotNullExpressionValue(a3, "api.params(\n            …    }, true, *errorCodes)");
        return (Pair) a3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Pair<Integer, String> a(String str, String str2, boolean z, String str3) throws i {
        com.netease.cloudmusic.network.i.d.a a2 = com.netease.cloudmusic.network.c.a("register/cellphone");
        String[] strArr = new String[8];
        strArr[0] = "phone";
        strArr[1] = str;
        strArr[2] = "countrycode";
        String str4 = "86";
        if (str3 != null) {
            if (!(!StringsKt.isBlank(str3))) {
                str3 = null;
            }
            if (str3 != null) {
                str4 = str3;
            }
        }
        strArr[3] = str4;
        strArr[4] = "captcha";
        strArr[5] = str2;
        strArr[6] = "allowDefaultNickname";
        strArr[7] = String.valueOf(z);
        com.netease.cloudmusic.network.i.d.a aVar = (com.netease.cloudmusic.network.i.d.a) ((com.netease.cloudmusic.network.i.d.a) a2.a(strArr)).e(true);
        $$Lambda$c$2jWbceohlIjinGHOF8RNFS8iJg __lambda_c_2jwbceohlijinghof8rnfs8ijg = new j() { // from class: com.netease.cloudmusic.wear.watch.login.captcha.-$$Lambda$c$2jWbceohlIjinGHOF-8RNFS8iJg
            @Override // com.netease.cloudmusic.network.b.j
            public final Object parse(JSONObject jSONObject) {
                Pair c2;
                c2 = WatchCaptchaLoginApi.c(jSONObject);
                return c2;
            }
        };
        int[] iArr = f6348b;
        Object a3 = aVar.a(__lambda_c_2jwbceohlijinghof8rnfs8ijg, Arrays.copyOf(iArr, iArr.length));
        Intrinsics.checkNotNullExpressionValue(a3, "request.forceHttps(true)… }, *errorCodes\n        )");
        return (Pair) a3;
    }

    public final Pair<Integer, String> a(JSONObject jsonResult) throws JSONException {
        Intrinsics.checkNotNullParameter(jsonResult, "jsonResult");
        int i = jsonResult.getInt("code");
        if (i != 200) {
            if (i == 320) {
                return new Pair<>(Integer.valueOf(i), jsonResult.optString("message"));
            }
            if (i != 702) {
                switch (i) {
                }
            }
            String optString = jsonResult.optString("redirectUrl");
            if (optString == null) {
                optString = "";
            }
            if (!jsonResult.isNull("phone")) {
                Uri.Builder appendQueryParameter = Uri.parse(optString).buildUpon().appendQueryParameter("phone", URLEncoder.encode(jsonResult.getString("phone")));
                if (!jsonResult.isNull("countrycode")) {
                    appendQueryParameter.appendQueryParameter("countrycode", URLEncoder.encode(jsonResult.getString("countrycode")));
                }
                optString = appendQueryParameter.build().toString();
                Intrinsics.checkNotNullExpressionValue(optString, "builder.build().toString()");
            }
            return new Pair<>(Integer.valueOf(i), optString);
        }
        if (!com.netease.cloudmusic.wear.watch.account.c.a(jsonResult)) {
            return new Pair<>(-1, "获取用户信息出错，请稍后再试");
        }
        return new Pair<>(Integer.valueOf(i), jsonResult.optString("message"));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final PhoneAccountInfo b(String str, String str2) throws i {
        com.netease.cloudmusic.network.i.d.a aVar = (com.netease.cloudmusic.network.i.d.a) com.netease.cloudmusic.network.c.a("cellphone/existence/check").a("cellphone", str);
        Object[] objArr = new Object[2];
        objArr[0] = "countrycode";
        String str3 = "86";
        if (str2 != null) {
            if (!(!StringsKt.isBlank(str2))) {
                str2 = null;
            }
            if (str2 != null) {
                str3 = str2;
            }
        }
        objArr[1] = str3;
        return (PhoneAccountInfo) ((com.netease.cloudmusic.network.i.d.a) aVar.a(objArr)).a(new j() { // from class: com.netease.cloudmusic.wear.watch.login.captcha.-$$Lambda$c$w1W9y9bIGmN4CCebchJZMtVRXsc
            @Override // com.netease.cloudmusic.network.b.j
            public final Object parse(JSONObject jSONObject) {
                PhoneAccountInfo b2;
                b2 = WatchCaptchaLoginApi.b(jSONObject);
                return b2;
            }
        }, new int[0]);
    }
}

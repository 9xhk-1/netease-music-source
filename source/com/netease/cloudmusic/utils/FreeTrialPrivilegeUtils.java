package com.netease.cloudmusic.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.cloudmusic.bilog.BIBaseLog;
import com.netease.cloudmusic.common.ServiceFacade;
import com.netease.cloudmusic.core.iapm.IAPMTracker;
import com.netease.cloudmusic.core.icustomconfig.ICustomConfig;
import com.netease.cloudmusic.meta.MusicInfo;
import com.netease.cloudmusic.meta.PlayList;
import com.netease.cloudmusic.meta.ResExposureReq;
import com.netease.cloudmusic.meta.virtual.PlayExtraInfo;
import com.netease.cloudmusic.meta.virtual.SongPrivilege;
import com.netease.cloudmusic.meta.virtual.SongUrlInfo;
import com.netease.cloudmusic.meta.virtual.freetrial.FreeTrialPrivilege;
import com.netease.cloudmusic.meta.virtual.freetrial.FreeTrialScene;
import com.netease.cloudmusic.module.vip.VipApi;
import com.netease.cloudmusic.service.PlayService;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ProGuard */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/netease/cloudmusic/utils/FreeTrialPrivilegeUtils;", "", "()V", "Companion", "neteaseMusic_userWatchXtcChildRelease"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* renamed from: com.netease.cloudmusic.utils.ab, reason: from Kotlin metadata */
/* loaded from: classes2.dex */
public final class FreeTrialPrivilegeUtils {

    /* renamed from: a, reason: collision with root package name */
    public static final a f5533a = new a(null);

    /* renamed from: b, reason: collision with root package name */
    private static long f5534b = Long.MIN_VALUE;

    /* renamed from: c, reason: collision with root package name */
    private static long f5535c = Long.MIN_VALUE;

    /* compiled from: ProGuard */
    @Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J \u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u0016\u0010\u0011\u001a\u00020\u00122\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u0014J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u001a\u0010\u0017\u001a\u00020\u00162\u0010\u0010\u0013\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\f\u0018\u00010\u0018H\u0007J\u0017\u0010\u0019\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0007¢\u0006\u0002\u0010\u001cJ\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0018\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\nH\u0002J\u0012\u0010!\u001a\u00020\u00122\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0010\u0010\"\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010#\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u000e\u0010$\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010%\u001a\u00020\n2\u0006\u0010&\u001a\u00020\u0016H\u0007J\u0006\u0010'\u001a\u00020\nJ\u0012\u0010(\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0014\u0010)\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0015\u0010*\u001a\u00020\n2\b\u0010+\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010,J\"\u0010-\u001a\u00020\u00122\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010.\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\nH\u0007J\u0012\u0010/\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0012\u00100\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002J\u0012\u00101\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0006\u00102\u001a\u00020\nJ\u0010\u00103\u001a\u00020\u00122\u0006\u00104\u001a\u000205H\u0007J\u001c\u00106\u001a\u00020\u00122\b\u00107\u001a\u0004\u0018\u00010\u00042\b\u00108\u001a\u0004\u0018\u000109H\u0007J\u001a\u0010:\u001a\u00020\u00122\u0006\u0010;\u001a\u00020\u000e2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u001a\u0010:\u001a\u00020\u00122\u0006\u0010;\u001a\u00020\u000e2\b\u00108\u001a\u0004\u0018\u000109H\u0007J!\u0010:\u001a\u00020\u00122\b\u0010;\u001a\u0004\u0018\u00010\u00162\b\u00108\u001a\u0004\u0018\u000109H\u0007¢\u0006\u0002\u0010<R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006="}, d2 = {"Lcom/netease/cloudmusic/utils/FreeTrialPrivilegeUtils$Companion;", "", "()V", "FREE_TRIAL_TYPE", "", "FREE_TRIAL_TYPE_SERVER_KEY", "lastInterceptAuditionHintMusicId", "", "lastInterceptTSHintMusicId", "canFetchFullFreeTrial", "", "musicInfo", "Lcom/netease/cloudmusic/meta/MusicInfo;", "scene", "Lcom/netease/cloudmusic/meta/virtual/freetrial/FreeTrialScene;", "freeTrialPrivilege", "Lcom/netease/cloudmusic/meta/virtual/freetrial/FreeTrialPrivilege;", "fixTrialModeToMusics", "", "musics", "", "getFetchFullFreeTrialMode", "", "getFullTrialModeForMusics", "", "getPlayListFreeTrialDynamicValue", "playList", "Lcom/netease/cloudmusic/meta/PlayList;", "(Lcom/netease/cloudmusic/meta/PlayList;)Ljava/lang/Integer;", "getPlayListFreeTrialScene", "innerLogFullTrial", "musicId", "success", "interceptedTSHint", "isAuditionInner", "isAuditionOrFullTrial", "isAuditionSongOnly", "isFreeFullTrialScene", "tsListenScene", "isFreeTrialOpen", "isPlayingFullTrialSongWithMusic", "isPlayingListenedFullTrialMusic", "isVipRadarPlayList", "playlistId", "(Ljava/lang/Long;)Z", "logFullTrial", PlayService.INTENT_EXTRA_KEY.PLAYTYPE, "needInterceptAuditionHint", "needInterceptAuditionHintFix", "needReShowTSHint", "needShowAuditionTip", "onPlayerStar", "context", "Landroid/content/Context;", "setFreeServerKeyToMusicExtra", "key", "playExtraInfo", "Lcom/netease/cloudmusic/meta/virtual/PlayExtraInfo;", "setFreeTrialTypeToMusicExtra", "type", "(Ljava/lang/Integer;Lcom/netease/cloudmusic/meta/virtual/PlayExtraInfo;)V", "neteaseMusic_userWatchXtcChildRelease"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* renamed from: com.netease.cloudmusic.utils.ab$a */
    /* loaded from: classes2.dex */
    public static final class a {

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: ProGuard */
        @Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\n¢\u0006\u0002\b\u0006"}, d2 = {"<anonymous>", "", "it", "", "", "", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
        /* renamed from: com.netease.cloudmusic.utils.ab$a$a, reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        public static final class C0137a extends Lambda implements Function1<Map<String, Object>, Unit> {

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ long f5536a;

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ boolean f5537b;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C0137a(long j, boolean z) {
                super(1);
                this.f5536a = j;
                this.f5537b = z;
            }

            public final void a(Map<String, Object> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                it.put("target", "ts_all");
                it.put(IAPMTracker.KEY_PAGE, "userfm");
                it.put("resource", "song");
                it.put("resourceid", Long.valueOf(this.f5536a));
                it.put("type", this.f5537b ? "success" : "fail");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* synthetic */ Unit invoke(Map<String, Object> map) {
                a(map);
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: ProGuard */
        @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/netease/cloudmusic/bilog/BIBaseLog;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
        /* renamed from: com.netease.cloudmusic.utils.ab$a$b */
        /* loaded from: classes2.dex */
        public static final class b extends Lambda implements Function1<BIBaseLog, Unit> {

            /* renamed from: a, reason: collision with root package name */
            public static final b f5538a = new b();

            b() {
                super(1);
            }

            public final void a(BIBaseLog doBILog) {
                Intrinsics.checkNotNullParameter(doBILog, "$this$doBILog");
                doBILog.a("5ff7c965696ce92a6feb1c82");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* synthetic */ Unit invoke(BIBaseLog bIBaseLog) {
                a(bIBaseLog);
                return Unit.INSTANCE;
            }
        }

        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final void a(long j, boolean z) {
            BIBaseLog.f2097c.b("sysaction").a(null, new C0137a(j, z), b.f5538a);
        }

        private final boolean g(MusicInfo musicInfo) {
            if (a()) {
                if (musicInfo.needAuditionSong()) {
                    return true;
                }
                SongUrlInfo su = musicInfo.getSu();
                if (su != null && su.isAuditionSong()) {
                    return true;
                }
            }
            return false;
        }

        @JvmStatic
        public final Integer a(PlayList playList) {
            Intrinsics.checkNotNullParameter(playList, "playList");
            if (playList.getTrialMode() > 0) {
                return Integer.valueOf(playList.getTrialMode());
            }
            if (a(Long.valueOf(playList.getId()))) {
                return Integer.valueOf(FreeTrialScene.MGC_VIP_RADAR.getValue());
            }
            if (playList.isMyStarPL()) {
                return Integer.valueOf(FreeTrialScene.PLAY_LIST_STAR.getValue());
            }
            return null;
        }

        @JvmStatic
        public final void a(MusicInfo musicInfo, int i, boolean z) {
            FreeTrialPrivilege freeTrialPrivilege;
            if (musicInfo == null) {
                return;
            }
            if (z) {
                SongUrlInfo su = musicInfo.getSu();
                freeTrialPrivilege = su != null ? su.getFreeTrialPrivilege() : null;
                if (freeTrialPrivilege == null || !freeTrialPrivilege.isPlayingFullFreeTrail(musicInfo.getSu())) {
                    return;
                }
                a(musicInfo.getFilterMusicId(), true);
                return;
            }
            SongPrivilege sp = musicInfo.getSp();
            freeTrialPrivilege = sp != null ? sp.getFreeTrialPrivilege() : null;
            if (freeTrialPrivilege == null || !a(FreeTrialPrivilegeBaseUtils.f5532a.a(i), freeTrialPrivilege, musicInfo)) {
                return;
            }
            a(musicInfo.getFilterMusicId(), false);
        }

        @JvmStatic
        public final void a(FreeTrialScene type, MusicInfo musicInfo) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (musicInfo != null) {
                PlayExtraInfo musicSource = musicInfo.getMusicSource();
                if (musicSource == null) {
                    musicSource = new PlayExtraInfo();
                }
                Map<String, Serializable> extraMap = musicSource.getExtraMap();
                Intrinsics.checkNotNullExpressionValue(extraMap, "extraInfo.extraMap");
                extraMap.put("FREE_TRIAL_TYPE", Integer.valueOf(type.getValue()));
                Map<String, Serializable> extraMap2 = musicSource.getExtraMap();
                Intrinsics.checkNotNullExpressionValue(extraMap2, "extraInfo.extraMap");
                extraMap2.put("FREE_TRIAL_TYPE_SERVER_KEY", type.getServerKey());
                musicInfo.setMusicSource(musicSource);
            }
        }

        @JvmStatic
        public final void a(FreeTrialScene type, PlayExtraInfo playExtraInfo) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (playExtraInfo == null) {
                return;
            }
            Map<String, Serializable> extraMap = playExtraInfo.getExtraMap();
            Intrinsics.checkNotNullExpressionValue(extraMap, "playExtraInfo.extraMap");
            extraMap.put("FREE_TRIAL_TYPE", Integer.valueOf(type.getValue()));
            Map<String, Serializable> extraMap2 = playExtraInfo.getExtraMap();
            Intrinsics.checkNotNullExpressionValue(extraMap2, "playExtraInfo.extraMap");
            extraMap2.put("FREE_TRIAL_TYPE_SERVER_KEY", type.getServerKey());
        }

        @JvmStatic
        public final void a(Integer num, PlayExtraInfo playExtraInfo) {
            if (playExtraInfo == null) {
                return;
            }
            Map<String, Serializable> extraMap = playExtraInfo.getExtraMap();
            Intrinsics.checkNotNullExpressionValue(extraMap, "playExtraInfo.extraMap");
            extraMap.put("FREE_TRIAL_TYPE", num);
        }

        @JvmStatic
        public final void a(String str, PlayExtraInfo playExtraInfo) {
            if (playExtraInfo == null) {
                return;
            }
            Map<String, Serializable> extraMap = playExtraInfo.getExtraMap();
            Intrinsics.checkNotNullExpressionValue(extraMap, "playExtraInfo.extraMap");
            extraMap.put("FREE_TRIAL_TYPE_SERVER_KEY", str);
        }

        public final void a(List<? extends MusicInfo> list) {
            List<? extends MusicInfo> list2 = list;
            if (list2 == null || list2.isEmpty()) {
                return;
            }
            Iterator<? extends MusicInfo> it = list.iterator();
            while (it.hasNext()) {
                PlayExtraInfo musicSource = it.next().getMusicSource();
                if (musicSource != null) {
                    Map<String, Serializable> extraMap = musicSource.getExtraMap();
                    if ((extraMap == null ? null : extraMap.get("FREE_TRIAL_TYPE")) != null) {
                        Map<String, Serializable> extraMap2 = musicSource.getExtraMap();
                        Object obj = extraMap2 == null ? null : (Serializable) extraMap2.get("FREE_TRIAL_TYPE_SERVER_KEY");
                        a(Integer.valueOf(FreeTrialPrivilegeBaseUtils.f5532a.a(obj instanceof String ? (String) obj : null).getValue()), musicSource);
                    }
                }
            }
        }

        public final boolean a() {
            return FreeTrialPrivilegeABManager.f5771a.b();
        }

        @JvmStatic
        public final boolean a(MusicInfo musicInfo) {
            return FreeTrialPrivilegeBaseUtils.f5532a.b(musicInfo);
        }

        public final boolean a(FreeTrialScene scene, FreeTrialPrivilege freeTrialPrivilege, MusicInfo musicInfo) {
            Intrinsics.checkNotNullParameter(scene, "scene");
            Intrinsics.checkNotNullParameter(freeTrialPrivilege, "freeTrialPrivilege");
            return FreeTrialPrivilegeBaseUtils.f5532a.a(scene, freeTrialPrivilege, musicInfo);
        }

        public final boolean a(Long l) {
            Object m213constructorimpl;
            if (l == null) {
                return false;
            }
            try {
                Result.Companion companion = Result.INSTANCE;
                m213constructorimpl = Result.m213constructorimpl((JSONObject) ((ICustomConfig) ServiceFacade.get(ICustomConfig.class)).getMainAppCustomConfig(new JSONObject(), "membership#free_trial_playlist_radar"));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                m213constructorimpl = Result.m213constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m216exceptionOrNullimpl(m213constructorimpl) != null) {
                m213constructorimpl = new JSONObject();
            }
            JSONArray jSONArray = ((JSONObject) m213constructorimpl).getJSONArray(ResExposureReq.ExposureRecord.RES_POS_PLAYLIST);
            if (jSONArray != null) {
                int size = jSONArray.size();
                int i = 0;
                while (i < size) {
                    int i2 = i + 1;
                    if (Intrinsics.areEqual(jSONArray.getLong(i), l)) {
                        return true;
                    }
                    i = i2;
                }
            }
            return false;
        }

        public final boolean b() {
            return FreeTrialPrivilegeABManager.f5771a.a();
        }

        @JvmStatic
        public final boolean b(MusicInfo musicInfo) {
            SongUrlInfo su;
            FreeTrialPrivilege freeTrialPrivilege;
            return (musicInfo == null || (su = musicInfo.getSu()) == null || (freeTrialPrivilege = su.getFreeTrialPrivilege()) == null || !freeTrialPrivilege.isPlayingFullFreeTrail(musicInfo.getSu())) ? false : true;
        }

        @JvmStatic
        public final int c(MusicInfo musicInfo) {
            PlayExtraInfo musicSource;
            Map<String, Serializable> extraMap;
            Serializable serializable = null;
            if (musicInfo != null && (musicSource = musicInfo.getMusicSource()) != null && (extraMap = musicSource.getExtraMap()) != null) {
                serializable = extraMap.get("FREE_TRIAL_TYPE");
            }
            Integer num = (Integer) serializable;
            int value = num == null ? FreeTrialScene.NORMAL.getValue() : num.intValue();
            if (value != FreeTrialScene.NORMAL.getValue()) {
                return value;
            }
            Boolean isPlayingFullTrialMusic = PlayService.isPlayingFullTrialMusic();
            Intrinsics.checkNotNullExpressionValue(isPlayingFullTrialMusic, "isPlayingFullTrialMusic()");
            return isPlayingFullTrialMusic.booleanValue() ? FreeTrialScene.OTHER_SCENE.getValue() : value;
        }

        @JvmStatic
        public final boolean d(MusicInfo musicInfo) {
            SongPrivilege sp;
            FreeTrialPrivilege freeTrialPrivilege;
            return ((musicInfo != null && (sp = musicInfo.getSp()) != null && (freeTrialPrivilege = sp.getFreeTrialPrivilege()) != null) ? freeTrialPrivilege.isPlayingAlreadyFullSong() : false) || PlayService.isPlayingListenedFullTrialMusic();
        }

        public final boolean e(MusicInfo musicInfo) {
            if (musicInfo == null || VipApi.c()) {
                return false;
            }
            return a(musicInfo) || b(musicInfo) || g(musicInfo);
        }

        public final boolean f(MusicInfo musicInfo) {
            Intrinsics.checkNotNullParameter(musicInfo, "musicInfo");
            return !VipApi.c() && g(musicInfo) && (!(a(musicInfo) || b(musicInfo)) || d(musicInfo));
        }
    }

    @JvmStatic
    public static final Integer a(PlayList playList) {
        return f5533a.a(playList);
    }

    @JvmStatic
    public static final void a(MusicInfo musicInfo, int i, boolean z) {
        f5533a.a(musicInfo, i, z);
    }

    @JvmStatic
    public static final void a(FreeTrialScene freeTrialScene, MusicInfo musicInfo) {
        f5533a.a(freeTrialScene, musicInfo);
    }

    @JvmStatic
    public static final void a(Integer num, PlayExtraInfo playExtraInfo) {
        f5533a.a(num, playExtraInfo);
    }

    @JvmStatic
    public static final void a(String str, PlayExtraInfo playExtraInfo) {
        f5533a.a(str, playExtraInfo);
    }

    @JvmStatic
    public static final boolean a(MusicInfo musicInfo) {
        return f5533a.a(musicInfo);
    }

    @JvmStatic
    public static final boolean b(MusicInfo musicInfo) {
        return f5533a.b(musicInfo);
    }

    @JvmStatic
    public static final int c(MusicInfo musicInfo) {
        return f5533a.c(musicInfo);
    }
}

package com.netease.cloudmusic.module.playlist;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.alibaba.fastjson.JSONObject;
import com.netease.cloudmusic.appground.IAppGroundManager;
import com.netease.cloudmusic.common.ServiceFacade;
import com.netease.cloudmusic.common.framework2.Utils.WatchChannelUtils;
import com.netease.cloudmusic.media.player.HttpStatusCode;
import com.netease.cloudmusic.meta.MusicInfo;
import com.netease.cloudmusic.service.PlayService;
import com.netease.cloudmusic.utils.FreeTrialPrivilegeUtils;
import com.netease.cloudmusic.utils.br;
import com.netease.cloudmusic.utils.bw;
import com.netease.cloudmusic.wear.watch.merge.ui.VipTagListContainer;
import com.netease.cloudmusic.wear.watch.merge.ui.VipTagView;
import com.netease.cloudmusic.wear.watch.utils.n;
import com.netease.cloudmusic.wear.watch.vip.log.VipReportHelper;
import com.netease.cloudmusic.wear.watch.vip.packages.VipPackageActivity;
import com.netease.xtc.cloudmusic.R;
import com.xtc.shareapi.share.constant.OpenApiConstant;
import io.agora.rtc.Constants;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ProGuard */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001'B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\u0004J\u0018\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\rJ$\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0012\u001a\u0004\u0018\u00010\r2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ4\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010 \u001a\u00020!2\b\b\u0002\u0010\"\u001a\u00020\u00042\b\b\u0002\u0010#\u001a\u00020\u00072\b\b\u0002\u0010$\u001a\u00020\u0007J,\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\r2\b\u0010%\u001a\u0004\u0018\u00010&2\b\b\u0002\u0010#\u001a\u00020\u00072\b\b\u0002\u0010$\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lcom/netease/cloudmusic/module/playlist/PlaylistNoPrivilegeManager;", "", "()V", "DIRECTION_LEFT", "", "DIRECTION_RIGHT", "isChild", "", "lastFilterMusicId", "", "lastSwitcherImpressId", "filterPlaylistOfflineMusic", "", "Lcom/netease/cloudmusic/meta/MusicInfo;", "musicList", "playlistType", "handleMusicAuditionSeek", "progress", "musicInfo", "handleMusicAuditionTip", "Lkotlinx/coroutines/Job;", "lifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "switcher", "Landroid/widget/ViewSwitcher;", "handleMusicNoPrivilege", "", NotificationCompat.CATEGORY_MESSAGE, "Landroid/os/Message;", "context", "Landroid/content/Context;", "showMusicTag", "targetView", "Landroid/widget/TextView;", "direction", "forceClear", "onlyShowVip", "tagContainer", "Lcom/netease/cloudmusic/wear/watch/merge/ui/VipTagListContainer;", "VipTagEnum", "neteaseMusic_userWatchXtcChildRelease"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* renamed from: com.netease.cloudmusic.module.o.a, reason: from Kotlin metadata */
/* loaded from: classes2.dex */
public final class PlaylistNoPrivilegeManager {

    /* renamed from: b, reason: collision with root package name */
    private static long f4168b;

    /* renamed from: c, reason: collision with root package name */
    private static long f4169c;

    /* renamed from: a, reason: collision with root package name */
    public static final PlaylistNoPrivilegeManager f4167a = new PlaylistNoPrivilegeManager();

    /* renamed from: d, reason: collision with root package name */
    private static final boolean f4170d = WatchChannelUtils.f2205a.n();

    /* compiled from: ProGuard */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lcom/netease/cloudmusic/module/playlist/PlaylistNoPrivilegeManager$VipTagEnum;", "", "resId", "", IjkMediaMeta.IJKM_KEY_WIDTH, IjkMediaMeta.IJKM_KEY_HEIGHT, "(Ljava/lang/String;IIII)V", "getHeight", "()I", "getResId", "getWidth", "VIP_OUTLINE", "VIP_DOWNLOAD_OUTLINE", "ALBUM_FEE_OUTLINE", "neteaseMusic_userWatchXtcChildRelease"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* renamed from: com.netease.cloudmusic.module.o.a$a */
    /* loaded from: classes2.dex */
    public enum a {
        VIP_OUTLINE(R.drawable.a2t, VipTagView.f6435a.a(), VipTagView.f6435a.c()),
        VIP_DOWNLOAD_OUTLINE(R.drawable.a2r, VipTagView.f6435a.b(), VipTagView.f6435a.c()),
        ALBUM_FEE_OUTLINE(R.drawable.a2n, VipTagView.f6435a.b(), VipTagView.f6435a.c());


        /* renamed from: d, reason: collision with root package name */
        private final int f4174d;

        /* renamed from: e, reason: collision with root package name */
        private final int f4175e;
        private final int f;

        a(int i, int i2, int i3) {
            this.f4174d = i;
            this.f4175e = i2;
            this.f = i3;
        }

        /* renamed from: a, reason: from getter */
        public final int getF4174d() {
            return this.f4174d;
        }

        /* renamed from: b, reason: from getter */
        public final int getF4175e() {
            return this.f4175e;
        }

        /* renamed from: c, reason: from getter */
        public final int getF() {
            return this.f;
        }
    }

    /* compiled from: ProGuard */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @DebugMetadata(c = "com.netease.cloudmusic.module.playlist.PlaylistNoPrivilegeManager$handleMusicAuditionTip$2", f = "PlaylistNoPrivilegeManager.kt", i = {0}, l = {HttpStatusCode.HTTP_RESPONSE_RETURN}, m = "invokeSuspend", n = {"$this$launchWhenResumed"}, s = {"L$0"})
    /* renamed from: com.netease.cloudmusic.module.o.a$b */
    /* loaded from: classes2.dex */
    static final class b extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {

        /* renamed from: a, reason: collision with root package name */
        int f4176a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ ViewSwitcher f4177b;

        /* renamed from: c, reason: collision with root package name */
        private /* synthetic */ Object f4178c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(ViewSwitcher viewSwitcher, Continuation<? super b> continuation) {
            super(2, continuation);
            this.f4177b = viewSwitcher;
        }

        @Override // kotlin.jvm.functions.Function2
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((b) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            b bVar = new b(this.f4177b, continuation);
            bVar.f4178c = obj;
            return bVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0041  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x002b  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:9:0x0038 -> B:5:0x003b). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.f4176a
                r2 = 1
                if (r1 == 0) goto L1c
                if (r1 != r2) goto L14
                java.lang.Object r1 = r6.f4178c
                kotlinx.coroutines.ak r1 = (kotlinx.coroutines.CoroutineScope) r1
                kotlin.ResultKt.throwOnFailure(r7)
                r7 = r6
                goto L3b
            L14:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L1c:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.f4178c
                kotlinx.coroutines.ak r7 = (kotlinx.coroutines.CoroutineScope) r7
                r1 = r7
                r7 = r6
            L25:
                boolean r3 = kotlinx.coroutines.al.a(r1)
                if (r3 == 0) goto L41
                r3 = 5000(0x1388, double:2.4703E-320)
                r5 = r7
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r7.f4178c = r1
                r7.f4176a = r2
                java.lang.Object r3 = kotlinx.coroutines.av.a(r3, r5)
                if (r3 != r0) goto L3b
                return r0
            L3b:
                android.widget.ViewSwitcher r3 = r7.f4177b
                r3.showNext()
                goto L25
            L41:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.netease.cloudmusic.module.playlist.PlaylistNoPrivilegeManager.b.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    private PlaylistNoPrivilegeManager() {
    }

    public static /* synthetic */ void a(PlaylistNoPrivilegeManager playlistNoPrivilegeManager, MusicInfo musicInfo, VipTagListContainer vipTagListContainer, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        playlistNoPrivilegeManager.a(musicInfo, vipTagListContainer, z, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void a(boolean z, MusicInfo musicInfo, JSONObject trialLogJson, View view) {
        com.netease.cloudmusic.datareport.inject.a.b(view);
        Intrinsics.checkNotNullParameter(trialLogJson, "$trialLogJson");
        String str = z ? "page_music_player_audition_paid_content" : "page_music_player_full_trial_paid_content";
        VipPackageActivity.a aVar = VipPackageActivity.k;
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "it.context");
        aVar.a(context, VipPackageActivity.k.b(), VipReportHelper.f7522a.a("watch_songplay", str, (Long) null, Long.valueOf(musicInfo.getFilterMusicId()), "song"));
        br.a("click", "673b018e778791b6d22a475c", trialLogJson);
        com.netease.cloudmusic.datareport.inject.a.e(view);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final List<MusicInfo> a(List<? extends MusicInfo> list, int i) {
        if (!f4170d || list == 0 || i == 5 || i == -25) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((MusicInfo) obj).hasCopyRight()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final Job a(LifecycleOwner lifecycleOwner, final MusicInfo musicInfo, ViewSwitcher viewSwitcher) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        if (!FreeTrialPrivilegeUtils.f5533a.b() || PlayService.isPlayingProgram() || musicInfo == null || viewSwitcher == null) {
            return null;
        }
        viewSwitcher.setOnClickListener(null);
        if (!FreeTrialPrivilegeUtils.f5533a.e(musicInfo)) {
            return null;
        }
        final boolean f = FreeTrialPrivilegeUtils.f5533a.f(musicInfo);
        final JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", (Object) (f ? "1" : OpenApiConstant.XTCShareSocialVideoType.INSTAGRAM));
        jSONObject.put("_mspm2", (Object) com.netease.cloudmusic.bilog.b.a(viewSwitcher, null, null, null, 0, null, 0, 0, Constants.ERR_WATERMARKR_INFO, null));
        jSONObject.put("_resource_1_id", (Object) Long.valueOf(musicInfo.getFilterMusicId()));
        viewSwitcher.setOnClickListener(new View.OnClickListener() { // from class: com.netease.cloudmusic.module.o.-$$Lambda$a$z7qfk385m3T9bKb6onYUlxQEHVo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PlaylistNoPrivilegeManager.a(f, musicInfo, jSONObject, view);
            }
        });
        if (musicInfo.getFilterMusicId() != f4169c) {
            f4169c = musicInfo.getFilterMusicId();
            br.a("impress", "673b017d8b39c8b6cce09fb6", jSONObject);
        }
        VipTagListContainer vipTagListContainer = (VipTagListContainer) viewSwitcher.findViewById(R.id.sd);
        if (vipTagListContainer == null) {
            return null;
        }
        a(this, musicInfo, vipTagListContainer, false, true, 4, null);
        vipTagListContainer.c(165);
        vipTagListContainer.b();
        TextView textView = (TextView) viewSwitcher.findViewById(R.id.uz);
        if (textView == null) {
            return null;
        }
        textView.setText(f ? "试听中，会员畅享完整版" : "本次免费听全曲，会员无限畅听>");
        return LifecycleOwnerKt.getLifecycleScope(lifecycleOwner).launchWhenResumed(new b(viewSwitcher, null));
    }

    public final void a(Message msg, Context context) {
        MusicInfo f;
        Intrinsics.checkNotNullParameter(msg, "msg");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((msg.what != 100 && msg.what != 8) || ((IAppGroundManager) ServiceFacade.get(IAppGroundManager.class)).isBackground() || (f = n.f()) == null) {
            return;
        }
        if (msg.arg1 != 10) {
            f4168b = 0L;
            return;
        }
        if (f.isAlbumFeeMusic()) {
            bw.a(R.string.b59);
        } else if (f4168b == f.getFilterMusicId()) {
            VipPackageActivity.k.a(context, VipPackageActivity.k.b(), VipReportHelper.f7522a.a("watch_songplay", "page_music_player_paid_content", (Long) null, Long.valueOf(f.getFilterMusicId()), "song"));
        } else {
            bw.a(R.string.bi2);
        }
        f4168b = f.getFilterMusicId();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void a(com.netease.cloudmusic.meta.MusicInfo r4, android.widget.TextView r5, int r6, boolean r7, boolean r8) {
        /*
            r3 = this;
            java.lang.String r0 = "musicInfo"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.lang.String r0 = "targetView"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            r0 = 2
            if (r6 == r0) goto L10
            if (r6 == 0) goto L10
            return
        L10:
            r1 = 0
            if (r7 == 0) goto L15
        L13:
            r4 = r1
            goto L32
        L15:
            boolean r7 = r4.isVipMusicButNotQQ()
            if (r7 == 0) goto L1e
            com.netease.cloudmusic.module.o.a$a r4 = com.netease.cloudmusic.module.playlist.PlaylistNoPrivilegeManager.a.VIP_OUTLINE
            goto L32
        L1e:
            if (r8 == 0) goto L21
            goto L13
        L21:
            boolean r7 = r4.isQQMusic()
            if (r7 == 0) goto L2a
            com.netease.cloudmusic.module.o.a$a r4 = com.netease.cloudmusic.module.playlist.PlaylistNoPrivilegeManager.a.VIP_DOWNLOAD_OUTLINE
            goto L32
        L2a:
            boolean r4 = r4.isAlbumFeeMusic()
            if (r4 == 0) goto L13
            com.netease.cloudmusic.module.o.a$a r4 = com.netease.cloudmusic.module.playlist.PlaylistNoPrivilegeManager.a.ALBUM_FEE_OUTLINE
        L32:
            r7 = 0
            if (r4 == 0) goto L51
            android.content.Context r8 = r5.getContext()
            int r2 = r4.getF4174d()
            android.graphics.drawable.Drawable r8 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r8, r2)
            if (r8 != 0) goto L44
            goto L53
        L44:
            int r1 = r4.getF4175e()
            int r4 = r4.getF()
            r8.setBounds(r7, r7, r1, r4)
            r1 = r8
            goto L53
        L51:
            android.graphics.drawable.Drawable r1 = (android.graphics.drawable.Drawable) r1
        L53:
            com.netease.cloudmusic.wear.watch.merge.ui.VipTagView$a r4 = com.netease.cloudmusic.wear.watch.merge.ui.VipTagView.f6435a
            int r4 = r4.d()
            r5.setCompoundDrawablePadding(r4)
            android.graphics.drawable.Drawable[] r4 = r5.getCompoundDrawables()
            r4[r6] = r1
            r6 = r4[r7]
            r7 = 1
            r7 = r4[r7]
            r8 = r4[r0]
            r0 = 3
            r4 = r4[r0]
            r5.setCompoundDrawables(r6, r7, r8, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.cloudmusic.module.playlist.PlaylistNoPrivilegeManager.a(com.netease.cloudmusic.meta.MusicInfo, android.widget.TextView, int, boolean, boolean):void");
    }

    public final void a(MusicInfo musicInfo, VipTagListContainer vipTagListContainer, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(musicInfo, "musicInfo");
        if (vipTagListContainer == null) {
            return;
        }
        vipTagListContainer.a();
        Integer num = null;
        if (!z) {
            if (musicInfo.isVipMusicButNotQQ()) {
                num = 162;
            } else if (!z2) {
                if (musicInfo.isQQMusic()) {
                    num = 163;
                } else if (musicInfo.isAlbumFeeMusic()) {
                    num = 164;
                }
            }
        }
        vipTagListContainer.b(num);
        if (FreeTrialPrivilegeUtils.f5533a.e(musicInfo)) {
            vipTagListContainer.b(165);
        }
        vipTagListContainer.b();
    }

    public final boolean a(int i, MusicInfo musicInfo) {
        boolean z = false;
        if (PlayService.isPlayingProgram()) {
            return false;
        }
        if (musicInfo != null && FreeTrialPrivilegeUtils.f5533a.f(musicInfo) && (i < musicInfo.getAuditionStartPosition() || i > musicInfo.getAuditionEndPosition())) {
            z = true;
        }
        if (z) {
            bw.a("试听中，开通会员畅听全曲");
        }
        return z;
    }
}

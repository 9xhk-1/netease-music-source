package com.netease.cloudmusic.meta.virtual.freetrial;

import androidx.core.app.NotificationCompat;
import com.netease.cloudmusic.wear.watch.podcast.favorite.LikedVoice;
import kotlin.Metadata;

/* compiled from: ProGuard */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015¨\u0006\u0016"}, d2 = {"Lcom/netease/cloudmusic/meta/virtual/freetrial/FreeTrialScene;", "", "value", "", NotificationCompat.CATEGORY_MESSAGE, "", "serverKey", "(Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;)V", "getMsg", "()Ljava/lang/String;", "getServerKey", "getValue", "()I", "setValue", "(I)V", LikedVoice.NORMAL, "RECOMMEND_DAILY", "PRIVATE_FM", "MGC_VIP_RADAR", "SONG_LIST_SEARCH", "PLAY_LIST_STAR", "OTHER_SCENE", "neteaseMusic_userWatchXtcChildRelease"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes2.dex */
public enum FreeTrialScene {
    NORMAL(1, "正常模式", "normal"),
    RECOMMEND_DAILY(82, "【手表】每日推荐", "dailyRcmd"),
    PRIVATE_FM(83, "【手表】私人漫游", "fm"),
    MGC_VIP_RADAR(84, "【手表】雷达歌单", "radar"),
    SONG_LIST_SEARCH(85, "【手表】搜索", "search"),
    PLAY_LIST_STAR(86, "【手表】红心歌单", "star"),
    OTHER_SCENE(-1, "【其他】", "commonActivity");

    private final String msg;
    private final String serverKey;
    private int value;

    FreeTrialScene(int i, String str, String str2) {
        this.value = i;
        this.msg = str;
        this.serverKey = str2;
    }

    public final String getMsg() {
        return this.msg;
    }

    public final String getServerKey() {
        return this.serverKey;
    }

    public final int getValue() {
        return this.value;
    }

    public final void setValue(int i) {
        this.value = i;
    }
}

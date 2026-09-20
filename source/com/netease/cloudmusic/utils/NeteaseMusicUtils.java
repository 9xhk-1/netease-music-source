package com.netease.cloudmusic.utils;

import android.app.AppOpsManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.TypedValue;
import androidx.core.util.Pair;
import androidx.core.view.InputDeviceCompat;
import com.netease.cg.center.sdk.GameJsonKeys;
import com.netease.cloudmusic.common.ApplicationWrapper;
import com.netease.cloudmusic.common.ServiceFacade;
import com.netease.cloudmusic.common.g;
import com.netease.cloudmusic.core.iapm.IAPMTracker;
import com.netease.cloudmusic.service.PlayService;
import com.netease.cloudmusic.service.ServiceConst;
import com.netease.cloudmusic.service.api.IStatisticService;
import com.netease.is.deviceid.NEDeviceID;
import com.netease.nis.bugrpt.user.Constant;
import im.yixin.sdk.util.SDKNetworkUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.Character;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.text.Typography;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ProGuard */
/* loaded from: classes.dex */
public class NeteaseMusicUtils {

    /* renamed from: a, reason: collision with root package name */
    private static final String f5527a = "com.netease.cloudmusic.utils.NeteaseMusicUtils";

    public static int a(float f) {
        return (int) TypedValue.applyDimension(1, f, ApplicationWrapper.getInstance().getResources().getDisplayMetrics());
    }

    public static int a(int i) {
        return ApplicationWrapper.getInstance().getResources().getDimensionPixelSize(i);
    }

    public static Pair<Integer, byte[]> a(InputStream inputStream, int i) {
        byte[] ncaeDecrypt;
        byte[] a2 = y.a(inputStream, i);
        int ncaeVerify = ncaeVerify(a2);
        if (ncaeVerify <= 0 || (ncaeDecrypt = ncaeDecrypt(a2)) == null) {
            return null;
        }
        return Pair.create(Integer.valueOf(ncaeVerify), ncaeDecrypt);
    }

    public static Object a(Context context, String str, boolean z) {
        ObjectInputStream objectInputStream;
        if (context == null) {
            return null;
        }
        try {
            objectInputStream = new ObjectInputStream(z ? context.openFileInput(str) : new FileInputStream(str));
            try {
                Object readObject = objectInputStream.readObject();
                try {
                    objectInputStream.close();
                } catch (IOException unused) {
                }
                return readObject;
            } catch (Throwable th) {
                th = th;
                try {
                    th.printStackTrace();
                    IStatisticService iStatisticService = (IStatisticService) ServiceFacade.get(ServiceConst.STATISTIC_SERVICE, IStatisticService.class);
                    if (iStatisticService != null) {
                        StringWriter stringWriter = new StringWriter();
                        th.printStackTrace(new PrintWriter(stringWriter));
                        iStatisticService.log3("sysdebug", IAPMTracker.KEY_COMMON_KEY_MSPM, "null_object", "filename", str, "internal", Boolean.valueOf(z), Constant.s, stringWriter.toString());
                    }
                    if (objectInputStream != null) {
                        try {
                            objectInputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    if (objectInputStream != null) {
                        try {
                            objectInputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            objectInputStream = null;
        }
    }

    public static String a() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress nextElement = inetAddresses.nextElement();
                    if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                        return nextElement.getHostAddress().toString();
                    }
                }
            }
            return null;
        } catch (SocketException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String a(int i, String str, long j) {
        if (i == 11) {
            return "A_AC_" + i + "_" + str;
        }
        if (i == 14) {
            return "A_DR_" + i + "_" + str;
        }
        if (i == 62) {
            return "R_VI_" + i + "_" + str;
        }
        if (i == 65) {
            return "R_SC_" + i + "_" + str;
        }
        if (i == 1001) {
            return "R_MLOG_" + i + "_" + str;
        }
        if (i == 1006) {
            return "R_CDEMO_" + i + "_" + str;
        }
        switch (i) {
            case 0:
                return "A_PL_" + i + "_" + str;
            case 1:
                return "A_DJ_" + i + "_" + str;
            case 2:
                return "A_EV_" + i + "_" + str + "_" + j;
            case 3:
                return "R_AL_" + i + "_" + str;
            case 4:
                return "R_SO_" + i + "_" + str;
            case 5:
                return "R_MV_" + i + "_" + str;
            case 6:
                return "A_TO_" + i + "_" + str;
            default:
                return null;
        }
    }

    public static String a(long j, int i) {
        return j + "-" + i;
    }

    public static String a(long j, int i, long j2, long j3, String str, int i2, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(j);
        sb.append(i);
        sb.append(j2);
        sb.append(j3);
        sb.append(str);
        sb.append(i2);
        if (str2 == null) {
            str2 = "";
        }
        sb.append(str2);
        return d(sb.toString());
    }

    public static String a(long j, int i, String str) {
        return c(j, i, str) + ".idx!";
    }

    public static String a(long j, boolean z) {
        return a(j, z, true);
    }

    public static String a(long j, boolean z, boolean z2) {
        int i = z ? 1000 : 1024;
        if (j < i) {
            return j + "B";
        }
        double d2 = j;
        double d3 = i;
        int log = (int) (Math.log(d2) / Math.log(d3));
        String str = "KMGTPE".charAt(log - 1) + "";
        String str2 = z2 ? "%.1f%s" : "%.0f%s";
        double pow = Math.pow(d3, log);
        Double.isNaN(d2);
        return String.format(str2, Double.valueOf(d2 / pow), str);
    }

    public static String a(Context context) {
        String string = bh.a().getString(GameJsonKeys.IMEI, "");
        if (bt.a(string)) {
            return string;
        }
        if (!com.netease.cloudmusic.common.framework2.loading.e.a(context)) {
            return "null";
        }
        IStatisticService iStatisticService = (IStatisticService) ServiceFacade.get(ServiceConst.STATISTIC_SERVICE, IStatisticService.class);
        try {
            String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            if (bt.a(deviceId)) {
                bh.a().edit().putString(GameJsonKeys.IMEI, deviceId).commit();
            }
            return deviceId;
        } catch (SecurityException e2) {
            iStatisticService.log3("sysdebug", IAPMTracker.KEY_COMMON_KEY_MSPM, "runtimepermission", "target", "securityexception", IAPMTracker.KEY_PAGE, e2.toString());
            return "";
        }
    }

    public static String a(Context context, long j) {
        if (j < 100000) {
            return String.valueOf(j);
        }
        if (j < 100000000) {
            long j2 = j / 10000;
            long j3 = (j % 10000) / 1000;
            if (j3 == 0) {
                return j2 + context.getResources().getString(g.h.l);
            }
            return j2 + "." + j3 + context.getResources().getString(g.h.l);
        }
        long j4 = j / 100000000;
        long j5 = (j % 100000000) / 10000000;
        if (j5 == 0) {
            return j4 + context.getResources().getString(g.h.h);
        }
        return j4 + "." + j5 + context.getResources().getString(g.h.h);
    }

    public static String a(String str) {
        Throwable th;
        FileInputStream fileInputStream;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(new File(str));
            try {
                try {
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            String a2 = a(messageDigest.digest());
                            ah.a(fileInputStream);
                            return a2;
                        }
                        messageDigest.update(bArr, 0, read);
                    }
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    ah.a(fileInputStream);
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                ah.a(fileInputStream);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            ah.a(fileInputStream);
            throw th;
        }
    }

    public static String a(String str, String str2) {
        String m = m(str.trim());
        String m2 = m(str2.trim());
        byte[] bytes = m.getBytes();
        byte[] bytes2 = m2.getBytes();
        while (bytes.length + bytes2.length > 240) {
            if (bytes.length > 90) {
                m = m.substring(0, m.length() - 1);
                bytes = m.getBytes();
            }
            if (bytes2.length > 90) {
                m2 = m2.substring(0, m2.length() - 1);
                bytes2 = m2.getBytes();
            }
        }
        return m + " - " + m2;
    }

    public static String a(boolean z) {
        NetworkInfo e2 = t.e();
        if (e2 == null || !e2.isConnected()) {
            return "Offline";
        }
        if (e2.getType() == 1) {
            return "Wifi";
        }
        if (e2.getType() != 0) {
            return "Offline";
        }
        if (z) {
            int subtype = e2.getSubtype();
            String subtypeName = e2.getSubtypeName();
            switch (subtype) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return SDKNetworkUtil.NETWORK_TYPE_2G;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    break;
                case 13:
                    return SDKNetworkUtil.NETWORK_TYPE_4G;
                default:
                    if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA") && !subtypeName.equalsIgnoreCase("CDMA2000")) {
                        return SDKNetworkUtil.NETWORK_TYPE_2G;
                    }
                    break;
            }
        }
        return SDKNetworkUtil.NETWORK_TYPE_3G;
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] cArr2 = new char[bArr.length * 2];
        int i = 0;
        for (byte b2 : bArr) {
            int i2 = i + 1;
            cArr2[i] = cArr[(b2 >>> 4) & 15];
            i = i2 + 1;
            cArr2[i2] = cArr[b2 & 15];
        }
        return new String(cArr2);
    }

    public static void a(int i, int i2) {
        bh.a("additional_perfer_file").edit().putInt("autoCloseCustomHour", i).putInt("autoCloseCustomMinute", i2).apply();
    }

    public static void a(Context context, int i, int i2, Serializable serializable) {
        a(context, i, i2, serializable, null);
    }

    public static void a(Context context, int i, int i2, Serializable serializable, Serializable serializable2) {
        Intent intent = new Intent(com.netease.cloudmusic.e.ax);
        if (serializable2 != null) {
            intent.putExtra(PlayService.INTENT_EXTRA_KEY.EXTRA, serializable2);
        }
        intent.putExtra("type", i);
        intent.putExtra("object", serializable);
        intent.putExtra("action", i2);
        ApplicationWrapper.getInstance().sendBroadcast(intent);
    }

    public static void a(File file, boolean z) {
        if (file == null || !file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            file.delete();
            return;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                a(file2, true);
            }
        }
        if (z) {
            file.delete();
        }
    }

    public static void a(String str, Object obj) {
        com.netease.cloudmusic.log.a.a(str, obj);
    }

    public static boolean a(char c2) {
        Character.UnicodeBlock of = Character.UnicodeBlock.of(c2);
        return of == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || of == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || of == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || of == Character.UnicodeBlock.GENERAL_PUNCTUATION || of == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || of == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static boolean a(long j) {
        return k() < j;
    }

    public static boolean a(Context context, Object obj, String str) {
        return a(context, obj, str, true);
    }

    public static boolean a(Context context, Object obj, String str, boolean z) {
        if (context == null) {
            return false;
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(z ? context.openFileOutput(str, 0) : new FileOutputStream(str));
            try {
                objectOutputStream2.writeObject(obj);
                objectOutputStream2.flush();
                try {
                    objectOutputStream2.close();
                } catch (IOException unused) {
                }
                return true;
            } catch (Throwable th) {
                th = th;
                objectOutputStream = objectOutputStream2;
                try {
                    th.printStackTrace();
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static boolean a(Context context, String str) {
        if (context == null) {
            return false;
        }
        try {
            return context.deleteFile(str);
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean a(Context context, String str, String str2) {
        if (context == null) {
            return false;
        }
        try {
            File fileStreamPath = context.getFileStreamPath(str);
            if (fileStreamPath.exists()) {
                return fileStreamPath.renameTo(context.getFileStreamPath(str2));
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private static boolean a(String str, List<String> list) {
        for (String str2 : list) {
            if ((str2 + File.separator).startsWith(str + File.separator)) {
                return c(str, str2);
            }
            if ((str + File.separator).startsWith(str2 + File.separator)) {
                return c(str, str2);
            }
        }
        return false;
    }

    private static boolean a(List<String> list) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (p(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean a(JSONObject jSONObject) {
        return jSONObject.optInt("version", -1) > 0 && jSONObject.optLong("musicId", -1L) > 0 && jSONObject.optLong("filesize", -1L) > 0 && jSONObject.optLong("duration", -1L) > 0 && jSONObject.optInt(IjkMediaMeta.IJKM_KEY_BITRATE, -1) > 0 && !jSONObject.isNull(GameJsonKeys.MD5) && jSONObject.optJSONArray("parts") != null && jSONObject.optJSONArray("parts").length() >= 0 && a(jSONObject.optLong("musicId"), jSONObject.optInt("version"), jSONObject.optLong("filesize"), jSONObject.optLong("duration"), jSONObject.optJSONArray("parts").toString(), jSONObject.optInt(IjkMediaMeta.IJKM_KEY_BITRATE), jSONObject.optString("filemd5")).equals(jSONObject.optString(GameJsonKeys.MD5));
    }

    public static byte[] a(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = null;
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            bArr = byteArrayOutputStream.toByteArray();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            return bArr;
        } catch (IOException e2) {
            e2.printStackTrace();
            return bArr;
        }
    }

    public static byte[] a(byte[] bArr, int i) {
        encodeCache(bArr, 0, i);
        return bArr;
    }

    public static byte[] a(byte[] bArr, int i, int i2) {
        decodeCache(bArr, i, i2);
        return bArr;
    }

    public static Object b(Context context, String str) {
        return a(context, str, true);
    }

    public static synchronized String b() {
        String i;
        synchronized (NeteaseMusicUtils.class) {
            i = t.i();
        }
        return i;
    }

    public static String b(long j) {
        return j % 100 == 0 ? String.valueOf(j / 100) : String.valueOf((((float) j) * 1.0f) / 100.0f);
    }

    @Deprecated
    public static String b(long j, int i) {
        return a(j, i) + ".mp3";
    }

    public static String b(long j, int i, String str) {
        return c(j, i, str) + ".uc!";
    }

    public static String b(Context context) {
        String wifi = NEDeviceID.getWifi(context);
        return bt.a((CharSequence) wifi) ? com.netease.nis.bugrpt.b.f.f7863b : wifi;
    }

    public static String b(String str) {
        Throwable th;
        FileInputStream fileInputStream;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(new File(str));
            try {
                try {
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            String a2 = a(messageDigest.digest());
                            ah.a(fileInputStream);
                            return a2;
                        }
                        b(bArr, read);
                        messageDigest.update(bArr, 0, read);
                    }
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    ah.a(fileInputStream);
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                ah.a(fileInputStream);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            ah.a(fileInputStream);
            throw th;
        }
    }

    public static String b(String str, String str2) {
        File file = new File(str);
        int i = 1;
        while (file.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(0, str.lastIndexOf(46)));
            sb.append("(");
            i++;
            sb.append(i);
            sb.append(").");
            sb.append(str2);
            file = new File(sb.toString());
        }
        return file.getPath();
    }

    public static String b(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            return a(messageDigest.digest());
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0350  */
    /* JADX WARN: Type inference failed for: r6v14, types: [int] */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v17, types: [java.lang.Object, java.io.File[]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:112:0x0333 -> B:75:0x0336). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<java.lang.String> b(boolean r13) {
        /*
            Method dump skipped, instructions count: 865
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.cloudmusic.utils.NeteaseMusicUtils.b(boolean):java.util.List");
    }

    public static void b(int i) {
        SharedPreferences.Editor edit = bh.a("additional_perfer_file").edit();
        edit.putInt("autoClose", i);
        edit.putLong("autoCloseSetTime", i == 0 ? 0L : System.currentTimeMillis());
        edit.apply();
        if (i != ApplicationWrapper.getInstance().getResources().getStringArray(g.a.f2248a).length - 1) {
            a(0, 0);
        }
    }

    private static void b(List<String> list) {
        IStatisticService iStatisticService = (IStatisticService) ServiceFacade.get(ServiceConst.STATISTIC_SERVICE, IStatisticService.class);
        try {
            String[] strArr = (String[]) bl.a((Class<?>) StorageManager.class, "getVolumePaths", (Class<?>[]) null, (StorageManager) ApplicationWrapper.getInstance().getApplicationContext().getSystemService("storage"), new Object[0]);
            if (strArr == null) {
                iStatisticService.log3("sysdebug", IAPMTracker.KEY_COMMON_KEY_MSPM, "storagedebug", "in", bt.a(list, ","), "storage", "null", "finger", Build.FINGERPRINT);
                return;
            }
            List asList = Arrays.asList(strArr);
            if (list.size() == asList.size() && list.containsAll(asList)) {
                return;
            }
            iStatisticService.log3("sysdebug", IAPMTracker.KEY_COMMON_KEY_MSPM, "storagedebug", "in", bt.a(list, ","), "storage", bt.a(asList, ","), "finger", Build.FINGERPRINT);
        } catch (Exception e2) {
            e2.printStackTrace();
            iStatisticService.log3("sysdebug", IAPMTracker.KEY_COMMON_KEY_MSPM, "storagedebug", "in", bt.a(list, ","), "storage", e2.toString(), "finger", Build.FINGERPRINT);
        }
    }

    private static boolean b(String str, List<String> list) {
        boolean z;
        String str2;
        String str3 = f5527a;
        com.netease.cloudmusic.log.a.a(str3, "getExternalMounts300:" + bt.a(list, ",") + "," + str);
        if (list.contains(str)) {
            return true;
        }
        com.netease.cloudmusic.log.a.a(str3, "getExternalMounts301:");
        String str4 = System.currentTimeMillis() + "";
        File file = new File(str, str4);
        if (new File(str).list() == null) {
            return true;
        }
        com.netease.cloudmusic.log.a.a(str3, "getExternalMounts302:");
        try {
            file.createNewFile();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        Iterator<String> it = list.iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                z = true;
                break;
            }
            String next = it.next();
            str2 = f5527a;
            com.netease.cloudmusic.log.a.a(str2, "getExternalMounts302:" + next);
            if (file.exists()) {
                com.netease.cloudmusic.log.a.a(str2, "getExternalMounts303:");
                if (new File(next, str4).exists() || c(str, next)) {
                    break;
                }
            } else {
                com.netease.cloudmusic.log.a.a(str2, "getExternalMounts305:");
                if (c(str, next)) {
                    com.netease.cloudmusic.log.a.a(str2, "getExternalMounts306:");
                    break;
                }
            }
        }
        com.netease.cloudmusic.log.a.a(str2, "getExternalMounts304:");
        file.delete();
        boolean z2 = !z;
        String str5 = f5527a;
        com.netease.cloudmusic.log.a.a(str5, "getExternalMounts307:" + z2);
        if (!z2) {
            z2 = a(str, list);
        }
        com.netease.cloudmusic.log.a.a(str5, "getExternalMounts308:" + z2);
        return z2;
    }

    public static byte[] b(byte[] bArr, int i) {
        a(bArr, 0, i);
        return bArr;
    }

    public static int c(int i) {
        if (i >= 400000) {
            return 999000;
        }
        if (i >= 400000 || i < 250000) {
            return (i >= 250000 || i < 160000) ? 128000 : 192000;
        }
        return 320000;
    }

    public static int c(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static String c() {
        return Build.BRAND;
    }

    public static String c(long j, int i, String str) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(a(j, i));
        if (str == null) {
            str2 = "";
        } else {
            str2 = "-" + str;
        }
        sb.append(str2);
        sb.append(".mp3");
        return sb.toString();
    }

    private static boolean c(String str, String str2) {
        File file = new File(str);
        File file2 = new File(str2);
        return file.getTotalSpace() == file2.getTotalSpace() && file.getFreeSpace() == file2.getFreeSpace();
    }

    public static byte[] c(String str) {
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            int digit = Character.digit(charArray[i2 + 1], 16) | (Character.digit(charArray[i2], 16) << 4);
            if (digit > 127) {
                digit += InputDeviceCompat.SOURCE_ANY;
            }
            bArr[i] = (byte) digit;
        }
        return bArr;
    }

    public static int d(int i) {
        int a2 = a(i);
        return (a2 % 2 == 0 || a2 <= 1) ? a2 : a2 - 1;
    }

    public static long d(Context context) {
        try {
            return Long.parseLong("1776866597");
        } catch (NumberFormatException unused) {
            return 0L;
        }
    }

    public static String d() {
        return Build.VERSION.RELEASE;
    }

    public static String d(String str) {
        return b(str.getBytes());
    }

    private static native void decodeCache(byte[] bArr, int i, int i2);

    public static native int deserialdata(byte[] bArr);

    public static native byte[] deserialdata2(byte[] bArr);

    public static native byte[] deserialdata2new(int i, byte[] bArr);

    public static native int deserialdatanew(int i, byte[] bArr);

    public static String e() {
        return Build.MODEL;
    }

    public static String e(Context context) {
        return f(context) + ".1776866597";
    }

    public static final String e(String str) {
        return serialurl(str);
    }

    private static native void encodeCache(byte[] bArr, int i, int i2);

    public static String f(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static boolean f() {
        return t.b();
    }

    public static boolean f(String str) {
        return str.endsWith(".uc!");
    }

    public static String g() {
        return a(false);
    }

    public static boolean g(Context context) {
        if (Build.VERSION.SDK_INT < 18) {
            return false;
        }
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            String packageName = context.getApplicationContext().getPackageName();
            Class<?> cls = Class.forName(AppOpsManager.class.getName());
            return ((Integer) cls.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class).invoke(appOpsManager, Integer.valueOf(((Integer) cls.getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(applicationInfo.uid), packageName)).intValue() == 0;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static Object[] g(String str) {
        int lastIndexOf = str.lastIndexOf(File.separator);
        if (lastIndexOf == -1) {
            return null;
        }
        try {
            String[] split = str.substring(lastIndexOf + 1, str.indexOf(".", lastIndexOf)).split("-");
            return new Object[]{Long.valueOf(Long.parseLong(split[0])), Integer.valueOf(Integer.parseInt(split[1])), split[2]};
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return null;
        } catch (Throwable th) {
            if (!(th instanceof ArrayIndexOutOfBoundsException)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static native long getFileInode(String str);

    public static native long getFileLastAccessTime(String str);

    public static native long getFileLastModifiedTime(String str);

    public static boolean h() {
        KeyguardManager keyguardManager = (KeyguardManager) ApplicationWrapper.getInstance().getSystemService("keyguard");
        if (keyguardManager == null) {
            return false;
        }
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    public static boolean h(String str) {
        return Pattern.compile("[^a-zA-Z0-9_\\-\\u4E00-\\u9FA5]").matcher(str).find();
    }

    public static long i() {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return -1L;
        }
        try {
            StatFs statFs = new StatFs(com.netease.cloudmusic.e.J);
            return statFs.getBlockSize() * statFs.getAvailableBlocks();
        } catch (Throwable th) {
            th.printStackTrace();
            return Long.MAX_VALUE;
        }
    }

    public static String i(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("[ \\n]+", PlayService.SEP);
    }

    public static String j() {
        return Base64.encodeToString((b() + PlayService.SEP + e(b())).getBytes(), 2);
    }

    public static boolean j(String str) {
        return com.netease.cloudmusic.e.aw.equals(str);
    }

    public static long k() {
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().toString());
            return statFs.getAvailableBlocks() * statFs.getBlockSize();
        } catch (RuntimeException unused) {
            return 0L;
        }
    }

    public static long k(String str) {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return -1L;
        }
        try {
            StatFs statFs = new StatFs(str);
            return statFs.getBlockSize() * statFs.getBlockCount();
        } catch (Throwable th) {
            th.printStackTrace();
            return Long.MAX_VALUE;
        }
    }

    public static String l(String str) {
        a("NginxPc param: ", (Object) str);
        String encodeToString = Base64.encodeToString(a.a(str, ")(13daqP@ssw0rd~"), 2);
        a("NginxPc key: ", (Object) encodeToString);
        return encodeToString;
    }

    public static native String[] listFiles(String str);

    public static String m(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt != '\t') {
                if (charAt == '\"') {
                    sb.append(Typography.rightDoubleQuote);
                } else if (charAt != '/') {
                    if (charAt == ':') {
                        sb.append((char) 65306);
                    } else if (charAt == '<') {
                        sb.append((char) 65308);
                    } else if (charAt != '|' && charAt != '*') {
                        if (charAt == '+') {
                            sb.append((char) 65291);
                        } else if (charAt == '>') {
                            sb.append((char) 65310);
                        } else if (charAt != '?') {
                            switch (charAt) {
                                case '[':
                                    sb.append((char) 65339);
                                    break;
                                case '\\':
                                    break;
                                case ']':
                                    sb.append((char) 65341);
                                    break;
                                default:
                                    sb.append(charAt);
                                    break;
                            }
                        } else {
                            sb.append((char) 65311);
                        }
                    }
                }
            }
            sb.append(' ');
        }
        return sb.toString();
    }

    public static Uri n(String str) {
        return Uri.parse(o(str));
    }

    public static native void nativeInit(Context context);

    private static native byte[] ncaeDecrypt(byte[] bArr);

    private static native int ncaeVerify(byte[] bArr);

    public static String o(String str) {
        return com.netease.cloudmusic.e.aF + "://" + str + "?" + System.currentTimeMillis();
    }

    private static boolean p(String str) {
        return str.equals("/storage/emulated/legacy") || str.equals("/mnt/shell/emulated/0") || str.equals("/mnt/shell/emulated") || str.equals("/mnt/shell/emulated/legacy") || str.equals("/storage/emulated/0") || str.equals("/storage/sdcard0");
    }

    public static native String serialdata(String str, String str2);

    public static native String serialdatanew(int i, String str, String str2);

    private static native String serialurl(String str);
}

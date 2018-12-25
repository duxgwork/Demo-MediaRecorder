package con.me.kevindue.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import java.util.List;

public class AppUtil {
    private AppUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
            if (runningAppProcessInfos != null && !runningAppProcessInfos.isEmpty()) {
                for (ActivityManager.RunningAppProcessInfo appProcess : runningAppProcessInfos) {
                    if (appProcess.pid == pid) {
                        return appProcess.processName;
                    }
                }
            }
        }
        return null;
    }

    public static synchronized boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pInfo.size(); i++) {
            if (pInfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 安装APK
     *
     * @param context
     * @param path
     * @return file:///storage/emulated/0/download/kry.print.1410414646626.apk
     */
    public static boolean installAPK(Context context, String path) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.parse(path), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取App包名
     *
     * @return App包名
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }


    /**
     * 获取正在运行桌面包名（注：存在多个桌面时且未指定默认桌面时，该方法返回Null,使用时需处理这个情况）
     * @param context
     * @return
     */
    public static String getLauncherPackageName(Context context) {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if (res.activityInfo == null  || "android".equals(res.activityInfo.packageName)) { // 有多个桌面程序存在，且未指定默认项时；
            return null;
        }
        return res.activityInfo.packageName;
    }
}

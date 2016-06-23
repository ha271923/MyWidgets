package com.hawk.widget.utils;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

// TODO: Very important!!!
// old getRecentTasks() API has been replaced with getAppTasks() API=23+, for
// <uses-permission android:name="android.permission.GET_TASKS" />  for getAppTasks
// Importantly, note that the getAppTasks( ) method does return all app tasks.
// Now, this replacement method returns only "the list of tasks associated with the calling application."
// Android 5.1+, for privacy, getRecentTasks() and getAppTasks() can't work normally. usageStatsManager.queryUsageStats is the only way.

public class Utils {

    public static void printProcessesList(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UStats.printProcessesList(context);
        } else {
            ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> RAP = activityManager.getRunningAppProcesses();
            for(ActivityManager.RunningAppProcessInfo processInfo: RAP ){
                SMLog.i("","process = [ "+processInfo.processName+" ]");
            }
        }
    }

    public static String getForegroundPackage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return UStats.getForegroundPackage(context);
        } else {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RecentTaskInfo> appTasks = activityManager.getRecentTasks(Integer.MAX_VALUE, 1);
            if (appTasks == null) {
                return null;
            }
            return appTasks.get(0).origActivity.getPackageName();
        }
    }


    public static String getForegroundActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // the following implementation is always showing com.hawk.widget/com.hawk.widget.ConfigActivity, it's not for all process.
            UStats.getForegroundActivity(context); // TODO: I don't know how to implement it after API21+
        } else {
            List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(Integer.MAX_VALUE);
            if (null != runningTaskInfos) {
                return (runningTaskInfos.get(0).topActivity).toString();
            }
        }
        return null;
    }

    /**
     * Returns whether the launcher which running on the device is importance foreground.
     *
     * @param context A Context of the application package implementing this class.
     * @return True if the importance of the launcher process is {@link android.app.ActivityManager.RunningAppProcessInfo#IMPORTANCE_FOREGROUND}.
     */
    public static boolean isLauncherOnForeground(Context context) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SMLog.e("", "API >= 21   not support!!!");
            result = UStats.isLauncherOnForeground(context);
        } else {
            List<String> names = getAllLaunchers(context);
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appList = mActivityManager.getRunningAppProcesses();
            if (appList.get(0).processName.equals(getDefaultLauncherName(context))) {
                result = true;
            }

            /*
            // Hawk: the following is another implementation, polling the importance value to know if it's foreground, or not.
            for (ActivityManager.RunningAppProcessInfo running : appList)
            {
                SMLog.d("",  " running.processName = " + running.processName +"    running.importance = "+running.importance);
                if (running.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                {
                    for (int i = 0; i < names.size(); i++)
                    {
                        if (names.get(i).equals(running.processName))
                        {
                            result = true;
                            break;
                        }
                    }
                }
                else
                {
                    SMLog.d("",  " processName="+running.processName +"        running.importance="+running.importance);
                }
            }
            */
        }
        return result;
    }

    public static boolean isPackageRunnig(Context context, String processName) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SMLog.e("", "API >= 21   not support!!!");
            result = UStats.isProcessRunnig(context, processName);
        } else {
            List<String> names = null;
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appList = mActivityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo running : appList) {
                for (int i = 0; i < names.size(); i++) {
                    SMLog.d("", " processName=" + names.get(i));
                    if (names.get(i).equals(running.processName)) {
                        if (processName.endsWith(running.processName)) {
                            SMLog.d("", " running.processName=" + running.processName);
                            result = true;
                            break;
                        }
                    }
                }
            }
        }
        SMLog.d("", " isProcessRunnig=" + result);
        return result;
    }

    public static boolean isPackageRunning2(Context context, String packageName) {
        boolean isBackground;
        boolean isLockedState;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            SMLog.e("", "API >= 21   not support!!!");
            return UStats.isProcessRunnig(context, packageName);
        }
        else
        {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            if (activityManager == null) {
                return false;
            }
            // get running application processes
            List<ActivityManager.RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo process : processList) {
                //Log.i(TAG, process.processName);
                if (process.processName.startsWith(packageName)) {
                    isBackground = process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                    isLockedState = keyguardManager.inKeyguardRestrictedInputMode();
                    if (isBackground || isLockedState) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }


    public static String getDefaultLauncherName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;

        return currentHomePackage;
    }

    /**
     * Returns a list of launcher that are running on the device.
     *
     * @param context A Context of the application package implementing this class.
     * @return A list which contains all the launcher package name.If there are no launcher, an empty
     * list is returned.
     */
    public static List<String> getAllLaunchers(Context context) {
        List<String> packageNames = new ArrayList<String>();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo resolveInfo : resolveInfos) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null) {
                packageNames.add(resolveInfo.activityInfo.processName);
                packageNames.add(resolveInfo.activityInfo.packageName);
            }
        }
        return packageNames;
    }
}

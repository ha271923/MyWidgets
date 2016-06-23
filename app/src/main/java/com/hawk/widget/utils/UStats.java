package com.hawk.widget.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by User on 3/2/15.
 */
@TargetApi(21)
public class UStats {
    // permission: android.permission.PACKAGE_USAGE_STATS, AndroidManifest.xml and MainActivity.java needed to modify.

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
    public static final String TAG = UStats.class.getSimpleName();

    // Hawk: tasks.get(0) is Top Activity
    // Hawk: it can work on API21+
    public static void printProcessesList(Context context) {
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        long time = System.currentTimeMillis();
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  time - 1000*1000, time);
        if (appList != null && appList.size() > 0) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : appList) {
                SMLog.i("","process = [ "+usageStats.getPackageName()+" ]");
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
            }
        }
    }


    public static boolean isLauncherOnForeground(Context context) {
        if(getForegroundPackage(context).equals(getDefaultLauncherName(context))){
            return true;
        }
        return false;
    }

    public static String getForegroundPackage(Context context) {
        long ts = System.currentTimeMillis();
        UsageStatsManager usm = getUsageStatsManager(context);
        List<UsageStats> queryUsageStats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST,ts-2000, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return null;
        }
        UsageStats recentStats = null;
        for (UsageStats usageStats : queryUsageStats) {
            if (recentStats == null ||recentStats.getLastTimeUsed() < usageStats.getLastTimeUsed())
            {
                recentStats = usageStats;
            }
        }
        SMLog.e("","Foreground Package = "+ recentStats.getPackageName());
        return recentStats.getPackageName();
    }

    public static String getForegroundActivity(Context context) {
        SMLog.e("","NOT support");
        // TODO: I don't know how to implement it after API21+
        // the following implementation is always showing com.hawk.widget/com.hawk.widget.ConfigActivity, it's not for all process.
        /*
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> tasks = am.getAppTasks();
        for (ActivityManager.AppTask task : tasks) {
            Log.i("", "description: " + task.getTaskInfo().description);
            Log.i("", "origActivity: " + task.getTaskInfo().origActivity);
            return task.getTaskInfo().origActivity.toString();
        }
        */
        return null;
    }


    public static boolean isProcessRunnig(Context context, String processName){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        long time = System.currentTimeMillis();
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  time - 1000*1000, time);
        if (appList != null && appList.size() > 0) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : appList) {
                SMLog.i("","process = [ "+usageStats.getPackageName()+" ]");
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                if(processName.equals(usageStats.getPackageName()))
                {
                    return true;
                }
            }
        }
        return false;
    }


    public static String getDefaultLauncherName(Context context)
    {
        return Utils.getDefaultLauncherName(context);
    }

    // Settings\Security\Apps with usage access
    public static boolean isUsageStatsON(Context context) {
        long ts = System.currentTimeMillis();
        UsageStatsManager usm = getUsageStatsManager(context);
        List queryUsageStats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return false;
        }
        return true;
    }

    public static void getStats(Context context){
        UsageStatsManager usm = getUsageStatsManager(context);
        int interval = UsageStatsManager.INTERVAL_YEARLY;
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();

        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));

        UsageEvents uEvents = usm.queryEvents(startTime,endTime);
        while (uEvents.hasNextEvent()){
            UsageEvents.Event e = new UsageEvents.Event();
            uEvents.getNextEvent(e);

            if (e != null){
                Log.d(TAG, "Event: " + e.getPackageName() + "\t" +  e.getTimeStamp());
            }
        }
    }

    public static List<UsageStats> getUsageStatsList(Context context){
        UsageStatsManager usm = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();

        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));

        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,startTime,endTime);
        return usageStatsList;
    }

    public static void printUsageStats(List<UsageStats> usageStatsList){
        for (UsageStats u : usageStatsList){
            Log.d(TAG, "Pkg: " + u.getPackageName() +  "\t" + "ForegroundTime: "
                    + u.getTotalTimeInForeground()) ;
        }

    }

    public static void printCurrentUsageStatus(Context context){
        printUsageStats(getUsageStatsList(context));
    }

    private static UsageStatsManager getUsageStatsManager(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        return usm;
    }


}
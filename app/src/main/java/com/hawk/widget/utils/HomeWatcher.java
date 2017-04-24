package com.hawk.widget.utils;

/**
 * Created by ha271 on 2016/6/24.
 */

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// For PowerSaving, a widget can try to monitor the HOME_KEY for more behavior control.
public class HomeWatcher {

    static final String TAG = HomeWatcher.class.getSimpleName();
    private Context mContext;
    private IntentFilter mFilter;
    private OnHomePressedListener mListener;
    private InnerRecevier mRecevier;

    public HomeWatcher(Context context) {
        mContext = context;
        mFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
    }

    public void setOnHomePressedListener(OnHomePressedListener listener) {
        mListener = listener;
        mRecevier = new InnerRecevier();
    }

    public void startWatch() {
        if (mRecevier != null) {
            mContext.registerReceiver(mRecevier, mFilter);
        }
    }

    public void stopWatch() {
        if (mRecevier != null) {
            mContext.unregisterReceiver(mRecevier);
        }
    }

    class InnerRecevier extends BroadcastReceiver {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    Log.e(TAG, "action:" + action + ",reason:" + reason);
                    if (mListener != null) {
                        if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                            mListener.onHomePressed();
                        } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                            mListener.onHomeLongPressed();
                        }
                    }
                }
            }
        }
    }


    private static List<String> homePackageNamesCache;

    public static List<String> GetHomePackageNames(Context context){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> info = context.getPackageManager().queryIntentActivities(intent, 0);
        List<String> homes = new ArrayList<String>(info.size());
        for(int i=0; i<info.size(); i++){
            homes.add(info.get(i).activityInfo.packageName);
            SMLog.i("CATEGORY_HOME: " + info.get(i).activityInfo.packageName);
        }
        homes.add(HomeWatcher.class.getPackage().getName());
        return homes;
    }

    public static boolean isHomeAtForeground(Context context){
        if (homePackageNamesCache==null)
            homePackageNamesCache = GetHomePackageNames(context);
        return isHomeAtForeground(context, homePackageNamesCache);
    }

    public static boolean isHomeAtForeground(Context context, List<String> homePackages){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String topPackage = am.getRunningTasks(1).get(0).topActivity.getPackageName();
        return homePackages.contains(topPackage);
    }

}


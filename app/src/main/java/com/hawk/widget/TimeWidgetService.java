package com.hawk.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.hawk.widget.utils.SMLog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hawk.wei on 2016/5/10.
 */
public class TimeWidgetService extends Service {
    private Handler mHandler = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        SMLog.i("");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMLog.i("");
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SMLog.i("");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SMLog.i("");
        mHandler = new Handler();
        mHandler.post(mRunnable);
        return super.onStartCommand(intent, flags, startId);
    }
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            SMLog.i("");
            updateView();
            mHandler.postDelayed(this, 1000);
        }
    };
    private void updateView(){
        SMLog.i("");

        SMLog.i("GetCurrentActivityName");

        // Regular do these jobs
        // Utils.printProcessesList(this);
        // Utils.getForegroundPackage(this);
        // Utils.getForegroundActivity(this);

        RemoteViews view = new RemoteViews(getPackageName(), R.layout.time_widget);
        view.setTextViewText(R.id.time, new SimpleDateFormat( "HH:mm" ).format( new Date()));
        view.setTextViewText(R.id.date, new SimpleDateFormat( "MM月dd日 " ).format( new Date()));

        ComponentName thisWidget = new ComponentName(this, TimeWidget.class);

        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget, view);
    }

}

package com.hawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hawk.widget.utils.SMLog;

/**
 * Created by hawk.wei on 2016/5/10.
 */
public class ListWidget extends AppWidgetProvider{
    public static final String CLICK_ACTION = "com.hawk.widget.CLICK_ACTION";
    public static final String CLICK_EXTRA = "com.hawk.widget.CLICK_EXTRA";


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        SMLog.i("");
        String action = intent.getAction();
        if (action.equals(CLICK_ACTION)) {
            String text = intent.getStringExtra(CLICK_EXTRA);
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        SMLog.i("");
        int minWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        SMLog.i("W="+minWidth+"~"+maxWidth);
        SMLog.i("H="+minHeight+"~"+maxHeight);

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SMLog.i("");

        for (int appWidgetId : appWidgetIds) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.list_widget);

            Intent serviceIntent = new Intent(context, ListWidgetService.class);
            rv.setRemoteAdapter(R.id.list, serviceIntent);

            Intent clickIntent = new Intent(context, ListWidget.class);
            clickIntent.setAction(CLICK_ACTION);

            PendingIntent pendingIntentTemplate = PendingIntent.getBroadcast(
                    context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            rv.setPendingIntentTemplate(R.id.list,pendingIntentTemplate);

            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public ListWidget() {
        super();
        SMLog.i("");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        SMLog.i("");

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        SMLog.i("");

    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        SMLog.i("");

    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        SMLog.i("");

    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        SMLog.i("");

        return super.peekService(myContext, service);
    }
}

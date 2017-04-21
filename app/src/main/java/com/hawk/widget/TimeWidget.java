package com.hawk.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.hawk.widget.utils.SMLog;

/**
 * Implementation of App Widget functionality.
 */
public class TimeWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SMLog.i("");
        // There may be multiple widgets active, so update all of them
        Intent intent = new Intent(context, TimeWidgetService.class);
        context.startService(intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        SMLog.i("");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        SMLog.i("");
        Intent intent = new Intent(context, TimeWidgetService.class);
        context.stopService(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        SMLog.i("");

    }

    public TimeWidget() {
        super();
        SMLog.i("");

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        SMLog.i("");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
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


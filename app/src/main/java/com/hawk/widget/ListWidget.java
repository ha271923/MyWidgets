package com.hawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by hawk.wei on 2016/5/10.
 */
public class ListWidget extends AppWidgetProvider{
    public static final String CLICK_ACTION = "com.hawk.widget.CLICK_ACTION";
    public static final String CLICK_EXTRA = "com.hawk.widget.CLICK_EXTRA";
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (action.equals(CLICK_ACTION)) {
            String text = intent.getStringExtra(CLICK_EXTRA);
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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
}

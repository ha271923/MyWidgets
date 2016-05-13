package com.hawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by hawk.wei on 2016/5/10.
 */
public class GifWidget extends AppWidgetProvider{
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
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            // Create an Intent to launch GifWidget
            Intent clickintent = new Intent(context, GifWidget.class);
            clickintent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, clickintent, PendingIntent.FLAG_UPDATE_CURRENT);
            // Get the layout for the App Widget and attach an on-click listener to the ui element
            String number = String.format("%03d", (new Random().nextInt(900) + 100));
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.activity_main_gifmoviewview);
            remoteViews.setTextViewText(R.id.gif_text, number);
            remoteViews.setOnClickPendingIntent(R.id.gif_text, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}


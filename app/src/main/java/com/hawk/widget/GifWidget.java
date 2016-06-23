package com.hawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Random;

import com.hawk.widget.utils.SMLog;
import com.hawk.widget.utils.Utils;
import com.hawk.widget.view.GifDecoderView;
import com.hawk.widget.view.GifMovieView;
/**
 * Created by hawk.wei on 2016/5/10.
 */
public class GifWidget extends AppWidgetProvider{
    public static final String CLICK_ACTION = "com.hawk.widget.CLICK_ACTION";
    public static final String CLICK_EXTRA = "com.hawk.widget.CLICK_EXTRA";
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        SMLog.i("","");
        String action = intent.getAction();
        if (action.equals(CLICK_ACTION)) {
            String text = intent.getStringExtra(CLICK_EXTRA);
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        SMLog.i("","");

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            appWidgetManager.updateAppWidget(widgetId, buildUpdate(context,appWidgetIds));
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews buildUpdate(Context context, int[] appWidgetIds) {
        // Create an Intent to launch GifWidget
        Intent clickintent = new Intent(context, GifWidget.class);
        clickintent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        clickintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickintent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the layout for the App Widget and attach an on-click listener to the ui element
        String number = String.format("%03d", (new Random().nextInt(900) + 100));
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.activity_main_gifmoviewview);
        remoteViews.setTextViewText(R.id.gif_text, number);
        remoteViews.setOnClickPendingIntent(R.id.gif_text, pendingIntent);

        Log.i("animation","buildUpdate +++++");
        Log.i("GIFa","1. TID="+Thread.currentThread()+"  mRv="+remoteViews+"  mVid="+R.id.gif_imageView);
    // PASSED APIs:
        remoteViews.setInt(R.id.gif_imageView, "setBackgroundResource", R.drawable.widget_bk); // Hawk: test OK! update background 1.39MB pic
        remoteViews.setInt(R.id.gif_imageView, "setImageResource", R.drawable.gpcat); // Hawk: test OK! update front pic
        // remoteViews.setImageViewResource(R.id.gif_imageView, R.drawable.gpcat); // Hawk: test OK! update front pic
    // FAILED APIs:
        // remoteViews.setImageViewBitmap(R.id.gif_imageView,BitmapFactory.decodeResource(context.getResources(),R.drawable.gpcat)); // Caused by: java.lang.IllegalArgumentException: RemoteViews for widget update exceeds maximum bitmap memory usage
        // remoteViews.setBitmap(R.id.gif_imageView, "setImageBitmap", BitmapFactory.decodeResource(context.getResources(),R.drawable.gpcat));

/*
        InputStream stream = null;
        try {
            stream = context.getAssets().open("piggy.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }
        new GifDecoderView(context, stream,remoteViews, R.id.gif_imageView);            // Tell the AppWidgetManager to perform an update on the current app widget
*/
        Log.i("animation","buildUpdate -----");
        return remoteViews;
    }
}

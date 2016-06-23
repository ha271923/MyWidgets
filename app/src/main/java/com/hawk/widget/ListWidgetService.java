package com.hawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.hawk.widget.utils.SMLog;

/**
 * Created by hawk.wei on 2016/5/10.
 */
public class ListWidgetService extends RemoteViewsService {
    private String[] data = new String[]{
            "item A", "item B", "item C", "item D", "item E", "item F", "item G", "item H"
    };
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        SMLog.i("","");
        return new ListRemoteViewsFactory(this, intent);
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
        private final Context mContext;
        private final int mAppWidgetId;

        public ListRemoteViewsFactory(Context context, Intent intent) {
            SMLog.i("","");
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public void onCreate() {
            SMLog.i("","");
        }

        @Override
        public void onDataSetChanged() {
            SMLog.i("","");
        }

        @Override
        public void onDestroy() {
            SMLog.i("","");
        }

        @Override
        public int getCount() {
            SMLog.i("","");
            return data.length;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            SMLog.i("","");
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
            rv.setTextViewText(R.id.widget_item, data[i]);

            Intent intent = new Intent();
            intent.putExtra(ListWidget.CLICK_EXTRA, data[i]);
            rv.setOnClickFillInIntent(R.id.widget_item, intent);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            SMLog.i("","");
            return null;
        }

        @Override
        public int getViewTypeCount() {
            SMLog.i("","");
            return 1;
        }

        @Override
        public long getItemId(int i) {
            SMLog.i("","");
            return i;
        }

        @Override
        public boolean hasStableIds() {
            SMLog.i("","");
            return true;
        }
    }

}



package com.hawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by hawk.wei on 2016/5/10.
 */
public class ListWidgetService extends RemoteViewsService {
    private String[] data = new String[]{
            "item A", "item B", "item C", "item D", "item E", "item F", "item G", "item H"
    };
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this, intent);
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
        private final Context mContext;
        private final int mAppWidgetId;

        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
            rv.setTextViewText(R.id.widget_item, data[i]);

            Intent intent = new Intent();
            intent.putExtra(ListWidget.CLICK_EXTRA, data[i]);
            rv.setOnClickFillInIntent(R.id.widget_item, intent);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}



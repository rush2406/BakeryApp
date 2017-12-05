package com.example.rusha.bakeryapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import java.util.ArrayList;

/**
 * Created by rusha on 7/1/2017.
 */

public class WidgetDataProvider implements RemoteViewsFactory {
    private Context mContext;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    private ArrayList<Ingredients> ingre = new ArrayList<>();

    @Override
    public void onCreate() {

        ingre = MainActivity.Ingre;
    }

    @Override
    public void onDataSetChanged() {
        ingre = MainActivity.Ingre;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingre.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        mView.setTextViewText(android.R.id.text1, ingre.get(i).quantity + "\n" + ingre.get(i).measure + "\n" + ingre.get(i).ingredient);
        return mView;
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

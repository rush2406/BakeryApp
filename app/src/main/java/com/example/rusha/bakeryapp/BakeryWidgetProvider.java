package com.example.rusha.bakeryapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
@TargetApi(16)
public class BakeryWidgetProvider extends AppWidgetProvider {

    /*static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int pos, boolean value) {

        RemoteViews rv;
        rv = getSimpleWidget(context, pos, value,appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews getSimpleWidget(Context context, int pos, boolean value,int appWidgetId) {
        Intent intent;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bakery_widget_provider);
        if (value) {
            intent = new Intent(context, DisplayActivity.class);
           // views.setImageViewResource(R.id.widgetImage, pos);
        } else {
            intent = new Intent(context, MainActivity.class);
            //views.setImageViewResource(R.id.widgetImage, R.drawable.bake);
        }
       // PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //views.setOnClickPendingIntent(R.id.widgetImage, pendingIntent);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);


        return views;
    }*/

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        BakeryService.startActionUpdateWidgets(context);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, int pos, boolean value) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, appWidgetManager, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, mView);
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private static RemoteViews initViews(Context context,
                                         AppWidgetManager widgetManager, int widgetId) {

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.bakery_widget_provider);

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mView.setRemoteAdapter(widgetId, R.id.widgetList, intent);

        return mView;
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


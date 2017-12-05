package com.example.rusha.bakeryapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.example.rusha.bakeryapp.data.BakeryContract;

/**
 * Created by rusha on 6/28/2017.
 */

public class BakeryService extends IntentService {
    public static final String ACTION_UPDATE_WIDGETS = "com.example.rusha.bakeryapp.action.update_widgets";

    public BakeryService() {
        super("BakeryService");
    }

    public static void startActionUpdateWidgets(Context context) {
        Intent intent = new Intent(context, BakeryService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.getAction().equals(ACTION_UPDATE_WIDGETS))
            handleActionUpdateWidgets();

    }

    private void handleActionUpdateWidgets() {

        int id = 0;
        boolean value;
        Cursor cursor = getContentResolver().query(BakeryContract.BakeryEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            value = true;
            id = cursor.getInt(cursor.getColumnIndex(BakeryContract.BakeryEntry.COLUMN_IMAGE));
        } else
            value = false;

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakeryWidgetProvider.class));

        BakeryWidgetProvider.updateWidgets(this, appWidgetManager, appWidgetIds, id, value);
    }
}

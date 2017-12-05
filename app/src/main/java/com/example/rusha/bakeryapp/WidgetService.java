package com.example.rusha.bakeryapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by rusha on 7/1/2017.
 */

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        WidgetDataProvider dataProvider = new WidgetDataProvider(
                getApplicationContext(), intent);

        return dataProvider;
    }
}

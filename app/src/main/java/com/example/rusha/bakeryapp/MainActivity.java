package com.example.rusha.bakeryapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rusha.bakeryapp.data.BakeryContract;
import com.example.rusha.bakeryapp.data.BakeryDbHelper;

import java.util.ArrayList;
import java.util.jar.Manifest;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>,
        com.example.rusha.bakeryapp.ListAdapter.ListItemHandler, SharedPreferences.OnSharedPreferenceChangeListener {
    @InjectView(R.id.list)
    RecyclerView list;
    @InjectView(R.id.empty)
    TextView Empty;
    @InjectView(R.id.progress)
    ProgressBar progressBar;
    private BakeryDbHelper mDbHelper = new BakeryDbHelper(this);
    private com.example.rusha.bakeryapp.ListAdapter adapter;
    public static int mPos;
    public static ArrayList<Ingredients> Ingre = new ArrayList<>();
    private static final int LOADER_ID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Configuration configuration = getResources().getConfiguration();
        if (configuration.smallestScreenWidthDp >= 600)
            list.setLayoutManager(new GridLayoutManager(this, 3));
        else
            list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                new LinearLayoutManager(this).getOrientation());
        list.addItemDecoration(dividerItemDecoration);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
    }

    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {

            @Override
            protected void onStartLoading() {
                progressBar.setVisibility(View.VISIBLE);
                Empty.setVisibility(View.INVISIBLE);
                list.setVisibility(View.INVISIBLE);
                super.onStartLoading();
            }

            @Override
            public ArrayList<Recipe> loadInBackground() {
                ArrayList<Recipe> r = NetworkUtils.fetchData("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
                return r;
            }
        };

    }

    @Override

    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> recipes) {
        progressBar.setVisibility(View.GONE);
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {

            list.setVisibility(View.VISIBLE);
            adapter = new com.example.rusha.bakeryapp.ListAdapter(this, recipes, this);
            list.setAdapter(adapter);
            setupPreferences();

        } else {
            Empty.setVisibility(View.VISIBLE);
            Empty.setText(getString(R.string.no));
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.changewidget) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void ListItemClick(int pos) {
        mPos = pos;
        Log.v(MainActivity.class.getSimpleName(), "Click");
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        int Img = 0;
        int pos;
        String value = "";
        if (s.equals(getString(R.string.widget)))
            value = sharedPreferences.getString(getString(R.string.widget), getString(R.string.defaultvalue));
        if (value.equals(getString(R.string.defaultvalue))) {

            Img = R.drawable.nutella;
            pos = 0;

        } else if (value.equals(getString(R.string.brownie))) {

            Img = R.drawable.brownie;
            pos = 1;
        } else if (value.equals(getString(R.string.yellow))) {

            Img = R.drawable.yellow;
            pos = 2;
        } else {

            Img = R.drawable.cheese;
            pos = 3;
        }
        Log.v(IngredientDisplayActivity.class.getSimpleName(), "Display = " + value);
        int rows = getContentResolver().delete(BakeryContract.BakeryEntry.CONTENT_URI, null, null);

        for (int i = 0; i < ListAdapter.recipies.get(pos).getIngredient().size(); i++) {
            ContentValues values = new ContentValues();
            values.put(BakeryContract.BakeryEntry.COLUMN_IMAGE, Img);
            values.put(BakeryContract.BakeryEntry.COLUMN_QUANTITY, ListAdapter.recipies.get(pos).getIngredient().get(i).getQuantity());
            values.put(BakeryContract.BakeryEntry.COLUMN_MEASURE, ListAdapter.recipies.get(pos).getIngredient().get(i).getMeasure());
            values.put(BakeryContract.BakeryEntry.COLUMN_INGREDIENT, ListAdapter.recipies.get(pos).getIngredient().get(i).getIngredient());

            getContentResolver().insert(BakeryContract.BakeryEntry.CONTENT_URI, values);
        }

        Ingre = new ArrayList<>();
        Cursor cursor = getContentResolver().query(BakeryContract.BakeryEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        while (cursor != null && cursor.moveToNext()) {
            String quantity = cursor.getString(cursor.getColumnIndex(BakeryContract.BakeryEntry.COLUMN_QUANTITY));
            String measure = cursor.getString(cursor.getColumnIndex(BakeryContract.BakeryEntry.COLUMN_MEASURE));
            String ingre = cursor.getString(cursor.getColumnIndex(BakeryContract.BakeryEntry.COLUMN_INGREDIENT));
            Log.v(MainActivity.class.getSimpleName(), "Tinku = " + ingre);
            Ingre.add(new Ingredients(quantity, measure, ingre));
        }

        cursor.close();

        Log.v(MainActivity.class.getSimpleName(), "Start");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakeryWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetList);
    }


    public void setupPreferences() {
        String[] Projection = {
                BakeryContract.BakeryEntry._ID,
                BakeryContract.BakeryEntry.COLUMN_QUANTITY,
                BakeryContract.BakeryEntry.COLUMN_MEASURE,
                BakeryContract.BakeryEntry.COLUMN_INGREDIENT
        };

        int Img = 0;
        int pos;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String value = preferences.getString(getString(R.string.widget), getString(R.string.defaultvalue));
        if (value.equals(getString(R.string.defaultvalue))) {
            pos = 0;
            Img = R.drawable.nutella;
        } else if (value.equals(getString(R.string.brownie))) {
            pos = 1;
            Img = R.drawable.brownie;
        } else if (value.equals(getString(R.string.yellow))) {
            pos = 2;
            Img = R.drawable.yellow;
        } else {
            pos = 3;
            Img = R.drawable.cheese;
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.query(BakeryContract.BakeryEntry.TABLE_NAME, Projection, null, null, null, null, null);
        if (cursor == null || cursor.getCount() == 0) {
            for (int i = 0; i < com.example.rusha.bakeryapp.ListAdapter.recipies.get(pos).getIngredient().size(); i++) {

                ContentValues values = new ContentValues();
                values.put(BakeryContract.BakeryEntry.COLUMN_IMAGE, Img);
                values.put(BakeryContract.BakeryEntry.COLUMN_QUANTITY, com.example.rusha.bakeryapp.ListAdapter.recipies.get(pos).getIngredient().get(i).getQuantity());
                values.put(BakeryContract.BakeryEntry.COLUMN_MEASURE, com.example.rusha.bakeryapp.ListAdapter.recipies.get(pos).getIngredient().get(i).getMeasure());
                values.put(BakeryContract.BakeryEntry.COLUMN_INGREDIENT, com.example.rusha.bakeryapp.ListAdapter.recipies.get(pos).getIngredient().get(i).getIngredient());

                getContentResolver().insert(BakeryContract.BakeryEntry.CONTENT_URI, values);

            }
        }

        cursor.close();

        Cursor c = getContentResolver().query(BakeryContract.BakeryEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        while (cursor != null && c.moveToNext()) {
            String quantity = c.getString(c.getColumnIndex(BakeryContract.BakeryEntry.COLUMN_QUANTITY));
            String measure = c.getString(c.getColumnIndex(BakeryContract.BakeryEntry.COLUMN_MEASURE));
            String ingre = c.getString(c.getColumnIndex(BakeryContract.BakeryEntry.COLUMN_INGREDIENT));
            Log.v(MainActivity.class.getSimpleName(), "Tinku = " + ingre);
            Ingre.add(new Ingredients(quantity, measure, ingre));
        }

        c.close();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakeryWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetList);
    }

}


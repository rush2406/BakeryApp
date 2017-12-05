package com.example.rusha.bakeryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.example.rusha.bakeryapp.data.BakeryContract;
import com.example.rusha.bakeryapp.data.BakeryDbHelper;

public class DetailActivity extends AppCompatActivity implements ViewFragment.ListClick {
    private PlayerFragment playerFragment;
    private boolean mTwoPane;
    public static int Reqpos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getIntent().hasExtra("PositionValue"))
            MainActivity.mPos = getIntent().getIntExtra("PositionVaue", 0);
        Recipe recipe = ListAdapter.recipies.get(MainActivity.mPos);
        Log.v(DetailActivity.class.getSimpleName(), "Title = " + recipe.getName());
        setTitle(ListAdapter.recipies.get(MainActivity.mPos).getName());
        if (findViewById(R.id.linear) != null) {
            mTwoPane = true;

            playerFragment = new PlayerFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.add(R.id.player_container, playerFragment);
            transaction.commit();

        } else
            mTwoPane = false;
    }


    @Override
    public void ClickHandler(int position) {

        Reqpos = position;
        Log.v(DetailActivity.class.getSimpleName(), "there = " + position);
        if (!mTwoPane) {
            Bundle bundle = new Bundle();
            bundle.putInt("keyPos", position);
            Intent intent = new Intent(DetailActivity.this, PlayerActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            PlayerActivity.enter = true;
            playerFragment = new PlayerFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragmentid, playerFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        Reqpos = 0;
        getSupportFragmentManager().popBackStack();
        super.onBackPressed();
    }


}

package com.example.rusha.bakeryapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

public class PlayerActivity extends AppCompatActivity {

    public static int pos;
    private PlayerFragment fragment;
    private FragmentManager manager;
    public static boolean enter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            pos = bundle.getInt("keyPos");
            enter = true;
            Log.v(PlayerActivity.class.getSimpleName(), "position = " + pos);
            ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            fragment = new PlayerFragment();
            manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(viewGroup.getId(), fragment);
            transaction.commit();
        }

    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("key", 3);
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            enter = false;
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}


package com.example.rusha.bakeryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rusha.bakeryapp.data.BakeryContract;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rusha on 6/27/2017.
 */

public class IngredientDisplayActivity extends AppCompatActivity {
    @InjectView(R.id.listingre)
    RecyclerView IngreList;
    private IngredientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_display);
        ButterKnife.inject(this);
        IngreList.setLayoutManager(new LinearLayoutManager(this));
        IngreList.setHasFixedSize(true);
        adapter = new IngredientAdapter(this, MainActivity.mPos);
        IngreList.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(IngreList.getContext(),
                new LinearLayoutManager(this).getOrientation());
        IngreList.addItemDecoration(dividerItemDecoration);

    }


}


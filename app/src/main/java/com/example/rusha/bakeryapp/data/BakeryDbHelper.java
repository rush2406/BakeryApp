package com.example.rusha.bakeryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.exoplayer2.C;

/**
 * Created by rusha on 6/29/2017.
 */

public class BakeryDbHelper extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bakery.db";

    public BakeryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + BakeryContract.BakeryEntry.TABLE_NAME + " (" +
                BakeryContract.BakeryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BakeryContract.BakeryEntry.COLUMN_IMAGE + " INTEGER, "+
                BakeryContract.BakeryEntry.COLUMN_QUANTITY + " TEXT NOT NULL, "+
                BakeryContract.BakeryEntry.COLUMN_MEASURE + " TEXT NOT NULL, "+
                BakeryContract.BakeryEntry.COLUMN_INGREDIENT + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(CREATE_TABLE);
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BakeryContract.BakeryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

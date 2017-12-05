package com.example.rusha.bakeryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rusha on 6/29/2017.
 */

public class BakeryProvider extends ContentProvider {

    private static final int BAKERY = 100;
    private static final int BAKERY_ID = 101;
    private static final String LOG_TAG = BakeryProvider.class.getSimpleName();
    private UriMatcher sUriMatcher = buildUriMatcher();
    private BakeryDbHelper mDbHelper;

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(BakeryContract.CONTENT_AUTHORITY, BakeryContract.PATH, BAKERY);
        uriMatcher.addURI(BakeryContract.CONTENT_AUTHORITY, BakeryContract.PATH + "/#", BAKERY_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new BakeryDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case BAKERY:
                long id = db.insert(BakeryContract.BakeryEntry.TABLE_NAME, null, contentValues);
                if (id < 0) {
                    Toast.makeText(getContext(), "Insert Failed", Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "Insert failed");
                } else
                    getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(BakeryContract.BakeryEntry.CONTENT_URI, id);
            default:
                throw new UnsupportedOperationException("Unknown Uri = " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rows=0;
        switch (match) {
            case BAKERY:
              rows = db.delete(BakeryContract.BakeryEntry.TABLE_NAME,null,null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri = " + uri);
        }
        if (rows > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BAKERY:
                return BakeryContract.CONTENT_LIST_TYPE;
            case BAKERY_ID:
                return BakeryContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor;
        switch (match)
        {
            case BAKERY:
                cursor = db.query(BakeryContract.BakeryEntry.TABLE_NAME,strings,s,strings1,null,null,null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri = " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
}

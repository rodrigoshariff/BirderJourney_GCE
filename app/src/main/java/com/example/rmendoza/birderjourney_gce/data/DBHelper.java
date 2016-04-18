package com.example.rmendoza.birderjourney_gce.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by rmendoza on 2/29/2016.
 */
public class DBHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BirderJourney.db";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_BIRDS_TABLE = "CREATE TABLE " + ProviderContract.BIRD_TABLE_NAME + " ("+
                ProviderContract.birds_table._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ProviderContract.birds_table.SISRECID_COL + " TEXT ," +
                ProviderContract.birds_table.COMMONNAME_COL + " TEXT ," +
                ProviderContract.birds_table.DATETIME_COL + " TEXT ," +
                ProviderContract.birds_table.LOCATION_COL + " TEXT ," +
                ProviderContract.birds_table.LAT_COL + " TEXT ," +
                ProviderContract.birds_table.LONG_COL + " TEXT ," +
                ProviderContract.birds_table.NOTE_COL + " TEXT ," +
                "UNIQUE ("+ ProviderContract.birds_table._ID +") ON CONFLICT IGNORE)";

        db.execSQL(SQL_CREATE_BIRDS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS birds_table");
        onCreate(db);
    }

    public void truncateBirdsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from birds_table");
    }

    public long getRecordCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db,ProviderContract.BIRD_TABLE_NAME);
        db.close();
        return cnt;
    }

}


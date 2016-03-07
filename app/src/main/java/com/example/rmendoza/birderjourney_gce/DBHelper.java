package com.example.rmendoza.birderjourney_gce;

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

    public static final String DATABASE_NAME = "BirderJourney_GCE.db";
    public static final String BIRDS_TABLE_NAME = "Birds";
    public static final String BIRDS_COLUMN_ID = "id";
    public static final String BIRDS_COLUMN_SISRECID = "SISRecID";
    public static final String BIRDS_COLUMN_NAME = "Name";  //common name (scientific name)
    public static final String BIRDS_COLUMN_FAMILY = "Family";
    public static final String BIRDS_COLUMN_ORDER = "BirdOrder";
    public static final String BIRDS_COLUMN_DESCRIPTION = "Description";
    public static final String BIRDS_COLUMN_REDLISTCAT = "RedListCategory";
    public static final String BIRDS_COLUMN_IMAGEID = "ImageID";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table Birds (id integer primary key autoincrement, SISRecID text, Name text, Family text, BirdOrder text, Description text, RedListCategory text, ImageID text)");
        db.execSQL("create table Birds (id integer primary key autoincrement, SISRecID text, Name text, Family text, BirdOrder text, Description text, RedListCategory text, ImageID text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Birds");
        onCreate(db);
    }

    public void truncateBirdsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Birds");
    }

    public long getRecordCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, "Birds");
        db.close();
        return cnt;
    }


    public boolean insertBird (String SISRecID, String Name, String Family, String BirdOrder, String Description, String RedListCategory, String ImageID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BIRDS_COLUMN_SISRECID, SISRecID);
        contentValues.put(BIRDS_COLUMN_NAME, Name);
        contentValues.put(BIRDS_COLUMN_FAMILY, Family);
        contentValues.put(BIRDS_COLUMN_ORDER, BirdOrder);
        contentValues.put(BIRDS_COLUMN_DESCRIPTION, Description);
        contentValues.put(BIRDS_COLUMN_REDLISTCAT, RedListCategory);
        contentValues.put(BIRDS_COLUMN_IMAGEID, ImageID);
        db.insert(BIRDS_TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<String> getAllBirds(String searchstring)
    {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Birds where Name like '%" + searchstring +"%'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(BIRDS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

}


package com.example.rmendoza.birderjourney_gce.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by rodrigoshariff on 4/14/2016.
 */
public class BirdProvider extends ContentProvider {


    private DBHelper mOpenHelper;

    private static final String DBNAME = "BirderJourney_GCE_db";
    private SQLiteDatabase db;

    public boolean onCreate() {

        mOpenHelper = new DBHelper(getContext());
        return true;
    }

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ProviderContract.CONTENT_AUTHORITY, ProviderContract.PATH,1);
        uriMatcher.addURI(ProviderContract.CONTENT_AUTHORITY, ProviderContract.PATH+"/groupby",2);
        uriMatcher.addURI(ProviderContract.CONTENT_AUTHORITY, ProviderContract.PATH+"/#",3);
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (uriMatcher.match(uri)) {
            case 1:
                retCursor=mOpenHelper.getReadableDatabase().query(
                        ProviderContract.BIRD_TABLE_NAME,
                        projection,
                        selection,
                        selection==null? null : selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case 2:
                retCursor=mOpenHelper.getReadableDatabase().query(
                        ProviderContract.BIRD_TABLE_NAME,
                        projection,
                        selection,
                        selection==null? null : selectionArgs,
                        "commonName",
                        null,
                        sortOrder
                );
                break;
            case 3:
                retCursor=mOpenHelper.getReadableDatabase().query(
                        ProviderContract.BIRD_TABLE_NAME,
                        projection,
                        ProviderContract.birds_table._ID + " = '" + uri.getLastPathSegment() + "'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case 1: {
                long _id = db.insert(ProviderContract.BIRD_TABLE_NAME, null, values);
                if ( _id > 0 ){
                    returnUri = ProviderContract.birds_table.buildBirdUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return returnUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case 3:
                String id = uri.getLastPathSegment(); //gets the id
                count = db.delete(ProviderContract.BIRD_TABLE_NAME, selection + "_ID = " + id, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;

    }




    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)){
            case 1:
                return ProviderContract.birds_table.CONTENT_TYPE;
            case 2:
                return ProviderContract.birds_table.CONTENT_TYPE;
            case 3:
                return ProviderContract.birds_table.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);

        }

    }




}

package com.example.rmendoza.birderjourney_gce.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rodrigoshariff on 4/14/2016.
 */
public class ProviderContract {

    //URI data
    public static final String CONTENT_AUTHORITY = "com.example.rmendoza.birderjourney_gce";
    public static final String PATH = "birds_table";
    public static Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String BIRD_TABLE_NAME = "birds_table";

    public static final class birds_table implements BaseColumns
    {
        public static final String SISRECID_COL = "SISRecID";
        public static final String COMMONNAME_COL = "commonName";
        public static final String DATETIME_COL = "datetime";
        public static final String LOCATION_COL = "location";
        public static final String LAT_COL = "latitude";
        public static final String LONG_COL = "longitude";
        public static final String NOTE_COL = "note";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final Uri CONTENT_URI_GROUPBY = BASE_CONTENT_URI.buildUpon().appendPath(PATH).appendPath("groupby").build();

        //Types
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH;

        public static Uri buildBirdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


}

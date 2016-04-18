package com.example.rmendoza.birderjourney_gce;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by rodrigoshariff on 4/16/2016.
 */
public class DistinctReportCursorAdapter  extends CursorAdapter {

        public DistinctReportCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_item_distinct_report, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView tvCommonName = (TextView) view.findViewById(R.id.list_item_commonname);
            TextView tvBirdCount = (TextView) view.findViewById(R.id.list_item_count);
            // Extract properties from cursor
            String commonname = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
            int birdCount = cursor.getInt(cursor.getColumnIndexOrThrow("birdCount"));
            // Populate fields with extracted properties
            tvCommonName.setText(commonname);
            tvBirdCount.setText(String.valueOf(birdCount));
        }


}

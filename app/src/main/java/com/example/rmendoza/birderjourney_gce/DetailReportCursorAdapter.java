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
public class DetailReportCursorAdapter extends CursorAdapter {

        public DetailReportCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_item_detail_report, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView tvDay = (TextView) view.findViewById(R.id.day_text);
            TextView tvTime = (TextView) view.findViewById(R.id.time_text);
            TextView tvLocation = (TextView) view.findViewById(R.id.location_text);
            TextView tvNote = (TextView) view.findViewById(R.id.note_text);

            tvDay.setText(Utilities.getDayOnly(cursor.getString(cursor.getColumnIndexOrThrow("datetime"))));
            tvTime.setText(Utilities.getTimeOnly(cursor.getString(cursor.getColumnIndexOrThrow("datetime"))));
            tvLocation.setText(cursor.getString(cursor.getColumnIndexOrThrow("location")));
            tvNote.setText(cursor.getString(cursor.getColumnIndexOrThrow("note")));

        }

}

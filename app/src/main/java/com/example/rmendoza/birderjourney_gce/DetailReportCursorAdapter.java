package com.example.rmendoza.birderjourney_gce;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmendoza.birderjourney_gce.data.ProviderContract;

import java.util.ArrayList;


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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.list_item_detail_report, parent, false);
            return view;

        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, final Context context, Cursor cursor) {

            final int row_id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            final String MapURL = "https://maps.google.com/maps?q="
                    + cursor.getString(cursor.getColumnIndexOrThrow("latitude"))
                    + "," + cursor.getString(cursor.getColumnIndexOrThrow("longitude"));
            final String Observation = '\n'+"Bird:  " + cursor.getString(cursor.getColumnIndexOrThrow("commonName"))
                    +'\n'+  "Location:  " + cursor.getString(cursor.getColumnIndexOrThrow("location"))
                    +'\n'+  "Date:  " + Utilities.getDayOnly(cursor.getString(cursor.getColumnIndexOrThrow("datetime")))
                    +"  " + Utilities.getTimeOnly(cursor.getString(cursor.getColumnIndexOrThrow("datetime")))
                    +'\n'+  MapURL;

            // Find fields to populate in inflated template
            TextView tvDay = (TextView) view.findViewById(R.id.day_text);
            TextView tvTime = (TextView) view.findViewById(R.id.time_text);
            TextView tvLocation = (TextView) view.findViewById(R.id.location_text);
            TextView tvNote = (TextView) view.findViewById(R.id.note_text);
            Button btnDelete = (Button) view.findViewById(R.id.button_delete);
            Button btnShare = (Button) view.findViewById(R.id.button_share);
            Button btnMap = (Button) view.findViewById(R.id.button_map);

            tvDay.setText(Utilities.getDayOnly(cursor.getString(cursor.getColumnIndexOrThrow("datetime"))));
            tvTime.setText(Utilities.getTimeOnly(cursor.getString(cursor.getColumnIndexOrThrow("datetime"))));
            tvLocation.setText(cursor.getString(cursor.getColumnIndexOrThrow("location")));
            tvNote.setText(cursor.getString(cursor.getColumnIndexOrThrow("note")));

            final String latitude = cursor.getString(cursor.getColumnIndexOrThrow("latitude"));
            final String longitude = cursor.getString(cursor.getColumnIndexOrThrow("longitude"));

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view != null) {
                        Log.i("DeleteButton", "DELETE button clicked "+row_id);
                        AlertDialog.Builder adb=new AlertDialog.Builder(context);
                        adb.setTitle("Delete?");
                        adb.setMessage(R.string.delete_observation);
                        adb.setNegativeButton("CANCEL", null);
                        adb.setPositiveButton("DELETE", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                context.getContentResolver().delete(ProviderContract.birds_table.buildBirdUri(row_id),"",null);
                                notifyDataSetChanged();
                                Intent intent = new Intent("refresh distinct count");
                                intent.putExtra("message", "This is my message!");
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                            }});
                        adb.show();
                    }
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view != null) {

                        Log.d("SHARELATLONG", Observation);
                        Intent intent = new Intent ();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, "Observation from Birder Journey" + Observation );
                        intent.setType("text/plain");
                        context.startActivity(Intent.createChooser(intent, "" )
                        );
                    }
                }
            });


            btnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view != null) {

                        FragmentActivity activity = (FragmentActivity)(context);
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();

                        Log.d("MAPLATLONG", latitude + "    " + longitude);
                        Bundle args = new Bundle();
                        args.putString("Latitude", latitude);
                        args.putString("Longitude", longitude);
                        DialogMapFragment obsMapFragment = new DialogMapFragment();
                        obsMapFragment.setArguments(args);
                        obsMapFragment.show(fragmentManager, "dialog");
                    }
                }
            });

        }

}

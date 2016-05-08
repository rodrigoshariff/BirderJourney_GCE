package com.example.rmendoza.birderjourney_gce.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.rmendoza.birderjourney_gce.MainActivity;
import com.example.rmendoza.birderjourney_gce.R;
import com.example.rmendoza.birderjourney_gce.Utilities;
import com.example.rmendoza.birderjourney_gce.data.ProviderContract;

/**
 * Created by rodrigoshariff on 5/7/2016.
 */
public class WidgetProvider  extends AppWidgetProvider {


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        String latestbird = "N/A";
        String day = "N/A";
        String time = "N/A";
        String location = "N/A";
        String note = "N/A";

        Cursor cursorLastBird = context.getContentResolver().query(
                ProviderContract.birds_table.CONTENT_URI,
                null,
                null,
                null,
                "datetime DESC LIMIT 1"
        );
        if (cursorLastBird.getCount() > 0) {
            cursorLastBird.moveToFirst();
            latestbird = cursorLastBird.getString(cursorLastBird.getColumnIndexOrThrow("commonName"));
            day = Utilities.getDayOnly(cursorLastBird.getString(cursorLastBird.getColumnIndexOrThrow("datetime")));
            time = Utilities.getTimeOnly(cursorLastBird.getString(cursorLastBird.getColumnIndexOrThrow("datetime")));
            location = cursorLastBird.getString(cursorLastBird.getColumnIndexOrThrow("location"));
            note = cursorLastBird.getString(cursorLastBird.getColumnIndexOrThrow("note"));
                    }

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews WidgetViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            WidgetViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);
            WidgetViews.setTextViewText(R.id.latestbird_text,latestbird);
            WidgetViews.setTextViewText(R.id.day_text,day);
            WidgetViews.setTextViewText(R.id.time_text,time);
            WidgetViews.setTextViewText(R.id.location_text,location);
            WidgetViews.setTextViewText(R.id.note_text,note);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, WidgetViews);
        }
    }



}

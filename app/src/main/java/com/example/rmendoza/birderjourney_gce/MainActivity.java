package com.example.rmendoza.birderjourney_gce;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.BirdArrayItem;
import com.example.rmendoza.birderjourney_gce.data.ProviderContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SearchActivityFragment.OnBirdSelectedListener,
        DistinctReportActivityFragment.OnSpeciesSelectedListener, SaveDialogFragment.SaveDialogListener,
        DetailReportActivityFragment.OnDeleteItemListener {

    private boolean mTwoPane;
    String period = "";
    String currentLatMain = "";
    String currentLongMain = "";
    String nearcityMain = "";
    String sisrecIDMain = "";
    String commonNameMain = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (findViewById(R.id.bird_detail_container) != null) {
            mTwoPane = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.bird_detail_container, new PlaceHolderFragment(), "TTTAG")
                    .addToBackStack(null)
                    .commit();

        } else {
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_container, new SearchActivityFragment(), "TTTAG")
                    .commit();
        }

    }

    public void onBirdSelected(BirdArrayItem birdItemSelected) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

            sisrecIDMain = birdItemSelected.getSISRecID();
            commonNameMain = birdItemSelected.getCommonName();

            Bundle args = new Bundle();
            args.putString("sisrecID", birdItemSelected.getSISRecID());
            args.putString("commonName", birdItemSelected.getCommonName());
            args.putString("scientificName", birdItemSelected.getScientificName());
            args.putString("fullName", birdItemSelected.getFullName());
            args.putString("family", birdItemSelected.getFamily());
            args.putString("order", birdItemSelected.getOrder());
            args.putString("na_Occurrence", birdItemSelected.getNA_Occurrence());
            args.putString("description", birdItemSelected.getDescription());
            args.putString("iucn_Category2014", birdItemSelected.getIUCN_Category2014());
            args.putString("imageID", birdItemSelected.getImageID());
            args.putString("imageFileName", birdItemSelected.getImageFileName());

            BirdDetailActivityFragment fragment = new BirdDetailActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.bird_detail_container, fragment)
                    .commit();

        } else {
            Intent intent = new Intent(this, BirdDetailActivity.class);

            intent.putExtra("sisrecID", birdItemSelected.getSISRecID());
            intent.putExtra("commonName", birdItemSelected.getCommonName());
            intent.putExtra("scientificName", birdItemSelected.getScientificName());
            intent.putExtra("fullName", birdItemSelected.getFullName());
            intent.putExtra("family", birdItemSelected.getFamily());
            intent.putExtra("order", birdItemSelected.getOrder());
            intent.putExtra("na_Occurrence", birdItemSelected.getNA_Occurrence());
            intent.putExtra("description", birdItemSelected.getDescription());
            intent.putExtra("iucn_Category2014", birdItemSelected.getIUCN_Category2014());
            intent.putExtra("imageID", birdItemSelected.getImageID());
            intent.putExtra("imageFileName", birdItemSelected.getImageFileName());

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Bundle bundletran = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(intent, bundletran);
            }
            else
            {
                startActivity(intent);
            }

        }
    }

    public void OnSpeciesSelected(String speciesCommonName, String period, boolean mTwoPane) {
        if (mTwoPane) {

            Context context = getApplicationContext();
            Toast.makeText(context, "Going to detail report TABLET for" + speciesCommonName, Toast.LENGTH_SHORT).show();

            Bundle args = new Bundle();
            args.putString("speciesCommonName", speciesCommonName);
            args.putString("period", period);
            args.putBoolean("mTwoPane", mTwoPane);

            DetailReportActivityFragment fragment = new DetailReportActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.bird_detail_container, fragment)
                    .commit();
        } else {

//            Context context = getApplicationContext();
//            Toast.makeText(context, "Going to detail report for" + speciesCommonName, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, DetailReportActivity.class);
            intent.putExtra("speciesCommonName", speciesCommonName);
            intent.putExtra("period", period);
            intent.putExtra("mTwoPane", mTwoPane);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Bundle bundletran = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(intent, bundletran);
            }
            else
            {
                startActivity(intent);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(SearchActivityFragment.rootView.getWindowToken(), 0);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_search) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_container, new SearchActivityFragment(), "TTTAG")
                    .addToBackStack(null)
                    .commit();

            if(mTwoPane) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.bird_detail_container, new PlaceHolderFragment(), "TTTAG")
                        .addToBackStack(null)
                        .commit();
            }

            return true;
        }

        if (id == R.id.action_shareall) {
            Cursor cursorTest = getContentResolver().query(
                    ProviderContract.birds_table.CONTENT_URI,
                    null,
                    null,
                    null,
                    "datetime DESC" );

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_TEXT, DatabaseUtils.dumpCursorToString(cursorTest));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Birder Journey Observations");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

            //Log.d("TAG", DatabaseUtils.dumpCursorToString(cursorTest));

            return true;
        }

        if (id == R.id.today) {
            period = "today";
            if (getFirstBird() == null) {

                Context context = getApplicationContext();
                Toast.makeText(context, "No saved observations yet; Use search", Toast.LENGTH_SHORT).show();
                return false;
            }
            commitDistinctReport();
        }

        if (id == R.id.week) {
            period = "week";
            if (getFirstBird() == null) {

                Context context = getApplicationContext();
                Toast.makeText(context, "No saved observations yet; Use search", Toast.LENGTH_SHORT).show();
                return false;
            }
            commitDistinctReport();
        }

        if (id == R.id.month) {
            period = "month";
            if (getFirstBird() == null) {

                Context context = getApplicationContext();
                Toast.makeText(context, "No saved observations yet; Use search", Toast.LENGTH_SHORT).show();
                return false;
            }
            commitDistinctReport();
        }

        if (id == R.id.year) {
            period = "year";
            if (getFirstBird() == null) {

                Context context = getApplicationContext();
                Toast.makeText(context, "No saved observations yet; Use search", Toast.LENGTH_SHORT).show();
                return false;
            }
            commitDistinctReport();
        }

        if (id == R.id.ever) {
            period = "ever";
            if (getFirstBird() == null) {

                Context context = getApplicationContext();
                Toast.makeText(context, "No saved observations yet; Use search", Toast.LENGTH_SHORT).show();
                return false;
            }
            commitDistinctReport();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDialogSaveClick(DialogFragment dialog, String note, String sisrecID, String commonName, String currentLat, String currentLong, String nearcity) {

        DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        ContentValues values = new ContentValues();
        values.put(ProviderContract.birds_table.SISRECID_COL, sisrecID);
        values.put(ProviderContract.birds_table.COMMONNAME_COL, commonName);
        values.put(ProviderContract.birds_table.DATETIME_COL, df.format(cal.getTime()));
        values.put(ProviderContract.birds_table.LOCATION_COL, nearcity);
        values.put(ProviderContract.birds_table.LAT_COL, currentLat);
        values.put(ProviderContract.birds_table.LONG_COL, currentLong);
        values.put(ProviderContract.birds_table.NOTE_COL, note);
        getContentResolver().insert(ProviderContract.birds_table.CONTENT_URI, values);
        Toast.makeText(this, "Observation saved. Note: " + note, Toast.LENGTH_SHORT).show();

    }


    public void OnFabLog(String currentLat, String currentLong, String nearcity) {

        currentLatMain = currentLat;
        currentLongMain = currentLong;
        nearcityMain = nearcity;

    }

    public void commitDistinctReport() {

        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putString("Period", period);
            args.putBoolean("mTwoPane", mTwoPane);

            DistinctReportActivityFragment fragment = new DistinctReportActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_container, fragment)
                    .addToBackStack(null)
                    .commit();

            OnSpeciesSelected(getFirstBird(), period, mTwoPane);


        } else {
            Intent intent = new Intent(this, DistinctReportActivity.class);
            intent.putExtra("Period", period);
            intent.putExtra("mTwoPane", mTwoPane);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                Bundle bundletran = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(intent, bundletran);
            }
            else
            {
                startActivity(intent);
            }
        }
    }

    public String getFirstBird() {
        Cursor cursorFirstBird = getContentResolver().query(
                ProviderContract.birds_table.CONTENT_URI,
                new String[]{"commonName"},
                "datetime > ?",
                new String[]{Utilities.getBeginTime(period)},
                "commonName ASC LIMIT 1"
        );
        if (cursorFirstBird.getCount() > 0) {
            cursorFirstBird.moveToFirst();
            return cursorFirstBird.getString(cursorFirstBird.getColumnIndexOrThrow("commonName"));
        } else {
            return null;
        }
    }


    public void OnDeleteItem(String periodClicked) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putString("Period", periodClicked);
            args.putBoolean("mTwoPane", mTwoPane);

            DistinctReportActivityFragment fragment = new DistinctReportActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_container, fragment)
                    .commit();
        }
    }

}

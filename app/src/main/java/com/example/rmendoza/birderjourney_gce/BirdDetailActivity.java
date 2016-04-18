package com.example.rmendoza.birderjourney_gce;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmendoza.birderjourney_gce.data.ProviderContract;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BirdDetailActivity extends AppCompatActivity implements SaveDialogFragment.SaveDialogListener{

    String sisrecID = "";
    String commonName = "";
    String scientificName = "";
    String family = "";
    String order = "";
    String iucn_Category2014 = "";
    String description = "";
    String ImageURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        final ImageView ViewBirdImage = (ImageView) findViewById(R.id.birdImage);
        final TextView ViewCommonName = (TextView) findViewById(R.id.txtCommonName);
        final TextView ViewScientificName = (TextView) findViewById(R.id.txtScientificName);
        final TextView ViewFamily = (TextView) findViewById(R.id.txtFamily);
        final TextView ViewOrder = (TextView) findViewById(R.id.txtOrder);
        final TextView ViewRedListCat = (TextView) findViewById(R.id.txtRedListCat);
        final TextView ViewDescription = (TextView) findViewById(R.id.txtDescription);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            sisrecID = intent.getStringExtra("sisrecID");
            commonName = intent.getStringExtra("commonName");
            scientificName=intent.getStringExtra("scientificName");
            family=intent.getStringExtra("family");
            order=intent.getStringExtra("order");
            iucn_Category2014=intent.getStringExtra("iucn_Category2014");
            description=intent.getStringExtra("description");
            if (intent.getStringExtra("imageID").equals("TBD"))
            {
                ImageURL = "http://vignette2.wikia.nocookie.net/legendmarielu/images/b/b4/No_image_available.jpg/revision/latest?cb=20130511180903";
            }
            else
            {
                ImageURL = "https://rodrigoshariff.smugmug.com/Bird/Birder-Journey/" + intent.getStringExtra("imageID") + "/0/L/" + intent.getStringExtra("imageFileName");
            }

/*          Bundle arguments = new Bundle();
            arguments.putString("commonName", getIntent().getStringExtra("commonName"));
            arguments.putString("scientificName", getIntent().getStringExtra("scientificName"));
            arguments.putString("fullName", getIntent().getStringExtra("fullName"));
            arguments.putString("family", getIntent().getStringExtra("family"));
            arguments.putString("order", getIntent().getStringExtra("order"));
            arguments.putString("na_Occurrence", getIntent().getStringExtra("na_Occurrence"));
            arguments.putString("description", getIntent().getStringExtra("description"));
            arguments.putString("iucn_Category2014", getIntent().getStringExtra("iucn_Category2014"));
            arguments.putString("imageID", getIntent().getStringExtra("imageID"));
            arguments.putString("imageFileName", getIntent().getStringExtra("imageFileName"));

            BirdDetailActivityFragment fragment = new BirdDetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.bird_detail_container, fragment)
                    .commit();*/
        }
        else
        {

            sisrecID = savedInstanceState.getString("sisrecID");
            commonName = savedInstanceState.getString("commonName");
            scientificName = savedInstanceState.getString("scientificName");
            family = savedInstanceState.getString("family");
            order = savedInstanceState.getString("order");
            iucn_Category2014 = savedInstanceState.getString("iucn_Category2014");
            description = savedInstanceState.getString("description");
            ImageURL = savedInstanceState.getString("imageURL");
        }


        ViewCommonName.setText(commonName);
        collapsingToolbar.setTitle(commonName);
        ViewScientificName.setText(scientificName);
        ViewFamily.setText(family);
        ViewOrder.setText(order);
        ViewRedListCat.setText(iucn_Category2014);
        ViewDescription.setText(description);
        Picasso.with(this).load(ImageURL).into(ViewBirdImage);


        FloatingActionButton fabLog = (FloatingActionButton) findViewById(R.id.fabLog);
        if (fabLog != null) {
            fabLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment SaveDialogFragment = new SaveDialogFragment();
                    SaveDialogFragment.show(getSupportFragmentManager(), "SaveDialog");

    //                Context context = getApplicationContext();
    //                Toast.makeText(context, "Start dialog to log observation", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onDialogSaveClick(DialogFragment dialog, String note) {

        DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        ContentValues values= new ContentValues();
        values.put(ProviderContract.birds_table.SISRECID_COL, sisrecID);
        values.put(ProviderContract.birds_table.COMMONNAME_COL, commonName);
        values.put(ProviderContract.birds_table.DATETIME_COL, df.format(cal.getTime()));
        values.put(ProviderContract.birds_table.LOCATION_COL, "");
        values.put(ProviderContract.birds_table.LAT_COL,"");
        values.put(ProviderContract.birds_table.LONG_COL,"");
        values.put(ProviderContract.birds_table.NOTE_COL, note);
        getContentResolver().insert(ProviderContract.birds_table.CONTENT_URI,values);
        Toast.makeText(this, "Observation saved. Note: " + note, Toast.LENGTH_SHORT).show();

    }



/*    public Location getLocation() {
        boolean isGPSEnabled = false;
        boolean isNetworkEnabled = false;
        boolean canGetLocation = false;
        Location location;
        LocationManager locationManager;
        GoogleApiClient mGoogleApiClient;

        final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
        final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

        try {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.v("TAG", "isGPSEnabled =" + isGPSEnabled);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.v("TAG", "isNetworkEnabled =" + isNetworkEnabled);

            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                Log.d("TAG", "Network");
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled && location == null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("TAG", "GPS Enabled");
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        } catch (Exception e) {
            Log.e("TAG", "Location Not Found");
        }
        return location;
    }*/




    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("sisrecID", sisrecID);
        state.putString("commonName", commonName);
        state.putString("scientificName", scientificName);
        state.putString("family", family);
        state.putString("order", order);
        state.putString("description", description);
        state.putString("iucn_Category2014", iucn_Category2014);
        state.putString("imageURL", ImageURL);
    }


}

package com.example.rmendoza.birderjourney_gce;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmendoza.birderjourney_gce.data.ProviderContract;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BirdDetailActivity extends AppCompatActivity implements SaveDialogFragment.SaveDialogListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//, com.google.android.gms.location.LocationListener

    String sisrecID = "";
    String commonName = "";
    String scientificName = "";
    String family = "";
    String order = "";
    String iucn_Category2014 = "";
    String description = "";
    String ImageURL = "";
    String currentLat = "";
    String currentLong = "";
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    LocationRequest mLocationRequest;
    private static final int REQUEST_FINE_LOCATION = 0;
    String nearcity = "unknown";


    public class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            nearcity = resultData.getString(GetLocalityService.Constants.RESULT_DATA_KEY);
            if (resultCode == GetLocalityService.Constants.SUCCESS_RESULT) {
                Context context = getApplicationContext();
                Toast.makeText(context, R.string.address_found, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        buildGoogleApiClient();

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
            scientificName = intent.getStringExtra("scientificName");
            family = intent.getStringExtra("family");
            order = intent.getStringExtra("order");
            iucn_Category2014 = intent.getStringExtra("iucn_Category2014");
            description = intent.getStringExtra("description");
            if (intent.getStringExtra("imageID").equals("TBD")) {
                ImageURL = null;
            } else {
                ImageURL = "https://rodrigoshariff.smugmug.com/Bird/Birder-Journey/" + intent.getStringExtra("imageID") + "/0/L/" + intent.getStringExtra("imageFileName");
            }

        } else {

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
        if (ImageURL == null){
            ViewBirdImage.setImageResource(R.drawable.no_image_available);
        }
        else
        {
            Picasso.with(this).load(ImageURL).into(ViewBirdImage);
        }


        FloatingActionButton fabLog = (FloatingActionButton) findViewById(R.id.fabLog);
        if (fabLog != null) {
            fabLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startIntentService();
                    DialogFragment SaveDialogFragment = new SaveDialogFragment();
                    SaveDialogFragment.show(getSupportFragmentManager(), "SaveDialog");

                    // Context context = getApplicationContext();
                    // Toast.makeText(context, "Start dialog to log observation", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void startIntentService() {
        AddressResultReceiver mResultReceiver;
        mResultReceiver = new AddressResultReceiver(new Handler());
        Intent intent = new Intent(this, GetLocalityService.class);
        intent.putExtra(GetLocalityService.Constants.RECEIVER, mResultReceiver);
        intent.putExtra(GetLocalityService.Constants.LOCATION_DATA_EXTRA, mCurrentLocation);
        startService(intent);
    }


    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d("TAG1", connectionResult.getErrorMessage());
    }

    public void onDisconnected() {

        Log.d("TAG1", "Disconnected");
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.d("TAG", "Connection suspended");
        mGoogleApiClient.connect();
    }

//    @Override
//    public void onLocationChanged(Location location) {
//        Log.d("locationtesting", "accuracy: " + location.getAccuracy() + " lat: " + location.getLatitude() + " lon: " + location.getLongitude());
//        Toast.makeText(this, "Location Changed", Toast.LENGTH_SHORT).show();
//    }

    public void onStatusChanged(String string, int i, Bundle bundle) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);

        } else {
//            mLocationRequest = new LocationRequest();
//            mLocationRequest.setInterval(100);
//            mLocationRequest.setFastestInterval(100);
//            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mCurrentLocation != null) {
                currentLat = String.valueOf(mCurrentLocation.getLatitude());
                currentLong = String.valueOf(mCurrentLocation.getLongitude());
//                startIntentService();

            } else {
                currentLat = "";
                currentLong = "";
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    mLocationRequest = new LocationRequest();
//                    mLocationRequest.setInterval(100);
//                    mLocationRequest.setFastestInterval(100);
//                    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

                    mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (mCurrentLocation != null) {
                        currentLat = String.valueOf(mCurrentLocation.getLatitude());
                        currentLong = String.valueOf(mCurrentLocation.getLongitude());
//                        startIntentService();
                    } else {
                        currentLat = "";
                        currentLong = "";
                    }

                } else {
                    // no granted
                }
                return;
            }
        }
    }


    public void onDialogSaveClick(DialogFragment dialog, String note, String sisrecIDd, String commonNamed, String currentLatd, String currentLongd, String nearcityd) {

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

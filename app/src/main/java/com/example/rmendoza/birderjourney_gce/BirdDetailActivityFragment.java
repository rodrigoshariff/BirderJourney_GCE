package com.example.rmendoza.birderjourney_gce;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class BirdDetailActivityFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


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
    Context thiscontext;


    public BirdDetailActivityFragment() {
    }


    public class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            nearcity = resultData.getString(GetLocalityService.Constants.RESULT_DATA_KEY);
            if (resultCode == GetLocalityService.Constants.SUCCESS_RESULT) {
                Context context = getActivity().getApplicationContext();
                Toast.makeText(context, R.string.address_found, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_bird_detail, container, false);

        final ImageView ViewBirdImage = (ImageView) rootView.findViewById(R.id.birdImage);
        final TextView ViewCommonName = (TextView) rootView.findViewById(R.id.txtCommonName);
        final TextView ViewScientificName = (TextView) rootView.findViewById(R.id.txtScientificName);
        final TextView ViewFamily = (TextView) rootView.findViewById(R.id.txtFamily);
        final TextView ViewOrder = (TextView) rootView.findViewById(R.id.txtOrder);
        final TextView ViewRedListCat = (TextView) rootView.findViewById(R.id.txtRedListCat);
        final TextView ViewDescription = (TextView) rootView.findViewById(R.id.txtDescription);

        thiscontext = container.getContext();
        buildGoogleApiClient();

        Bundle arguments = getArguments();
        if (arguments != null) {
            sisrecID = arguments.getString("sisrecID");
            commonName = arguments.getString("commonName");
            scientificName = arguments.getString("scientificName");
            family = arguments.getString("family");
            order = arguments.getString("order");
            iucn_Category2014 = arguments.getString("iucn_Category2014");
            description = arguments.getString("description");
            if (arguments.getString("imageID").equals("TBD")) {
                ImageURL = "http://vignette2.wikia.nocookie.net/legendmarielu/images/b/b4/No_image_available.jpg/revision/latest?cb=20130511180903";
            } else {
                ImageURL = "https://rodrigoshariff.smugmug.com/Bird/Birder-Journey/" + arguments.getString("imageID") + "/0/L/" + arguments.getString("imageFileName");
            }
        }

        ViewCommonName.setText(commonName);
        ViewScientificName.setText(scientificName);
        ViewFamily.setText(family);
        ViewOrder.setText(order);
        ViewRedListCat.setText(iucn_Category2014);
        ViewDescription.setText(description);
        Picasso.with(this.getContext()).load(ImageURL).into(ViewBirdImage);


        FloatingActionButton fabLog = (FloatingActionButton) rootView.findViewById(R.id.fabLog);
        if (fabLog != null) {
            fabLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startIntentService();
                    //mCallback.OnFabLog(currentLat, currentLong, nearcity);

                    Bundle arguments = new Bundle();
                    arguments.putString("sisrecID", sisrecID);
                    arguments.putString("commonName", commonName);
                    arguments.putString("currentLat", currentLat);
                    arguments.putString("currentLong", currentLong);
                    arguments.putString("nearcity", nearcity);

                    FragmentManager fragmentManager = getFragmentManager();
                    DialogFragment saveDialogFragment = new SaveDialogFragment();
                    saveDialogFragment.setArguments(arguments);

                    saveDialogFragment.show(fragmentManager, "SaveDialog");

                    // Context context = getApplicationContext();
                    // Toast.makeText(context, "Start dialog to log observation", Toast.LENGTH_SHORT).show();
                }
            });
        }


        return rootView;
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(thiscontext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void startIntentService() {
        AddressResultReceiver mResultReceiver;
        mResultReceiver = new AddressResultReceiver(new Handler());
        Intent intent = new Intent(getContext(), GetLocalityService.class);
        intent.putExtra(GetLocalityService.Constants.RECEIVER, mResultReceiver);
        intent.putExtra(GetLocalityService.Constants.LOCATION_DATA_EXTRA, mCurrentLocation);
        getContext().startService(intent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
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
//                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);

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



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d("TAG1", connectionResult.getErrorMessage());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.d("TAG", "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }



}

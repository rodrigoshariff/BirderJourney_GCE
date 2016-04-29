package com.example.rmendoza.birderjourney_gce;

import android.app.Activity;
import android.content.ContentValues;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmendoza.birderjourney_gce.data.ProviderContract;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class BirdDetailActivityFragment extends Fragment implements SaveDialogFragment.SaveDialogListener{


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
    public BirdDetailActivityFragment() {
    }


    OnFabLogListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnFabLogListener {

        public void OnFabLog(String currentLat, String currentLong, String nearcity);
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
                    mCallback.OnFabLog(currentLat, currentLong, nearcity);
                    FragmentManager fragmentManager = getFragmentManager();
                    SaveDialogFragment saveDialogFragment = new SaveDialogFragment();
                    //saveDialogFragment.setTargetFragment(BirdDetailActivityFragment.this, 1);
                    saveDialogFragment.show(fragmentManager, "SaveDialog");



                    // Context context = getApplicationContext();
                    // Toast.makeText(context, "Start dialog to log observation", Toast.LENGTH_SHORT).show();
                }
            });
        }


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnFabLogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSpeciesSelectedListener");
        }
    }

    public void onDialogSaveClick(DialogFragment dialog, String note) {

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
        getActivity().getContentResolver().insert(ProviderContract.birds_table.CONTENT_URI, values);
        Toast.makeText(getContext(), "Observation saved. Note: " + note, Toast.LENGTH_SHORT).show();

    }




}

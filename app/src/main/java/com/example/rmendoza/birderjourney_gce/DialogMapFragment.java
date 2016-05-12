package com.example.rmendoza.birderjourney_gce;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.appengine.repackaged.com.google.common.base.Flag;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogMapFragment extends DialogFragment implements OnMapReadyCallback{

    GoogleMap obs_map;
    boolean mapReady = false;
    double latitude = 0;
    double longitude = 0;
    SupportMapFragment mapFrag;

    public DialogMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle arguments = getArguments();
        if (arguments != null) {
            latitude = Double.parseDouble(arguments.getString("Latitude"));
            longitude = Double.parseDouble(arguments.getString("Longitude"));
        }

        View rootView  = inflater.inflate(R.layout.fragment_dialog_map, container, false);

        mapFrag = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady=true;
        obs_map = googleMap;
        LatLng obsLocation = new LatLng(latitude, longitude);
        CameraPosition obsPosition = CameraPosition.builder().target(obsLocation).zoom(13).build();
        obs_map.moveCamera(CameraUpdateFactory.newCameraPosition(obsPosition));
        obs_map.addMarker(new MarkerOptions().position(obsLocation));

    }

    @Override
    public void onStop() {
        this.dismiss();
        super.onStop();
    }


    @Override
    public void onPause() {
        this.dismiss();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getFragmentManager().beginTransaction().remove(mapFrag).commit();
    }

}

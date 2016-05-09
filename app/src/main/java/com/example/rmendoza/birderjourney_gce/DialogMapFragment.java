package com.example.rmendoza.birderjourney_gce;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogMapFragment extends DialogFragment implements OnMapReadyCallback{

    GoogleMap obs_map;
    boolean mapReady = false;

    public DialogMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_map, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady=true;
        obs_map = googleMap;
        LatLng obsLocation = new LatLng(40.7, -73.98);

    }
}

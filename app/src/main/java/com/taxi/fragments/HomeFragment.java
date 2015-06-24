package com.taxi.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.taxi.R;

public class HomeFragment extends Fragment {

    private MapView mMapView;
    private GoogleMap googleMap;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }





    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
            googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            googleMap.setMyLocationEnabled(true);

    }

    @Override
    public void onResume() {
        super.onResume();
      //  initilizeMap();
    }


}

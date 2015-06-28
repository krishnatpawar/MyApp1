package com.taxi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.taxi.R;


public class HomeFragment extends Fragment {

    OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);

            Toast.makeText(getActivity(),
                    "Maps activated", Toast.LENGTH_SHORT)
                    .show();

        }
    };
    private MapView mMapView;
    private GoogleMap googleMap;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(
                R.id.map)).getMapAsync(onMapReadyCallback);
        try {
            // Loading map
            //initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(
                R.id.map)).getMapAsync(onMapReadyCallback);
        // check if map is created successfully or not
        if (googleMap == null) {
            Toast.makeText(getActivity(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
//        // latitude and longitude
//        double latitude = 17.385044;
//        double longitude = 78.486671;
//
//        // create marker
//        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");
//
//        // Changing marker icon
//        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon));
//
//        // adding marker
//        googleMap.addMarker(marker);
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        googleMap.setMyLocationEnabled(true);
//        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//        googleMap.getUiSettings().setCompassEnabled(true);
//
//        Toast.makeText(getActivity(),
//                "Maps activated", Toast.LENGTH_SHORT)
//                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //initilizeMap();
    }


}

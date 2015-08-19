package com.taxi.fragments;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.taxi.Activity.HomeScreenActivity;
import com.taxi.Activity.MainActivity;
import com.taxi.R;
import com.taxi.utils.CustomLog;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private GoogleMap mGoogleMap;
    private SupportMapFragment map;
    private Date mLastUpdateTime;
    private Location mCurrentLocation;
    private ImageButton imgHome;
    private Location mLastLocation;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(final GoogleMap googleMap) {
            mGoogleMap = googleMap;

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            //googleMap.getUiSettings().setCompassEnabled(true);
            //googleMap.getMyLocation();
            CustomLog.v("Taxi_map_onMapReady", "" + mGoogleApiClient);
        }
    };
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private EditText edtSearchMap;
    private boolean isCalled;

    private Toolbar mToolbar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        map = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (map == null) {
            map = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, map).commit();
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_acticity, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.map_toolbar);
        ((HomeScreenActivity) getActivity()).setMapToolbar(mToolbar);
//        ((ActionBarActivity) getActivity()).setSupportActionBar(mToolbar);
//        ((ActionBarActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.map_toolbar);
        imgHome = (ImageButton) view.findViewById(R.id.imgMapHome);
        edtSearchMap = (EditText) view.findViewById(R.id.map_search);
        //customizedActionBAr();
        //buildGoogleApiClient();

        // First we need to check availability of play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();

            createLocationRequest();
        }

        ((SupportMapFragment) getChildFragmentManager().findFragmentById(
                R.id.map)).getMapAsync(onMapReadyCallback);

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            CustomLog.v("taxi_map", " " + mGoogleApiClient.isConnected() + ", " +
                    mGoogleApiClient.isConnecting());
        }
        return view;
    }

    /**
     * Method to display the location on UI
     */
    private void displayLocation() {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
        } else {

        }
    }

    /**
     * Method to toggle periodic location updates
     */
    private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;

            // Starting the location updates
            startLocationUpdates();

            Log.d(TAG, "Periodic location updates started!");

        } else {

            mRequestingLocationUpdates = false;

            // Stopping the location updates
            stopLocationUpdates();

            Log.d(TAG, "Periodic location updates stopped!");
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            MarkerOptions marker = new MarkerOptions().position
                    (new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())).title("Hello Maps");
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon));
            if (mGoogleMap != null) {
                mGoogleMap.addMarker(marker);
            }
            Toast.makeText(getActivity(),
                    "Maps activated: " + mLastLocation.getLatitude() + " : " + mLastLocation.getLongitude(), Toast.LENGTH_SHORT)
                    .show();
        }
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }


    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        if (mCurrentLocation != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                    LatLng(mCurrentLocation.getLatitude(),
                    mCurrentLocation.getLongitude()), 15));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        CustomLog.v("taxi_map_google", "" + mGoogleApiClient.isConnected() +
                ", " + connectionResult.hasResolution());
    }

    @Override
    public void onLocationChanged(final Location location) {
        mCurrentLocation = location;
        final String mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        CustomLog.v("Map", "" + mLastUpdateTime + " : " + mCurrentLocation.getLatitude() + " , " + mCurrentLocation.getLongitude());
        //updateUI();

        if (isCalled) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                    LatLng(location.getLatitude(),
                    location.getLongitude()), 15));
            isCalled = false;
        }
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                        LatLng(location.getLatitude(),
                        location.getLongitude()), 15));

            }
        });

        Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
        try {
            List<Address> list = geocoder.getFromLocation(mCurrentLocation.getLatitude(),
                    mCurrentLocation.getLongitude(), 1);
            CustomLog.d("Taxi_map_location", "" + getLocation(list.get(0)));
            edtSearchMap.setText("");
            edtSearchMap.setText(getLocation(list.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLocation(Address address) {
        StringBuilder tempLocation = new StringBuilder();
        tempLocation.append(address.getAddressLine(0));
        tempLocation.append("," + address.getAddressLine(1));
        tempLocation.append("," + address.getAddressLine(2));
        tempLocation.append("," + address.getAddressLine(3));
        return tempLocation.toString();

    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleMap == null) {
            CustomLog.v("taxi_map", " " + mGoogleApiClient.isConnected() + ", " +
                    mGoogleApiClient.isConnecting());
            checkPlayServices();

            // Resuming the periodic location updates
            if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
                startLocationUpdates();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

}

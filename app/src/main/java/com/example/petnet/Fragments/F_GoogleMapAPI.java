package com.example.petnet.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.petnet.CostumersActivities.C_FoundDogActivity;
import com.example.petnet.R;
import com.example.petnet.CostumersActivities.C_SignUpPageActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class F_GoogleMapAPI extends Fragment implements OnMapReadyCallback {
    private MapListener listener;
    private MapLisinterForFoundDog listenerForFoundDog;
    private static final String TAG = "mapfrag";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUESR_CODE = 1234;
    private static final float DEAFULT_ZOOM = 15;


    private boolean mLocationPermissionGraunted = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment supportMapFragment;
    private GoogleMap mMap;
    private LatLng homeCordintae;

    //widgets
    private SearchView mSerach;
    private ImageView mGps;
    private AlertDialog dialog;
    private FloatingActionButton sendCoordinates;


    public interface MapListener{
        void onInputMapSend(List<Double> coordiantes);
    }


    public interface MapLisinterForFoundDog{
        void onInputMapSend(List<Double> coordiantes);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_google_map_a_p_i,container, false);

        homeCordintae = null;
        mSerach = v.findViewById(R.id.SV_searchmap);
        mGps = v.findViewById(R.id.IV_gps);
        sendCoordinates = v.findViewById(R.id.FAB_save_address);


        sendCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Send Coordinates button");
                if(homeCordintae!=null){
                    List<Double> addr = new ArrayList<>();
                    addr.add(homeCordintae.latitude);
                    addr.add(homeCordintae.longitude);
                    if(listener !=null)
                    {
                        Toast.makeText(getActivity(),"Got location",Toast.LENGTH_LONG).show();
                        listener.onInputMapSend(addr);
                        Log.d(TAG, "onClick: coordinate sent to listener");
                    }
                    else{
                        Toast.makeText(getActivity(),"Got location",Toast.LENGTH_LONG).show();
                        listenerForFoundDog.onInputMapSend(addr);
                        Log.d(TAG, "onClick: coordinate sent to doundgof");
                    }

                }
                else{
                    Log.d(TAG, "onClick: Failed to send Coordinates");
                    Toast.makeText(getContext(),"Failed to get location,Try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Focus on current location");
                getDeviceLocation();
            }
        });

        mSerach.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onClick: Getting location from the user");
                geolocate();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        getLocationPermission();
        return v;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach: Attching parent activity of the fragment.");
        super.onAttach(context);
        if(context instanceof MapListener)
            listener = (C_SignUpPageActivity)context;

        else if(context instanceof  MapLisinterForFoundDog)
            listenerForFoundDog = (C_FoundDogActivity)context;
    }


    /**
     * in this function we will get the Latlng of the user and save it into out database.
     */
    private void geolocate(){
        Log.d(TAG, "geolocate: looking for the position");
        String location = mSerach.getQuery().toString();
        Geocoder geocoder = new Geocoder(getContext());

        List<Address> addressList = new ArrayList<>();

        try {
            Log.d(TAG, "geolocate: Got location, location is:  " + location);
            addressList = geocoder.getFromLocationName(location,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addressList.size()>0) {
            Address address = addressList.get(0);
            Log.d(TAG, "geolocate: Found location ");
            LatLng curr = null;

            if(address!=null){
                // the curr variable is latlng that represnt the address that the user enter into the serach box.
                curr = new LatLng(address.getLatitude(),address.getLongitude());
                moveCamera(curr,DEAFULT_ZOOM);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(curr));
                homeCordintae = curr;
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //  Toast.makeText(getApplicationContext(),"Map is ready", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onMapReady: Map is ready");
        mMap = googleMap;
        if(mLocationPermissionGraunted) getDeviceLocation();
    }


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: Getting Devices current location");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        try {
            if(mLocationPermissionGraunted){
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: Location is found");
                            Location currLocation = (Location)task.getResult();
                            LatLng curr=null;
                            if(currLocation!=null){
                                curr = new LatLng(currLocation.getLatitude(),currLocation.getLongitude());
                                moveCamera(curr,DEAFULT_ZOOM);
                                mMap.clear();
                                mMap.addMarker(new MarkerOptions().position(curr).title("home"));
                                homeCordintae = curr;
                            }

                        }
                        else{
                            Log.d(TAG, "onComplete: Unable to get current location");
                        }
                    }
                });
            }
        }catch (SecurityException e){

        }
    }


    private void moveCamera(LatLng latlng, float zoom){
        Log.d(TAG, "moveCamera: Moving camera zoom");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,zoom));
    }


    private void initMap(){
        Log.d(TAG, "initMap: Initialize map");
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);
    }


    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: Getting location permission");
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGraunted = true;
            initMap();

        }
        else{
            ActivityCompat.requestPermissions(getActivity(),permissions,LOCATION_PERMISSION_REQUESR_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d(TAG, "onRequestPermissionsResult: request permission for gps.");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case LOCATION_PERMISSION_REQUESR_CODE:
                if(grantResults.length> 0){
                    for(int i = 0 ; i<grantResults.length;i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGraunted = false;
                            return;
                        }
                        mLocationPermissionGraunted = true;

                        //initiallize the map.
                        initMap();
                    }

                }
        }
    }

}





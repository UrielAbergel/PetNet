package com.example.petnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleMapAPI extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "GoogleMapAPI";
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




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_a_p_i);


        homeCordintae = null;

        mSerach = findViewById(R.id.SV_searchmap);
        mGps = findViewById(R.id.IV_gps);
        sendCoordinates = findViewById(R.id.FAB_save_address);



        sendCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Send Coordinate");
                if(homeCordintae!=null){
                    Log.d(TAG, "onClick: send coordinate to signup");
                    Intent intet = new Intent(getApplicationContext(),SignUpPage.class);
                    intet.putExtra("coordinate",homeCordintae);
                    startActivity(intet);


                }
                else{
                    Toast.makeText(getApplicationContext(),"Failed to get location,Try again.",Toast.LENGTH_LONG).show();
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
    }


    /**
     * in this function we will get the Latlng of the user and save it into out database.
     */
    private void geolocate(){
        Log.d(TAG, "geolocate: looking for the position");
        String location = mSerach.getQuery().toString();
        Geocoder geocoder = new Geocoder(this);

        List<Address> addressList = new ArrayList<>();

        try {
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
                mMap.addMarker(new MarkerOptions().position(curr));
                homeCordintae = curr;
            }




        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getApplicationContext(),"Map is ready", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onMapReady: Map is ready");
        mMap = googleMap;

        if(mLocationPermissionGraunted) getDeviceLocation();

        isAtHome();

    }


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: Getting Devices current location");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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
                                mMap.addMarker(new MarkerOptions().position(curr).title("home"));
                            }

                            homeCordintae = curr;



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
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);
    }


    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: Getting location permission");
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGraunted = true;
            initMap();

        }
        else{
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUESR_CODE);
        }
    }

    public void isAtHome(){
        Log.d(TAG, "isAtHome: Check if user is at home to take coordinates");
         dialog = new AlertDialog.Builder(this)
                .setTitle("Verify address")
                .setMessage("Are you at home?")
                .setPositiveButton("Yes",null )
                .setNegativeButton("No",null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // At this point if the user choosed yes we have the address save in homeCordinate variable, need to send it back to SignUpPage.
                //check homeCordinat not null. if null do nothing close the dialog.

                Log.d(TAG, "onClick: From Dialog , the Cordinate is:" + homeCordintae);
                dialog.cancel();

                if(homeCordintae!= null){
                    //return homeCordinate to signuppage and close activity.

                }

            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing only close the alert diialog.
                Log.d(TAG, "onClick: Close dialog and get address from user.");
                dialog.cancel();

            }
        });


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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





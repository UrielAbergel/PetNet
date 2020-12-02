package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

public class FoundDogActivity extends AppCompatActivity implements GoogleMapAPI.MapLisinterForFoundDog {

    final float loc = 260, add_for_loc = 20;
    final short loc_huge = 0, loc_big = 1, loc_medium = 2, loc_small = 3, loc_tiny = 4;
    final double size = 1.2, add_for_size = 0.2;
    final double size_huge = 0, size_big = 1, size_medium = 2, size_small = 3, size_tiny = 4;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = "FoundDogActivity";
    Button B_dog_size_huge;
    Button B_dog_size_big;
    Button B_dog_size_medium;
    Button B_dog_size_small;
    Button B_dog_size_tiny;
    ImageView IV_dog;
    Fragment G_map;

    List<Double> coordinators;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_found_dog);

        B_dog_size_huge = findViewById(R.id.B_huge);
        B_dog_size_big = findViewById(R.id.B_big);
        B_dog_size_medium = findViewById(R.id.B_medium);
        B_dog_size_small = findViewById(R.id.B_small);
        B_dog_size_tiny = findViewById(R.id.B_tiny);
        IV_dog = findViewById(R.id.dog_pic_for_size);

        G_map = new GoogleMapAPI();
        coordinators = new ArrayList<Double>();

        if (isServicesOK())
        {
           getSupportFragmentManager().beginTransaction().replace(R.id.found_dog_frame_layout, G_map).commit();
        }

        B_dog_size_huge.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             IV_dog.setScaleX((float) (size - add_for_size*size_huge));
             IV_dog.setScaleY((float) (size - add_for_size*size_huge));
             IV_dog.setY(loc + add_for_loc*loc_huge);
         }
     });

        B_dog_size_big.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IV_dog.setScaleX((float) (size - add_for_size*size_big));
            IV_dog.setScaleY((float) (size - add_for_size*size_big));
            IV_dog.setY(loc + add_for_loc* loc_big);

        }
    });

        B_dog_size_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IV_dog.setScaleX((float) (size - add_for_size*size_medium));
                IV_dog.setScaleY((float) (size - add_for_size*size_medium));
                IV_dog.setY(loc + add_for_loc* loc_medium);
            }
        });

        B_dog_size_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IV_dog.setScaleX((float)(size - add_for_size*size_small));
                IV_dog.setScaleY((float)(size - add_for_size*size_small));
                IV_dog.setY(loc + add_for_loc* loc_small);
            }
        });

        B_dog_size_tiny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IV_dog.setScaleX((float)(size - add_for_size*size_tiny));
                IV_dog.setScaleY((float)(size - add_for_size*size_tiny));
                IV_dog.setY(loc + add_for_loc* loc_tiny);
            }
        });
    }

    @Override
    public void onInputMapSend(List<Double> coordiantes) {
        coordinators = coordiantes;
    }

    private boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: Checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            //Every thing is fine and the user have can make map request
            Log.d(TAG, "isServicesOK: Google play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: There is a problem we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Log.d(TAG, "isServicesOK: You cant make request map");
        }

        return false;

    }
}
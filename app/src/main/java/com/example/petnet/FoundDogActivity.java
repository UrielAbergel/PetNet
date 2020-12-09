package com.example.petnet;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.petnet.Algorithms.FindDogOwner;
import com.example.petnet.Algorithms.SortHashMap;
import com.example.petnet.Fragments.DogColors;
import com.example.petnet.Fragments.DogSize;
import com.example.petnet.Fragments.GoogleMapAPI;
import com.example.petnet.Objects.Dog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FoundDogActivity extends AppCompatActivity implements GoogleMapAPI.MapLisinterForFoundDog ,DogColors.FoundDoglistener,DogSize.foundDog {


    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = "FoundDogActivity";


    private final String distance_type = "K";
    private final double minimum_distance = 3;


    private CheckBox pet_gender_male;
    private CheckBox pet_gender_female;
    private CheckBox Pet_gender_not_sure;
    private Button B_search_for_owner;
    private List<Integer> colors;
    private Dog dogToFind;
    private Fragment DogColors;
    private Fragment DogSize;
    private Fragment G_map;


    private AutoCompleteTextView pet_race;



    FirebaseFirestore FbFs = FirebaseFirestore.getInstance();






    List<Double> coordinators;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_found_dog);

        InitializeVariables();

        if (isServicesOK())
        {
           getSupportFragmentManager().beginTransaction().replace(R.id.found_dog_frame_layout, G_map).commit();
        }

        B_search_for_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogToFind.setColors(colors);
                dogToFind.setPet_race(pet_race.getText().toString());
                Log.d(TAG, "onClick: race" + dogToFind.getPet_race());
                look_for_optionals_owners();
            }
        });




        pet_gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = pet_gender_male.isChecked();
                if (checked) pet_gender_male.setChecked(false);
                dogToFind.setPet_gender(0);            // 0 means the pet is a female.
            }
        });


        pet_gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = pet_gender_female.isChecked();
                if (checked) pet_gender_female.setChecked(false);
                dogToFind.setPet_gender(1);  // 1 means the pet is a male.
            }
        });
    }

    private void InitializeVariables() {
        dogToFind = new Dog();
        G_map = new GoogleMapAPI();
        DogColors = new DogColors();
        DogSize = new DogSize();


        getSupportFragmentManager().beginTransaction().replace(R.id.dogsize_found_frame_layout,DogSize).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.dog_color_frame_layout,DogColors).commit();
        pet_gender_male = findViewById(R.id.CB_pet_gender_male);
        pet_gender_female = findViewById(R.id.CB_pet_gender_female);
        Pet_gender_not_sure = findViewById(R.id.CB_pet_gender_not_sure);


        colors = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            colors.add(0);
        }

        B_search_for_owner = findViewById(R.id.B_search_owner);

        pet_race = findViewById(R.id.ACTV_pet_race);
        pet_race.setAdapter(new ArrayAdapter<>(FoundDogActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.pet_races)));
        pet_race.setDropDownAnchor(R.id.ACTV_pet_race);

        pet_race.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "onFocusChange: Change Focus on petrace setmax lines to 5" + hasFocus);
                if (hasFocus) {
                    pet_race.showDropDown();
                }
            }
        });


    }


    /**
     * Get all dogs from our database and run the algorithm to see who dog fits best.
     */
    private void look_for_optionals_owners() {
        FirebaseFirestore FbFs = FirebaseFirestore.getInstance(); // get pointer to cloud storage root
        FbFs.collection("dogs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                HashMap<String,Double> userCandidateList = FindDogOwner.find_dog_possible_owners(task, dogToFind); // get list of candidate owners;
                userCandidateList = SortHashMap.sortByValue(userCandidateList);
                Intent intent = new Intent(FoundDogActivity.this, TinderSwipe.class);
                intent.putExtra("data",userCandidateList);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onInputMapSend(List<Double> coordiantes) {
        dogToFind.setAddress(coordiantes);
        Log.d(TAG, "onInputMapSend: coordinates" + dogToFind.getAddress());
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

    @Override
    public void setColor(int position, int value) {
        Log.d(TAG, "setColor: Set dog color");
        colors.set(position,value);

    }

    @Override
    public void getDogSize(int size) {
        Log.d(TAG, "getDogSize: Set dog size");
        dogToFind.setSize(size);

    }
}
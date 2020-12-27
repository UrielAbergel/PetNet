package com.example.src.CostumersActivities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.src.Algorithms.FindDogOwner;
import com.example.src.Algorithms.SortHashMap;
import com.example.src.Fragments.F_DogColors;
import com.example.src.Fragments.F_DogSize;
import com.example.src.Fragments.F_GoogleMapAPI;
import com.example.src.Objects.Dog;
import com.example.src.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class C_FoundDogActivity extends AppCompatActivity implements F_GoogleMapAPI.MapLisinterForFoundDog , F_DogColors.FoundDoglistener, F_DogSize.foundDog {

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = "FoundDogActivity";
    private CheckBox pet_gender_male;
    private CheckBox pet_gender_female;
    private CheckBox pet_gender_not_sure;
    private Button B_search_for_owner;
    private List<Integer> colors;
    private Dog dogToFind;
    private Fragment DogColors;
    private Fragment DogSize;
    private Fragment G_map;
    private AutoCompleteTextView pet_race;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_found_dog);

        InitializeVariables();

        if (isServicesOK()) // check if google servers connections are ok
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
                boolean not_sure = pet_gender_not_sure.isChecked();
                if (checked) pet_gender_male.setChecked(false);
                if (not_sure) pet_gender_not_sure.setChecked(false);
                dogToFind.setPet_gender(0);            // 0 means the pet is a female.
            }
        });

        pet_gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean female_checked = pet_gender_female.isChecked();
                boolean not_sure = pet_gender_not_sure.isChecked();
                if (female_checked) pet_gender_female.setChecked(false);
                if (not_sure) pet_gender_not_sure.setChecked(false);
                dogToFind.setPet_gender(1);  // 1 means the pet is a male.
            }
        });

        pet_gender_not_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean female_checked = pet_gender_female.isChecked();
                boolean male_checked =  pet_gender_male.isChecked();
                if (female_checked) pet_gender_female.setChecked(false);
                if (male_checked) pet_gender_male.setChecked(false);
                dogToFind.setPet_gender(1);  // 1 means the pet is a male.
            }
        });
    }

    /**
     * Initialize all local variables
     */
    private void InitializeVariables() {
        dogToFind = new Dog();
        G_map = new F_GoogleMapAPI();
        DogColors = new F_DogColors();
        DogSize = new F_DogSize();

        getSupportFragmentManager().beginTransaction().replace(R.id.dogsize_found_frame_layout,DogSize).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.dog_color_frame_layout,DogColors).commit();
        pet_gender_male = findViewById(R.id.CB_pet_gender_male);
        pet_gender_female = findViewById(R.id.CB_pet_gender_female);
        pet_gender_not_sure = findViewById(R.id.CB_pet_gender_not_sure);

        colors = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            colors.add(0);
        }

        B_search_for_owner = findViewById(R.id.B_search_owner);

        pet_race = findViewById(R.id.ACTV_pet_race);
        pet_race.setAdapter(new ArrayAdapter<>(C_FoundDogActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.pet_races)));
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
                Intent intent = new Intent(C_FoundDogActivity.this, C_TinderSwipeActivity.class);
                intent.putExtra("data",userCandidateList);
                startActivity(intent);
            }
        });
    }

    /**
     *
     * @param coordiantes represent location of the map who found the dog
     */
    @Override
    public void onInputMapSend(List<Double> coordiantes) {
        dogToFind.setAddress(coordiantes);
        Log.d(TAG, "onInputMapSend: coordinates" + dogToFind.getAddress());
    }


    /**
     * check that google services are ok
     * @return
     */
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
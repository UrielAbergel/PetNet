package com.example.src.CostumersActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.src.Fragments.F_DogColors;
import com.example.src.Fragments.F_DogSize;
import com.example.src.Fragments.F_GoogleMapAPI;
import com.example.src.Objects.Dog;
import com.example.src.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

public class C_UpdateDog extends AppCompatActivity {

    private static final String TAG = "C_UpdateDog";
    private Dog toUpdate;
    private com.google.android.material.textfield.TextInputLayout Pname;
    private AutoCompleteTextView Prace;
    private CheckBox[] genders;
    private com.google.android.material.textfield.TextInputLayout Usigns;
    private Button updateButton;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private int size;
    private Dog toShow;
    private List<Integer> colors;


    private Fragment gMap;
    private Fragment Dogsize;
    private Fragment DogColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_update_dog);

        InitializeVariables();
        setAdapter();
       // getDogInfo();

     //   displayDogInfo();
       // setClicks();
    }

    private void displayDogInfo() {

        Pname.getEditText().setText(toShow.getPet_name());
        Prace.setText(toShow.getPet_race());


    }

    private void getDogInfo() {
        Bundle extras = getIntent().getExtras();
        toShow.setAddress((List)extras.get("address"));
        toShow.setPet_name(extras.getString("pname"));
        toShow.setSize(extras.getInt("size"));
        toShow.setColors((List)extras.get("colors"));
        toShow.setPet_gender(extras.getInt("gender"));
        toShow.setPet_race(extras.getString("race"));
        toShow.setUniqe_signs(extras.getString("usigns"));
    }

    private void setAdapter() {

        Prace.setAdapter(new ArrayAdapter<>(C_UpdateDog.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.pet_races)));
        Prace.setDropDownAnchor(R.id.ACTV_pet_race);
        Prace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Prace.setText(getResources().getStringArray(R.array.pet_races)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Prace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "onFocusChange: Change Focus on petrace setmax lines to 5" + hasFocus);
                if (hasFocus) {
                    Prace.showDropDown();
                }


            }
        });

    }

    private void InitializeVariables() {
        toUpdate = new Dog();
        genders = new CheckBox[2];
        Pname = findViewById(R.id.ET_update_pet_name);
        Prace = findViewById(R.id.ACTV_update_pet_race);
        genders[0] = findViewById(R.id.CB_update_pet_gender_male);
        genders[1]  = findViewById(R.id.CB_update_pet_gender_female);
        updateButton =  findViewById(R.id.B_update);
        gMap = new F_GoogleMapAPI();
        Dogsize = new F_DogSize();
        DogColors = new F_DogColors();
        colors = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            colors.add(0);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.dogSize_update_frame_layout,Dogsize).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.update_dog_color,DogColors).commit();
        if (isServicesOK()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.update_frame_layout,gMap).commit();
        }


    }
    /**
     * check if have permission for googlemap.
     * @return true if there is false otherwise.
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


}
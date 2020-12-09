package com.example.petnet.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.petnet.CostumersActivities.FoundDogActivity;
import com.example.petnet.R;
import com.example.petnet.CostumersActivities.SignUpPage;

import java.util.ArrayList;
import java.util.List;

public class DogColors extends Fragment {
    private CheckBox cb_colors[];
    private View v;
    private final int PET_COLOR_BLACK = 0;
    private final int PET_COLOR_WHITE = 1;
    private final int PET_COLOR_GRAY = 2;
    private final int PET_COLOR_GOLDEN = 3;
    private final int PET_COLOR_BROWN = 4;
    private final int PET_COLOR_LIGHT_GRAY = 5;
    private final int PET_COLOR_LIGHT_BROWN = 6;
    private final int PET_COLOR_DARK_GRAY = 7;
    private final int PET_COLOR_GINGER= 8;
    private List<Integer> colors;

    Signuplistener signuplistener = null;
    FoundDoglistener foundDoglistener = null;

    public interface Signuplistener{
        public void setColor(int position , int value);
    }
    public interface FoundDoglistener{
        public void setColor(int position , int value);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_dog_colors, container, false);

        InitializeVariables();
        setColorsClicks();

        return v;
    }

    private void InitializeVariables() {
        colors = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            colors.add(0);
        }
        cb_colors =  new CheckBox[9];
        cb_colors[PET_COLOR_BLACK] = v.findViewById(R.id.CB_pet_color_black);
        cb_colors[PET_COLOR_WHITE] = v.findViewById(R.id.CB_pet_color_white);
        cb_colors[PET_COLOR_GRAY] = v.findViewById(R.id.CB_pet_color_gray);
        cb_colors[PET_COLOR_GOLDEN] = v.findViewById(R.id.CB_pet_color_golden);
        cb_colors[PET_COLOR_BROWN] = v.findViewById(R.id.CB_pet_color_brown);
        cb_colors[PET_COLOR_LIGHT_GRAY] = v.findViewById(R.id.CB_pet_color_light_gray);
        cb_colors[PET_COLOR_LIGHT_BROWN] = v.findViewById(R.id.CB_pet_color_light_brown);
        cb_colors[PET_COLOR_DARK_GRAY] = v.findViewById(R.id.CB_pet_color_dark_gray);
        cb_colors[PET_COLOR_GINGER] = v.findViewById(R.id.CB_pet_color_ginger);
    }
    private void setColorsClicks() {
        // if color[i] == 1 the user choosed the color else didnt choose.
        cb_colors[PET_COLOR_BLACK].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_BLACK);
            }
        });


        cb_colors[PET_COLOR_WHITE].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_WHITE);
            }
        });

        cb_colors[PET_COLOR_GRAY].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_GRAY);
            }
        });

        cb_colors[PET_COLOR_GOLDEN].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_GOLDEN);

            }
        });

        cb_colors[PET_COLOR_BROWN].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_BROWN);
            }
        });

        cb_colors[PET_COLOR_LIGHT_GRAY].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_LIGHT_GRAY);

            }
        });


        cb_colors[PET_COLOR_LIGHT_BROWN].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_LIGHT_BROWN);

            }
        });
        cb_colors[PET_COLOR_DARK_GRAY].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_DARK_GRAY);

            }
        });

        cb_colors[PET_COLOR_GINGER].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_GINGER);

            }
        });
    }


    /**
     * function that will send the activity who attach the fragment
     * the position of the color and the value.
     * @param position which color have been chosen.
     */
    public void setColor(int position){
        if (colors.get(position) == 0) {
           if(signuplistener!= null){
               signuplistener.setColor(position,1);
           }
           else{
               foundDoglistener.setColor(position,1);
           }
        }
        else{
            if(signuplistener!= null){
                signuplistener.setColor(position,0);
            }
            else{
                foundDoglistener.setColor(position,0);
            }

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Signuplistener){
            signuplistener = (SignUpPage) context;
        }
        else if(context instanceof FoundDoglistener){
            foundDoglistener = (FoundDogActivity)context;
        }
    }
}
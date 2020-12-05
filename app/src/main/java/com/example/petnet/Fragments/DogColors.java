package com.example.petnet.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.petnet.R;

import java.util.List;

public class DogColors extends Fragment {
    private CheckBox cb_colors[];
    View v;
    private final int PET_COLOR_BLACK = 0;
    private final int PET_COLOR_WHITE = 1;
    private final int PET_COLOR_GRAY = 2;
    private final int PET_COLOR_GOLDEN = 3;
    private final int PET_COLOR_BROWN = 4;
    private final int PET_COLOR_ = 5;
    private final int PET_COLOR = 6;
    private final int PET_COLOR_GAY = 7;
    private final int PET_COLOR_GOLEN = 8;
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

        return v;
    }

    private void InitializeVariables() {
        cb_colors[0] = v.findViewById(R.id.CB_pet_color_black);
        cb_colors[1] = v.findViewById(R.id.CB_pet_color_white);
        cb_colors[2] = v.findViewById(R.id.CB_pet_color_gray);
        cb_colors[3] = v.findViewById(R.id.CB_pet_color_golden);
        cb_colors[4] = v.findViewById(R.id.CB_pet_color_brown);
        cb_colors[5] = v.findViewById(R.id.CB_pet_color_);
        cb_colors[6] = v.findViewById(R.id.CB_pet_color);
        cb_colors[7] = v.findViewById(R.id.CB_pet_color_gay);
        cb_colors[8] = v.findViewById(R.id.CB_pet_color_golen);
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

        cb_colors[PET_COLOR_].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_);

            }
        });


        cb_colors[PET_COLOR].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR);

            }
        });
        cb_colors[PET_COLOR_GAY].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_GAY);

            }
        });

        cb_colors[PET_COLOR_GOLEN].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setColor(PET_COLOR_GOLEN);

            }
        });
    }

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
}
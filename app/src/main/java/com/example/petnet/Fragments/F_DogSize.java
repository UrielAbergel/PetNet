package com.example.petnet.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.petnet.CostumersActivities.C_FoundDogActivity;
import com.example.petnet.R;
import com.example.petnet.CostumersActivities.C_SignUpPageActivity;


public class F_DogSize extends Fragment {


    private final int TINY=1,SMALL=2,MEDIUM=3,BIG=4,HUGE=5;
    private final float loc = 260, add_for_loc = 20;
    private  final short loc_huge = 0, loc_big = 1, loc_medium = 2, loc_small = 3, loc_tiny = 4;
    private  final double size = 1.2, add_for_size = 0.2;
    private  final double size_huge = 0, size_big = 1, size_medium = 2, size_small = 3, size_tiny = 4;
    foundDog foundDogListener= null;
    dogSizeForSign dogSizeForSignListener=null;
    private Button B_dog_size_huge;
    private Button B_dog_size_big;
    private Button B_dog_size_medium;
    private Button B_dog_size_small;
    private Button B_dog_size_tiny;
    private ImageView IV_dog;
    private View v;

    public interface foundDog{
        public void getDogSize(int size);
    }

    public interface dogSizeForSign{
        public void getDogSize(int size);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_dog_size, container, false);
        B_dog_size_huge = v.findViewById(R.id.B_huge);
        B_dog_size_big = v.findViewById(R.id.B_big);
        B_dog_size_medium = v.findViewById(R.id.B_medium);
        B_dog_size_small = v.findViewById(R.id.B_small);
        B_dog_size_tiny = v.findViewById(R.id.B_tiny);
        IV_dog = v.findViewById(R.id.dog_pic_for_size);

        setButtonsClick();

        return v;
    }

    private void setButtonsClick() {
        B_dog_size_huge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IV_dog.setScaleX((float) (size - add_for_size*size_huge));
                IV_dog.setScaleY((float) (size - add_for_size*size_huge));
                IV_dog.setY(loc + add_for_loc*loc_huge);
                if(dogSizeForSignListener!=null) dogSizeForSignListener.getDogSize(HUGE);
                else foundDogListener.getDogSize(HUGE);
            }
        });

        B_dog_size_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IV_dog.setScaleX((float) (size - add_for_size*size_big));
                IV_dog.setScaleY((float) (size - add_for_size*size_big));
                IV_dog.setY(loc + add_for_loc* loc_big);
                if(dogSizeForSignListener!=null) dogSizeForSignListener.getDogSize(BIG);
                else foundDogListener.getDogSize(BIG);

            }
        });

        B_dog_size_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IV_dog.setScaleX((float) (size - add_for_size*size_medium));
                IV_dog.setScaleY((float) (size - add_for_size*size_medium));
                IV_dog.setY(loc + add_for_loc* loc_medium);
                if(dogSizeForSignListener!=null) dogSizeForSignListener.getDogSize(MEDIUM);
                else foundDogListener.getDogSize(MEDIUM);
            }
        });

        B_dog_size_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IV_dog.setScaleX((float)(size - add_for_size*size_small));
                IV_dog.setScaleY((float)(size - add_for_size*size_small));
                IV_dog.setY(loc + add_for_loc* loc_small);
                if(dogSizeForSignListener!=null) dogSizeForSignListener.getDogSize(SMALL);
                else foundDogListener.getDogSize(SMALL);
            }
        });

        B_dog_size_tiny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IV_dog.setScaleX((float)(size - add_for_size*size_tiny));
                IV_dog.setScaleY((float)(size - add_for_size*size_tiny));
                IV_dog.setY(loc + add_for_loc* loc_tiny);
                if(dogSizeForSignListener!=null) dogSizeForSignListener.getDogSize(TINY);
                else foundDogListener.getDogSize(TINY);
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof foundDog){
            foundDogListener = (C_FoundDogActivity)context;
        }
        if(context instanceof dogSizeForSign){
            dogSizeForSignListener = (C_SignUpPageActivity)context;
        }

    }
}
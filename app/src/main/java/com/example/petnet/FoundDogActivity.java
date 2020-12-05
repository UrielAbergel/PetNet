package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.petnet.Fragments.DogColors;

public class FoundDogActivity extends AppCompatActivity implements DogColors.FoundDoglistener {

    final float loc = 260, add_for_loc = 20;
    final short loc_huge = 0, loc_big = 1, loc_medium = 2, loc_small = 3, loc_tiny = 4;
    final double size = 1.2, add_for_size = 0.2;
    final double size_huge = 0, size_big = 1, size_medium = 2, size_small = 3, size_tiny = 4;

    Button B_dog_size_huge;
    Button B_dog_size_big;
    Button B_dog_size_medium;
    Button B_dog_size_small;
    Button B_dog_size_tiny;
    ImageView IV_dog;

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
    public void setColor(int position, int value) {

    }
}
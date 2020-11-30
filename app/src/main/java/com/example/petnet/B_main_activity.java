package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class B_main_activity extends AppCompatActivity {

    LinearLayout my_store;
    LinearLayout store_dog_sitter;
    LinearLayout store_dog_trainer;
    LinearLayout store_dog_walker;
    LinearLayout store_dog_vet;
    LinearLayout store_dog_pet_shop;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_activity);
        start_all_listiner();

    }


    private void start_all_listiner(){
        my_store = (LinearLayout)findViewById(R.id.my_store);
        store_dog_sitter = (LinearLayout)findViewById(R.id.dog_sitter_but);
        store_dog_trainer = (LinearLayout)findViewById(R.id.store_dog_trainer);
        store_dog_walker = (LinearLayout)findViewById(R.id.store_dog_walker);
        store_dog_pet_shop = (LinearLayout)findViewById(R.id.store_pet_shop);
        store_dog_vet = (LinearLayout)findViewById(R.id.store_vet);

        store_dog_sitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_dog_sitter_activity();
            }
        });

        store_dog_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_dog_trainer_activity();
            }
        });

        store_dog_walker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_dog_walker_activity();
            }
        });

        store_dog_pet_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("start my store", "onDataChange: start1");
                go_to_my_pet_shop_activity();
            }
        });

        store_dog_vet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("start my store", "onDataChange: start1");
                go_to_my_pet_vet_activity();
            }
        });



        my_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("start my store", "onDataChange: start1");
                go_to_my_store_activity();
            }
        });




    }

    private void go_to_my_pet_vet_activity() {
        Intent intent = new Intent(this, Store_vet.class);
        Log.d("start my store", "onDataChange: start");
        startActivity(intent);
    }

    private void go_to_my_pet_shop_activity() {
        Intent intent = new Intent(this, Store_dog_pet_shop.class);
        Log.d("start my store", "onDataChange: start");
        startActivity(intent);
    }

    private void go_to_dog_walker_activity() {
        Intent intent = new Intent(this, Store_dog_walker.class);
        Log.d("start my store", "onDataChange: start");
        startActivity(intent);


    }

    private void go_to_dog_trainer_activity() {
        Intent intent = new Intent(this, Store_dog_trainer.class);
        Log.d("start my store", "onDataChange: start");
        startActivity(intent);

    }

    private void go_to_dog_sitter_activity() {
        Intent intent = new Intent(this, Store_dog_sitter.class);
        Log.d("start my store", "onDataChange: start");
        startActivity(intent);
    }

    private void go_to_my_store_activity() {
        Log.d("start my store", "onDataChange: start1");

        Intent intent = new Intent(this, B_my_store.class);
        Log.d("start my store", "onDataChange: start");
        startActivity(intent);
    }
}
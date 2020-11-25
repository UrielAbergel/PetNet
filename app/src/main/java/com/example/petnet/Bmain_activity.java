package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Bmain_activity extends AppCompatActivity {

    LinearLayout my_store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmain_activity);
        my_store = (LinearLayout)findViewById(R.id.my_store);

        my_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               go_to_my_store_activity();
            }
        });
    }

    private void go_to_my_store_activity() {
        Intent intent = new Intent(this, Bmy_stores.class);
        startActivity(intent);
    }
}
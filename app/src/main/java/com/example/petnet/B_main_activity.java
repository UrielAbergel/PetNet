package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class B_main_activity extends AppCompatActivity {

    LinearLayout my_store;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_activity);
        my_store = (LinearLayout)findViewById(R.id.my_store);

        my_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("start my store", "onDataChange: start1");
               go_to_my_store_activity();
            }
        });
    }

    private void go_to_my_store_activity() {
        Log.d("start my store", "onDataChange: start1");

        Intent intent = new Intent(this, B_store_adapter.class);
        Log.d("start my store", "onDataChange: start");
        startActivity(intent);
    }
}
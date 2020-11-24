package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Bmy_stores extends AppCompatActivity {

    ImageView new_store_button;
    static int store_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmy_stores);

        new_store_button = (ImageView) findViewById(R.id.s1);

        new_store_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_my_store_bar();
            }
        });


    }

    private void go_to_my_store_bar() {
        New_store_dialog dialog = new New_store_dialog();
        dialog.showDialog(this);
        refresh();


    }

    private void refresh() {

    }
}
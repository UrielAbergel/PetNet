package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class UserMainActivity extends AppCompatActivity {

    LinearLayout LL_found_a_dogl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        LL_found_a_dogl = findViewById(R.id.LL_found_a_dog);

        LL_found_a_dogl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_find_a_dog_page();
            }
        });
    }

    public void go_find_a_dog_page(){
        Intent intent = new Intent(this, FoundDogActivity.class);
        startActivity(intent);
    }
}
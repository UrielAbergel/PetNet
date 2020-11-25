package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Bmy_stores extends AppCompatActivity {

    private ImageView new_store_button;
    static int store_count = 0;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmy_stores);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!! uid  " + currentFirebaseUser.getUid().toString());

        myRef = FirebaseDatabase.getInstance().getReference().child("Busers").child(currentFirebaseUser.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference();
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
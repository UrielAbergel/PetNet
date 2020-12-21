package com.example.petnet.BusinessActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.petnet.BusinessObjects.B_DogTrainer;
import com.example.petnet.GeneralActivity.StoreView;
import com.example.petnet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class B_MainActivity extends AppCompatActivity {

    TextView headline;
    LinearLayout my_store;
    DatabaseReference myRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    LinearLayout store_dog_sitter;
    LinearLayout store_dog_trainer;
    LinearLayout store_dog_walker;
    LinearLayout store_dog_vet;
    LinearLayout store_dog_pet_shop;
    FirebaseDatabase myDB = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_activity);

        start_all_listiner();
        setUserView();


    }









    //==============================================================

    private void setUserView() {
        String uid = mAuth.getCurrentUser().getUid();
        Log.d("ChangeHeadLine","im here !! ");

        myRef = myDB.getReference("Busers").child(uid);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Log.d("ChangeHeadLine","im here ");
                    updateUI(ds);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void updateUI(DataSnapshot ds){
        String key = ds.getKey();
        switch (key){
            case "fname":
                headline.setText("Wellcome " + ds.getValue().toString());
                break;

        }
    }


    private void start_all_listiner(){
        headline = (TextView)findViewById(R.id.textbashboard);
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
        Intent intent = new Intent(this, StoreView.class);
        Log.d("start my store", "onDataChange: start");
        intent.putExtra("type_number" ,4 );
        startActivity(intent);
    }

    private void go_to_my_pet_shop_activity() {
        Intent intent = new Intent(this, StoreView.class);
        Log.d("start my store", "onDataChange: start");
        intent.putExtra("type_number" ,3 );
        startActivity(intent);
    }

    private void go_to_dog_walker_activity() {
        Intent intent = new Intent(this, StoreView.class);
        Log.d("start my store", "onDataChange: start");
        intent.putExtra("type_number" ,2 );
        startActivity(intent);
    }

    private void go_to_dog_trainer_activity() {
        Intent intent = new Intent(this, StoreView.class);
        Log.d("start my store", "onDataChange: start");
        intent.putExtra("type_number", 1);
        startActivity(intent);

    }

    private void go_to_dog_sitter_activity() {
        Intent intent = new Intent(this, StoreView.class);
        Log.d("start my store", "onDataChange: start");
        intent.putExtra("type_number" ,0 );
        startActivity(intent);
    }

    private void go_to_my_store_activity() {
        Log.d("start my store", "onDataChange: start1");

        Intent intent = new Intent(this, B_MyStoreActivity.class);
        Log.d("start my store", "onDataChange: start");
        startActivity(intent);
    }
}
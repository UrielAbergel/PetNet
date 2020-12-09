package com.example.petnet.CostumersActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.petnet.GeneralActivity.StoreView;
import com.example.petnet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class C_UserMainActivity extends AppCompatActivity {

    private static final String TAG = "UserMainActivity";
    LinearLayout LL_found_a_dogl;
    FirebaseDatabase myDB = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView dashbord;
    LinearLayout store_dog_sitter;
    LinearLayout store_dog_trainer;
    LinearLayout store_dog_walker;
    LinearLayout store_dog_vet;
    LinearLayout store_dog_pet_shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        LL_found_a_dogl = findViewById(R.id.LL_found_a_dog);
        dashbord = findViewById(R.id.textbashboard);
        start_all_listiner();
        LL_found_a_dogl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_find_a_dog_page();
            }
        });


        setUserView();
    }





    private void start_all_listiner(){
        store_dog_sitter = (LinearLayout)findViewById(R.id.u_dog_sitter);
        store_dog_trainer = (LinearLayout)findViewById(R.id.u_dog_trainer);
        store_dog_walker = (LinearLayout)findViewById(R.id.u_dog_walker);
        store_dog_pet_shop = (LinearLayout)findViewById(R.id.u_pet_shop);
        store_dog_vet = (LinearLayout)findViewById(R.id.u_vet_store);

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
        intent.putExtra("type_number" ,1 );
        startActivity(intent);

    }

    private void go_to_dog_sitter_activity() {
        Intent intent = new Intent(this, StoreView.class);
        Log.d("start my store", "onDataChange: start");
        intent.putExtra("type_number" ,0 );
        startActivity(intent);
    }


    private void setUserView() {
        String uid = mAuth.getCurrentUser().getUid();
        myRef = myDB.getReference("users").child(uid);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds :snapshot.getChildren()){
                    updateUI(ds);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void go_find_a_dog_page(){
        Intent intent = new Intent(this, C_FoundDogActivity.class);
        startActivity(intent);
    }


    public void updateUI(DataSnapshot ds){
        String key = ds.getKey();
        switch (key){
            case "fname":
                dashbord.setText("Wellcome " + ds.getValue().toString());
                break;

        }
    }
}
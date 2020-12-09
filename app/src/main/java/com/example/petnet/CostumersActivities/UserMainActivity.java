package com.example.petnet.CostumersActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.petnet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserMainActivity extends AppCompatActivity {

    private static final String TAG = "UserMainActivity";
    LinearLayout LL_found_a_dogl;
    FirebaseDatabase myDB = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView dashbord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        LL_found_a_dogl = findViewById(R.id.LL_found_a_dog);
        dashbord = findViewById(R.id.textbashboard);

        LL_found_a_dogl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_find_a_dog_page();
            }
        });

        setUserView();
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
        Intent intent = new Intent(this, FoundDogActivity.class);
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
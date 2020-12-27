package com.example.src.BusinessActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.src.Algorithms.StringsManipulators;
import com.example.src.CostumersActivities.C_LogInActivity;
import com.example.src.GeneralActivity.StoreView;
import com.example.src.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class B_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView headline;
    private LinearLayout my_store;
    private DatabaseReference myRef;
    private ImageView petImage;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LinearLayout store_dog_sitter;
    private LinearLayout store_dog_trainer;
    private LinearLayout store_dog_walker;
    private  LinearLayout store_dog_vet;
    private LinearLayout store_dog_pet_shop;
    private FirebaseDatabase myDB = FirebaseDatabase.getInstance();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_activity);

        start_all_listiner();
        setUserView();


    }









    //=======================================================initialization===========================


    /**
     * Initializes the username
     */
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
                headline.setText("Wellcome " + StringsManipulators.SetFirstCharToUpperCase(ds.getValue().toString()));
                break;

        }
    }


    /**
     * A function is responsible for initializing all buttons and all page fields
     */
    private void start_all_listiner(){
        petImage = findViewById(R.id.IV_B_pet_image);
        headline = (TextView)findViewById(R.id.textbashboard);
        my_store = (LinearLayout)findViewById(R.id.my_store);
        store_dog_sitter = (LinearLayout)findViewById(R.id.dog_sitter_but);
        store_dog_trainer = (LinearLayout)findViewById(R.id.store_dog_trainer);
        store_dog_walker = (LinearLayout)findViewById(R.id.store_dog_walker);
        store_dog_pet_shop = (LinearLayout)findViewById(R.id.store_pet_shop);
        store_dog_vet = (LinearLayout)findViewById(R.id.store_vet);
        drawerLayout = (DrawerLayout)findViewById(R.id.b_draw_layout);
        navigationView = (NavigationView)findViewById(R.id.b_nav_view);
        toolbar = findViewById(R.id.b_tool_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

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


    //==============================================intents===================================

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
//========================================navbar===================================


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.b_nav_home:
                break;
            case R.id.b_nav_edit_profile:

            case R.id.b_nav_log_out:
                Intent log_out = new Intent(this, C_LogInActivity.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(log_out);
                break;
        }



        return true;
    }
}
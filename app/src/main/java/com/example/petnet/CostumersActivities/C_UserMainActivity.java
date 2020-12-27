package com.example.petnet.CostumersActivities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.example.petnet.Algorithms.StringsManipulators;
import com.example.petnet.GeneralActivity.StoreView;
import com.example.petnet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class C_UserMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "UserMainActivity";
    LinearLayout LL_found_a_dogl;
    FirebaseDatabase myDB = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView dashbord;
    ImageView petImage;
    LinearLayout store_dog_sitter;
    LinearLayout store_dog_trainer;
    LinearLayout store_dog_walker;
    LinearLayout store_dog_vet;
    LinearLayout store_dog_pet_shop;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

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
        petImage = findViewById(R.id.IV_pet_image);
        store_dog_sitter = (LinearLayout)findViewById(R.id.u_dog_sitter);
        store_dog_trainer = (LinearLayout)findViewById(R.id.u_dog_trainer);
        store_dog_walker = (LinearLayout)findViewById(R.id.u_dog_walker);
        store_dog_pet_shop = (LinearLayout)findViewById(R.id.u_pet_shop);
        store_dog_vet = (LinearLayout)findViewById(R.id.u_vet_store);
        drawerLayout = (DrawerLayout)findViewById(R.id.draw_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.tool_bar);

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
        intent.putExtra("type_number" ,1);
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
        updatePic();
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

    private void updatePic() {
        String path = "pics/" + mAuth.getCurrentUser().getUid();
        StorageReference sRef = FirebaseStorage.getInstance().getReference(path);
        sRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri imageUri = task.getResult();
                    Picasso.get()
                            .load(imageUri)
                            .fit()
                            .centerCrop()
                            .into(petImage);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Failed to load image",Toast.LENGTH_LONG).show();

                }
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
                dashbord.setText("Wellcome " + StringsManipulators.SetFirstCharToUpperCase(ds.getValue().toString()));
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_home:
                break;
            case R.id.nav_edit_dog:

            case R.id.nav_edit_profile:

            case R.id.nav_log_out:
                Intent log_out = new Intent(this, C_LogInActivity.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(log_out);
                break;
        }



        return true;
    }
}
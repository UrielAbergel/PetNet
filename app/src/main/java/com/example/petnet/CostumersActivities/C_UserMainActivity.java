package com.example.petnet.CostumersActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.petnet.Adapters.StringsManipulators;
import com.example.petnet.Objects.User;
import com.example.petnet.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class C_UserMainActivity extends AppCompatActivity {

    private static final String TAG = "UserMainActivity";
    LinearLayout LL_found_a_dogl;
    FirebaseDatabase myDB = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView dashbord;
    private ImageView pet_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        LL_found_a_dogl = findViewById(R.id.LL_found_a_dog);
        dashbord = findViewById(R.id.textbashboard);
        pet_image = findViewById(R.id.IV_pet_image);

        LL_found_a_dogl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_find_a_dog_page();
            }
        });

        setUserView();
    }

    /**
     *  set all user view
     */
    private void setUserView() {
        String uid = mAuth.getCurrentUser().getUid();
        myRef = myDB.getReference("users").child(uid);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                updateUI(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     *  go to fine fog intent
     */
    public void go_find_a_dog_page(){
        Intent intent = new Intent(this, C_FoundDogActivity.class);
        startActivity(intent);
    }


    /**
     * Update user info to the GUI
     * @param user
     */
    public void updateUI(User user){
        String name = StringsManipulators.SetFirstCharToUpperCase(user.getFname());
        dashbord.setText("Welcome " + name + "!"); // update user name

        // get firebase reference
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();

        // update dog profile picture
        storageRef.child("pics/" + user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, "onSuccess: uri = " + uri.toString());
                Picasso.get().load(uri).into(pet_image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "onFailure: fail to load image from DB");
            }
        });
    }
}
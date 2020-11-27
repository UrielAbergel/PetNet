package com.example.petnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Bmy_stores extends AppCompatActivity {


    private static final String TAG = "Bmy_stores";

    private ImageView new_store_button;
    static Long store_count ;
    private BusinessUser[] store_array = new BusinessUser[9];
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private TextView[] store_name = new TextView[9];
    private TextView[] store_type = new TextView[9];
    private TextView[] store_phone = new TextView[9];
    private TextView[] store_rate = new TextView[9];
    private TextView[] store_address = new TextView[9];
    private TextView[] store_descre = new TextView[9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmy_stores);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        myRef = FirebaseDatabase.getInstance().getReference().child("Stores").child(currentFirebaseUser.getUid());


        mStorageRef = FirebaseStorage.getInstance().getReference();
        new_store_button = (ImageView) findViewById(R.id.s1);

        start_all_array();
        refresh();



        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                refresh();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                refresh();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                refresh();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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
    }

    private void refresh() {


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: Start take data from firebase");
                store_count = snapshot.getChildrenCount();


                for (int i = 0; i < store_count; i++) {
                    Log.d(TAG, "onDataChange: read all stores");
                    int type = Integer.parseInt(snapshot.child("s" + i).child("_store_type").getValue().toString());
                    switch (type)
                    {
                        case 0:
                            store_array[i] = snapshot.child("s" + i).getValue(Bdog_sitter.class);
                            break;

                        case 1:
                            store_array[i] = snapshot.child("s" + i).getValue(Bdog_trainer.class);
                            break;

                        case 2:
                            store_array[i] = snapshot.child("s" + i).getValue(Bdog_walker.class);
                            System.out.println("!" + store_array[i].get_store_name());
                            break;

                        case 3:
                            store_array[i] = snapshot.child("s" + i).getValue(Bpet_shop.class);

                        case 4:
                            store_array[i] = snapshot.child("s" + i).getValue(Bveterinarian.class);
                            break;

                    }
                    Log.d(TAG, "onDataChange: got data from firebase " + store_array[i].get_store_name());

                    store_name[i].setText(store_array[i].get_store_name());
                    store_address[i].setText("Address: "+store_array[i].get_address());
                    store_descre[i].setText(store_array[i].get_description());
                    store_rate[i].setText("Rate: "+store_array[i].get_store_rate());
                    store_phone[i].setText("Phone: "+store_array[i].get_phone_number());
                    store_type[i].setText("Type: "+return_type_as_string(store_array[i].get_store_type()));
                    Log.d(TAG, "onDataChange: got data from firebase " + store_array[i]);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void start_all_array()
    {
        store_name[0] = findViewById(R.id.l1_name);
        store_name[1] = findViewById(R.id.l2_name);
        store_name[2] = findViewById(R.id.l3_name);
        store_name[3] = findViewById(R.id.l4_name);
        store_name[4] = findViewById(R.id.l5_name);
        store_name[5] = findViewById(R.id.l6_name);
        store_name[6] = findViewById(R.id.l7_name);
        store_name[7] = findViewById(R.id.l8_name);
        store_name[8] = findViewById(R.id.l9_name);

        store_type[0] = findViewById(R.id.l1_type);
        store_type[1] = findViewById(R.id.l2_type);
        store_type[2] = findViewById(R.id.l3_type);
        store_type[3] = findViewById(R.id.l4_type);
        store_type[4] = findViewById(R.id.l5_type);
        store_type[5] = findViewById(R.id.l6_type);
        store_type[6] = findViewById(R.id.l7_type);
        store_type[7] = findViewById(R.id.l8_type);
        store_type[8] = findViewById(R.id.l9_type);

        store_phone[0] = findViewById(R.id.l1_phone);
        store_phone[1] = findViewById(R.id.l2_phone);
        store_phone[2] = findViewById(R.id.l3_phone);
        store_phone[3] = findViewById(R.id.l4_phone);
        store_phone[4] = findViewById(R.id.l5_phone);
        store_phone[5] = findViewById(R.id.l6_phone);
        store_phone[6] = findViewById(R.id.l7_phone);
        store_phone[7] = findViewById(R.id.l8_phone);
        store_phone[8] = findViewById(R.id.l9_phone);

        store_address[0] = findViewById(R.id.l1_address);
        store_address[1] = findViewById(R.id.l2_address);
        store_address[2] = findViewById(R.id.l3_address);
        store_address[3] = findViewById(R.id.l4_address);
        store_address[4] = findViewById(R.id.l5_address);
        store_address[5] = findViewById(R.id.l6_address);
        store_address[6] = findViewById(R.id.l7_address);
        store_address[7] = findViewById(R.id.l8_address);
        store_address[8] = findViewById(R.id.l9_address);


        store_descre[0] = findViewById(R.id.l1_descre);
        store_descre[1] = findViewById(R.id.l2_descre);
        store_descre[2] = findViewById(R.id.l3_descre);
        store_descre[3] = findViewById(R.id.l4_descre);
        store_descre[4] = findViewById(R.id.l5_descre);
        store_descre[5] = findViewById(R.id.l6_descre);
        store_descre[6] = findViewById(R.id.l7_descre);
        store_descre[7] = findViewById(R.id.l8_descre);
        store_descre[8] = findViewById(R.id.l9_descre);


        store_rate[0] = findViewById(R.id.l1_rate);
        store_rate[1] = findViewById(R.id.l2_rate);
        store_rate[2] = findViewById(R.id.l3_rate);
        store_rate[3] = findViewById(R.id.l4_rate);
        store_rate[4] = findViewById(R.id.l5_rate);
        store_rate[5] = findViewById(R.id.l6_rate);
        store_rate[6] = findViewById(R.id.l7_rate);
        store_rate[7] = findViewById(R.id.l8_rate);
        store_rate[8] = findViewById(R.id.l9_rate);



    }


    public String return_type_as_string(int type)
    {
        switch (type)
        {
            case 0: return "Dog Sitter";
            case 1: return "Dog Trainer";
            case 2: return "Dog Walker";
            case 3: return "Pet Shop";
            case 4: return "Pet Vet";
        }

        return "";
    }
}
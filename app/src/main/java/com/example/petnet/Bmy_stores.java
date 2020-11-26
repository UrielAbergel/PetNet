package com.example.petnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Bmy_stores extends AppCompatActivity {

    private ImageView new_store_button;
    static int store_count ;
    private BusinessUser[] store_array = new BusinessUser[9];
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private Buser current_user;
    private TextView[] write_store_name = new TextView[9];
//    private BusinessUser[] store_array = new BusinessUser[9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmy_stores);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        myRef = FirebaseDatabase.getInstance().getReference().child("Busers").child(currentFirebaseUser.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference();
        new_store_button = (ImageView) findViewById(R.id.s1);

        write_store_name[0] = findViewById(R.id.l1name);
        write_store_name[1] = findViewById(R.id.l2name);
        write_store_name[2] = findViewById(R.id.l3name);
        write_store_name[3] = findViewById(R.id.l4name);
        write_store_name[4] = findViewById(R.id.l5name);
        write_store_name[5] = findViewById(R.id.l6name);
        write_store_name[6] = findViewById(R.id.l7name);
        write_store_name[7] = findViewById(R.id.l8name);
        write_store_name[8] = findViewById(R.id.l9name);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ORIAN");
                current_user = snapshot.getValue(Buser.class);
                store_count = current_user.getStore_count();

                for (int i = 0; i < store_count; i++) {
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
                    write_store_name[i].setText(store_array[i].get_store_name());
                    System.out.println("helllo !!!!!!!!!!!!!!!!!  " + store_array[i].get_store_name());
                }




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
        refresh();


    }

    private void refresh() {

    }
}
package com.example.petnet;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class New_store_dialog {

    String name,store_price,phone_num,address,store_des;
    private EditText get_store_name;
    private EditText get_store_price;
    private EditText get_store_phone_number;
    private EditText get_store_address;
    private EditText get_store_des;
    private CheckBox[] get_type;
    private ImageView send_button;
    private ImageView cancel_button;
    private boolean check = false;
    private BusinessUser store;
    private int type = -1;
    private int store_count;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private Buser current_user;



    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.new_store_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        get_store_name = dialog.findViewById(R.id.enter_name);
        get_store_address = dialog.findViewById(R.id.enter_address);
        get_store_des = dialog.findViewById(R.id.get_description);
        get_store_phone_number = dialog.findViewById(R.id.phone_number);
        get_store_price = dialog.findViewById(R.id.type_price);
        send_button = dialog.findViewById(R.id.send);
        cancel_button = dialog.findViewById(R.id.cancel);
        get_type = new CheckBox[5];
        get_type[0] = dialog.findViewById(R.id.type_sitter);
        get_type[1] = dialog.findViewById(R.id.type_trainer);
        get_type[2] = dialog.findViewById(R.id.type_walker);
        get_type[3] = dialog.findViewById(R.id.type_shop);
        get_type[4] = dialog.findViewById(R.id.type_vet);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        myRef = FirebaseDatabase.getInstance().getReference().child("Busers").child(currentFirebaseUser.getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                        current_user = snapshot.getValue(Buser.class);
                        store_count = current_user.getStore_count();

                    }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference();

        get_type[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0 ;
            }
        });

        get_type[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1 ;
            }
        });

        get_type[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2 ;
            }
        });

        get_type[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 3 ;
            }
        });

        get_type[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 4 ;
            }
        });

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = true;
                name = get_store_name.getText().toString();
                phone_num = get_store_phone_number.getText().toString();
                address = get_store_address.getText().toString();
                store_des = get_store_des.getText().toString();
                store_price = get_store_price.getText().toString();


                switch (type)
                {
                    case -1:
                        Toast.makeText(dialog.getContext(), "Please choose 1 shop type", Toast.LENGTH_LONG).show();
                        break;

                    case 0:
                        store = new Bdog_sitter(name,phone_num,store_des,address,Integer.parseInt(store_price));
                        myRef.child("s" + store_count).setValue(store);
                        myRef.child("store_count").setValue(store_count+1);

                        break;

                    case 1:
                        store = new Bdog_trainer(name,phone_num,store_des,address,Integer.parseInt(store_price));
                        myRef.child("s" + store_count).setValue(store);
                        myRef.child("store_count").setValue(store_count+1);
                        break;

                    case 2:
                        store = new Bdog_walker(name,phone_num,store_des,address,Integer.parseInt(store_price));
                        myRef.child("s" + store_count).setValue(store);
                        myRef.child("store_count").setValue(store_count+1);
                        break;

                    case 3:
                        store = new Bpet_shop(name,phone_num,store_des,address);
                        myRef.child("s" + store_count).setValue(store);
                        myRef.child("store_count").setValue(store_count+1);
                        break;

                    case 4:
                        store = new Bveterinarian(name,phone_num,store_des,address);
                        myRef.child("s" + store_count).setValue(store);
                        myRef.child("store_count").setValue(store_count+1);
                        break;

                }
                dialog.dismiss();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }
}

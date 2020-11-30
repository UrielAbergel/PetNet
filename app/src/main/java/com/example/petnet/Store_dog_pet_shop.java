package com.example.petnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Store_dog_pet_shop extends AppCompatActivity {

    private static final String TAG = "Store_dog_sitter";
    private ArrayList<B_store> store_array = new ArrayList<>();
    private DatabaseReference myRef;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_dog_pet_shop);
        Log.d(TAG, "onDataChange: start1");
        myRef = FirebaseDatabase.getInstance().getReference().child("Stores");
        Log.d(TAG, "onDataChange: start");
        mListView = (ListView) findViewById(R.id.list_view_dog_pet_shop);
        Context this_con = this;
        add_all_stores_to_array(this_con);
    }



    private void add_all_stores_to_array(Context this_con) {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: Start take data from firebase");
                Long users_number = snapshot.getChildrenCount();

                store_array.clear();

                for (DataSnapshot user : snapshot.getChildren()) {
                    for (DataSnapshot store : user.getChildren()) {

                        Log.d(TAG, "onDataChange: type  "  + store.child("_store_type").getValue().toString());
                        int type = Integer.parseInt(store.child("_store_type").getValue().toString());
                        if(type == 3) store_array.add(store.getValue(B_pet_shop.class));

                    }
                }
                B_store_list_adapter adapter = new B_store_list_adapter(this_con, R.layout.b_store_view, store_array);
                mListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
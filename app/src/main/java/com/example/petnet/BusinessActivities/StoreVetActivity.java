package com.example.petnet.BusinessActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.petnet.Adapters.B_StoreListAdapter;
import com.example.petnet.BusinessObjects.B_Store;
import com.example.petnet.BusinessObjects.B_VetStore;
import com.example.petnet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreVetActivity extends AppCompatActivity {

    private static final String TAG = "Store_dog_sitter";
    private ArrayList<B_Store> store_array = new ArrayList<>();
    private DatabaseReference myRef;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_vet);
        Log.d(TAG, "onDataChange: start1");
        myRef = FirebaseDatabase.getInstance().getReference().child("Stores");
        Log.d(TAG, "onDataChange: start");
        mListView = (ListView) findViewById(R.id.list_view_dog_vet);
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
                        if(type == 4) store_array.add(store.getValue(B_VetStore.class));

                    }
                }
                B_StoreListAdapter adapter = new B_StoreListAdapter(this_con, R.layout.b_store_view, store_array);
                mListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
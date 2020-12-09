package com.example.petnet.BusinessActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.petnet.Adapters.B_store_list_adapter;
import com.example.petnet.Bobjects.B_dog_sitter;
import com.example.petnet.Bobjects.B_dog_trainer;
import com.example.petnet.Bobjects.B_dog_walker;
import com.example.petnet.Bobjects.B_pet_shop;
import com.example.petnet.Bobjects.B_store;
import com.example.petnet.Bobjects.B_veterinarian_store;
import com.example.petnet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreView extends AppCompatActivity {

    private static final String TAG = "Store_dog_sitter";
    private ArrayList<B_store> store_array = new ArrayList<>();
    private DatabaseReference myRef;
    private ListView mListView;
    private TextView headText;
    private int typeOfStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_view);
        Log.d(TAG, "onDataChange: start1");
        myRef = FirebaseDatabase.getInstance().getReference().child("Stores");
        Log.d(TAG, "onDataChange: start");
        mListView = (ListView) findViewById(R.id.list_view_store);
        headText = (TextView)  findViewById(R.id.store_view_text) ;
        Bundle type = getIntent().getExtras();
        typeOfStore = type.getInt("type_number");
        headText.setText(get_head_text(typeOfStore));
        Context this_con = this;
        add_all_stores_to_array(this_con);
    }

    private String get_head_text(int type) {
        String result = "" ;
        switch (type) {
            case 0 : return "Dog Sitter Store`s";
            case 1 : return "Dog Trainer Store`s";
            case 2 : return "Dog Walker`s";
            case 3 : return "Pet Shops Store`s";
            case 4 : return "Veterinarian";
        }
        return "";
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
                        if(type == typeOfStore) {
                            Log.d(TAG, "onDataChange: add store");
                            switch (typeOfStore) {
                                case 0 :
                                    store_array.add(store.getValue(B_dog_sitter.class));
                                    break;

                                case 1 :
                                    store_array.add(store.getValue(B_dog_trainer.class));
                                    break;

                                case 2 :
                                    store_array.add(store.getValue(B_dog_walker.class));
                                    break;

                                case 3 :
                                    store_array.add(store.getValue(B_pet_shop.class));
                                    break;

                                case 4 :
                                    store_array.add(store.getValue(B_veterinarian_store.class));
                                    break;
                            }
                        }

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
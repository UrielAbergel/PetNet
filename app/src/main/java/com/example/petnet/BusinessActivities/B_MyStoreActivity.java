package com.example.petnet.BusinessActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.petnet.Adapters.B_StoreListAdapter;
import com.example.petnet.BusinessObjects.B_DogSitter;
import com.example.petnet.BusinessObjects.B_DogTrainer;
import com.example.petnet.BusinessObjects.B_DogWalker;
import com.example.petnet.BusinessObjects.B_NewStoreDialog;
import com.example.petnet.BusinessObjects.B_PetShop;
import com.example.petnet.BusinessObjects.B_Store;
import com.example.petnet.BusinessObjects.B_VetStore;
import com.example.petnet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class B_MyStoreActivity extends AppCompatActivity {

    private static final String TAG = "Bmy_stores";
    private ImageView new_store_button;
    static Long store_count ;
    private ArrayList<B_Store> store_array = new ArrayList<>();
    private DatabaseReference myRef;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onDataChange: start1");
        setContentView(R.layout.b_my_store);
        Log.d(TAG, "onDataChange: start");
        mListView = (ListView) findViewById(R.id.list_view);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        myRef = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "onDataChange: load page");
        Context this_con = this;
        new_store_button = (ImageView) findViewById(R.id.new_store_butt);

        refresh(this_con);

        change_event_update(this_con);

        new_store_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_my_store_bar();
            }
        });

    }


    private void change_event_update(Context this_con) {

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                refresh(this_con);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                refresh(this_con);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                refresh(this_con);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void refresh(Context this_con) {

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: Start take data from firebase");
                store_array.clear();
                Log.d(TAG, "onDataChange: Store count=" + store_count);
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                for (DataSnapshot user_store : snapshot.child("Busers").child(uid).child("stores").getChildren()) {
                    Log.d(TAG, "onDataChange: read all stores");
                    int type = Integer.parseInt(snapshot.child("Stores").child(user_store.getValue().toString()).child("_store_type").getValue().toString());
                    switch (type)
                    {
                        case 0:
                            store_array.add(snapshot.child("Stores").child(user_store.getValue().toString()).getValue(B_DogSitter.class));
                            break;

                        case 1:
                            store_array.add(snapshot.child("Stores").child(user_store.getValue().toString()).getValue(B_DogTrainer.class));
                            break;

                        case 2:
                            store_array.add(snapshot.child("Stores").child(user_store.getValue().toString()).getValue(B_DogWalker.class));
                            break;

                        case 3:
                            store_array.add(snapshot.child("Stores").child(user_store.getValue().toString()).getValue(B_PetShop.class));
                            break;

                        case 4:
                            store_array.add(snapshot.child("Stores").child(user_store.getValue().toString()).getValue(B_VetStore.class));
                            break;

                    }

                }
                B_StoreListAdapter adapter = new B_StoreListAdapter(this_con, R.layout.b_mystore_storeview, store_array,B_MyStoreActivity.this);
                Log.d(TAG, "onDataChange: store_array" + store_array);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void go_to_my_store_bar() {
        B_NewStoreDialog dialog = new B_NewStoreDialog();
        dialog.showDialog(this);
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
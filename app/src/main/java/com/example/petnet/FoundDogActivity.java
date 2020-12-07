package com.example.petnet;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.petnet.Algorithms.FindDogOwner;
import com.example.petnet.Algorithms.SortHashMap;
import com.example.petnet.Fragments.GoogleMapAPI;
import com.example.petnet.Objects.Dog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoundDogActivity extends AppCompatActivity implements GoogleMapAPI.MapLisinterForFoundDog {

    private final float loc = 260, add_for_loc = 20;
    private final short loc_huge = 0, loc_big = 1, loc_medium = 2, loc_small = 3, loc_tiny = 4;
    private final double size = 1.2, add_for_size = 0.2;
    private final double size_huge = 0, size_big = 1, size_medium = 2, size_small = 3, size_tiny = 4;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = "FoundDogActivity";
    private int final_size;

    private final String distance_type = "K";
    private final double minimum_distance = 3;

    private Button B_dog_size_huge;
    private Button B_dog_size_big;
    private Button B_dog_size_medium;
    private Button B_dog_size_small;
    private Button B_dog_size_tiny;
    private Button B_search_for_owner;
    private CheckBox pet_gender_male;
    private CheckBox pet_gender_female;
    private CheckBox Pet_gender_not_sure;
    private CheckBox cb_colors[];
    private List<Integer> colors;
    private Dog dogToFind;


    private AutoCompleteTextView pet_race;

    private final int PET_COLOR_BLACK = 0;
    private final int PET_COLOR_WHITE = 1;
    private final int PET_COLOR_GRAY = 2;
    private final int PET_COLOR_GOLDEN = 3;
    private final int PET_COLOR_BROWN = 4;
    private final int PET_COLOR_ = 5;
    private final int PET_COLOR = 6;
    private final int PET_COLOR_GAY = 7;
    private final int PET_COLOR_GOLEN = 8;

    FirebaseFirestore FbFs = FirebaseFirestore.getInstance();

    private String races[] = {"Pitbull", "Golden-Retriver", "Pincher", "Malinoa", "a", "a", "a", "a", "a", "a"};


    ImageView IV_dog;
    Fragment G_map;

    List<Double> coordinators;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_found_dog);

        dogToFind = new Dog();

        B_dog_size_huge = findViewById(R.id.B_huge);
        B_dog_size_big = findViewById(R.id.B_big);
        B_dog_size_medium = findViewById(R.id.B_medium);
        B_dog_size_small = findViewById(R.id.B_small);
        B_dog_size_tiny = findViewById(R.id.B_tiny);
        IV_dog = findViewById(R.id.dog_pic_for_size);

        pet_gender_male = findViewById(R.id.CB_pet_gender_male);
        pet_gender_female = findViewById(R.id.CB_pet_gender_female);
        Pet_gender_not_sure = findViewById(R.id.CB_pet_gender_not_sure);

        cb_colors = new CheckBox[9];
        cb_colors[0] = findViewById(R.id.CB_pet_color_black); //1000269
        cb_colors[1] = findViewById(R.id.CB_pet_color_white);
        cb_colors[2] = findViewById(R.id.CB_pet_color_gray);
        cb_colors[3] = findViewById(R.id.CB_pet_color_golden);
        cb_colors[4] = findViewById(R.id.CB_pet_color_brown);
        cb_colors[5] = findViewById(R.id.CB_pet_color_); //1000269
        cb_colors[6] = findViewById(R.id.CB_pet_color);
        cb_colors[7] = findViewById(R.id.CB_pet_color_gay);
        cb_colors[8] = findViewById(R.id.CB_pet_color_golen);
        colors = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            colors.add(0);
        }

        B_search_for_owner = findViewById(R.id.B_search_owner);

        pet_race = findViewById(R.id.ACTV_pet_race);
        pet_race.setAdapter(new ArrayAdapter<>(FoundDogActivity.this, android.R.layout.simple_list_item_1, races));
        pet_race.setDropDownAnchor(R.id.ACTV_pet_race);
        pet_race.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "onFocusChange: Change Focus on petrace setmax lines to 5" + hasFocus);
                if (hasFocus) {
                    pet_race.showDropDown();
                }
            }
        });

        G_map = new GoogleMapAPI();
        coordinators = new ArrayList<Double>();


        if (isServicesOK())
        {
           getSupportFragmentManager().beginTransaction().replace(R.id.found_dog_frame_layout, G_map).commit();
        }

        B_search_for_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogToFind.setColors(colors);
                dogToFind.setSize(final_size);
                look_for_optionals_owners();
            }
        });

        B_dog_size_huge.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             final_size = (int)size_huge;
             IV_dog.setScaleX((float) (size - add_for_size*size_huge));
             IV_dog.setScaleY((float) (size - add_for_size*size_huge));
             IV_dog.setY(loc + add_for_loc*loc_huge);
         }
     });

        B_dog_size_big.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final_size = (int)size_big;
            IV_dog.setScaleX((float) (size - add_for_size*size_big));
            IV_dog.setScaleY((float) (size - add_for_size*size_big));
            IV_dog.setY(loc + add_for_loc* loc_big);

        }
    });

        B_dog_size_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final_size = (int)size_medium;
                IV_dog.setScaleX((float) (size - add_for_size*size_medium));
                IV_dog.setScaleY((float) (size - add_for_size*size_medium));
                IV_dog.setY(loc + add_for_loc* loc_medium);
            }
        });

        B_dog_size_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final_size = (int)size_small;
                IV_dog.setScaleX((float)(size - add_for_size*size_small));
                IV_dog.setScaleY((float)(size - add_for_size*size_small));
                IV_dog.setY(loc + add_for_loc* loc_small);
            }
        });

        B_dog_size_tiny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final_size = (int)size_tiny;
                IV_dog.setScaleX((float)(size - add_for_size*size_tiny));
                IV_dog.setScaleY((float)(size - add_for_size*size_tiny));
                IV_dog.setY(loc + add_for_loc* loc_tiny);
            }
        });

        cb_colors[PET_COLOR_BLACK].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (colors.get(PET_COLOR_BLACK) == 0) colors.set(PET_COLOR_BLACK, 1);
                else colors.set(PET_COLOR_BLACK, 0);
            }
        });

        cb_colors[PET_COLOR_WHITE].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (colors.get(PET_COLOR_WHITE) == 0) colors.set(PET_COLOR_WHITE, 1);
                else colors.set(PET_COLOR_WHITE, 0);
            }
        });

        cb_colors[PET_COLOR_GRAY].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (colors.get(PET_COLOR_GRAY) == 0) colors.set(PET_COLOR_GRAY, 1);
                else colors.set(PET_COLOR_GRAY, 0);
            }
        });

        cb_colors[PET_COLOR_GOLDEN].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (colors.get(PET_COLOR_GOLDEN) == 0) colors.set(PET_COLOR_GOLDEN, 1);
                else colors.set(PET_COLOR_GOLDEN, 0);

            }
        });

        cb_colors[PET_COLOR_BROWN].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (colors.get(PET_COLOR_BROWN) == 0) colors.set(PET_COLOR_BROWN, 1);
                else colors.set(PET_COLOR_BROWN, 0);
            }
        });

        cb_colors[PET_COLOR_].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (colors.get(PET_COLOR_) == 0) colors.set(PET_COLOR_, 1);
                else colors.set(PET_COLOR_, 0);

            }
        });


        cb_colors[PET_COLOR].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (colors.get(PET_COLOR) == 0) colors.set(PET_COLOR, 1);
                else colors.set(PET_COLOR, 0);

            }
        });
        cb_colors[PET_COLOR_GAY].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (colors.get(PET_COLOR_GAY) == 0) colors.set(PET_COLOR_GAY, 1);
                else colors.set(PET_COLOR_GAY, 0);

            }
        });

        cb_colors[PET_COLOR_GOLEN].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (colors.get(PET_COLOR_GOLEN) == 0) colors.set(PET_COLOR_GOLEN, 1);
                else colors.set(PET_COLOR_GOLEN, 0);
            }
        });

        pet_gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = pet_gender_male.isChecked();
                if (checked) pet_gender_male.setChecked(false);
                dogToFind.setPet_gender(0);            // 0 means the pet is a female.
            }
        });


        pet_gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = pet_gender_female.isChecked();
                if (checked) pet_gender_female.setChecked(false);
                dogToFind.setPet_gender(1);  // 1 means the pet is a male.
            }
        });
    }

    private void look_for_optionals_owners() {
        FirebaseFirestore FbFs = FirebaseFirestore.getInstance(); // get pointer to cloud storage root
        FbFs.collection("dogs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                HashMap<String,Double> userCandidateList;

                userCandidateList = FindDogOwner.find_dog_possible_owners(task, dogToFind); // get list of candidate owners

                userCandidateList = SortHashMap.sortByValue(userCandidateList);
                List<ItemModel> items = getItemList(userCandidateList);
                Log.d(TAG, "onComplete: Items size is" + items.size());


                if(userCandidateList.size() == 0)
                {
                    // dialog not found
                }
                else if(items.size()!= 0)
                {
                    Intent intent = new Intent(FoundDogActivity.this, TinderSwipe.class);
                    intent.putExtra("data",userCandidateList);
                    intent.putExtra("items", (Parcelable) items);
                    startActivity(intent);
                }
            }
        });
    }

    private List<ItemModel> getItemList(HashMap<String, Double> userCandidateList) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final List<ItemModel> items = new ArrayList<>();
        for (Map.Entry<String,Double> current_dog: userCandidateList.entrySet()) {

            String key = current_dog.getKey();
            Log.d(TAG, "mapToItemModel:uid: " + key);

            FbFs.collection("dogs").document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Log.d(TAG, "onComplete:  firestore task completed");
                    if(task.isSuccessful()){
                        ItemModel toReturn = new ItemModel();
                        Log.d(TAG, "onComplete:  firestore task sucseccs");
                        DocumentSnapshot DS = task.getResult();
                        Dog tempDog = DS.toObject(Dog.class);
                        toReturn.setDog_name(tempDog.getPet_name());
                        if (tempDog.getPet_gender() == 0)
                            toReturn.setGender("Male");
                        else if (tempDog.getPet_gender() == 1)
                            toReturn.setGender("Female");
                        toReturn.setRace(tempDog.getPet_race());
                        toReturn.setUniqe_signs(tempDog.getUniqe_signs());
                        items.add(toReturn);
                        Log.d(TAG, "onComplete: before storage");

                        storageRef.child("pics/" + key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                toReturn.setImage(uri);
                                Log.d(TAG, "onSuccess: !! IM HERE!!!" + items.toString());

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {

                                Log.d(TAG, "onFailure: !!! BHHHHHHH");
                            }
                        });

                    }


                }
            });

        }
        return items;

    }

    private void show_possible_owners_activity()
    {

    }

    @Override
    public void onInputMapSend(List<Double> coordiantes) {
        dogToFind.setAddress(coordiantes);
        Log.d(TAG, "onInputMapSend: coordinates" + dogToFind.getAddress());
    }

    private boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: Checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            //Every thing is fine and the user have can make map request
            Log.d(TAG, "isServicesOK: Google play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: There is a problem we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Log.d(TAG, "isServicesOK: You cant make request map");
        }

        return false;

    }
}
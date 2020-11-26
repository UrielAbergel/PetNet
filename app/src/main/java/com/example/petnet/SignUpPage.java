package com.example.petnet;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {


    private static final String TAG = "SignUpPage";
    private final int PET_COLOR_BLACK = 0;
    private final int PET_COLOR_WHITE = 1;
    private final int PET_COLOR_GRAY = 2;
    private final int PET_COLOR_GOLDEN = 3;
    private final int PET_COLOR_BROWN = 4;
    private final int PET_COLOR_ = 5;
    private final int PET_COLOR = 6;
    private final int PET_COLOR_GAY = 7;
    private final int PET_COLOR_GOLEN = 8;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String races[] = {"Pitbull", "Golden-Retriver", "Pincher", "Malinoa", "a", "a", "a", "a", "a", "a"};


    private List<Integer> colors;


    private final int REQUEST_IMAGE_CAPTURE = 0;
    private final int REQUEST_IMAGE_FROM_GALLERY = 1;
    private int check;

    private LatLng coordinate;
    private GoogleMapAPI map;
    private Button sign_up_button;
    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private EditText password;
    private EditText confirm_password;
    private EditText pet_name;
    private AutoCompleteTextView pet_race;
    private CheckBox gender_male;
    private CheckBox gender_female;
    private CheckBox cb_colors[];
    private CheckBox pet_gender_male;
    private CheckBox pet_gender_female;
    private EditText uniqe_signs;
    private ImageButton hide_pass;
    private ImageButton hide_confirm_pass;
    private androidx.appcompat.widget.Toolbar toolbar;
    private TextView business_sign_up;
    private ImageButton take_photo;
    private ImageButton take_photo_from_gallery;
    private ImageView pet_photo;
    private User userToAdd;
    private Bitmap imageBitmap;
    private Uri Imageuri;
    private Button addAdress;




    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);




        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("users");
        mStorageRef = FirebaseStorage.getInstance().getReference();



//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.hasChildren()) {
//
//                    Iterable<DataSnapshot> childer = snapshot.getChildren();
//                    for (DataSnapshot snapshot1 : childer) {
//
//                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ORIAN");
//                        User temp = snapshot1.getValue(User.class);
//
//                        System.out.println(temp + "FROM FIREBASE!!!!!!!!!!!!!!");
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //initillaize


        userToAdd = new User();
        cb_colors = new CheckBox[9];
        colors = new ArrayList<>();
        check = -1;

        for (int i = 0; i < 9; i++) {
            colors.add(0);
        }


        //buttons
        sign_up_button = findViewById(R.id.B_signup);
        take_photo = findViewById(R.id.camera_photo);
        take_photo_from_gallery = findViewById(R.id.gallery_photo);
        hide_pass = findViewById(R.id.IV_hide_pass);
        hide_confirm_pass = findViewById(R.id.IV_hide_confirm_pass);
        business_sign_up = findViewById(R.id.TV_Business_signup);
        addAdress = findViewById(R.id.add_address);


        //EditTexts
        first_name = findViewById(R.id.PT_register_first_name);
        last_name = findViewById(R.id.PT_register_last_name);
        email = findViewById(R.id.PT_register_email);
        password = findViewById(R.id.PT_register_password);
        confirm_password = findViewById(R.id.PT_register_confirm_password);
        pet_photo = findViewById(R.id.pet_pic);
        pet_name = findViewById(R.id.ET_pet_name);
        pet_race = findViewById(R.id.ACTV_pet_race);
        uniqe_signs = findViewById(R.id.ET_uniqe_signs);

        //CheckBoxs
        pet_gender_male = findViewById(R.id.CB_pet_gender_male);
        pet_gender_female = findViewById(R.id.CB_pet_gender_female);
        gender_female = findViewById(R.id.CB_gender_female);
        gender_male = findViewById(R.id.CB_gender_male);
        cb_colors[0] = findViewById(R.id.CB_pet_color_black); //1000269
        cb_colors[1] = findViewById(R.id.CB_pet_color_white);
        cb_colors[2] = findViewById(R.id.CB_pet_color_gray);
        cb_colors[3] = findViewById(R.id.CB_pet_color_golden);
        cb_colors[4] = findViewById(R.id.CB_pet_color_brown);
        cb_colors[5] = findViewById(R.id.CB_pet_color_); //1000269
        cb_colors[6] = findViewById(R.id.CB_pet_color);
        cb_colors[7] = findViewById(R.id.CB_pet_color_gay);
        cb_colors[8] = findViewById(R.id.CB_pet_color_golen);


        pet_race.setAdapter(new ArrayAdapter<>(SignUpPage.this, android.R.layout.simple_list_item_1, races));
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


        addAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // need to check how to get the Coordinates from the MapActivity.
                if (isServicesOK()) {
                    Intent intent = new Intent(getApplicationContext(), GoogleMapAPI.class);
                    startActivityForResult(intent,444);
                }
                //after get the Latlng of the user update the usertoadd object.

            }
        });


//        // if color[i] == 1 the user choosed the color else didnt choose.
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


        gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = gender_male.isChecked();
                if (checked) gender_male.setChecked(false);
                userToAdd.setGender(0);             // 0 means the user is a female.
            }
        });


        gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = gender_female.isChecked();
                if (checked) gender_female.setChecked(false);
                userToAdd.setGender(1);  // 1 means the user is a male.
            }
        });


        pet_gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = pet_gender_male.isChecked();
                if (checked) pet_gender_male.setChecked(false);
                userToAdd.setPet_gender(0);            // 0 means the pet is a female.
            }
        });


        pet_gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = pet_gender_female.isChecked();
                if (checked) pet_gender_female.setChecked(false);
                userToAdd.setPet_gender(1);  // 1 means the pet is a male.
            }
        });


        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(password.getText().toString() + " , confirm:" + password.getText().toString());
                if (!password.getText().toString().equals(confirm_password.getText().toString())) {

                    Toast.makeText(getApplicationContext(), "Password does not equals.", Toast.LENGTH_LONG).show();
                    password.setError("Passwords dont match");
                    password.requestFocus();
                    return;
                }
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                userToAdd.setEmail(mail);


                mAuth.createUserWithEmailAndPassword(mail, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        userToAdd.setUid(user.getUid());
                        userToAdd.setFname(first_name.getText().toString());
                        userToAdd.setLname(last_name.getText().toString());
                        userToAdd.setPassword(password.getText().toString());
                        userToAdd.setPet_name(pet_name.getText().toString());
                        userToAdd.setColors(colors);
                        myRef.child(userToAdd.getUid()).setValue(userToAdd);
                        Toast.makeText(getApplicationContext(), user.getUid(), Toast.LENGTH_LONG).show();
                        System.out.println("after auth,:" + userToAdd.getUid());

                        if (check == 0) uploadImage(REQUEST_IMAGE_FROM_GALLERY, userToAdd.getUid());
                        else if (check == 1) uploadImage(REQUEST_IMAGE_CAPTURE, userToAdd.getUid());
                        Intent intent = new Intent(getApplicationContext(), Log_in_activity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("ICANTDOIT :" + e.toString());
                        Toast.makeText(getApplicationContext(), "Sign-up failed, Try again", Toast.LENGTH_LONG).show();


                    }
                });


            }

        });

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 1;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        take_photo_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 0;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_IMAGE_FROM_GALLERY);
            }
        });


        business_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BSignUpPage.class);
                startActivity(intent);

            }
        });


        hide_confirm_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePass(confirm_password, confirm_password.getInputType());
            }
        });


        hide_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePass(password, password.getInputType());
            }
        });
    }


    /**
     * function to change InputType.
     *
     * @param toChange Which EditText need to be change.
     * @param check    represent the inputType.
     */
    public void togglePass(EditText toChange, int check) {
        switch (check) {
            case 129:
                toChange.setInputType(128);
                break;
            case 128:
                toChange.setInputType(129);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK && data != null) {

                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                Bitmap check = imageBitmap;

                pet_photo.setImageBitmap(imageBitmap);

                Toast.makeText(this, "Result ok", Toast.LENGTH_LONG).show();
            } else Toast.makeText(this, "Result failed", Toast.LENGTH_LONG).show();

        } else if (requestCode == REQUEST_IMAGE_FROM_GALLERY) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                Imageuri = data.getData();
                pet_photo.setImageURI(Imageuri);


            }
        }

    }


    protected void uploadImage(int request, String uid) {
        String path = "pics/" + uid;
        mStorageRef = mStorageRef.child(path);

        //image has been taken from camera.

        if (request == REQUEST_IMAGE_CAPTURE) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte bytes[] = baos.toByteArray();

            mStorageRef.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "sucsess", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                }
            });
        }

        //image has been taken from gallery.

        if (request == REQUEST_IMAGE_FROM_GALLERY) {
            mStorageRef.putFile(Imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "image uploiaded", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Image uploade failed", Toast.LENGTH_LONG).show();
                }
            });


        }


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
package com.example.src.CostumersActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.src.BusinessActivities.B_SignUpPageActivity;
import com.example.src.Fragments.F_DogColors;
import com.example.src.Fragments.F_DogSize;
import com.example.src.Firebase.DataBase;
import com.example.src.Fragments.F_GoogleMapAPI;
import com.example.src.Objects.Dog;
import com.example.src.Objects.User;
import com.example.src.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class C_SignUpPageActivity extends AppCompatActivity implements F_GoogleMapAPI.MapListener, F_DogSize.dogSizeForSign, F_DogColors.Signuplistener {


    private static final String TAG = "SignUpPage";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private final int REQUEST_IMAGE_CAPTURE = 0;
    private final int REQUEST_IMAGE_FROM_GALLERY = 1;

    private List<Integer> colors;
    private int check;

   //Widgets.
    private Button sign_up_button;
    private com.google.android.material.textfield.TextInputLayout first_name;
    private com.google.android.material.textfield.TextInputLayout last_name;
    private com.google.android.material.textfield.TextInputLayout email;
    private com.google.android.material.textfield.TextInputLayout password;
    private com.google.android.material.textfield.TextInputLayout confirm_password;
    private com.google.android.material.textfield.TextInputLayout pet_name;
    private com.google.android.material.textfield.TextInputLayout phone_number;
    private AutoCompleteTextView pet_race;
    private CheckBox gender_male;
    private CheckBox gender_female;
    private CheckBox pet_gender_male;
    private CheckBox pet_gender_female;
    private com.google.android.material.textfield.TextInputLayout uniqe_signs;
    private TextView business_sign_up;
    private ImageButton take_photo;
    private ImageButton take_photo_from_gallery;
    private ImageView pet_photo;

    //Object.
    private User userToAdd;
    private Dog dogToAdd;
    private Bitmap imageBitmap;
    private Uri Imageuri;

   //Fragments.
    private Fragment gMap;
    private Fragment dogSize;
    private Fragment dogColors;

    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        gMap = new F_GoogleMapAPI();
        dogSize = new F_DogSize();
        dogColors = new F_DogColors();

        initializeVariables();


        if (isServicesOK()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,gMap).commit();
        }
        // set adapters
        pet_race.setAdapter(new ArrayAdapter<>(C_SignUpPageActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.pet_races)));
        pet_race.setDropDownAnchor(R.id.ACTV_pet_race);
        pet_race.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pet_race.setText(getResources().getStringArray(R.array.pet_races)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        pet_race.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "onFocusChange: Change Focus on petrace setmax lines to 5" + hasFocus);
                if (hasFocus) {
                    pet_race.showDropDown();
                }


            }
        });

        setGenderClick();

        setPetGenderClick();

        setPhotosClick();


        setSignupClick();

        setBusinessClick();

    }


    private void setBusinessClick() {
        business_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), B_SignUpPageActivity.class);
                startActivity(intent);

            }
        });
    }

    /**
     * check if all inserted values are valid, if yes - sign it up
     *                                         if no  - send error massage
     */
    private void setSignupClick() {
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateSignUp()){
                    fillUser();
                    fillDog();
                    String mail = email.getEditText().getText().toString();
                    String pass = password.getEditText().getText().toString();
                    mAuth.createUserWithEmailAndPassword(mail, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            userToAdd.setUid(user.getUid());
                            DataBase.insertDog(dogToAdd,userToAdd.getUid());
                            DataBase.insertUser(userToAdd);

                            if (check == 0) uploadImage(REQUEST_IMAGE_FROM_GALLERY, userToAdd.getUid());
                            else if (check == 1) uploadImage(REQUEST_IMAGE_CAPTURE, userToAdd.getUid());
                            Intent intent = new Intent(getApplicationContext(), C_LogInActivity.class);
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
            }
        });
    }

    /**
     * fill dog parameters
     */
    private void fillDog() {
        dogToAdd.setPet_name(pet_name.getEditText().getText().toString());
        dogToAdd.setColors(colors);
        dogToAdd.setPet_race(pet_race.getText().toString());
        dogToAdd.setUniqe_signs(uniqe_signs.getEditText().getText().toString());

    }

    /**
     * fill user parameters
     */
    private void fillUser() {
        userToAdd.setFname(first_name.getEditText().getText().toString());
        userToAdd.setLname(last_name.getEditText().getText().toString());
        userToAdd.setPassword(password.getEditText().getText().toString());
        userToAdd.setEmail(email.getEditText().getText().toString());
        userToAdd.setPhone(phone_number.getEditText().getText().toString());

    }

    /**
     * check if all sign up details are right
     * @return validation status
     */
    private boolean validateSignUp() {
        if(first_name.getEditText().getText().toString().equals("")){
            first_name.setError("Please fill First name");
            first_name.requestFocus();
            return false;
        }
        if(last_name.getEditText().getText().toString().equals("")){
            last_name.setError("Please fill Last name");
            last_name.requestFocus();
            return false;
        }
        if(!gender_female.isChecked() && !gender_male.isChecked()){
            Toast.makeText(this,"Please choose Gender.",Toast.LENGTH_LONG).show();
            return false;
        }
        if(email.getEditText().getText().toString().equals("")){
            email.setError("Wrong email format.");
            email.requestFocus();
            return false;
        }
        if(!password.getEditText().getText().toString().equals(confirm_password.getEditText().getText().toString())){
            password.setError("Passwords must match");
            password.requestFocus();
            return false;
        }

        if(pet_name.getEditText().getText().toString().equals("")){
            pet_name.setError("Please fill Pet name");
            pet_name.requestFocus();
            return false;
        }
        if(pet_race.getText().toString().equals("")){
            pet_race.setError("Please fill pet race");
            pet_race.requestFocus();
            return false;
        }

        if(!pet_gender_male.isChecked() && !pet_gender_female.isChecked()){
            pet_gender_female.setError("Please choose pet gender");
            pet_gender_female.requestFocus();
            return false;
        }
        return true;
    }


    /**
     * set click listener for change input type of passwords edit texts.
     */


    /**
     * set Click listeners for photo icons.
     */
    private void setPhotosClick() {
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
    }

    /**
     * set Click listener for  PetGender Checkboxes.
     */
    private void setPetGenderClick() {
        pet_gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = pet_gender_male.isChecked();
                if (checked) pet_gender_male.setChecked(false);
                dogToAdd.setPet_gender(0);            // 0 means the pet is a female.
            }
        });


        pet_gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = pet_gender_female.isChecked();
                if (checked) pet_gender_female.setChecked(false);
                dogToAdd.setPet_gender(1);  // 1 means the pet is a male.
            }
        });
    }

    /**
     * set Click listener for Gender Checkboxes.
     */
    private void setGenderClick() {
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
    }


    /**
     * initialize all the variables and widgets in the class.
     */
    private void initializeVariables(){

        getSupportFragmentManager().beginTransaction().replace(R.id.dogSize_frame_layout,dogSize).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.dog_color,dogColors).commit();

        userToAdd = new User();
        dogToAdd = new Dog();

        colors = new ArrayList<>();
        check = -1;

        for (int i = 0; i < 9; i++) {
            colors.add(0);
        }

        //buttons
        sign_up_button = findViewById(R.id.B_signup);
        take_photo = findViewById(R.id.camera_photo);
        take_photo_from_gallery = findViewById(R.id.gallery_photo);

        business_sign_up = findViewById(R.id.TV_Business_signup);

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
        phone_number = findViewById(R.id.ET_register_phone);

        //CheckBoxs
        pet_gender_male = findViewById(R.id.CB_pet_gender_male);
        pet_gender_female = findViewById(R.id.CB_pet_gender_female);
        gender_female = findViewById(R.id.CB_gender_female);
        gender_male = findViewById(R.id.CB_gender_male);
    }

    /**
     * function to change InputType.
     *
     * @param toChange Which EditText need to be change.
     * @param check    represent the inputType.
     */
    private void togglePass(EditText toChange, int check) {
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

        Log.d(TAG, "onActivityResult: Back from gallery/camera.");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK && data != null) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
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

    /**
     * This function upload the image to the firebase storage.
     */
    protected void uploadImage(int request, String uid) {
        Log.d(TAG, "uploadImage: uploading image to firebase storage.");
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


    /**
     * check if have permission for googlemap.
     * @return true if there is false otherwise.
     */
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


    /**
     * implement Maplistener interface for communicate with map fragment
     * @param coordiantes Latlng of the user home.
     */
    @Override
    public void onInputMapSend(List<Double> coordiantes) {
        Log.d(TAG, "onInputMapSend: Save coordinates from GoogleMap.");
        dogToAdd.setAddress(coordiantes);

    }


    @Override
    public void getDogSize(int size) {
        Log.d(TAG, "getDogSize: set Dogsize for signup");
        dogToAdd.setSize(size);
    }


    @Override
    public void setColor(int position, int value) {
        colors.set(position,value);
    }
}
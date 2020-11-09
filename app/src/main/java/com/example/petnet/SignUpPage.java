package com.example.petnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class SignUpPage extends AppCompatActivity {



    final int REQUEST_IMAGE_CAPTURE = 0;
    final int REQUEST_IMAGE_FROM_GALLERY =1;
    Button sign_up_button;
    EditText first_name;
    EditText last_name;
    EditText email;
    EditText password;
    EditText confirm_password;
    EditText pet_name;
    EditText pet_race;
    EditText pet_color;
    EditText pet_gender;
    EditText uniqe_signs;
    ImageButton hide_pass;
    ImageButton hide_confirm_pass;
    androidx.appcompat.widget.Toolbar toolbar;
    TextView business_sign_up;
    ImageButton take_photo;
    ImageButton take_photo_from_gallery;
    ImageView pet_photo;
    User userToAdd;

    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //buttons
        sign_up_button= findViewById(R.id.B_signup);
        take_photo = findViewById(R.id.camera_photo);
        take_photo_from_gallery = findViewById(R.id.gallery_photo);
        hide_pass = findViewById(R.id.IV_hide_pass);
        hide_confirm_pass = findViewById(R.id.IV_hide_confirm_pass);


        //EditTexts
        first_name = findViewById(R.id.PT_register_first_name);
        last_name = findViewById(R.id.PT_register_last_name);
        email=findViewById(R.id.PT_register_email);
        password = findViewById(R.id.PT_register_password);
        confirm_password = findViewById(R.id.PT_register_confirm_password);
        pet_photo = findViewById(R.id.pet_pic);
        pet_name = findViewById(R.id.ET_pet_name);
        pet_race = findViewById(R.id.ET_pet_race);
        pet_color = findViewById(R.id.ET_pet_color);
        pet_gender = findViewById(R.id.ET_pet_gender);
        uniqe_signs = findViewById(R.id.ET_uniqe_signs);





        userToAdd = new User();




        business_sign_up= findViewById(R.id.TV_Business_signup);
        myRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();






       sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(password.getText().toString()!=confirm_password.getText().toString()) {

                    Toast.makeText(getApplicationContext(), "Password does not equals.", Toast.LENGTH_LONG).show();
                    password.setError("Passwords dont match");
                    password.requestFocus();
                    return;
                }
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                 mAuth.createUserWithEmailAndPassword(mail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                     @Override
                     public void onSuccess(AuthResult authResult) {
                         FirebaseUser user = mAuth.getCurrentUser();
                         System.out.println("User UID: "  +user.getUid());
                         userToAdd.setUid(user.getUid());
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(getApplicationContext(),"Sign-up failed, Try again",Toast.LENGTH_LONG).show();

                     }
                 });
                 userToAdd.setFname(first_name.getText().toString());
                 userToAdd.setLname(last_name.getText().toString());
               //  userToAdd.setAddress(addres.getText().toString()); check what about address
               // userToAdd.setGender();
                userToAdd.setEmail(email.getText().toString());
                userToAdd.setPassword(password.getText().toString());
                userToAdd.setPname(pet_name.getText().toString());
                //userToAdd.setRace();







             myRef.child("users").child(userToAdd.getUid()).setValue(userToAdd);
            }
//
//
        });

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
            }
        });

        take_photo_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                startActivityForResult(intent, REQUEST_IMAGE_FROM_GALLERY);
            }
        });


        business_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BSignUpPage.class);
                startActivity(intent);

            }
        });


        hide_confirm_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePass(confirm_password,confirm_password.getInputType());
            }
        });



        hide_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              togglePass(password,password.getInputType());
            }
        });
    }


    /**
     * function to change InputType.
     * @param toChange Which EditText need to be change.
     * @param check represent the inputType.
     */
    public void togglePass(EditText toChange ,int check){
        switch(check){
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
        if (requestCode == REQUEST_IMAGE_CAPTURE ) {
            if (resultCode == RESULT_OK) {

                System.out.println("im at acticityOnResult");
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bitmap check = imageBitmap;
                System.out.println(check  + "BITMAP," + imageBitmap);
                userToAdd.setPet_pic(imageBitmap);
               pet_photo.setImageBitmap(imageBitmap);

                Toast.makeText(this, "Result ok", Toast.LENGTH_LONG).show();
            } else Toast.makeText(this, "Result failed", Toast.LENGTH_LONG).show();

        }
        else if(requestCode == REQUEST_IMAGE_FROM_GALLERY){
            if(resultCode == RESULT_OK){
                System.out.println("REQUEST IMAGE FROM GALLERY");
            }
        }

    }

//    protected void uploadImage(byte bytes[]){
//        StorageReference sr = mStorageRef.child("pics/firstpic.jpg");
//        sr.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(getApplicationContext(),"sucsess",Toast.LENGTH_LONG).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }

}
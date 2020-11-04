package com.example.petnet;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignUpPage extends AppCompatActivity {



    final int REQUEST_IMAGE_CAPTURE = 0;
    Button sign_up_button;
    EditText first_name;
    EditText last_name;
    EditText email;
    EditText password;
    EditText confirm_password;
    ImageButton hide_pass;
    ImageButton hide_confirm_pass;
    androidx.appcompat.widget.Toolbar toolbar;
    TextView business_sign_up;
    ImageButton take_photo;
    ImageButton take_photo_from_gallery;
    ImageView pet_photo;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        pet_photo = findViewById(R.id.pet_pic);
        sign_up_button= findViewById(R.id.B_signup);
        password = findViewById(R.id.PT_register_password);
        hide_pass = findViewById(R.id.IV_hide_pass);
        confirm_password = findViewById(R.id.PT_register_confirm_password);
        hide_confirm_pass = findViewById(R.id.IV_hide_confirm_pass);
        take_photo = findViewById(R.id.camera_photo);
        take_photo_from_gallery = findViewById(R.id.gallery_photo);


        email=findViewById(R.id.PT_register_email);
        business_sign_up= findViewById(R.id.TV_Business_signup);






//        sign_up_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(password.getText().toString()!=confirm_password.getText().toString()) {
//
//                    Toast.makeText(getApplicationContext(), "Password does not equals.", Toast.LENGTH_LONG).show();
//                    password.setError("Passwords dont match");
//                    password.requestFocus();
//                    return;
//                }
//                String mail = email.getText().toString();
//                String pass = password.getText().toString();
//             try{
//                 mAuth.createUserWithEmailAndPassword(mail,pass); // need to add oncompelte listener and check the exceptions.
//
//             }catch (Exception e){
//
//                }
//             }
//
//
//        });

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
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
        if (resultCode == REQUEST_IMAGE_CAPTURE ) {
            if (resultCode == RESULT_OK) {
                System.out.println("im at acticityOnResult");
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                pet_photo.setImageBitmap(imageBitmap);
                Toast.makeText(this, "Result ok", Toast.LENGTH_LONG).show();
            } else Toast.makeText(this, "Result failed", Toast.LENGTH_LONG).show();

        }

    }

}
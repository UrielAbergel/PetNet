package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignUpPage extends AppCompatActivity {

    Button sign_up_button;
    EditText first_name;
    EditText last_name;
    EditText email;
    EditText password;
    EditText confirm_passwrod;
    ImageButton hide_pass;
    ImageButton hide_confirm_pass;
    androidx.appcompat.widget.Toolbar toolbar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        password = findViewById(R.id.PT_register_password);
        hide_pass = findViewById(R.id.IV_hide_pass);
        confirm_passwrod = findViewById(R.id.PT_register_confirm_password);
        hide_confirm_pass = findViewById(R.id.IV_hide_confirm_pass);
        sign_up_button = findViewById(R.id.B_signup);
        email=findViewById(R.id.PT_register_email);



        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString()!=confirm_passwrod.getText().toString()) {

                    Toast.makeText(getApplicationContext(), "Password does not equals.", Toast.LENGTH_LONG).show();
                    password.setError("Passwords dont match");
                    password.requestFocus();
                    return;
                }
                String mail = email.getText().toString();
                String pass = password.getText().toString();
             try{
                 mAuth.createUserWithEmailAndPassword(mail,pass); // need to add oncompelte listener and check the exceptions.

             }catch (Exception e){

                }
             }


        });


        hide_confirm_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePass(confirm_passwrod,confirm_passwrod.getInputType());
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

}
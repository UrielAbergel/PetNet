package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        password = findViewById(R.id.PT_register_password);
        hide_pass = findViewById(R.id.IV_hide_pass);
        confirm_passwrod = findViewById(R.id.PT_register_confirm_password);
        hide_confirm_pass = findViewById(R.id.IV_hide_confirm_pass);
        sign_up_button = findViewById(R.id.B_signup);



        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate email

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
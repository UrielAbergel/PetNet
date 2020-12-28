package com.example.src.CostumersActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


import com.example.src.BusinessActivities.B_MainActivity;
import com.example.src.Firebase.DataBase;
import com.example.src.Objects.User;
import com.example.src.R;

public class C_UpdateProfile extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputLayout confirmPassword;
    private com.google.android.material.textfield.TextInputLayout password;
    private com.google.android.material.textfield.TextInputLayout Fname;
    private com.google.android.material.textfield.TextInputLayout Lname;
    private com.google.android.material.textfield.TextInputLayout email;
    private com.google.android.material.textfield.TextInputLayout phone;
    private Button sendButton;
    private User toUpdate;
    private CheckBox[] genders;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_update_profile);
        InitializeVariables();
        getUser();
        setClicks();
        displayUserData();
    }


    private void displayUserData() {
        Fname.getEditText().setText(toUpdate.getFname());
        Lname.getEditText().setText(toUpdate.getLname());
        email.getEditText().setText(toUpdate.getEmail());
        password.getEditText().setText(toUpdate.getPassword());
        confirmPassword.getEditText().setText(toUpdate.getPassword());
        genders[toUpdate.getGender()].setChecked(true);
        phone.getEditText().setText(toUpdate.getPhone());
    }


    private void setClicks() {
        genders[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(genders[1].isChecked()) genders[1].setChecked(false);
            }
        });

        genders[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(genders[0].isChecked()) genders[0].setChecked(false);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!password.getEditText().getText().toString().equals(confirmPassword.getEditText().getText().toString())){
                    password.getEditText().setError("Password must equals.");
                    return;
                }
                updateAuthService();
                DataBase.updateUser(Fname.getEditText().getText().toString()
                        ,Lname.getEditText().getText().toString()
                        ,email.getEditText().getText().toString()
                        ,password.getEditText().getText().toString()
                        ,getGender()
                        ,phone.getEditText().getText().toString());
                Intent i = new Intent(getApplicationContext(), C_UserMainActivity.class);
                startActivity(i);
            }
        });


    }

    private void updateAuthService() {
        DataBase.updateEmailAndPassword(this,toUpdate.getPassword(),
                password.getEditText().getText().toString(),
                toUpdate.getEmail(),
                email.getEditText().getText().toString()
        );
    }


    private int getGender() {
        if(genders[0].isChecked()) return 0 ;
        else return 1;
    }


    private void getUser() {
        Bundle extras = getIntent().getExtras();
        toUpdate.setFname(extras.getString("fname"));
        toUpdate.setLname(extras.getString("lname"));
        toUpdate.setEmail(extras.getString("email"));
        toUpdate.setGender(extras.getInt("gender"));
        toUpdate.setPassword(extras.getString("password"));
        toUpdate.setPhone(extras.getString("phone"));
    }


    private void InitializeVariables() {
        genders = new CheckBox[2];
        toUpdate = new User();
        password = findViewById(R.id.ET__C_update_password);
        confirmPassword = findViewById(R.id.ET_C_update_confirm_password);
        Fname = findViewById(R.id.ET_update_first_name);
        Lname = findViewById(R.id.ET_update_last_name);
        email = findViewById(R.id.ET__C_update_email);
        phone = findViewById(R.id.ET_C_update_phone);
        genders[0] = findViewById(R.id.CB_update_gender_male);
        genders[1] = findViewById(R.id.CB_update_gender_female);
        sendButton = findViewById(R.id.C_update_user);
    }


}
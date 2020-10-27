package com.example.petnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    String User_Name , Password;
    Button getResult;

    EditText User_Name_input;
    EditText Password_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        User_Name_input = (EditText) findViewById(R.id.UserNameInput);
        Password_input = (EditText) findViewById(R.id.PasswordInput);

        

    }


}
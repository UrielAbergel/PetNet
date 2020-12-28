package com.example.src.BusinessActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.src.BusinessObjects.B_User;
import com.example.src.Firebase.DataBase;
import com.example.src.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class B_UpdateProfile extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputLayout confirmPassword;
    private com.google.android.material.textfield.TextInputLayout password;
    private com.google.android.material.textfield.TextInputLayout Fname;
    private com.google.android.material.textfield.TextInputLayout Lname;
    private com.google.android.material.textfield.TextInputLayout email;
    private Button sendButton;
    private B_User toUpdate;
    private CheckBox[] genders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_update_profile);
        InitializeVariables();
        getUser();
        setClicks();
        displayUserData();
    }

    private void setClicks() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!password.getEditText().getText().toString().equals(confirmPassword.getEditText().getText().toString())){
                    password.getEditText().setError("Password must equals.");
                    return;
                }
                updateAuthService();
                DataBase.updateBuser(Fname.getEditText().getText().toString()
                        ,Lname.getEditText().getText().toString()
                        ,email.getEditText().getText().toString()
                        ,password.getEditText().getText().toString()
                        ,getGender());
                Intent i = new Intent(getApplicationContext(), B_MainActivity.class);
                startActivity(i);
            }
        });

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

    private void displayUserData() {
        System.out.println("IM AT DISPLAYUSER!@!");
        Fname.getEditText().setText(toUpdate.getFname());
        Lname.getEditText().setText(toUpdate.getLname());
        email.getEditText().setText(toUpdate.getEmail());
        password.getEditText().setText(toUpdate.getPassword());
        confirmPassword.getEditText().setText(toUpdate.getPassword());
        genders[toUpdate.getGender()].setChecked(true);
    }

    private void InitializeVariables() {
        toUpdate = new B_User();
        genders  = new CheckBox[2];
        confirmPassword = (com.google.android.material.textfield.TextInputLayout )findViewById(R.id.BS_update_confirm_password);
        password = (com.google.android.material.textfield.TextInputLayout )findViewById(R.id.BS_update_password);
        email = (com.google.android.material.textfield.TextInputLayout )findViewById(R.id.BS_update_email);
        Fname = (com.google.android.material.textfield.TextInputLayout )findViewById(R.id.BS_update_first_name);
        Lname = (com.google.android.material.textfield.TextInputLayout )findViewById(R.id.BS_update_last_name);
        genders[0] = findViewById(R.id.BS_update_gender_male);
        genders[1] = findViewById(R.id.BS_update_gender_female);
        sendButton = findViewById(R.id.B_update_buser);



    }


    private void getUser() {
     Bundle extras = getIntent().getExtras();
     toUpdate.setFname(extras.getString("fname"));
     toUpdate.setLname(extras.getString("lname"));
     toUpdate.setEmail(extras.getString("email"));
     toUpdate.setGender(extras.getInt("gender"));
     toUpdate.setPassword(extras.getString("password"));
    }
}

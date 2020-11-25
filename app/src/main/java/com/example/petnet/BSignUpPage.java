package com.example.petnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BSignUpPage extends AppCompatActivity {


    Button sign_up_button;
    EditText first_name;
    EditText last_name;
    EditText email;
    EditText password;
    EditText confirm_password;
    CheckBox gender_male;
    CheckBox gender_female;
    Buser userToAdd;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_sign_up_page);

        sign_up_button = findViewById(R.id.BS_signup);
        first_name = findViewById(R.id.BS_register_first_name);
        last_name = findViewById(R.id.BS_register_last_name);
        email = findViewById(R.id.BS_register_email);
        password = findViewById(R.id.BS_register_password);
        confirm_password = findViewById(R.id.BS_register_confirm_password);
        gender_female = findViewById(R.id.BS_gender_female);
        gender_male = findViewById(R.id.BS_gender_male);


        mAuth=FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("Busers");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        userToAdd = new Buser();
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(password.getText().toString() + " , confirm:" + password.getText().toString());
                if(!password.getText().toString().equals(confirm_password.getText().toString())) {

                    Toast.makeText(getApplicationContext(), "Password does not equals.", Toast.LENGTH_LONG).show();
                    password.setError("Passwords dont match");
                    password.requestFocus();
                    return;
                }

                String mail = email.getText().toString();
                String pass = password.getText().toString();
                System.out.println("this mail is " + mail + "  pass " + pass);
                userToAdd.setEmail(mail);


                mAuth.createUserWithEmailAndPassword(mail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        userToAdd.setUID(user.getUid());
                        userToAdd.setFname(first_name.getText().toString());
                        userToAdd.setLname(last_name.getText().toString());
                        userToAdd.setPassword(password.getText().toString());
                        myRef.child(userToAdd.getUID()).setValue(userToAdd);
                        Toast.makeText(getApplicationContext(),user.getUid(),Toast.LENGTH_LONG).show();
                        Intent log_in_page = new Intent(getApplicationContext(),Log_in_activity.class);
                        startActivity(log_in_page);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("ICANTDOIT :" + e.toString());
                        Toast.makeText(getApplicationContext(),"Sign-up failed, Try again",Toast.LENGTH_LONG).show();


                    }
                });

            }
        });


    }
}
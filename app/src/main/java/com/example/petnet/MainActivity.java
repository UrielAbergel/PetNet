package com.example.petnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private String User_Name , Password;
    private Button getResult;

    private EditText User_Name_input;
    private EditText Password_input;
    private TextView Sign_up_input;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Button login_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // get input from the user
        User_Name_input = (EditText) findViewById(R.id.TI_username);
        Password_input = (EditText) findViewById(R.id.TI_password);
        Sign_up_input =  (TextView)  findViewById(R.id.TV_signup);

        //take instant to sign up page
        Sign_up_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this,SignUpPage.class);
                startActivity(intent);
            }
        });

        //if
        login_button = (Button) findViewById(R.id.B_login);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Name = User_Name_input.getText().toString();
                Password = Password_input.getText().toString();

                mAuth.signInWithEmailAndPassword(User_Name, Password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    System.out.println("sss");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_LONG).show();

                                    // ...
                                }

                                // ...
                            }
                        });

            }
        });




    }


}
package com.example.petnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView FAB_mail_input;

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
        FAB_mail_input = (ImageView) findViewById(R.id.FAB_mail);

        //take instant to sign up page
        Sign_up_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              go_sign_up_page();
            }
        });


        //check log in button and log in into the app
        login_button = (Button) findViewById(R.id.B_login);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Name = User_Name_input.getText().toString();
                Password = Password_input.getText().toString();

                try {
                    mAuth.signInWithEmailAndPassword(User_Name, Password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        System.out.println(user.getEmail());
                                        // Sign in success, update UI with the signed-in user's information


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "Password or UserName incorrect",
                                                Toast.LENGTH_LONG).show();


                                    }
                                }
                            });
                    // in case of null name if not throw exeption
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Login Failed please try again",
                            Toast.LENGTH_LONG).show();
                }
            }
        });



        // call us by email
        FAB_mail_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_mail_send_screen();
            }
        });




    }
    public void go_sign_up_page(){
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);
    }

    public void go_to_mail_send_screen(){
        Mail_Active mail_active = new Mail_Active();
        mail_active.show(getSupportFragmentManager(),"Text Us");
    }


}
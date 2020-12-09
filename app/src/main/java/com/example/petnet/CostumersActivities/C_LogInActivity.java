package com.example.petnet.CostumersActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petnet.BusinessActivities.B_MainActivity;
import com.example.petnet.Mail.MailDiaglog;
import com.example.petnet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class C_LogInActivity extends AppCompatActivity {

    private static final String TAG = "Log_in_activity";
    private EditText User_Name_input;
    private EditText Password_input;
    private TextView Sign_up_input;
    private ImageView FAB_mail_input;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button login_button;
    private FirebaseDatabase myDB = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private int rememberMe =0;
    private CheckBox CB_rememebr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);
        if(rememberMe ==1 && mAuth.getCurrentUser()!= null ){
            String uid = mAuth.getCurrentUser().getUid();
            go_to_connector(uid);
        }
        else{
            InitializeVariables();
            //take instant to sign up page
            Sign_up_input.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    go_sign_up_page();
                }
            });

            //check log in button and log in into the app
            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String User_Name = User_Name_input.getText().toString();
                    String Password = Password_input.getText().toString();

                    try {
                        mAuth.signInWithEmailAndPassword(User_Name, Password)
                                .addOnCompleteListener(C_LogInActivity.this, new OnCompleteListener<AuthResult>() {

                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            go_to_connector(task.getResult().getUser().getUid());
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

            CB_rememebr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rememberMe%2 == 0) rememberMe++;
                    else rememberMe--;
                    Log.d(TAG, "onClick: remeberme :" + rememberMe);
                }
            });
        }
    }


    /**
     * Initialize all variables.
     */
    private void InitializeVariables() {
        CB_rememebr = findViewById(R.id.CB_remember_me);
        // get input from the user
        User_Name_input = (EditText) findViewById(R.id.TI_username);
        Password_input = (EditText) findViewById(R.id.TI_password);
        Sign_up_input =  (TextView)  findViewById(R.id.TV_signup);
        FAB_mail_input = (ImageView) findViewById(R.id.FAB_mail);
        login_button = (Button) findViewById(R.id.B_login);
    }


    /**
     * after user logged in successful we take the uid and check if he user or buser,
     * @param uid represent the user in our database.
     */
    private void go_to_connector(String uid) {
        Log.d(TAG, "go_to_connector: Check user or buser activity");
        myRef = myDB.getReference("users").child(uid);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    go_bus_main_activity_page();
                }
                else{
                    go_user_main_activity_page();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void go_sign_up_page(){
        Intent intent = new Intent(this, C_SignUpPageActivity.class);
        startActivity(intent);
    }


    public void go_to_mail_send_screen(){
        MailDiaglog alert = new MailDiaglog();
        alert.showDialog(this, "Error de conexión al servidor");
    }


    private void go_bus_main_activity_page() {
        Intent intent = new Intent(this, B_MainActivity.class);
        startActivity(intent);
    }

    public void go_user_main_activity_page(){
        Intent intent = new Intent(this, C_UserMainActivity.class);
        startActivity(intent);
    }
}
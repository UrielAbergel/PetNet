package com.example.petnet.CostumersActivities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.petnet.Mail.JavaMailAPI;
import com.example.petnet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class C_ResetPassword {

        private com.google.android.material.textfield.TextInputLayout email_box;

        private String email;
        private ImageView send_button;
        private ImageView cancel_button;


        public void showDialog(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.activity_reset_pass_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            email_box = dialog.findViewById(R.id.enter_email);
            send_button = dialog.findViewById(R.id.send_email);
            cancel_button = dialog.findViewById(R.id.cancel_mail);


            send_button.setOnClickListener(new View.
                    OnClickListener() {
                @Override
                public void onClick(View v) {
                    email = email_box.getEditText().getText().toString();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(activity, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });;

                    dialog.dismiss();
                }
            });

            cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }

}


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

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class C_ResetPassword {

        private com.google.android.material.textfield.TextInputLayout get_text_msg;

        private String eamil;
        private ImageView send_button;
        private ImageView cancel_button;


        public void showDialog(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.activity_reset_pass_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            get_text_msg = dialog.findViewById(R.id.Mail_getText);
            get_subject = dialog.findViewById(R.id.enter_email);
            get_text_mail = dialog.findViewById(R.id.Mail_email);
            send_button = dialog.findViewById(R.id.Mail_send);
            cancel_button = dialog.findViewById(R.id.Mail_cancel);


            send_button.setOnClickListener(new View.
                    OnClickListener() {
                @Override
                public void onClick(View v) {
                    text_email = get_text_mail.getEditText().getText().toString();
                    text_msg = "Email: " + text_email + "\n\n" + get_text_msg.getEditText().getText().toString();
                    text_subject = get_subject.getEditText().getText().toString();
                    JavaMailAPI sendAPI = new JavaMailAPI(dialog.getContext(),"petnetcontact@gmail.com",text_subject,text_msg);
                    sendAPI.execute();
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


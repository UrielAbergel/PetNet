package com.example.petnet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Mail_Active extends AppCompatDialogFragment {

    private EditText get_text_msg;
    private EditText get_text_mail;
    private EditText get_subject;
    private String text_subject;
    private String text_msg;
    private String text_email;

    public Dialog onCreateDialog(Bundle saveInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.mail_active,null);
        get_text_msg = view.findViewById(R.id.Mail_getText);
        get_subject = view.findViewById(R.id.Mail_subject);
        get_text_mail = view.findViewById(R.id.Mail_email);
        builder.setView(view).setTitle("Contact Us!").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                text_email = get_text_mail.getText().toString();
                text_msg = "Email: " + text_email + "\n\n" + get_text_msg.getText().toString();
                text_subject = get_subject.getText().toString();

                sendEmail();
            }
        });

        return builder.create();
    }

    private void sendEmail() {

        JavaMailAPI sendAPI = new JavaMailAPI(this.getContext(),"petnetcontact@gmail.com",text_subject,text_msg);
        sendAPI.execute();
    }


}


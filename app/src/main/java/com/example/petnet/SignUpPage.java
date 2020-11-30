package com.example.petnet;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SignUpPage extends AppCompatActivity {



    final int PET_COLOR_BLACK = 0;
    final int PET_COLOR_WHITE = 1;
    final int PET_COLOR_GRAY = 2;
    final int PET_COLOR_GOLDEN = 3;
    final int PET_COLOR_BROWN = 4;
    final int PET_COLOR_ = 5;
    final int PET_COLOR = 6;
    final int PET_COLOR_GAY = 7;
    final int PET_COLOR_GOLEN = 8;

    List<Integer> colors;



    final int REQUEST_IMAGE_CAPTURE = 0;
    final int REQUEST_IMAGE_FROM_GALLERY =1;


    Button sign_up_button;
    EditText first_name;
    EditText last_name;
    EditText email;
    EditText password;
    EditText confirm_password;
    EditText pet_name;
    EditText pet_race;
    CheckBox gender_male;
    CheckBox gender_female;
    CheckBox cb_colors[];
    CheckBox pet_gender_male;
    CheckBox pet_gender_female;
    EditText uniqe_signs;
    ImageButton hide_pass;
    ImageButton hide_confirm_pass;
    androidx.appcompat.widget.Toolbar toolbar;
    TextView business_sign_up;
    ImageButton take_photo;
    ImageButton take_photo_from_gallery;
    ImageView pet_photo;
    User userToAdd;
    Bitmap imageBitmap;
    Uri Imageuri;

    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);


        mAuth=FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("users");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){

                    Iterable<DataSnapshot> childer = snapshot.getChildren();
                    for(DataSnapshot snapshot1 : childer ){

                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ORIAN");
                        User temp = snapshot1.getValue(User.class);

                        System.out.println(temp + "FROM FIREBASE!!!!!!!!!!!!!!");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //initillaize





        userToAdd = new User();
        cb_colors = new CheckBox[9];
        colors=  new ArrayList<>();

        for (int i =0 ; i<9 ;i++){
            colors.add(0);
        }





        //buttons
        sign_up_button= findViewById(R.id.B_signup);
        take_photo = findViewById(R.id.camera_photo);
        take_photo_from_gallery = findViewById(R.id.gallery_photo);
        hide_pass = findViewById(R.id.IV_hide_pass);
        hide_confirm_pass = findViewById(R.id.IV_hide_confirm_pass);
        business_sign_up= findViewById(R.id.TV_Business_signup);


        //EditTexts
        first_name = findViewById(R.id.PT_register_first_name);
        last_name = findViewById(R.id.PT_register_last_name);
        email=findViewById(R.id.PT_register_email);
        password = findViewById(R.id.PT_register_password);
        confirm_password = findViewById(R.id.PT_register_confirm_password);
        pet_photo = findViewById(R.id.pet_pic);
        pet_name = findViewById(R.id.ET_pet_name);
        pet_race = findViewById(R.id.ET_pet_race);
        uniqe_signs = findViewById(R.id.ET_uniqe_signs);

        //CheckBoxs
        pet_gender_male = findViewById(R.id.CB_pet_gender_male);
        pet_gender_female= findViewById(R.id.CB_pet_gender_female);
        gender_female = findViewById(R.id.CB_gender_female);
        gender_male = findViewById(R.id.CB_gender_male);
        cb_colors[0] = findViewById(R.id.CB_pet_color_black); //1000269
        cb_colors[1] =findViewById(R.id.CB_pet_color_white);
        cb_colors[2] = findViewById(R.id.CB_pet_color_gray);
        cb_colors[3] = findViewById(R.id.CB_pet_color_golden);
        cb_colors[4] =findViewById(R.id.CB_pet_color_brown);
        cb_colors[5] = findViewById(R.id.CB_pet_color_); //1000269
        cb_colors[6] =findViewById(R.id.CB_pet_color);
        cb_colors[7] = findViewById(R.id.CB_pet_color_gay);
        cb_colors[8] = findViewById(R.id.CB_pet_color_golen);






//        // if color[i] == 1 the user choosed the color else didnt choose.
        cb_colors[PET_COLOR_BLACK].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(colors.get(PET_COLOR_BLACK) == 0) colors.set(PET_COLOR_BLACK, 1);
                else colors.set(PET_COLOR_BLACK, 0);
            }
        });


        cb_colors[PET_COLOR_WHITE].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(colors.get(PET_COLOR_WHITE) == 0 ) colors.set(PET_COLOR_WHITE, 1);
                else colors.set(PET_COLOR_WHITE, 0);
            }
        });

        cb_colors[PET_COLOR_GRAY].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(colors.get(PET_COLOR_GRAY) == 0) colors.set(PET_COLOR_GRAY, 1);
                else colors.set(PET_COLOR_GRAY, 0);
            }
        });

        cb_colors[PET_COLOR_GOLDEN].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(colors.get(PET_COLOR_GOLDEN) == 0) colors.set(PET_COLOR_GOLDEN, 1);
                else colors.set(PET_COLOR_GOLDEN, 0);

            }
        });

        cb_colors[PET_COLOR_BROWN].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(colors.get(PET_COLOR_BROWN) == 0) colors.set(PET_COLOR_BROWN, 1);
                else colors.set(PET_COLOR_BROWN, 0);
            }
        });

        cb_colors[PET_COLOR_].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(colors.get(PET_COLOR_) == 0) colors.set(PET_COLOR_, 1);
                else colors.set(PET_COLOR_, 0);

            }
        });


        cb_colors[PET_COLOR].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(colors.get(PET_COLOR) == 0) colors.set(PET_COLOR, 1);
                else colors.set(PET_COLOR, 0);

            }
        });
        cb_colors[PET_COLOR_GAY].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(colors.get(PET_COLOR_GAY) == 0) colors.set(PET_COLOR_GAY, 1);
                else colors.set(PET_COLOR_GAY, 0);

            }
        });

        cb_colors[PET_COLOR_GOLEN].setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(colors.get(PET_COLOR_GOLEN) == 0) colors.set(PET_COLOR_GOLEN, 1);
                else colors.set(PET_COLOR_GOLEN, 0);

            }
        });



        gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = gender_male.isChecked();
                if(checked) gender_male.setChecked(false);
                userToAdd.setGender(0);             // 0 means the user is a female.
            }
        });


        gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = gender_female.isChecked();
                if(checked) gender_female.setChecked(false);
                userToAdd.setGender(1);  // 1 means the user is a male.
            }
        });










        pet_gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = pet_gender_male.isChecked();
                if(checked) pet_gender_male.setChecked(false);
                userToAdd.setPet_gender(0);            // 0 means the pet is a female.
            }
        });


        pet_gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = pet_gender_female.isChecked();
                if(checked) pet_gender_female.setChecked(false);
                userToAdd.setPet_gender(1);  // 1 means the pet is a male.
            }
        });





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
                userToAdd.setEmail(mail);


                mAuth.createUserWithEmailAndPassword(mail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        userToAdd.setUid(user.getUid());
                        userToAdd.setFname(first_name.getText().toString());
                        userToAdd.setLname(last_name.getText().toString());
                        userToAdd.setPassword(password.getText().toString());
                        userToAdd.setPet_name(pet_name.getText().toString());
                        userToAdd.setColors(colors);
                        myRef.child(userToAdd.getUid()).setValue(userToAdd);
                        Toast.makeText(getApplicationContext(),user.getUid(),Toast.LENGTH_LONG).show();
                        System.out.println("after auth,:" + userToAdd.getUid());

                        uploadImage(REQUEST_IMAGE_FROM_GALLERY,userToAdd.getUid());

                        Intent intent = new Intent(getApplicationContext(), B_sign_up_page.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("ICANTDOIT :" + e.toString());
                        Toast.makeText(getApplicationContext(),"Sign-up failed, Try again",Toast.LENGTH_LONG).show();


                    }
                });

//                userToAdd.setAddress(adress.getText().toString()); check what about address










            }
//
//
        });

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
            }
        });

        take_photo_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_IMAGE_FROM_GALLERY);
            }
        });


        business_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), B_sign_up_page.class);
                startActivity(intent);

            }
        });


        hide_confirm_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePass(confirm_password,confirm_password.getInputType());
            }
        });



        hide_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePass(password,password.getInputType());
            }
        });
    }


    /**
     * function to change InputType.
     * @param toChange Which EditText need to be change.
     * @param check represent the inputType.
     */
    public void togglePass(EditText toChange ,int check){
        switch(check){
            case 129:
                toChange.setInputType(128);
                break;
            case 128:
                toChange.setInputType(129);
                break;

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE ) {
            if (resultCode == RESULT_OK && data!=null && data.getData()!=null) {

                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                Bitmap check = imageBitmap;


                pet_photo.setImageBitmap(imageBitmap);

                Toast.makeText(this, "Result ok", Toast.LENGTH_LONG).show();
            } else Toast.makeText(this, "Result failed", Toast.LENGTH_LONG).show();

        }
        else if(requestCode == REQUEST_IMAGE_FROM_GALLERY){
            if(resultCode == RESULT_OK && data!= null && data.getData()!=null){
                Imageuri = data.getData();
                pet_photo.setImageURI(Imageuri);


            }
        }

    }


    protected void uploadImage(int request, String uid){
        String path = "pics/" + uid;
        mStorageRef = mStorageRef.child(path);

        //image has been taken from camera.

        if(request == REQUEST_IMAGE_CAPTURE){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
            byte bytes[] = baos.toByteArray();

            mStorageRef.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"sucsess",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();
                }
            });
        }

        //image has been taken from gallery.

         if (request == REQUEST_IMAGE_FROM_GALLERY) {
            mStorageRef.putFile(Imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "image uploiaded",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Image uploade failed",Toast.LENGTH_LONG).show();
                }
            });


        }


    }


}
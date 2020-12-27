package com.example.src.BusinessObjects;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.src.Firebase.DataBase;
import com.example.src.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class B_NewStoreDialog {
    private static final String TAG = "B_NewStoreDialog";
    private String name,store_price,phone_num,address,store_des;
    private com.google.android.material.textfield.TextInputLayout get_store_name;
    private com.google.android.material.textfield.TextInputLayout get_store_price;
    private com.google.android.material.textfield.TextInputLayout get_store_phone_number;
    private com.google.android.material.textfield.TextInputLayout get_store_address;
    private com.google.android.material.textfield.TextInputLayout get_store_des;
    private CheckBox[] get_type;
    private ImageView send_button;
    private ImageView cancel_button;
    private boolean toUpdate = false;
    private B_Store store;
    private int type = -1;

    String storeName,phoneNum,storeAddress,storeDescription,storeKey;

    //constructor
    public B_NewStoreDialog(){
        storeName = phoneNum= storeAddress= storeDescription = "";
    }

    //constructor
    public B_NewStoreDialog(int type, String storeName, String phoneNum , String storeAddress , String storeDescription,boolean toUpdate,String storeKey ){
        this.type = type;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeDescription = storeDescription;
        this.phoneNum = phoneNum;
        this.toUpdate = toUpdate;
        this.storeKey = storeKey;

    }


    /**
     * A function that turns on as soon as the user wants to edit a store
     */
    public void setData(){
        if(type != -1)  get_type[type].setChecked(true);
        get_store_name.getEditText().setText(storeName);
        get_store_phone_number.getEditText().setText(phoneNum);
        get_store_address.getEditText().setText(storeAddress);
        get_store_des.getEditText().setText(storeDescription);

    }

    /**
     * A function that initializes all variables
     * @param activity
     */
    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.b_new_store_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        get_store_name = dialog.findViewById(R.id.enter_name);
        get_store_address = dialog.findViewById(R.id.enter_address);
        get_store_des = dialog.findViewById(R.id.get_description);
        get_store_phone_number = dialog.findViewById(R.id.phone_number);
        get_store_price = dialog.findViewById(R.id.type_price);
        send_button = dialog.findViewById(R.id.send);
        cancel_button = dialog.findViewById(R.id.cancel);
        get_type = new CheckBox[5];
        get_type[0] = dialog.findViewById(R.id.type_sitter);
        get_type[1] = dialog.findViewById(R.id.type_trainer);
        get_type[2] = dialog.findViewById(R.id.type_walker);
        get_type[3] = dialog.findViewById(R.id.type_shop);
        get_type[4] = dialog.findViewById(R.id.type_vet);
        setData();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;




//==========================================initializes check box =========================

        get_type[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCheck(0);
                type = 0 ;
            }
        });

        get_type[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCheck(1);
                type = 1 ;
            }
        });

        get_type[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCheck(2);
                type = 2 ;
            }
        });

        get_type[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCheck(3);
                type = 3 ;
            }
        });

        get_type[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCheck(4);
                type = 4 ;
            }
        });
        
        



//===========================create store===================================

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = get_store_name.getEditText().getText().toString();
                Log.d(TAG, "onClick: name:" + name);
                phone_num = get_store_phone_number.getEditText().getText().toString();
                address = get_store_address.getEditText().getText().toString();
                store_des = get_store_des.getEditText().getText().toString();
                store_price = get_store_price.getEditText().getText().toString();

                if(!toUpdate) {
                    switch (type) {
                        case -1:
                            Toast.makeText(dialog.getContext(), "Please choose 1 shop type", Toast.LENGTH_LONG).show();
                            break;

                        case 0:
                            store = new B_DogSitter(name, phone_num, store_des, address, Integer.parseInt(store_price));
                            DataBase.insertBusiness(store, currentFirebaseUser.getUid());
                            break;

                        case 1:
                            store = new B_DogTrainer(name, phone_num, store_des, address, Integer.parseInt(store_price));
                            DataBase.insertBusiness(store, currentFirebaseUser.getUid());
                            break;

                        case 2:
                            store = new B_DogWalker(name, phone_num, store_des, address, Integer.parseInt(store_price));
                            DataBase.insertBusiness(store, currentFirebaseUser.getUid());
                            break;

                        case 3:
                            store = new B_PetShop(name, phone_num, store_des, address);
                            DataBase.insertBusiness(store, currentFirebaseUser.getUid());
                            break;

                        case 4:
                            store = new B_VetStore(name, phone_num, store_des, address);
                            DataBase.insertBusiness(store, currentFirebaseUser.getUid());
                            break;

                    }
                }
                else{
                    DataBase.updateStore(storeKey,name,store_des,address,phone_num,type);
                }


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

    private void clearCheck(int pos){
        for(int i = 0 ; i< get_type.length; i ++ ){
            if(i!= pos) {
                if(get_type[i].isChecked())get_type[i].setChecked(false);
            }

        }
    }
}

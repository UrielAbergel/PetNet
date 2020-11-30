package com.example.petnet;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBase {

    private static final String TAG = "DataBase";
    private static FirebaseDatabase FBdatabase = FirebaseDatabase.getInstance();
    public static final String USERS_ROOT="users",DOGS_ROOT="dogs",STORES_ROOT="Stores",BUSERS_ROOT="Busers";


    //------------------------Insert Data into Firebase-------------------------//

    public static boolean insertUser(User userToAdd){
        DatabaseReference myRef =FBdatabase.getReference(USERS_ROOT);
        try{
            myRef.child(userToAdd.getUid()).setValue(userToAdd);
            Log.d(TAG, "insertUser: userToAdd has been added to Database:->" + userToAdd.toString());
            return true;
        }catch (Exception e){
            Log.d(TAG, "insertUser: Failed to add userToAdd to Database.");
            return false;
        }
    }

    public static boolean insertDog(Dog dogToAdd,String Uid){
        DatabaseReference myRef = FBdatabase.getReference(DOGS_ROOT);
        try{
            myRef.child(Uid).setValue(dogToAdd);
            Log.d(TAG, "insertDog: dogToAdd has been added to Database");
            return true;
        }catch (Exception e){
            Log.d(TAG, "insertDog: Failed to add dogToAdd to Database");
            return false;
        }
    }

    public static boolean insertBusiness(BusinessUser businessToAdd ,String uid , int store_count){

        /**
         *  Think on the database, if we want make root for each shop type for not travle on the whole shops 
         *  when want show only pet_shot/vets../dog_walkers
         *  if yes add the shop to the right root under the uid of the shop owner. 
         */

        
        int type = businessToAdd.get_store_type();
        String numShop ="s" + store_count;
        DatabaseReference myRef = FBdatabase.getReference(STORES_ROOT).child(uid).child(numShop);
        try{
            switch(type){
                case 0:
                    Bdog_sitter DStoAdd = (Bdog_sitter)businessToAdd;
                    myRef.setValue(DStoAdd);
                    Log.d(TAG, "insertBusiness: Dogsitter has been added to Database");
                    return true;

                case 1:
                    Bdog_trainer DTtoAdd = (Bdog_trainer)businessToAdd;
                    myRef.setValue(DTtoAdd);
                    Log.d(TAG, "insertBusiness: Dogtrainer has been added to Database");
                    return true;

                case 2:
                    Bdog_walker DWtoAdd = (Bdog_walker)businessToAdd;
                    myRef.setValue(DWtoAdd);
                    Log.d(TAG, "insertBusiness: Dogwalker has been added to Database");
                    return true;

                case 3:
                    Bpet_shop PStoAdd = (Bpet_shop) businessToAdd;
                    myRef.setValue(PStoAdd);
                    Log.d(TAG, "insertBusiness: Petshop has been added to Database");
                    return true;

                case 4:
                    Bveterinarian vetToAdd = (Bveterinarian)businessToAdd;
                    myRef.setValue(vetToAdd);
                    Log.d(TAG, "insertBusiness: Vet has been added to Database.");
                    return true;
            }

        }catch (Exception e){
            Log.d(TAG, "insertBusiness: Failed to add businessToAdd to Database");
            return false;
        }


        return true;
    }

    public static boolean insertBuser(Buser userToAdd){
        DatabaseReference myRef = FBdatabase.getReference(BUSERS_ROOT);
        try{
            myRef.child(userToAdd.getUID()).setValue(userToAdd);
            Log.d(TAG, "insertBuser: BuserToAdd has been added to Database.");
            return true;

        }catch(Exception e){
            Log.d(TAG, "insertBuser: Failed to add BuserToAdd to Database");
            return false;
        }



    }



}

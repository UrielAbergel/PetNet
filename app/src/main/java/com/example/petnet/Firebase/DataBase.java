package com.example.petnet.Firebase;

import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.petnet.Bdog_sitter;
import com.example.petnet.Bdog_trainer;
import com.example.petnet.Bdog_walker;
import com.example.petnet.Bpet_shop;
import com.example.petnet.Buser;
import com.example.petnet.BusinessUser;
import com.example.petnet.Bveterinarian;
import com.example.petnet.Objects.Dog;
import com.example.petnet.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DataBase {

    private static final String TAG = "DataBase";
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseDatabase FBdatabase = FirebaseDatabase.getInstance();
    public static DatabaseReference reference = FBdatabase.getReference("dogs");
    public static final String USERS_ROOT="users",DOGS_ROOT="dogs",STORES_ROOT="Stores",BUSERS_ROOT="Busers";


    //------------------------Insert Data into Firebase-------------------------//


    /**
     * have been tested, work good.
     * @param userToAdd the user we want add to our database.
     * @return true on success false on failure.
     */
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

    /**
     * have been tested, work good.
     * @param dogToAdd the dog we want add to our database.
     * @param Uid represent the dog owner.
     * @return true on success false on failure.
     */
    public static boolean insertDog(Dog dogToAdd, String Uid){
        try{
            db.collection(DOGS_ROOT).document(Uid).set(dogToAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "insertDog: dogToAdd has been added to Database");

                    }
                    else{

                        Log.d(TAG, "insertDog: Failed to add dogToAdd to Database:-->" +task.getException().toString());
                    }
                }
            });

        }catch (Exception e){

            return false;
        }
        return true;
    }

    public static boolean insertBusiness(BusinessUser businessToAdd , String uid , int store_count){

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



    public static Dog getDog(String uid) {
        DocumentReference ds = db.collection(DOGS_ROOT).document(uid);
        ds.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot ds = task.getResult();

                    Dog t = ds.toObject(Dog.class);
                    Log.d(TAG, "onComplete:  Dog is:" + t.toString());
                }
            }
        });


        return null;
    }

}
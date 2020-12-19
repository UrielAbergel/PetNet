package com.example.petnet.Firebase;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.petnet.BusinessObjects.B_DogSitter;
import com.example.petnet.BusinessObjects.B_DogTrainer;
import com.example.petnet.BusinessObjects.B_DogWalker;
import com.example.petnet.BusinessObjects.B_PetShop;
import com.example.petnet.BusinessObjects.B_User;
import com.example.petnet.BusinessObjects.B_Store;
import com.example.petnet.BusinessObjects.B_VetStore;
import com.example.petnet.Objects.Dog;
import com.example.petnet.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DataBase {

    private static final String TAG = "DataBase";
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseDatabase FBdatabase = FirebaseDatabase.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static final String USERS_ROOT="users",DOGS_ROOT="dogs",STORES_ROOT="Stores",
            BUSERS_ROOT="Busers",STORE_CATEGORY="Store_Categories",DOG_SITTER_STORE ="Dog_Sitters",
            DOG_TRAINER_STROE="Dog_Trainers",DOG_WALKER_STROE = "Dog_Walker",PET_STROE="Pet_Store",VET_STORE = "Vet_Store";


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


    /**
     * Each shop that been added into data base few things happened
     * 1) all the store object pushed into Stores tree with uniqe key.
     * 2) the storekey pushed to owner tree under stores child.
     * 3) the store key pushed to Category stores tree under the right category of the shop that has been added.
     * have been tested work good.
     * @param toAdd store we want add to our database
     * @param uid the uid of the store owner.
     * @return
     */

    public static boolean insertBusiness(B_Store toAdd , String uid){
        int type = toAdd.get_store_type();
        DatabaseReference storeRef = FBdatabase.getReference(STORES_ROOT);
        DatabaseReference ownerRef = FBdatabase.getReference(BUSERS_ROOT).child(uid);
        DatabaseReference nodeRef = FBdatabase.getReference();
        String storeKey = storeRef.push().getKey();
        try{
            switch (type) {
                case 0:
                    B_DogSitter DStoAdd = (B_DogSitter) toAdd;
                    storeRef.child(storeKey).setValue(DStoAdd);
                    ownerRef.child("stores").child(storeKey).setValue(storeKey);
                    nodeRef.child(STORE_CATEGORY).child(DOG_SITTER_STORE).child(storeKey).setValue(storeKey);
                    Log.d(TAG, "insertBusiness: Dogsitter has been added to Database");
                    return true;

                case 1:
                    B_DogTrainer DTtoAdd = (B_DogTrainer) toAdd;
                    storeRef.child(storeKey).setValue(DTtoAdd);
                    ownerRef.child("stores").child(storeKey).setValue(storeKey);
                    nodeRef.child(STORE_CATEGORY).child(DOG_TRAINER_STROE).child(storeKey).setValue(storeKey);
                    Log.d(TAG, "insertBusiness: Dogtrainer has been added to Database");
                    return true;

                case 2:
                    B_DogWalker DWtoAdd = (B_DogWalker) toAdd;
                    storeRef.child(storeKey).setValue(DWtoAdd);
                    ownerRef.child("stores").child(storeKey).setValue(storeKey);
                    nodeRef.child(STORE_CATEGORY).child(DOG_WALKER_STROE).child(storeKey).setValue(storeKey);
                    Log.d(TAG, "insertBusiness: Dogwalker has been added to Database");
                    return true;

                case 3:
                    B_PetShop PStoAdd = (B_PetShop) toAdd;
                    storeRef.child(storeKey).setValue(PStoAdd);
                    ownerRef.child("stores").child(storeKey).setValue(storeKey);
                    nodeRef.child(STORE_CATEGORY).child(PET_STROE).child(storeKey).setValue(storeKey);
                    Log.d(TAG, "insertBusiness: Petshop has been added to Database");
                    return true;

                case 4:
                    B_VetStore vetToAdd = (B_VetStore) toAdd;
                    storeRef.child(storeKey).setValue(vetToAdd);
                    ownerRef.child("stores").child(storeKey).setValue(storeKey);
                    nodeRef.child(STORE_CATEGORY).child(VET_STORE).child(storeKey).setValue(storeKey);

                    Log.d(TAG, "insertBusiness: Vet has been added to Database.");
                    return true;

            }

        }catch (Exception e){return false;}

        return true;

    }

    /**
     * has been tested work good.
     * @param userToAdd business user to add to database.
     * @return true on success false otherwise.
     */

    public static boolean insertBuser(B_User userToAdd){
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



// ---------------------------------------------------Delete Data from Database----------------------------------------------------------//


    /**
     * has been tested work good.
     * This function delete a chosen store.
     * like insertbusiness the chosen store gonna delete from 3 diffrent trees.
     * @param type the type of the shop e.g. vetStore,DogTrainer..
     * @param uid  the uid of the shop owner
     * @param storeKey the uniqe key of the shop
     * @return true if the shop has been deleted false otherwise.
     */
    public static boolean deleteBusiness(int type, String uid , String storeKey){

        DatabaseReference storeRef = FBdatabase.getReference(STORES_ROOT).child(storeKey);
        DatabaseReference ownerRef = FBdatabase.getReference(BUSERS_ROOT).child(uid);
        DatabaseReference nodeRef = FBdatabase.getReference();

        try{
            switch (type){
                case 0:
                    storeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                ds.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled: Error while delete store,Error:" + error.toException().toString());

                        }
                    });

                    ownerRef.child("stores").child(storeKey).removeValue();
                    nodeRef.child(STORE_CATEGORY).child(DOG_SITTER_STORE).child(storeKey).removeValue();
                    Log.d(TAG, "deleteBusiness: Dog sitter has been deleted.");
                    return true;

                case 1:
                    storeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                ds.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled: Error while delete store,Error:" + error.toException().toString());

                        }
                    });
                    ownerRef.child("stores").child(storeKey).removeValue();
                    nodeRef.child(STORE_CATEGORY).child(DOG_TRAINER_STROE).child(storeKey).removeValue();
                    Log.d(TAG, "deleteBusiness: Dog trainer has been deleted.");
                    return true;

                case 2:
                    storeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                ds.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled: Error while delete store,Error:" + error.toException().toString());

                        }
                    });
                    ownerRef.child("stores").child(storeKey).removeValue();
                    nodeRef.child(STORE_CATEGORY).child(DOG_WALKER_STROE).child(storeKey).removeValue();
                    Log.d(TAG, "deleteBusiness: Dog walker has been deleted.");
                    return true;

                case 3:
                    storeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                ds.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled: Error while delete store,Error:" + error.toException().toString());

                        }
                    });
                    ownerRef.child("stores").child(storeKey).removeValue();
                    nodeRef.child(STORE_CATEGORY).child(PET_STROE).child(storeKey).removeValue();
                    Log.d(TAG, "deleteBusiness: Pet store has been deleted.");
                    return true;

                case 4:
                    storeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                ds.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled: Error while delete store,Error:" + error.toException().toString());

                        }
                    });

                    ownerRef.child("stores").child(storeKey).removeValue();
                    nodeRef.child(STORE_CATEGORY).child(VET_STORE).child(storeKey).removeValue();
                    Log.d(TAG, "deleteBusiness: Vet store has been deleted.");

                    return true;

            }

        }catch (Exception e){return false;}

        return true;
    }


    /**
     * This function has not tested yet*
     * this function delete user from our system.
     * 1) delete all the data from RT database.
     * 2) delete the dog from Firebase Firestore.
     * 3) delete the dog picture from the Firebase Storage.
     * 4) delete the user from authentication.
     * @return true all the data has been deleted false otherwise.
     */
    public static boolean deleteUser(){
        FirebaseUser userToDelete = mAuth.getCurrentUser();
        String uid = userToDelete.getUid();


        DatabaseReference userRef = FBdatabase.getReference(USERS_ROOT).child(uid);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("pics").child(uid);
        try{

            //delete the user from USERS tree.
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()){
                        ds.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //delete the dog from the cloud firestore
            db.collection(DOGS_ROOT).document(uid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: Dog has been deleted");
                    }
                    else{
                        Log.d(TAG, "onComplete: Delete dog has been failed.");
                    }
                }
            });
            //delete the picture of the dog.
            storageRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: dog picture has been deleted.");
                    }
                    else{
                        Log.d(TAG, "onComplete: Failed to delete dog pic.");
                    }
                }
            });

            //delete the user from auth
            userToDelete.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: Auth has been deleted.");
                    }
                    else{
                        Log.d(TAG, "onComplete:Failed to delete Auth.");
                    }
                }
            });

        }catch (Exception e){return false;}

        return true;
    }


    /**
     * this function hasnot been tested.
     * this function delete a business user from out system.
     * 1) delete all the shop that this user own.
     * 2) delete the user from authentication.
     * @return true if user has been deleted false otherwise.
     */
    public static boolean deleteBUser(){
        FirebaseUser userToDelete = mAuth.getCurrentUser();
        String uid = userToDelete.getUid();

        DatabaseReference userRef = FBdatabase.getReference(BUSERS_ROOT).child(uid);


        try{

            //delete the user from buser tree and all the stores of this user.
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds:snapshot.getChildren()) {
                        if(ds.getKey().equals("stores")){
                            if(ds.hasChildren()) {
                                for (DataSnapshot ds1 : ds.getChildren()) {
                                    deleteHelperBuser(ds1.getKey(),uid);

                                }
                            }
                        }
                        else{
                            ds.getRef().removeValue();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            userToDelete.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: Buser has been deleted.");
                    }
                    else{
                        Log.d(TAG, "onComplete: Failed to delete Buser");
                    }
                }
            });

        }catch (Exception e){return false;}



        return true;
    }


    /**
     * helper function for delete BUSER.
     * this function will get ea storekey of the owner and will use deletebusiness to delete this store from all the placed that needed.
     * @param storeKey the uniqe key of the store
     * @param uid the uid of the owenr.
     */
    private static void deleteHelperBuser(String storeKey, String uid) {
        DatabaseReference myRef = FBdatabase.getReference(STORES_ROOT).child(storeKey);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                B_Store toRemove = snapshot.getValue(B_Store.class);
                deleteBusiness(toRemove.get_store_type(),uid,storeKey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
package com.example.petnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.petnet.Algorithms.SortHashMap;
import com.example.petnet.Objects.Dog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class TinderSwipe extends AppCompatActivity  {


    private FirebaseFirestore FbFs = FirebaseFirestore.getInstance();
    private final String dog_root = "dogs";
    static List<ItemModel> items;
    static ItemModel toReturn;
    static Uri my_uri;
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    HashMap<String,Double> userCandidateList;

    private int counterActivity,counterThread;
    private static final String TAG = "TinderSwipe";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_swipe);
        Bundle extras = getIntent().getExtras();
        userCandidateList = (HashMap<String, Double>)extras.get("data");
        items = (List<ItemModel>)extras.get("items");
        //addList();


//        getDogsFromFirebase();
//        getPicsFromStorage();

        CardStackView cardStackView = findViewById(R.id.CSV_tinder);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: d=" + manager.getTopPosition()+ " d=" + direction);
                if(direction== Direction.Right){
                    Toast.makeText(getApplicationContext(),"Direction right", Toast.LENGTH_LONG).show();
                }
                if(direction== Direction.Top){
                    Toast.makeText(getApplicationContext(),"Direction top", Toast.LENGTH_LONG).show();
                }
                if(direction== Direction.Left){
                    Toast.makeText(getApplicationContext(),"Direction left", Toast.LENGTH_LONG).show();
                }
                if(direction== Direction.Bottom){
                    Toast.makeText(getApplicationContext(),"Direction bottom", Toast.LENGTH_LONG).show();
                }

//                if(manager.getTopPosition() == adapter.getItemCount()-5)
                if(manager.getTopPosition() == adapter.getItemCount()-5)
                {
                    paginate();
                }
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardCanceled: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.TV_pet_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());

        adapter = new CardStackAdapter(items);//addList()
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }


    private void paginate(){
        List<ItemModel> old = adapter.getItems();
        addList();
        List<ItemModel> baru = new ArrayList<>(items);
        CardStackCallback callback = new CardStackCallback(old,baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }

 // after this function done items need to be initialize to dogs we found.
    private void addList() {


        userCandidateList = SortHashMap.sortByValue(userCandidateList);
        counterActivity = userCandidateList.size();
//        items.add(new ItemModel(null,"zipi","male","pitbull","Asf"));
//        items.add(new ItemModel(null,"zipi1","male","pitbull","Asf"));
//        items.add(new ItemModel(null,"zipi2","male","pitbull","Asf"));
//        items.add(new ItemModel(null,"zipi3","male","pitbull","Asf"));
        counterThread = 0;
        for (Map.Entry<String, Double> en : userCandidateList.entrySet()) {
            Log.d(TAG,  "Key = " + en.getKey() +
                    ", Value = " + en.getValue());
        }
       mapToItemModel();
//        while(counterThread != counterActivity);
        Log.d(TAG, "addList: " + items);
        return ;
    }


    private void mapToItemModel()
    {
        //for dog pics
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Log.d(TAG, "mapToItemModel: usercandidate size:" + userCandidateList.size());
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try{
                    for (Map.Entry<String,Double> current_dog: userCandidateList.entrySet()) {

                        String key = current_dog.getKey();
                        Log.d(TAG, "mapToItemModel:uid: " + key);
                        toReturn = new ItemModel();
                        FbFs.collection(dog_root).document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                Log.d(TAG, "onComplete:  firestore task completed");
                                if(task.isSuccessful()){
                                    Log.d(TAG, "onComplete:  firestore task sucseccs");
                                    DocumentSnapshot DS = task.getResult();
                                    Dog tempDog = DS.toObject(Dog.class);
                                    toReturn.setDog_name(tempDog.getPet_name());
                                    if (tempDog.getPet_gender() == 0)
                                        toReturn.setGender("Male");
                                    else if (tempDog.getPet_gender() == 1)
                                        toReturn.setGender("Female");
                                    toReturn.setRace(tempDog.getPet_race());
                                    toReturn.setUniqe_signs(tempDog.getUniqe_signs());

                                    Log.d(TAG, "onComplete: before storage");

                                    storageRef.child("pics/" + key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            my_uri = uri;
                                            toReturn.setImage(my_uri);

                                            Log.d(TAG, "onSuccess: !! IM HERE!!!" + items.toString());
                                            counterThread++;
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            counterThread++;
                                            Log.d(TAG, "onFailure: !!! BHHHHHHH");
                                        }
                                    });
                                    items.add(toReturn);
                                }


                            }
                        });

                    }

                }catch (Exception e){
                    Log.d(TAG, "run: runnable failed.");
                }


            }
        };
        Log.d(TAG, "mapToItemModel: after read all data from firestore");

        Executor exec = Executors.newSingleThreadExecutor();
        exec.execute(task);

    }
}
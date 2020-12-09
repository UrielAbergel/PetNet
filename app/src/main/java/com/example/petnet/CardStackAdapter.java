package com.example.petnet;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<ItemModel> items;

    public CardStackAdapter(List<ItemModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dog_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, race, uniqe_signs, gender,owner_name;
        FloatingActionButton FAB_check,FAB_cancel;
        String uid;
        private static final String TAG = "ViewHolder";
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.IV_dog_profile);
            name = itemView.findViewById(R.id.TV_pet_name);
            race = itemView.findViewById(R.id.TV_race_info);
            uniqe_signs = itemView.findViewById(R.id.TV_uniqe_signs_info);
            gender = itemView.findViewById(R.id.TV_gender_info);
            FAB_check = itemView.findViewById(R.id.FAB_check);
            owner_name = itemView.findViewById(R.id.TV_owner_name);

            
            FAB_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  getPhoneNumber(uid,itemView);



                }
            });
        }
        private void getPhoneNumber(String uid,final View viewHolder) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference myRef = db.getReference("users");
            myRef.child(uid).child("phone").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "onDataChange: uid:" + uid);
                    String number = (String)snapshot.getValue();
                    Log.d(TAG, "onDataChange: number is" + number);
                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + number));
                    viewHolder.getContext().startActivity(intent);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



        void setData(ItemModel data) {
            Picasso.get()
                    .load(data.getImage())
                    .fit()
                    .centerCrop()
                    .into(image);
            name.setText(data.getDog_name());
            race.setText(data.getRace());
            uniqe_signs.setText(data.getUniqe_signs());
            gender.setText(data.getGender());
            owner_name.setText(data.getOwner_name());
            uid = data.getUid();
        }
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }


}

package com.example.petnet.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petnet.Objects.ItemModel;
import com.example.petnet.R;
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


    /**
     * this function will bind the view holder we got with the dog we found match
     * @param holder the view holder
     * @param position position in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    /**
     * each object from this class will represent by a card in the emulator.
     * all the click buttons and info we need saved here.
     */
    class  ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, race, uniqe_signs, gender,owner_name;
        ImageView FAB_check;
        String uid;
        private static final String TAG = "ViewHolder";
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.IV_dog_profile);
            name = itemView.findViewById(R.id.TV_pet_name);
            race = itemView.findViewById(R.id.TV_race_info);
            uniqe_signs = itemView.findViewById(R.id.TV_uniqe_signs_info);
            gender = itemView.findViewById(R.id.TV_gender_info);
            FAB_check = itemView.findViewById(R.id.IV_check);
            owner_name = itemView.findViewById(R.id.TV_owner_name);
            FAB_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  isValidCall(itemView);
                }
            });
        }


        /**
         * check if the user wanna call to the dog owner that might match.
         * @param viewHolder View to transfer to getPhone to start activity if needed.
         */
        private void isValidCall(final View viewHolder) {
            AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.getContext());
            builder.setMessage("Call the owner?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getPhoneNumber(viewHolder);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();


        }


        /**
         * this application will get the phone number of the user from firebase,
         * and make a dial activity.
         * @param viewHolder  View to be able start activity from view holder.
         */
        private void getPhoneNumber(final View viewHolder) {
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


        /**
         * set all the data .
         * @param data represent a dog we found match to the description.
         */
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

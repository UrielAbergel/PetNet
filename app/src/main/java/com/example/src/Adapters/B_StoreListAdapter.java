package com.example.src.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.src.BusinessObjects.B_NewStoreDialog;
import com.example.src.BusinessObjects.B_Store;
import com.example.src.Firebase.DataBase;
import com.example.src.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


/**
 *  A class that represents a correlation between each store and the page
 */
public class B_StoreListAdapter extends ArrayAdapter<B_Store> {


    private Context mContext;
    private Activity activity;
    private int mResource;
    private int lastPosition = -1;


    /**
     * An object that preserves all store values
     */
    private static class ViewHolder{
        TextView store_name;
        TextView store_type;
        TextView store_adress;
        TextView store_description;
        TextView store_phone;
        ImageView delete_store;
        ImageView edit_store;

    }

    /**
     * constructor
     * @param context for activity
     * @param resource
     * @param objects all store to adapt
     */
    public B_StoreListAdapter(@NonNull Context context, int resource, @NonNull List<B_Store> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    /**
     * constructor with activity
     * @param context
     * @param resource
     * @param objects
     * @param activity
     */
    public B_StoreListAdapter(@NonNull Context context, int resource, @NonNull List<B_Store> objects,Activity activity) {
        super(context, resource, objects);
        this.activity = activity;
        mContext = context;
        mResource = resource;
    }

    //----------------------------------------convert-------------------------------------------------

    /**
     *A function that takes each store and converts it to view according to a defined LAYOUT
     * @param position the position of current
     * @param convertView the layout of the store
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).get_store_name();
        String phone = getItem(position).get_phone_number();
        int type = getItem(position).get_store_type();
        String address = getItem(position).get_address();
        String description = getItem(position).get_description();
        String uid = getItem(position).get_uid();


        ViewHolder holder;


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        holder= new ViewHolder();
        holder.store_name = (TextView) convertView.findViewById(R.id.l_name);
        holder.store_adress = (TextView) convertView.findViewById(R.id.l_address);
        holder.store_phone = (TextView) convertView.findViewById(R.id.l_phone);
        holder.store_description = (TextView) convertView.findViewById(R.id.l_descre);
        holder.store_type = (TextView) convertView.findViewById(R.id.l_type);
        holder.delete_store = (ImageView) convertView.findViewById(R.id.delete_store);
        holder.edit_store = (ImageView) convertView.findViewById(R.id.edit_store);

        Log.d("listview", "onDataChange: im here ");

        holder.store_name.setText(name);
        holder.store_phone.setText(phone);
        holder.store_adress.setText(address);
        holder.store_description.setText(description);
        holder.store_type.setText(return_type_as_string(type));

        try {
            holder.delete_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Delete Store?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBase.deleteBusiness(type, FirebaseAuth.getInstance().getCurrentUser().getUid(), uid);
                            dialog.dismiss();
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
            });
        } catch (Exception e) {

        }



        try{
            holder.edit_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    B_NewStoreDialog dialog = new B_NewStoreDialog(type,name,phone,address,description,true,uid);
                    dialog.showDialog(activity);

                }
            });
        }catch (Exception e ){}
        return convertView;
    }


    /**
     * A function that receives a type of store and returns it as a string
     * @param type of the store
     * @return
     */

    public String return_type_as_string(int type)
    {
        switch (type)
        {
            case 0: return "Dog Sitter";
            case 1: return "Dog Trainer";
            case 2: return "Dog Walker";
            case 3: return "Pet Shop";
            case 4: return "Pet Vet";
        }

        return "";
    }



}

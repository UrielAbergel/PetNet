package com.example.petnet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;




public class B_store_list_adapter extends ArrayAdapter<B_store> {


    private Context mContext;
    private int mResource;
    private int lastPosition = -1;


    private static class ViewHolder{
        TextView store_name;
        TextView store_type;
        TextView store_adress;
        TextView store_description;
        TextView store_phone;

    }



    public B_store_list_adapter(@NonNull Context context, int resource, @NonNull List<B_store> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).get_store_name();
        String phone = getItem(position).get_phone_number();
        int type = getItem(position).get_store_type();
        String address = getItem(position).get_address();
        String description = getItem(position).get_description();

        ViewHolder holder;


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        holder= new ViewHolder();
        holder.store_name = (TextView) convertView.findViewById(R.id.l_name);
        holder.store_adress = (TextView) convertView.findViewById(R.id.l_address);
        holder.store_phone = (TextView) convertView.findViewById(R.id.l_phone);
        holder.store_description = (TextView) convertView.findViewById(R.id.l_descre);
        holder.store_type = (TextView) convertView.findViewById(R.id.l_type);

        Log.d("listview", "onDataChange: im here ");

        holder.store_name.setText(name);
        holder.store_phone.setText(phone);
        holder.store_adress.setText(address);
        holder.store_description.setText(description);
        holder.store_type.setText(return_type_as_string(type));

        return convertView;
        }


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

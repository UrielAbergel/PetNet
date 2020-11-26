package com.example.petnet;

import android.location.Address;

public class Bpet_shop implements BusinessUser{

    private String _store_name;
    private String _phone_number;
    private String _description;
    private final int _store_type = 3;
    private double _store_rate;
    private String _address;


    public Bpet_shop(){}


    public Bpet_shop(String name , String phone_number , String description , String address){
        this._store_name = name;
        this._phone_number = phone_number;
        this._description = description;
        this._address = address;
    }



    @Override
    public String get_store_name() {
        return this._store_name;
    }

    @Override
    public int get_store_type() {
        return this._store_type;
    }

    @Override
    public String get_address() {
        return this._address;
    }

    @Override
    public String get_phone_number() {
        return this._phone_number;
    }

    @Override
    public double get_store_rate() {
        return this._store_rate;
    }

    @Override
    public String get_description() {
        return this._description;
    }


    public void setName(String name) {
        this._store_name = name;
    }

    public void setPhone_number(String phone_number) {
        this._phone_number = phone_number;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public void setStore_rate(double store_rate) {
        this._store_rate = store_rate;
    }

    public void setAddress(String address) {
        this._address = address;
    }



}


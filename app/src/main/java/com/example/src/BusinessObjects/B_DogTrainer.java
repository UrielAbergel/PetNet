package com.example.src.BusinessObjects;

public class B_DogTrainer implements B_Store {

    private String _store_name;
    private String _phone_number;
    private String _description;
    private final int _store_type = 1;
    private double _store_rate;
    private String _address;
    private int _price;
    private String uid;

    public B_DogTrainer(){}


    public B_DogTrainer(String name , String phone_number , String description , String address , int price ){
        this._store_name = name;
        this._phone_number = phone_number;
        this._description = description;
        this._address = address;
        this._price = price;
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

    @Override
    public String get_uid() {
        return this.uid;
    }
    public void set_uid(String uid){ this.uid = uid;}


    public int get_price(){
        return this._price;
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

    public void setPrice(int price) {
        this._price = price;
    }

    @Override
    public String toString() {
        return "Bdog_trainer{" +
                "_name='" + _store_name + '\'' +
                ", _phone_number='" + _phone_number + '\'' +
                ", _description='" + _description + '\'' +
                ", _store_type=" + _store_type +
                ", _store_rate=" + _store_rate +
                ", _address='" + _address + '\'' +
                ", _price=" + _price +
                '}';
    }
}


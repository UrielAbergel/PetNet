package com.example.petnet;

import android.graphics.Bitmap;

import java.util.Arrays;
import java.util.Map;

public class User {

    private String Fname;
    private String Lname;
    private String Email;
    private String Password;
    private String Address;
    private int Gender;

    private String Uid;
    private String Pet_name;
    private String Pet_race;
    private int Pet_gender;
    private int Size; // split into categories 1-5
    private Map<String,Integer> colors;
    private String Uniqe_signs;

    public Map<String, Integer> getColors() {
        return colors;
    }

    public void setColors(Map<String, Integer> colors) {
        this.colors = colors;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }



    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getPet_name() {
        return Pet_name;
    }

    public void setPet_name(String pet_name) {
        Pet_name = pet_name;
    }

    public String getPet_race() {
        return Pet_race;
    }

    public void setPet_race(String pet_race) {
        Pet_race = pet_race;
    }

    public int getPet_gender() {
        return Pet_gender;
    }

    public void setPet_gender(int pet_gender) {
        Pet_gender = pet_gender;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }




    public String getUniqe_signs() {
        return Uniqe_signs;
    }

    public void setUniqe_signs(String uniqe_signs) {
        Uniqe_signs = uniqe_signs;
    }

    @Override
    public String toString() {
        return "User{" +
                "Fname='" + Fname + '\'' +
                ", Lname='" + Lname + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Address='" + Address + '\'' +
                ", Gender=" + Gender +
                ", Uid='" + Uid + '\'' +
                ", Pet_name='" + Pet_name + '\'' +
                ", Pet_race='" + Pet_race + '\'' +
                ", Pet_gender=" + Pet_gender +
                ", Size=" + Size +
                ", colors=" + colors +
                ", Uniqe_signs='" + Uniqe_signs + '\'' +
                '}';
    }
}

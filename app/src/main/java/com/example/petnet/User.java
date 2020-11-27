package com.example.petnet;

import android.graphics.Bitmap;
import android.location.Address;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private String Fname;
    private String Lname;
    private String Email;
    private String Password;
    private List<Double> Address;
    private int Gender;
    private String Uid;
    Dog dog;

    public List<Double> getAddress() {
        return Address;
    }

    public void setAddress(List<Double> address) {
        Address = address;
    }


    public User(){

    }



    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
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
                '}';
    }



}

package com.example.petnet.Objects;

import com.example.petnet.Objects.Dog;

import java.util.List;

public class User {

    private String Fname;
    private String Lname;
    private String Email;
    private String Password;
    private int Gender;
    private String Uid;




    public User(){

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
                ", Gender=" + Gender +
                ", Uid='" + Uid + '\'' +
                '}';
    }



}

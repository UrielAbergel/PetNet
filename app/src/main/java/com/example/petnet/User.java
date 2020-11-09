package com.example.petnet;

import android.graphics.Bitmap;

public class User {

    private String fname;
    private String lname;
    private String email;
    private String password;
    private String address;
    private int gender;
    private Bitmap pet_pic;

    private String uid;
    private String pname;
    private String race;
    private int pgender;
    private int size; // split into categories 1-5
    private int[] colors; //need to check how many colors we accept and their values.
    private String uniqe_signs;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Bitmap getPet_pic() {
        return pet_pic;
    }

    public void setPet_pic(Bitmap pet_pic) {
        this.pet_pic = pet_pic;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getPgender() {
        return pgender;
    }

    public void setPgender(int pgender) {
        this.pgender = pgender;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public String getUniqe_signs() {
        return uniqe_signs;
    }

    public void setUniqe_signs(String uniqe_signs) {
        this.uniqe_signs = uniqe_signs;
    }
}

package com.example.petnet.Objects;

import android.net.Uri;
import android.widget.ImageView;

public class ItemModel {

    private Uri image;
    private String dog_name, gender, race, uniqe_signs,owner_name;
    String uid;

    public ItemModel() {
    }


    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getDog_name() {
        return dog_name;
    }

    public void setDog_name(String dog_name) {
        this.dog_name = dog_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getUniqe_signs() {
        return uniqe_signs;
    }

    public void setUniqe_signs(String uniqe_signs) {
        this.uniqe_signs = uniqe_signs;
    }

    public ItemModel(Uri image, String dog_name, String gender, String race, String uniqe_signs) {
        this.image = image;
        this.dog_name = dog_name;
        this.gender = gender;
        this.race = race;
        this.uniqe_signs = uniqe_signs;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "image=" + image +
                ", dog_name='" + dog_name + '\'' +
                ", gender='" + gender + '\'' +
                ", race='" + race + '\'' +
                ", uniqe_signs='" + uniqe_signs + '\'' +
                '}';
    }
}

package com.example.src.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dog {
    private String Pet_name;
    private String Pet_race;
    private int Pet_gender;
    private int Size; // split into categories 1-5
    private List<Integer> colors;
    private List<Double> Address = new ArrayList<>();
    private String Uniqe_signs;


    public List<Double> getAddress() {
        return Address;
    }

    public void setAddress(List<Double> address) {
        Address = address;
    }

    public HashMap toHashmap(){
        HashMap<String,Object> toReturn = new HashMap<>();
        toReturn.put("Pet_name",Pet_name);
        toReturn.put("Pet_race",Pet_race);
        toReturn.put("Pet_gender",Pet_gender);
        toReturn.put("Pet_size",Size);
        toReturn.put("Colors",colors);
        toReturn.put("Uniqe_signs",Uniqe_signs);
        return toReturn;
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

    public List<Integer> getColors() {
        return colors;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    public String getUniqe_signs() {
        return Uniqe_signs;
    }

    public void setUniqe_signs(String uniqe_signs) {
        Uniqe_signs = uniqe_signs;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "Pet_name='" + Pet_name + '\'' +
                ", Pet_race='" + Pet_race + '\'' +
                ", Pet_gender=" + Pet_gender +
                ", Size=" + Size +
                ", colors=" + colors +
                ", Uniqe_signs='" + Uniqe_signs + '\'' +
                '}';
    }



}

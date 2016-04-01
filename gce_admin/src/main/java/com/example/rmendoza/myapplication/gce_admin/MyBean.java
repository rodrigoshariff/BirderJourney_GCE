package com.example.rmendoza.myapplication.gce_admin;

import com.example.BirdArrayItem;

import java.util.ArrayList;

/** The object model for the data we are sending through endpoints */
public class MyBean {

//    private String myData;
//
//    public String getData() {
//        return myData;
//    }
//
//    public void setData(String data) {
//        myData = data;
//    }

    private ArrayList<BirdArrayItem> myData;

    public ArrayList<BirdArrayItem> getData() {
        return myData;
    }

    public void setData(ArrayList<BirdArrayItem> data) {
        myData = data;
    }



}
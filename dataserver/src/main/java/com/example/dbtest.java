package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by rmendoza on 3/3/2016.
 */
public class dbtest {

/*    public static void main(String[] args) throws ClassNotFoundException {

        final DBHelper_Java mydb = new DBHelper_Java();

        try {
            mydb.CreateDB();
            ArrayList results = mydb.getSearchedBirds("her");
            System.out.println("name = " + results);
*//*            Connection con = null;
            con = DriverManager.getConnection("jdbc:sqlite:BirdCards.db");
            Statement stmt = null;
            String query = "select * from BirdCard where BirdName like '%her%'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String birdname = rs.getString("BirdName");
                System.out.println("name = " + birdname);
            }*//*
        }

        catch (Exception e) {
            e.getMessage();
            //return null;
        }
    }*/

    public static void main(String[] args) {

        final SimpleArray mydb = new SimpleArray();

        try {
            //boolean isarrayempty = mydb.isArrayEmpty();
            ArrayList<BirdArrayItem> results = mydb.getSearchedBirds("blue heron");
            System.out.println("name = " + results.get(0).getFullName());
        }

        catch (Exception e) {
            e.getMessage();
            //return null;
        }
    }



}

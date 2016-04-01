/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.rmendoza.myapplication.gce_admin;

import com.example.BirdArrayItem;
import com.example.SimpleArray;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;


import java.util.ArrayList;


/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "gce_admin.myapplication.rmendoza.example.com",
    ownerName = "gce_admin.myapplication.rmendoza.example.com",
    packagePath=""
  )
)
public class MyEndpoint {

    private SimpleArray mydb = new SimpleArray();


    @ApiMethod(name = "searchDB")
    public MyBean searchDB(@Named("name") String searchstring) {

        MyBean response = new MyBean();

        try {
            ArrayList results = mydb.getSearchedBirds(searchstring);
            //response.setData(results.toString());
            response.setData(results);
        }
        catch (Exception e) {
            e.getMessage();
            //return null;
        }
        return response;
    }


/*    public MyBean loadDb(){
        MyBean response = new MyBean();
        try {
            DBHelper_Java mydb = new DBHelper_Java();
            mydb.CreateDB();

            response.setData("Success");
            return response;
        }
        catch (Exception e) {
            response.setData(e.getMessage());
            return response;
        }
    }*/

}

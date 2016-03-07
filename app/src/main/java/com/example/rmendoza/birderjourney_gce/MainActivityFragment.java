package com.example.rmendoza.birderjourney_gce;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.DBHelper_Java;
import com.example.rmendoza.myapplication.gce_admin.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    String searchText = "";
    ArrayList GCE_Search = new ArrayList();
    String GCE_String = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_main, container, false);

        //final DBHelper mydb = new DBHelper(getActivity());
        //final DBHelper_Java mydb = new DBHelper_Java();


        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final ListView listView = (ListView) rootView.findViewById(R.id.listView1);
        EditText editText = (EditText) rootView.findViewById(R.id.searchText);

        if (searchText.length() > 0) {
            editText.setText(searchText);
        };


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView editText, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    listView.setAdapter(null);
                    if (editText.getText() != "" && editText.getText() != null && editText.length() > 0) {

                        searchText = editText.getText().toString();
                        editText.clearFocus();

                        //ArrayList array_list = mydb.getAllBirds(searchText);
                        //try {
//                            mydb.CreateDB();
//                            ArrayList array_list = mydb.getSearchedBirds(searchText);

                            EndpointsAsyncTask searchDB = new EndpointsAsyncTask();
                            searchDB.execute(searchText);



//                            String testresults = GCE_String;
//
//                            GCE_Search = (ArrayList) Arrays.asList(GCE_String.split(","));
//
//                            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, GCE_Search);
//
//                            listView.setAdapter(arrayAdapter);

                            //hide keyboard
//                            InputMethodManager in = (InputMethodManager) getActivity().
//                                    getSystemService(Context.INPUT_METHOD_SERVICE);
//                            in.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                        //}
                        //catch(Exception e){
                         //   e.getMessage();
                        //};

                        return true;
                    } else {
                        //hide keyboard
                        InputMethodManager in = (InputMethodManager) getActivity().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                        Toast.makeText(getActivity(), "Please enter part of the name of the bird", Toast.LENGTH_SHORT).show();
                        listView.invalidateViews();
                    }
                }
                return false;
            }
        });


        return rootView;
    }


    private class EndpointsAsyncTask extends AsyncTask<String, Void, String> {
        private MyApi myApiService = null;
        //private Context context;

        @Override
        protected String doInBackground(String... params) {
            if(myApiService == null) {  // Only do this once

/*                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setRootUrl("http://192.168.1.209:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                            });*/


                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("https://birderjourney.appspot.com/_ah/api/");

                myApiService = builder.build();
            }

            //context = params[0].first;
            String searchstring = params[0];

            try {
                String ApiResults = myApiService.searchDB(searchstring).execute().getData();
                return ApiResults;
            } catch (Exception e) {
                Log.e("LOG_TAG", "Error ==========>: " + e.getMessage(), e);
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String ApiResults) {
            GCE_String = ApiResults;
        }
    }

}

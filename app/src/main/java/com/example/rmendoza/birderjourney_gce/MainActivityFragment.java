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
import java.util.List;

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
    List GCE_Search = new ArrayList();
    String GCE_String = null;
    private static MyApi myApiService1 = null;
    ListView listView1 ;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_main, container, false);

        //final DBHelper mydb = new DBHelper(getActivity());
        //final DBHelper_Java mydb = new DBHelper_Java();


        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView1 = (ListView) rootView.findViewById(R.id.listView1);
        EditText editText = (EditText) rootView.findViewById(R.id.searchText);

        if (searchText.length() > 0) {
            editText.setText(searchText);
        }
        ;


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView editText, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    GCE_Search.clear();
                    listView1.setAdapter(null);
                    if (editText.getText() != "" && editText.getText() != null && editText.length() > 0) {

                        searchText = editText.getText().toString();
                        editText.clearFocus();

                        new EndpointsAsyncTask1().execute(new Pair<Context, String>(getActivity(), searchText));


//                        EndpointsAsyncTask searchDB = new EndpointsAsyncTask();
//                        searchDB.execute(searchText);
//                        String testresults = "Great Blue Heron (Ardea herodias), Little Blue Heron (Egretta caerulea)";
//                        GCE_Search.addAll(Arrays.asList(GCE_String.split(", ")));
//
//                        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, GCE_Search);
//                        listView.setAdapter(arrayAdapter);

                        //hide keyboard
                            InputMethodManager in = (InputMethodManager) getActivity().
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                            in.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                        return true;
                    } else {
                        //hide keyboard
                        InputMethodManager in = (InputMethodManager) getActivity().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                        Toast.makeText(getActivity(), "Please enter part of the name of the bird", Toast.LENGTH_SHORT).show();
                        listView1.invalidateViews();
                    }


                }
                return false;
            }
        });


        return rootView;
    }


    private void refreshListView() {

        GCE_Search.addAll(Arrays.asList(GCE_String.split(", ")));
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, GCE_Search);
        listView1.setAdapter(arrayAdapter);

    }


    public class EndpointsAsyncTask1 extends AsyncTask<Pair<Context, String>, Void, String> {

        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if (myApiService1 == null) {  // Only do this once

                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://birderjourney.appspot.com/_ah/api/");

                myApiService1 = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;

            try {
                return myApiService1.searchDB(name).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
//            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            GCE_String = result;
            refreshListView();
        }
    }


}

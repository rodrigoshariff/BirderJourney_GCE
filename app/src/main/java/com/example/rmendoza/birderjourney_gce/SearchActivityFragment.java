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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.DBHelper_Java;
import com.example.BirdArrayItem;
import com.example.rmendoza.myapplication.gce_admin.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {

    String searchText = "";
    List GCE_Search = new ArrayList();
    List GCE_Search1 = new ArrayList();
    String GCE_String = null;
    private static MyApi myApiService1 = null;
    ListView searchResults;
    ImageAndTextArrayAdapter mSearchBirdAdapter;

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_main, container, false);

        //final DBHelper mydb = new DBHelper(getActivity());
        //final DBHelper_Java mydb = new DBHelper_Java();


        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        searchResults = (ListView) rootView.findViewById(R.id.searchResults);
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
                    searchResults.setAdapter(null);
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
                        searchResults.invalidateViews();
                    }


                }
                return false;
            }
        });


        return rootView;
    }


    private void refreshListView() {

        //GCE_Search.addAll(Arrays.asList(GCE_String.split(", ")));
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, GCE_Search1);
//        listView1.setAdapter(arrayAdapter);

        mSearchBirdAdapter = new ImageAndTextArrayAdapter(getActivity(),
                R.id.list_item_search_bird, GCE_Search);

        searchResults.setAdapter(mSearchBirdAdapter);


    }


    public class EndpointsAsyncTask1 extends AsyncTask<Pair<Context, String>, Void, List> {

        private Context context;
        private final String LOG_TAG = EndpointsAsyncTask1.class.getSimpleName();

        @Override
        protected List doInBackground(Pair<Context, String>... params) {
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
                //return e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List result) {
//            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            GCE_Search.clear();
            GCE_Search1.clear();

            JSONArray BirdList = new JSONArray(result);
            for (int i = 0; i < BirdList.length(); i++) {
                BirdArrayItem birdItem = new BirdArrayItem();
                try {
                    birdItem.setCommonName(BirdList.getJSONObject(i).get("commonName").toString());
                    birdItem.setScientificName(BirdList.getJSONObject(i).get("scientificName").toString());
                    birdItem.setFullName(BirdList.getJSONObject(i).get("fullName").toString());
                    birdItem.setFamily(BirdList.getJSONObject(i).get("family").toString());
                    birdItem.setOrder(BirdList.getJSONObject(i).get("order").toString());
                    birdItem.setNA_Occurrence(BirdList.getJSONObject(i).get("na_Occurrence").toString());
                    birdItem.setDescription(BirdList.getJSONObject(i).get("description").toString());
                    birdItem.setIUCN_Category2014(BirdList.getJSONObject(i).get("iucn_Category2014").toString());
                    birdItem.setImageID(BirdList.getJSONObject(i).get("imageID").toString());
                    birdItem.setImageFileName(BirdList.getJSONObject(i).get("imageFileName").toString());

                } catch (Exception e) {

                    Log.e(LOG_TAG, "Error ==========>: " + e.getMessage(), e);
                    //Toast.makeText(getActivity(), "Unable to connect", Toast.LENGTH_SHORT).show();
                }
                GCE_Search.add(birdItem);
                GCE_Search1.add(birdItem.getFullName());

            }

            refreshListView();
        }
    }


}

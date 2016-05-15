package com.example.rmendoza.birderjourney_gce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
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
import android.widget.AdapterView;
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

    OnBirdSelectedListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnBirdSelectedListener {
        //Called by MainFragment when a list item is selected
        public void onBirdSelected(BirdArrayItem birdItemSelected);
    }

    String searchText = "";
    List GCE_Search = new ArrayList();
    private static MyApi myApiService1 = null;
    ListView searchResults;
    ImageAndTextArrayAdapter mSearchBirdAdapter;
    private boolean mTwoPane;
    static View rootView;

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        searchResults = (ListView) rootView.findViewById(R.id.searchResults);
        EditText editText = (EditText) rootView.findViewById(R.id.searchText);
        editText.requestFocus();


        if (!(savedInstanceState == null)) {
            searchText = savedInstanceState.getString("searchText");
            editText.setText(searchText);
        }

        if (getActivity().findViewById(R.id.bird_detail_container) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView editText, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    GCE_Search.clear();
                    searchResults.setAdapter(null);
                    if (editText.getText() != "" && editText.getText() != null && editText.length() > 0) {

                        searchText = editText.getText().toString();
                        editText.clearFocus();

                        if (isNetworkAvailable() == false){
                            Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        new EndpointsAsyncTask1().execute(new Pair<Context, String>(getActivity(), searchText));

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

                        Toast.makeText(getActivity(), R.string.empty_searchbox, Toast.LENGTH_SHORT).show();
                        searchResults.invalidateViews();
                    }

                }
                return false;
            }
        });

        searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                 BirdArrayItem birdItemSelected = new BirdArrayItem();
                 birdItemSelected = (BirdArrayItem)GCE_Search.get(position);

                 mCallback.onBirdSelected(birdItemSelected);

             }

        });

        return rootView;
    }


    //save bird text once back from API
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("searchText", searchText);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnBirdSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBirdSelectedListener");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void refreshListView() {

        mSearchBirdAdapter = new ImageAndTextArrayAdapter(getActivity(),
                R.id.list_item_search_bird, GCE_Search);
        searchResults.setAdapter(mSearchBirdAdapter);
        if (mTwoPane) {
            searchResults.performItemClick(rootView, 0, searchResults.getItemIdAtPosition(0));
        }
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

            if (result == null || result.size() == 0) {
                Toast.makeText(getActivity(), R.string.search_fail, Toast.LENGTH_SHORT).show();

            } else {

                JSONArray BirdList = new JSONArray(result);
                for (int i = 0; i < BirdList.length(); i++) {
                    BirdArrayItem birdItem = new BirdArrayItem();
                    try {
                        birdItem.setSISRecID(BirdList.getJSONObject(i).get("sisrecID").toString());
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

                }

                refreshListView();

            }
        }
    }


}

package com.example.rmendoza.birderjourney_gce;

import android.app.Activity;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rmendoza.birderjourney_gce.data.ProviderContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class DistinctReportActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public DistinctReportActivityFragment() {
    }

    OnSpeciesSelectedListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnSpeciesSelectedListener {

        public void OnSpeciesSelected(String SpeciesCommonName, String period, boolean mTwoPane);
    }


    String period = "";
    private boolean mTwoPane = false;
    private ListView lstvwdistinctResults = null;
    private TextView titledistinctResults = null;
    public DistinctReportCursorAdapter distinctAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_distinct_report, container, false);
        lstvwdistinctResults = (ListView) rootView.findViewById(R.id.distinctResults);
        titledistinctResults = (TextView) rootView.findViewById(R.id.distincReportTitle);

        Bundle arguments = getArguments();
        if (arguments != null) {
            period = arguments.getString("Period");
            mTwoPane = arguments.getBoolean("mTwoPane");

            refreshTitle();
            distinctAdapter = new DistinctReportCursorAdapter(getActivity(), null);
            lstvwdistinctResults.setAdapter(distinctAdapter);

            getLoaderManager().initLoader(0, null, this);

        }

        lstvwdistinctResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor distinctCursor = distinctAdapter.getCursor();
                if (distinctCursor != null && distinctCursor.moveToPosition(position)) {
                    String speciesCommonName = distinctCursor.getString(0);
                    mCallback.OnSpeciesSelected(speciesCommonName, period, mTwoPane);
                }
            }
        });

        return rootView;
    }

    public void refreshTitle(){
        Cursor cursorSpeciesCount = getActivity().getContentResolver().query(
                ProviderContract.birds_table.CONTENT_URI,
                new String[]{"COUNT(distinct commonName) as speciesCount"},
                "datetime > ?",
                new String[]{Utilities.getBeginTime(period)},
                null
        );
        cursorSpeciesCount.moveToFirst();
        String speciesCount = cursorSpeciesCount.getString(cursorSpeciesCount.getColumnIndexOrThrow("speciesCount"));
        titledistinctResults.setText(Utilities.getPeriodName(period) + " (" + speciesCount + " Species)");
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                getActivity(),
                ProviderContract.birds_table.CONTENT_URI_GROUPBY,
                new String[]{"commonName as _id, COUNT(*) as birdCount"},
                "datetime > ?",
                new String[]{Utilities.getBeginTime(period)},
                "commonName ASC"
        );
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        distinctAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        distinctAdapter.swapCursor(null);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnSpeciesSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSpeciesSelectedListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
        refreshTitle();
    }
}

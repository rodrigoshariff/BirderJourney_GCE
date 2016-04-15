package com.example.rmendoza.birderjourney_gce;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rmendoza.birderjourney_gce.data.ProviderContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class DistinctReportActivityFragment extends Fragment {

    public DistinctReportActivityFragment() {
    }

    String period = "";
    private boolean mTwoPane = false;
    private ListView lstvwdistinctResults = null;
    private TextView titledistinctResults = null;


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

            Cursor cursorToday = getActivity().getContentResolver().query(
                    ProviderContract.birds_table.CONTENT_URI_GROUPBY,
                    //null, // leaving "columns" null just returns all the columns.
                    new String[]{"fullname, COUNT(*) as SpeciesCount"},
                    //new String[]{"fullname + \"(\" +COUNT(*) + \")\""},
                    null, // cols for "where" clause
                    null, // values for "where" clause
                    null  // sort order
            );

            String[] from = new String[] {"answer"};
            int[] to = new int[] {android.R.id.text1};

/*            SimpleCursorAdapter quickadapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    cursorToday, from, to);
            lstvwdistinctResults.setAdapter(quickadapter);*/


            Log.d("TAG", DatabaseUtils.dumpCursorToString(cursorToday));
        }
        return rootView;
    }
}

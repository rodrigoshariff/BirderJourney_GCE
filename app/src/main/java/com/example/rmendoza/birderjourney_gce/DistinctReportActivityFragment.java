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
                    new String[]{"commonName as _id, COUNT(*) as birdCount"},
                    null, //"datetime between ? and ?",
                    null,//new String[] {"04-01-2016 22:33:00", "04-17-2016 23:59:59"},
                    "commonName ASC"
            );
            Log.d("TAG", DatabaseUtils.dumpCursorToString(cursorToday));


            Cursor cursorAll = getActivity().getContentResolver().query(
                    ProviderContract.birds_table.CONTENT_URI,
                    null,
                    null, //"datetime between ? and ?",
                    null, //new String[] {"04-01-2016 22:33:00", "04-17-2016 23:59:59"},
                    "commonName ASC"
            );
            Log.d("TAG1", DatabaseUtils.dumpCursorToString(cursorAll));



            DistinctReportCursorAdapter distinctAdapter = new DistinctReportCursorAdapter(getActivity(), cursorToday);
            lstvwdistinctResults.setAdapter(distinctAdapter);
            titledistinctResults.setText(period);


        }
        return rootView;
    }
}

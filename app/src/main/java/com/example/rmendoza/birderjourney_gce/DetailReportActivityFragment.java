package com.example.rmendoza.birderjourney_gce;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rmendoza.birderjourney_gce.data.ProviderContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailReportActivityFragment extends Fragment {

    String period = "";
    String speciesCommonName ="";
    private boolean mTwoPane = false;
    private ListView lstvwdetailResults = null;
    private TextView titledetailResults = null;

    public DetailReportActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_detail_report, container, false);
        lstvwdetailResults = (ListView) rootView.findViewById(R.id.detailResults);
        titledetailResults = (TextView) rootView.findViewById(R.id.detailReportTitle);

        Bundle arguments = getArguments();
        if (arguments != null) {
            speciesCommonName = arguments.getString("speciesCommonName");
            period = arguments.getString("period");
            mTwoPane = arguments.getBoolean("mTwoPane");

            Cursor cursorTest = getActivity().getContentResolver().query(
                    ProviderContract.birds_table.CONTENT_URI,
                    null,
                    "datetime > ? AND commonName = ?",
                    new String[] {Utilities.getBeginTime(period), speciesCommonName},
                    "commonName ASC"
            );

            Log.d("TAG", DatabaseUtils.dumpCursorToString(cursorTest));

            DetailReportCursorAdapter distinctAdapter = new DetailReportCursorAdapter(getActivity(), cursorTest);
            lstvwdetailResults.setAdapter(distinctAdapter);
            titledetailResults.setText(speciesCommonName);

        }

        return rootView;
    }
}

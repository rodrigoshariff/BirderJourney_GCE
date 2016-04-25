package com.example.rmendoza.birderjourney_gce;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmendoza.birderjourney_gce.data.ProviderContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailReportActivityFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{

    String period = "";
    String speciesCommonName ="";
    private boolean mTwoPane = false;
    private ListView lstvwdetailResults = null;
    private TextView titledetailResults = null;
    public DetailReportCursorAdapter detailAdapter;

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

//            Cursor cursorTest = getActivity().getContentResolver().query(
//                    ProviderContract.birds_table.CONTENT_URI,
//                    null,
//                    "datetime > ? AND commonName = ?",
//                    new String[] {Utilities.getBeginTime(period), speciesCommonName},
//                    "commonName ASC"
//            );
//
//            Log.d("TAG", DatabaseUtils.dumpCursorToString(cursorTest));

            detailAdapter = new DetailReportCursorAdapter(getActivity(), null);
            lstvwdetailResults.setAdapter(detailAdapter);
            titledetailResults.setText(speciesCommonName);
            getLoaderManager().initLoader(0, null, this);

        }

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                getActivity(),
                ProviderContract.birds_table.CONTENT_URI,
                null,
                "datetime > ? AND commonName = ?",
                new String[] {Utilities.getBeginTime(period), speciesCommonName},
                "commonName ASC"
        );
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        detailAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        detailAdapter.swapCursor(null);
    }




}

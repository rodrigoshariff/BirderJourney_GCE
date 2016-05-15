package com.example.rmendoza.birderjourney_gce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class DistinctReportActivity extends AppCompatActivity implements  DistinctReportActivityFragment.OnSpeciesSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distinct_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDistinct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putString("Period", getIntent().getStringExtra("Period"));
            arguments.putString("mTwoPane", getIntent().getStringExtra("mTwoPane"));

            DistinctReportActivityFragment fragment = new DistinctReportActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_distinct_report, fragment)
                    .commit();
        }



/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void OnSpeciesSelected(String speciesCommonName, String period, boolean mTwoPane) {
        if (mTwoPane) {
//            Bundle args = new Bundle();
//            args.putStringArray("IdAndNameArray", idAndName);
//            args.putBoolean("mTwoPane",mTwoPane);
//
//            ArtistTopTenActivityFragment fragment = new ArtistTopTenActivityFragment();
//            fragment.setArguments(args);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.artist_top_ten_container, fragment)
//                    .commit();
        } else {

//            Context context = getApplicationContext();
//            Toast.makeText(context, "Going to detail report for" + speciesCommonName, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, DetailReportActivity.class);
            intent.putExtra("speciesCommonName", speciesCommonName);
            intent.putExtra("period", period);
            intent.putExtra("mTwoPane", mTwoPane);
            startActivity(intent);
        }
    }

}

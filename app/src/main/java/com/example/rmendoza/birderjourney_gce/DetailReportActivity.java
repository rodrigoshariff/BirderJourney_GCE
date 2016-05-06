package com.example.rmendoza.birderjourney_gce;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DetailReportActivity extends AppCompatActivity implements DetailReportActivityFragment.OnDeleteItemListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putString("speciesCommonName", getIntent().getStringExtra("speciesCommonName"));
            arguments.putString("period", getIntent().getStringExtra("period"));
            arguments.putString("mTwoPane", getIntent().getStringExtra("mTwoPane"));

            DetailReportActivityFragment fragment = new DetailReportActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail_report, fragment)
                    .commit();
        }

    }


    public void OnDeleteItem(String periodClicked) {

    }


}

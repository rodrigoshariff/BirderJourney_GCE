package com.example.rmendoza.birderjourney_gce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.BirdArrayItem;
import com.example.DBHelper_Java;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchActivityFragment.OnBirdSelectedListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


//        mydb = new DBHelper(this);
//        mydb.truncateBirdsTable();
//        if (mydb.getRecordCount() == 0)
//        {
//            LoadDataBirdsTable(mydb);
//        }

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

    public void onBirdSelected(BirdArrayItem birdItemSelected) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

//            Bundle args = new Bundle();
//            args.putStringArray("IdAndNameArray", birdItemSelected);
//            args.putBoolean("mTwoPane",mTwoPane);

//            ArtistTopTenActivityFragment fragment = new ArtistTopTenActivityFragment();
//            fragment.setArguments(args);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.artist_top_ten_container, fragment)
//                    .commit();
        } else {
            Intent intent = new Intent(this, BirdDetailActivity.class);

            intent.putExtra("commonName", birdItemSelected.getCommonName());
            intent.putExtra("scientificName", birdItemSelected.getScientificName());
            intent.putExtra("fullName", birdItemSelected.getFullName());
            intent.putExtra("family", birdItemSelected.getFamily());
            intent.putExtra("order", birdItemSelected.getOrder());
            intent.putExtra("na_Occurrence", birdItemSelected.getNA_Occurrence());
            intent.putExtra("description", birdItemSelected.getDescription());
            intent.putExtra("iucn_Category2014", birdItemSelected.getIUCN_Category2014());
            intent.putExtra("imageID", birdItemSelected.getImageID());
            intent.putExtra("imageFileName", birdItemSelected.getImageFileName());

            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.today){
            Context context = getApplicationContext();
            Toast.makeText(context, "Load summary report for today", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.week){
            Context context = getApplicationContext();
            Toast.makeText(context, "Load summary report for this week", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.month){
            Context context = getApplicationContext();
            Toast.makeText(context, "Load summary report for current month", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.year){
            Context context = getApplicationContext();
            Toast.makeText(context, "Load summary report for current year", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.ever){
            Context context = getApplicationContext();
            Toast.makeText(context, "Load summary report for ever", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}

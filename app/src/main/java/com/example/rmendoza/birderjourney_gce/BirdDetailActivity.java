package com.example.rmendoza.birderjourney_gce;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmendoza.birderjourney_gce.data.ProviderContract;
import com.squareup.picasso.Picasso;

public class BirdDetailActivity extends AppCompatActivity implements SaveDialogFragment.SaveDialogListener{

    String commonName = "";
    String scientificName = "";
    String family = "";
    String order = "";
    String iucn_Category2014 = "";
    String description = "";
    String ImageURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        final ImageView ViewBirdImage = (ImageView) findViewById(R.id.birdImage);
        final TextView ViewCommonName = (TextView) findViewById(R.id.txtCommonName);
        final TextView ViewScientificName = (TextView) findViewById(R.id.txtScientificName);
        final TextView ViewFamily = (TextView) findViewById(R.id.txtFamily);
        final TextView ViewOrder = (TextView) findViewById(R.id.txtOrder);
        final TextView ViewRedListCat = (TextView) findViewById(R.id.txtRedListCat);
        final TextView ViewDescription = (TextView) findViewById(R.id.txtDescription);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            commonName=intent.getStringExtra("commonName");
            scientificName=intent.getStringExtra("scientificName");
            family=intent.getStringExtra("family");
            order=intent.getStringExtra("order");
            iucn_Category2014=intent.getStringExtra("iucn_Category2014");
            description=intent.getStringExtra("description");
            if (intent.getStringExtra("imageID").equals("TBD"))
            {
                ImageURL = "http://vignette2.wikia.nocookie.net/legendmarielu/images/b/b4/No_image_available.jpg/revision/latest?cb=20130511180903";
            }
            else
            {
                ImageURL = "https://rodrigoshariff.smugmug.com/Bird/Birder-Journey/" + intent.getStringExtra("imageID") + "/0/L/" + intent.getStringExtra("imageFileName");
            }

/*          Bundle arguments = new Bundle();
            arguments.putString("commonName", getIntent().getStringExtra("commonName"));
            arguments.putString("scientificName", getIntent().getStringExtra("scientificName"));
            arguments.putString("fullName", getIntent().getStringExtra("fullName"));
            arguments.putString("family", getIntent().getStringExtra("family"));
            arguments.putString("order", getIntent().getStringExtra("order"));
            arguments.putString("na_Occurrence", getIntent().getStringExtra("na_Occurrence"));
            arguments.putString("description", getIntent().getStringExtra("description"));
            arguments.putString("iucn_Category2014", getIntent().getStringExtra("iucn_Category2014"));
            arguments.putString("imageID", getIntent().getStringExtra("imageID"));
            arguments.putString("imageFileName", getIntent().getStringExtra("imageFileName"));

            BirdDetailActivityFragment fragment = new BirdDetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.bird_detail_container, fragment)
                    .commit();*/
        }
        else
        {
            commonName = savedInstanceState.getString("commonName");
            scientificName = savedInstanceState.getString("scientificName");
            family = savedInstanceState.getString("family");
            order = savedInstanceState.getString("order");
            iucn_Category2014 = savedInstanceState.getString("iucn_Category2014");
            description = savedInstanceState.getString("description");
            ImageURL = savedInstanceState.getString("imageURL");
        }


        ViewCommonName.setText(commonName);
        collapsingToolbar.setTitle(commonName);
        ViewScientificName.setText(scientificName);
        ViewFamily.setText(family);
        ViewOrder.setText(order);
        ViewRedListCat.setText(iucn_Category2014);
        ViewDescription.setText(description);
        Picasso.with(this).load(ImageURL).into(ViewBirdImage);


        FloatingActionButton fabLog = (FloatingActionButton) findViewById(R.id.fabLog);
        if (fabLog != null) {
            fabLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment SaveDialogFragment = new SaveDialogFragment();
                    SaveDialogFragment.show(getSupportFragmentManager(), "SaveDialog");

    //                Context context = getApplicationContext();
    //                Toast.makeText(context, "Start dialog to log observation", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onDialogSaveClick(DialogFragment dialog, String note) {

        ContentValues values= new ContentValues();
        values.put(ProviderContract.birds_table.FULLNAME_COL, commonName +" (" + scientificName + ")");
        values.put(ProviderContract.birds_table.DATE_COL,"");
        values.put(ProviderContract.birds_table.TIME_COL,"");
        values.put(ProviderContract.birds_table.LOCATION_COL,"");
        values.put(ProviderContract.birds_table.LAT_COL,"");
        values.put(ProviderContract.birds_table.LONG_COL,"");
        values.put(ProviderContract.birds_table.NOTE_COL, note);
        getContentResolver().insert(ProviderContract.birds_table.CONTENT_URI,values);
        Toast.makeText(this, "Save button clicked and note: " + note, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("commonName", commonName);
        state.putString("scientificName", scientificName);
        state.putString("family", family);
        state.putString("order", order);
        state.putString("description", description);
        state.putString("iucn_Category2014", iucn_Category2014);
        state.putString("imageURL", ImageURL);
    }


}

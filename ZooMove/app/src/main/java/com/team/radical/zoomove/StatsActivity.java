package com.team.radical.zoomove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by kempm on 8/10/2016.
 */
public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);


    }

    public void chooseButton(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    public void backButton(View view)
    {
        Intent intent = new Intent(this, CharSelectActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    public void editCharacterName(View view)
    {
        // REPLACE CHARACTERNAME TEXT VIEW WITH EDIT TEXT
        // SET EDIT TEXT TO SHOW CHARACTER NAME
        // HIGHLIGHT THAT NAME
        // LET USER CHANGE THE NAME
    }
}

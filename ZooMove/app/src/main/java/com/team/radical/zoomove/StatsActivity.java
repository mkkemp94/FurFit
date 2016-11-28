package com.team.radical.zoomove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.team.radical.zoomove.CharSelectActivity.allCharacters;

/**
 * Created by kempm on 8/10/2016.
 */
public class StatsActivity extends AppCompatActivity {

    EditText editText;
    ImageView imageView;
    Character thisCharacter;

    TextView generalTime;
    TextView runningTime;
    TextView strengthTime;
    TextView totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        // Gets the character to display on this page
        getCharacter();

        // Displays that characters image
        try {
            imageView = (ImageView) findViewById(R.id.iv_character_image);
            imageView.setImageResource(thisCharacter.getImageResource());
        } catch (Exception ex) {
            Toast.makeText(this, "No image found.", Toast.LENGTH_LONG).show();
        }

        // Displays that character's name
        editText = (EditText) findViewById(R.id.et_character_name);
        editText.setText(thisCharacter.getName());

        // Displays general time
        generalTime = (TextView) findViewById(R.id.tv_general_time);
        generalTime.setText( String.valueOf(thisCharacter.getGeneralTime()) );

        // Displays general time
        runningTime = (TextView) findViewById(R.id.tv_running_time);
        runningTime.setText( String.valueOf(thisCharacter.getRunningTime()) );

        // Displays general time
        strengthTime = (TextView) findViewById(R.id.tv_strength_time);
        strengthTime.setText( String.valueOf(thisCharacter.getStrengthTime()) );

        // Displays general time
        totalTime = (TextView) findViewById(R.id.tv_total_time);
        totalTime.setText( String.valueOf(thisCharacter.getTotalTime()) );

    }

    /**
     * Gets selected character to display info
     * Only one character should have isSelected set to true.
     */
    private void getCharacter() {
        for (Character ch : allCharacters) {
            if (ch.getIsSelected()) {
                Toast.makeText(this, "Character " + ch.getName() + " is selected.", Toast.LENGTH_LONG).show();
                thisCharacter = ch;
            }
        }
    }

    public void chooseButton(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);

        for (Character ch : allCharacters) {
            if (ch.getIsSelected()) {
                MainActivity.currentCharacter = ch;
            }
        }

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

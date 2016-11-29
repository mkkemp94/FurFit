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
        loadCharacterImage();

        // Display character name and times
        loadStats();

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

    /**
     * Load the icon for the character being checked out
     */
    private void loadCharacterImage() {
        try {
            imageView = (ImageView) findViewById(R.id.iv_character_image);
            imageView.setImageResource(thisCharacter.getImageResource());
        } catch (Exception ex) {
            Toast.makeText(this, "No image found.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Edit character nickname
     * @param view
     */
    public void editCharacterName(View view)
    {
        // REPLACE CHARACTERNAME TEXT VIEW WITH EDIT TEXT
        // SET EDIT TEXT TO SHOW CHARACTER NAME
        // HIGHLIGHT THAT NAME
        // LET USER CHANGE THE NAME

        thisCharacter.setName(editText.getText().toString());
        Toast.makeText(this, "New name saved!", Toast.LENGTH_SHORT).show();

//        TextView charLv = (TextView) findViewById(R.id.tv_character_level);
//        charLv.setText(characterName);
    }

    /**
     * Loads this character's nickname and exercise times
     */
    private void loadStats() {

        // Displays this character's name
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
     * Set this character as currentCharacter and go back to MainActivity
     * @param view
     */
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

    /**
     * Go back to Character Select
     * @param view
     */
    public void backButton(View view)
    {
        Intent intent = new Intent(this, CharSelectActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);

        startActivity(intent);
    }

    /**
     * Back button press: Do nothing
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CharSelectActivity.class);
        startActivity(intent);
    }
}

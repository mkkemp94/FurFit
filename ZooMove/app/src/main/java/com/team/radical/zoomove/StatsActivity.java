package com.team.radical.zoomove;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static android.util.Log.v;
import static com.team.radical.zoomove.MainActivity.allCharacters;
import static com.team.radical.zoomove.MainActivity.keepPlayingMusic;
import static com.team.radical.zoomove.MainActivity.mMediaPlayer;


/**
 * Activity displays stats of the character selected in char select activity.
 * Clicking back goes back to the character select activity.
 * Clicking choose goes back to the main menu and saves the current character as selected.
 * Created by kempm on 8/10/2016.
 */
public class StatsActivity extends AppCompatActivity {

    // Edit Text for setting the character's name
    EditText editText;

    // Image View for displaying the current selected character
    ImageView imageView;

    // The current character that is selected and displayed
    Character thisCharacter;

    // Text View to show this character's total general time
    TextView generalTime;

    // Text View to show this character's total running time
    TextView runningTime;

    // Text View to show this character's total strength training time
    TextView strengthTime;

    // Text View to show this character's total time exercising (general + running + strength)
    TextView totalTime;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * ON CREATE
     * Gets the current selected character and assigns it to thisCharacter.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Gets the character to display on this page
        getThisCharacter();
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * Gets selected character to display image and info
     * Only one character should have isSelected set to true.
     * @param
     */
    private void getThisCharacter()
    {
        for (Character ch : allCharacters) {
            if (ch.getIsSelected()) {
                thisCharacter = ch;
            }
        }

        // Displays that characters image
        loadCharacterImage();

        // Display character name and times
        loadStats();
    }

    /**
     * Loads the icon for thisCharacter.
     * @param
     */
    private void loadCharacterImage()
    {
        try {
            imageView = (ImageView) findViewById(R.id.iv_character_image);
            imageView.setImageResource(thisCharacter.getImageResource());
        } catch (Exception ex) {
            Toast.makeText(this, "No image found.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Loads this character's nickname and exercise times.
     * @param
     */
    private void loadStats()
    {
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
        strengthTime.setText( String.valueOf(thisCharacter.getmStrengthTime()) );

        // Displays general time
        totalTime = (TextView) findViewById(R.id.tv_total_time);
        totalTime.setText( String.valueOf(thisCharacter.getTotalTime()) );
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * Save allCharacters in their current state.
     */
    public void saveCharacters()
    {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("CF", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(allCharacters);
            //os.writeObject(currentCharacter);
            os.close();
            fos.close();
            Toast.makeText(this, "Characters saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            v("chars", e.getMessage());
        }
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * Edit character nickname on "Save" button tap.
     * @param view
     */
    public void editCharacterName(View view)
    {
        thisCharacter.setName(editText.getText().toString());
        Toast.makeText(this, "New name saved!", Toast.LENGTH_SHORT).show();
        saveCharacters();
    }

    /**
     * Go back to Character Select.
     * Keep the music playing.
     * @param view
     */
    public void backButton(View view)
    {
        keepPlayingMusic = true;
        Intent intent = new Intent(this, CharSelectActivity.class);
        startActivity(intent);
    }

    /**
     * Save this character and go back to MainActivity.
     * Keep the music playing.
     * @param view
     */
    public void chooseButton(View view)
    {
        saveCharacters();
        keepPlayingMusic = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * If character select is being loaded, keepPlayingMusic will be set to true.
     * Set the variable to false and keep playing the music.
     * If the app is being minimized, keepPlayingMusic will be set to false.
     * In this case, pause the music.
     * @param
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (keepPlayingMusic) {
            // If switching activities, this will be true. Set back to false.
            keepPlayingMusic = false;
        } else {
            // If already false, we're not switching activities. We're leaving the app.
            mMediaPlayer.pause();
        }
    }

    /**
     * Start the media player back up when coming back from paused state.
     * @param
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (!mMediaPlayer.isPlaying()) { mMediaPlayer.start(); }
    }

    /**
     * Back button press:
     * Go back to character select.
     * Set keepMusicPlaying to true so the music doesn't stop.
     */
    @Override
    public void onBackPressed()
    {
        keepPlayingMusic = true;
        Intent intent = new Intent(this, CharSelectActivity.class);
        startActivity(intent);
    }
}
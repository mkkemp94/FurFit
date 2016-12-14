package com.team.radical.zoomove;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.Arrays;
import java.util.List;

import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
import static android.util.Log.v;

/**
 * Main menu for the app.
 */
public class MainActivity extends AppCompatActivity {

    // A list of all nine characters, created once and saved to a file
    public static List<Character> allCharacters;

    // Flag to keep music going or not
    public static boolean keepPlayingMusic = false;

    // Settings for the app. Used currently for checking whether app is loaded for the first time
    private SharedPreferences sharedPreferences;
    private String PREFS_NAME = "MyPrefsFile";

    // Media player to handle background music
    public static MediaPlayer mMediaPlayer;

    // Audio manager to handle audio focus
    private static AudioManager mAudioManager;

    // Listen for audio focus changes
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AUDIOFOCUS_LOSS ||
                    focusChange == AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback
                mMediaPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                Toast.makeText(MainActivity.this, "In AUTOFOCUS_GAIN", Toast.LENGTH_SHORT);
                // Resume playback
                mMediaPlayer.start();
            }
        }
    };

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * ON CREATE
     * Requests audio service.
     * Set up shared preference settings.
     * Load characters from internal file.
     * Start playing background music.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request audio service
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Preferences for app, with the constant name PREFS_NAME
        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);

        // Load all characters and display current character
        getCharacters();

        // Start playing the background music (or continue if already playing)
        playMusic();
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * If this is first load from installation, create base characters
     * Otherwise, load the preexisting characters
     * @param
     */
    private void getCharacters() {

        if (sharedPreferences.getBoolean("my_first_time", true)) {
            Toast.makeText(this, "Welcome to FurFit!", Toast.LENGTH_SHORT).show();
            onFirstEverLoad();
        } else {
            Toast.makeText(this, "Loading existing characters...", Toast.LENGTH_SHORT).show();
            loadCharacters();
        }
    }

    /**
     * When app is started for the first time, create the base characters.
     * @param
     */
    private void onFirstEverLoad() {

        // Record the fact that the app has been started at least once
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("my_first_time", false);

        // Build initial characters
        createBaseCharacters();

        // Select the first character by default
        allCharacters.get(0).select();

        // Save these new characters
        saveCharacters();

        // Load the characters (and display the one marked as selected)
        loadCharacters();

        // Commit changes to preferences editor
        editor.apply(); // was commit. will apply cause errors?
    }

    /**
     * Create base characters and put them in a static characters list.
     * @param
     */
    private void createBaseCharacters() {

        Character char1 = new Character(1, "Beedrill", R.drawable.beedrill);
        Character char2 = new Character(2, "Charizard", R.drawable.charizardy);
        Character char3 = new Character(3, "Delphox", R.drawable.delphox);
        Character char4 = new Character(4, "Flareon", R.drawable.flareon);
        Character char5 = new Character(5, "Jolteon", R.drawable.jolteon);
        Character char6 = new Character(6, "Squirtle", R.drawable.squirtle);
        Character char7 = new Character(7, "Mareep", R.drawable.mareep);
        Character char8 = new Character(8, "Pidgeot", R.drawable.pidgeot);
        Character char9 = new Character(9, "Staryu", R.drawable.staryu);

        // Put characters in a list
        allCharacters = Arrays.asList
                (char1, char2, char3, char4, char5, char6, char7, char8, char9);
    }

    /**
     * Save all characters in their current state.
     * @param
     */
    private void saveCharacters() {

        FileOutputStream fos = null;
        try {
            fos = openFileOutput("CF", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(allCharacters);
            os.close();
            fos.close();
            //Toast.makeText(this, "Characters saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("MainActivity", e.getMessage());
        }
    }

    /**
     * Load all characters and display current selected character.
     * @param
     */
    private void loadCharacters() {

        FileInputStream fis = null;
        try {
            fis = openFileInput("CF");
            ObjectInputStream in = new ObjectInputStream(fis);
            allCharacters = (List<Character>) in.readObject();
            in.close();
            fis.close();
            //Toast.makeText(this, "Characters loaded!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            v("chars", e.getMessage());;
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Shows the selected character on the main page
        displaySelectedCharacter();
    }

    /**
     * Loads the selected character into the image view on title page.
     * @return
     */
    private void displaySelectedCharacter() {

        // Look through all characters and find the ONE that is selected
        for (Character ch : allCharacters) {
            if (ch.getIsSelected()) {
                ImageView currentCharacterImageView = (ImageView) findViewById(R.id.iv_current_character);
                currentCharacterImageView.setImageResource(ch.getImageResource());
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * Start playing the background music if it is not already playing.
     * Make sure the music stops when the focus is changed to another activity/System service.
     * @param
     */
    private void playMusic() {

        // Media player hasn't been created yet (application just loaded)
        if (mMediaPlayer == null) {

            // Request audio focus for playback
            int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                    // Use the music stream
                    AudioManager.STREAM_MUSIC,
                    // Request focus
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);

            // If we get focus
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                // Free current media player
                releaseMediaPlayer();

                // Start up a new one
                mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_menu);

                // Play it
                mMediaPlayer.start();

                // We want it to loop
                mMediaPlayer.setLooping(true);
            }
        }
    }

    /**
     * Clean up the media player by releasing its resources.
     * @param
     */
    public void releaseMediaPlayer() {

        // If the media player is not null, then it may be currently playing a sound
        if (mMediaPlayer != null) {

            // Release it's resources as we no longer need them
            mMediaPlayer.release();

            // Set the media player back to null
            mMediaPlayer = null;

            // Abandon audio focus
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * Go to general exercise.
     * @param view
     */
    public void generalSwitch(View view) {

        Intent intent = new Intent(this, RunActivity.class);
        startActivity(intent);
    }

    /**
     * Go to muscle exercise.
     * @param view
     */
    public void muscleSwitch(View view) {

        Intent intent = new Intent(this, MuscleActivity.class);
        startActivity(intent);
    }

    /**
     * Go to running exercise.
     * @param view
     */
    public void runSwitch(View view) {

        Intent intent = new Intent(this, GeneralActivity.class);
        startActivity(intent);
    }

    /**
     * Go to the Character Select screen.
     * Keep playing the background music!
     * @param view
     */
    public void charSwitch(View view) {

        keepPlayingMusic = true;
        Intent intent = new Intent(this, CharSelectActivity.class);
        startActivity(intent);
    }

    /**
     * Go to extras page.
     * @param view
     */
    public void extrasSwitch(View view) {

        Intent intent = new Intent(this, ExtrasActivity.class);
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
     * Minimize app. The Android system will kill it automatically when necessary.
     * This also causes onPause() and stops the background music.
     * @param
     */
    @Override
    public void onBackPressed() {

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}





//    /**
//     * Find the character that is selected and set it to currentCharacter
//     */
//    private void setCurrentCharacter()
//    {
////        Log.v("selected", "Setting current character");
////        for (Character ch : allCharacters) {
////            if (ch.getIsSelected()) {
////                currentCharacter = ch;
////            }
////        }
////
////        if (currentCharacter == null) {
////            Log.v("selected", "None selected. Defaulting to beedrill.");
////            currentCharacter = allCharacters.get(0);
////            allCharacters.get(0).select();
////        }
////
////        Log.v("selected", currentCharacter.getName());
//
////        for (Character c : allCharacters) {
////            if (c.getImageResource() == currentCharacter.getImageResource()) {
////                //Log.v("selected", c.getName() + " is selected!");
////                c.select();
////            }
////        }
//
//        //howManyAreSelected();
//
//        currentCharacterImageView = (ImageView) findViewById(R.id.iv_current_character);
//        currentCharacterImageView.setImageResource(currentCharacter.getImageResource());
//    }







//    /**
//     * Check how many character objects are selected
//     */
//    public static void howManyAreSelected() {
//        int selected = 0;
//        for (Character c : allCharacters) {
//            if (c.getIsSelected()) {
//                v("selected", c.getName() + " is selected!");
//                selected++;
//            }
//        }
//        v("selected", "# Selected: " + selected);
//    }
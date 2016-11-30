package com.team.radical.zoomove;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.team.radical.zoomove.CharSelectActivity.deselectAllCharacters;

/**
 * Main menu for app
 *
 */
public class MainActivity extends AppCompatActivity {

    public static Character currentCharacter = new Character(1, "Beedrill", R.drawable.beedrill);
    public static List<Character> allCharacters;
    private SharedPreferences settings;
    private String PREFS_NAME = "MyPrefsFile";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Preferences to save in app
        settings = getSharedPreferences(PREFS_NAME, 0);

        // If this is first load of app, create characters
        if (settings.getBoolean("my_first_time", true)) {
            Toast.makeText(this, "First time!", Toast.LENGTH_SHORT).show();
            onFirstLoad();
        } else {
            // Else load existing ones
            loadCharacters();
        }

        // Find the character that is selected and set it to currentCharacter
        for (Character ch : allCharacters) {
            if (ch.getIsSelected()) {
                currentCharacter = ch;
            }
        }

        // Deselect all characters from char select
        deselectAllCharacters();

        // Show the current character image
        loadCurrentCharacter();

        Log.v("allChars", allCharacters.toString());
    }

    /**
     * When app is started for the first time, load new characters.
     * Then save them so that they can always be accessed.
     */
    private void onFirstLoad()
    {
        Toast.makeText(this, "Welcome to FurFit!", Toast.LENGTH_SHORT).show();

        // Record the fact that the app has been started at least once
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("my_first_time", false);

        // Make initial characters
        Character char1 = new Character(1, "Beedrill", R.drawable.beedrill);
        Character char2 = new Character(2, "Charizard", R.drawable.charizardy);
        Character char3 = new Character(3, "Delphox", R.drawable.delphox);
        Character char4 = new Character(4, "Flareon", R.drawable.flareon);
        Character char5 = new Character(5, "Jolteon", R.drawable.jolteon);
        Character char6 = new Character(6, "Squirtle", R.drawable.squirtle);
        Character char7 = new Character(7, "Mareep", R.drawable.mareep);
        Character char8 = new Character(8, "Pidgeot", R.drawable.pidgeot);
        Character char9 = new Character(9, "Staryu", R.drawable.staryu);

        // Put them in a list
        allCharacters = Arrays.asList
                (char1, char2, char3, char4, char5, char6, char7, char8, char9);

        // Save these new characters so they're always accessible
        saveCharacters();

        // Commit these changes
        editor.commit();
    }

    /**
     * Save Character objects in current state
     */
    private void saveCharacters()
    {
        FileOutputStream fos;
        try {
            fos = openFileOutput("CF", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(allCharacters);
            os.close();
            fos.close();
            Toast.makeText(this, "New characters created!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("chars", e.getMessage());
        }
    }

    /**
     * Load Character objects
     */
    private void loadCharacters()
    {
        allCharacters = new ArrayList<>();
        FileInputStream fis;
        try {
            fis = openFileInput("CF");
            ObjectInputStream in = new ObjectInputStream(fis);
            allCharacters = (List<Character>) in.readObject();
            in.close();
            fis.close();
            Toast.makeText(this, "Existing characters loaded!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.v("chars", e.getMessage());;
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the current character onto the main screen
     *
     */
    private void loadCurrentCharacter() {
        imageView = (ImageView) findViewById(R.id.iv_current_character);
        imageView.setImageResource(currentCharacter.getImageResource());
    }

    public void generalSwitch(View view){
        Intent intent = new Intent(this, RunActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    public void muscleSwitch(View view){
        Intent intent = new Intent(this, MuscleActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    public void runSwitch(View view){
        Intent intent = new Intent(this, GeneralActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    public void extrasSwitch(View view){
        Intent intent = new Intent(this, ExtrasActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Go to the Character Select screen
     *
     */
    public void charSwitch(View view){
        Intent intent = new Intent(this, CharSelectActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Back button press:
     * Minimize app. The Android system will kill it automatically when necessary.
     * (Many apps work this way.)
     *
     */
    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    /**
     * On application pause or close, save the current character.
     */
//    @Override
//    protected void onPause()
//    {
//        saveCharacters();
//    }
}

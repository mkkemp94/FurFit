package com.team.radical.zoomove;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import static android.util.Log.v;

/**
 * Main menu for app
 *
 */
public class MainActivity extends AppCompatActivity {

    public static Character currentCharacter; // = new Character(1, "Beedrill", R.drawable.beedrill);
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
        getCharacters();
    }

    /**
     * If this is first load from installation, create base characters
     * Else load what we already have
     */
    private void getCharacters()
    {
        if (settings.getBoolean("my_first_time", true)) {
            Toast.makeText(this, "This is your first time here!", Toast.LENGTH_SHORT).show();
            onFirstLoad();
        } else {
            Toast.makeText(this, "Loading existing characters...", Toast.LENGTH_SHORT).show();
            loadCharacters();
        }
    }

    /**
     * When app is started for the first time, create new characters.
     */
    private void onFirstLoad()
    {
        Toast.makeText(this, "Welcome to FurFit!", Toast.LENGTH_SHORT).show();

        // Record the fact that the app has been started at least once
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("my_first_time", false);

        // Build initial characters
        createBaseCharacters();

        // Select the first character by default
        allCharacters.get(0).select();

        // Save these new characters
        saveCharacters();

        // Commit these changes
        editor.commit();
    }

    /**
     * Create initial characters and put them in a list
     */
    private void createBaseCharacters()
    {
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
     * Save the allCharacters object in current state
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

    /**
     * Load character objects and display current select character
     */
    private void loadCharacters()
    {
        //allCharacters = new ArrayList<>();
        FileInputStream fis = null;
        try {
            fis = openFileInput("CF");
            ObjectInputStream in = new ObjectInputStream(fis);
            allCharacters = (List<Character>) in.readObject();
            //currentCharacter = (Character) in.readObject();
            in.close();
            fis.close();
            Toast.makeText(this, "Characters loaded!", Toast.LENGTH_SHORT).show();
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

        for (Character ch : allCharacters) {
            if (ch.getIsSelected()) {
                imageView = (ImageView) findViewById(R.id.iv_current_character);
                imageView.setImageResource(ch.getImageResource());
            }
        }
    }

    public void generalSwitch(View view)
    {
        Intent intent = new Intent(this, RunActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    public void muscleSwitch(View view)
    {
        Intent intent = new Intent(this, MuscleActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    public void runSwitch(View view)
    {
        Intent intent = new Intent(this, GeneralActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    public void extrasSwitch(View view)
    {
        Intent intent = new Intent(this, ExtrasActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Go to the Character Select screen
     *
     */
    public void charSwitch(View view)
    {
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
    public void onBackPressed()
    {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
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
//        imageView = (ImageView) findViewById(R.id.iv_current_character);
//        imageView.setImageResource(currentCharacter.getImageResource());
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






// Loads whichever character is set as current
//loadCharacters();

// Deselect all characters from char select
// deselectAllCharacters();

// Show the current character image
//setCurrentCharacter();



// For debugging
//howManyAreSelected();









/**
 * On application pause or close, save the current character.
 */
//    @Override
//    protected void onPause()
//    {
//        saveCharacters();
//    }
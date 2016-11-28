package com.team.radical.zoomove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import static com.team.radical.zoomove.CharSelectActivity.deselectAllCharacters;

public class MainActivity extends AppCompatActivity {

    public static Character currentCharacter = new Character("Beedrill", R.drawable.beedrill);
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Deselect all characters from char select
        deselectAllCharacters();

        // Show the current character image
        loadCurrentCharacter();


    }

    /**
     * Loads the current character onto the main screen
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

    public void charSwitch(View view){
        Intent intent = new Intent(this, CharSelectActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}

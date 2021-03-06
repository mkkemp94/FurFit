package com.team.radical.zoomove;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
import static android.util.Log.v;
import static com.team.radical.zoomove.MainActivity.allCharacters;
import static com.team.radical.zoomove.MainActivity.keepPlayingMusic;
import static com.team.radical.zoomove.MainActivity.mBackgroundMediaPlayer;

/**
 * This activity holds a grid to display all characters.
 * Tapping a character selects it and deselects all others.
 * Clicking back goes back to the main menu without saving this character as selected.
 * Clicking the choose button goes back to the main menu and saves this character as selected.
 * Clicking the stats button goes to a stats page that displays this character's info.
 */
public class CharSelectActivity extends AppCompatActivity {

    // Adapter for displaying characters in a grid view
    private CharSelectAdapter charSelectAdapter;

    // The media player to character cries
    private MediaPlayer mCryMediaPlayer;

    // Audio manager
    AudioManager mCryAudioManager;

    // This listens for when the media finishes playing and calls a method to release resources
    private MediaPlayer.OnCompletionListener mCompletionListener =
            new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    // Listen for audio focus changes
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback
                mCryMediaPlayer.pause();
                mCryMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) {
                // Resume playback
                mCryMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback
                releaseMediaPlayer();
            }
        }
    };

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * ON CREATE
     * Puts a grid view on the character select screen holding all selectable characters.
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        // Request audio service
        mCryAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Load grid of characters with default selected
        loadCharacterSelectGrid();
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * Load grid of characters and set adapter for data population.
     * @param
     */
    private void loadCharacterSelectGrid()
    {
        GridView gridview = (GridView) findViewById(R.id.gridView);
        charSelectAdapter = new CharSelectAdapter(this, allCharacters);
        gridview.setAdapter(charSelectAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // On character tap, select that character
                selectTappedCharacter(position);
            }
        });
    }

    /**
     * Select the character at the tapped position.
     * Only one character should be selected at a time.
     * @param position of tapped character
     */
    private void selectTappedCharacter(int position)
    {
        // Deselect all
        deselectAllCharacters();

        // Select this character that was tapped
        Character thisCharacter = allCharacters.get(position);
        thisCharacter.select();

        // Play character cry
        playCry(thisCharacter);

        // Redraw grid
        charSelectAdapter.notifyDataSetChanged();
    }

    /**
     * Sets all characters to be deselected.
     * @param
     */
    private void deselectAllCharacters()
    {
        for (Character ch : allCharacters) {
            ch.deselect();
        }
    }

    /**
     * Play this particular character's cry
     * @param thisCharacter is the character that was clicked
     */
    private void playCry(Character thisCharacter) {
        // Request audio focus for playback
        int result = mCryAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // We have audio focus now!

            // Free media player just to make sure
            releaseMediaPlayer();

            // Play its cry on tap
            mCryMediaPlayer =
                    MediaPlayer.create(CharSelectActivity.this, thisCharacter.getCreResource());
            mCryMediaPlayer.start();

            // Listen for when audio is finished, and then release media player
            mCryMediaPlayer.setOnCompletionListener(mCompletionListener);
        }
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mCryMediaPlayer != null) {

            // Release its resources because we no longer need it.
            mCryMediaPlayer.release();

            // Set the media player back to null. Makes it so the media player
            // is not configured to play an audio file at the moment.
            mCryMediaPlayer = null;

            // Abandon audio focus
            mCryAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * Save allCharacters in their in current state.
     */
    private void saveCharacters()
    {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("CF", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(allCharacters);
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
     * Go back to main menu without changing currentCharacter.
     * Keep the music playing.
     * @param view
     */
    public void backButton(View view)
    {
        keepPlayingMusic = true;
        Intent backIntent = new Intent(this, MainActivity.class);
        startActivity(backIntent);

    }

    /**
     * Go back to the main menu, saving the current tapped character as selected.
     * Keep the music playing.
     * @param
     */
    public void chooseButton(View view)
    {
        saveCharacters();
        keepPlayingMusic = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Go to the selected character's stats page.
     * Keep the music playing.
     * @param view
     */
    public void statsButton(View view)
    {
        keepPlayingMusic = true;
        Intent intent = new Intent(this, StatsActivity.class);
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
            mBackgroundMediaPlayer.pause();
        }

        // Stop playing sound!
        releaseMediaPlayer();
    }

    /**
     * Start the media player back up when coming back from paused state.
     * @param
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (!mBackgroundMediaPlayer.isPlaying()) { mBackgroundMediaPlayer.start(); }
    }

    /**
     * Back button press:
     * Go back to the main menu.
     * Set keepMusicPlaying to true so the music doesn't stop.
     * @param
     */
    @Override
    public void onBackPressed()
    {
        keepPlayingMusic = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}










// Find the character that is selected and set it to currentCharacter
//        for (Character ch : allCharacters) {
//            if (ch.getIsSelected()) {
//                currentCharacter = ch;
//            }
//        }






















//    /**
//     * Check to see if there is a currently selected character.
//     * If not, select the currentCharacter.
//     */
//    private void getCharacter()
//    {
////        for (Character ch : allCharacters) {
////            if (ch.getIsSelected()) {
////                return;
////            }
////        }
//
//
//        // There is no selected character
//        //selectCurrentCharacter();
//    }
//
//    /**
//     * Select the currentCharacter from MainActivity.
//     *
//     */
//    private void selectCurrentCharacter()
//    {
//        for (Character ch : allCharacters) {
//            if (ch.getImageResource() == currentCharacter.getImageResource()) {
//                ch.select();
//                break;
//            }
//        }
//    }


























//        for (Character ch : allCharacters) {
//            if (ch.getIsSelected()) {
//                if (ch.getImageResource() == currentCharacter.getImageResource()) {
//                    // If currentCharacter is already selected
//                    startActivity(backIntent);
//                } else {
//
//                    // Trying to go back without saving character
//                    AlertDialog.Builder builder = new AlertDialog.Builder(CharSelectActivity.this);
//                    builder.setMessage("Current selection will not be saved.")
//                            .setTitle("Cancel character select?")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Go back to main activity with no changes
//                                    startActivity(backIntent);
//                                }
//                            })
//                            .setNegativeButton("Keep Choosing", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Stay on character select screen
//                                    dialog.cancel();
//                                }
//                            });
//
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//                }
//            }
//        }




// ----------------------------------
//    /**
//     * Class for drawing character select grid
//     */
//    class CharSelectAdapter extends BaseAdapter {
//
//        private Context mContext;
//        private final Character[] characters;
//
//        public CharSelectAdapter(Context c, Character[] characters) {
//            mContext = c;
//            this.characters = characters;
//        }
//
//        /**
//         * Count of character icons array
//         * @return
//         */
//        public int getCount() {
//            return characterIconArray.length;
//        }
//
//        /**
//         * Returns this specific character icon
//         * Unimplemented here
//         * @return
//         */
//        public Object getItem(int position) {
//            return null;
//        }
//
//        /**
//         * Returns this specific character id
//         * Unimplemented here
//         * @return
//         */
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        /**
//         * Create a new ImageView for each item referenced by the Adapter
//         * @return
//         */
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            // To hold this specific icon
//            ImageView imageView;
//            TextView textView;
//
//
//            final Character character = characters[position];
//
//            if (convertView == null) {
//
//                imageView = new ImageView(mContext);
//                //textView = new TextView(mContext);
//
//                // resize and crop images
//                //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//
//                // crop to the center
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//                imageView.setPadding(8, 8, 8, 8);
//            } else {
//                imageView = (ImageView) convertView;
//                //textView = (TextView) convertView;
//            }
//
//            // If book is favorited, star it
////            imageView.setImageResource(
////                    character.getIsSelected() ?  R.drawable.fox : characterIconArray[position]
////            );
//
//            if (character.getIsSelected()) {
//                imageView.setImageResource(R.drawable.fox);
//                //textView.setText("Hi");
//            } else {
//                imageView.setImageResource(characterIconArray[position]);
//            }
//
//            //imageView.setImageResource(characterIconArray[position]);
//            return imageView;
//        }
//
//        // references to our images
//        private Integer[] characterIconArray = {
//                R.drawable.bear, R.drawable.dog,
//                R.drawable.falcon, R.drawable.fox,
//                R.drawable.horse, R.drawable.kangaroo,
//                R.drawable.koala, R.drawable.ostrich,
//                R.drawable.tiger
//        };
//    }




//    GridView character_grid;
//    CharSelectAdapter character_select_adapter;
//    ArrayList<Character> character_array_list;
//
//    // TEMPORARY STRING ARRAY OF CHARACTER NAMES
//    static final String[] character_string_list = new String[] { "Bear", "Dog", "Falcon", "Fox", "Horse",
//            "Kangaroo", "Koala", "Ostrich", "Tiger" };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_character_select);
//
//        // ADD EACH CHARACTER NAME TO AN ARRAY LIST OF CHARACTERS
//        character_array_list = new ArrayList<Character>();
//        for (String name : character_string_list)
//        {
//            // STATE 0 FOR UNCHECKED, NAME IS NAME
//            character_array_list.add(new Character(0, name));
//        }
//
//        // GRID OF CHARACTERS
//        character_grid = (GridView) findViewById(R.id.gridView);
//
//        // ADAPTER SET UP
//        character_select_adapter = new CharSelectAdapter(this);
//        character_grid.setAdapter(character_select_adapter);
//
//
//        //character_grid.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
//
//
//        // ON CHARACTER SELECT
//        character_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
//
//                for (Character character : character_array_list)
//                {
//                    character.setChecked(false);
//                }
//
//                character_array_list.get(position).setChecked(true);
//                character_select_adapter.notifyDataSetChanged();
//            }
//        });
//    }




//    class CharSelectAdapter extends BaseAdapter
//    {
//        Context context;
//
//        /**
//         * Initializes characterArrayList of character_string_list.
//         */
//        CharSelectAdapter(Context context) {
//            this.context = context;
//            character_array_list = new ArrayList<Character>();
//
//            // CHARACTER NAMES
//            Resources resources = context.getResources();
//            String[] tempCharNames = resources.getStringArray(R.array.characters);
//
//            // CHARACTER IMAGES
//            int[] charImages = {R.drawable.bear, R.drawable.dog, R.drawable.falcon,
//                    R.drawable.fox, R.drawable.horse, R.drawable.kangaroo,
//                    R.drawable.koala, R.drawable.ostrich, R.drawable.tiger};
//
//            // ADD CHARACTERS TO LIST
//            for (int i = 0; i < 9; i++)
//            {
//                Character tempChar = new Character(charImages[i], tempCharNames[i]);
//                character_array_list.add(tempChar);
//            }
//        }
//
//        /**
//         * Returns size of array characterArrayList.
//         * @return
//         */
//        @Override
//        public int getCount() {
//            return character_array_list.size();
//        }
//
//        /**
//         * Get item at given position i.
//         * @param position
//         * @return
//         */
//        @Override
//        public Object getItem(int position) {
//            return character_array_list.get(position);
//        }
//
//        /**
//         * Used mostly in database-related apps.
//         * @param position
//         * @return
//         */
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        /**
//         * Instead of finding the image view every time in the getView method,
//         * Use this class to find the image view once.
//         * To perform once time initialization of the expensive task findviewbyid.
//         */
//        class ViewHolder
//        {
//            ImageView character_image;
//
//            // FINDVIEWBYID GETS CALLED WHEN OBJECT IS INITIALIZED IN THE CONSTRUCTOR
//            ViewHolder (View v)
//            {
//                character_image = (ImageView) v.findViewById(R.id.character_image_view);
//            }
//        }
//
//        /**
//         * When the grid view wants to display an item to the user, it calls this method with:
//         * @param position : position of the ite
//         * @param view : is this the first time the item is created?
//         * @param parent : reference to the parent grid view
//         * @return : view to character_grid in OnCreate
//         */
//        @Override
//        public View getView(int position, View view, ViewGroup parent) {
//
//            // If view is null, it is being created for the first time.
//            View row = view;
//            ViewHolder holder = null;
//            //Character character_image = character_array_list.get(position);
//
//            // IF CREATING FOR THE FIRST TIME, INFLATE
//            if (row == null)
//            {
//
//                // BRINGS CHARACTER FROM XML TO JAVA OBJECT. THIS IS AN EXPENSIVE OPERATION.
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                row = inflater.inflate(R.layout.item_single_character, parent, false);
//
//                // ROW NOW CONTAINS RELATIVE LAYOUT. PASS TO MAKE NEW HOLDER
//                holder = new ViewHolder(row);
//
//                // STORE HOLDER INSIDE VIEW OBJECT (NO LONGER NULL)
//                row.setTag(holder);
//
//            }
//            // OTHERWISE, RECYCLE
//            else
//            {
//                // GET HOLDER FROM ROW.
//                // This way, findviewbyid doesn't get called again.
//                holder = (ViewHolder) row.getTag();
//            }
//
//            Character character = character_array_list.get(position);
//            holder.character_image.setImageResource(character.id);
//
//            // TINTS CHARACTER IF SELECTED
//            if (character.isChecked())
//            {
//                holder.character_image.setImageResource(R.drawable.tiger);
//                //holder.character_image.setTint
//                //holder.text.setBackgroundColor(Color.GREEN);
//            }
//
//            return row;
//        }
//    }
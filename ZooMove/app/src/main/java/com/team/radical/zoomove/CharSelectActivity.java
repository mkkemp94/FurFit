package com.team.radical.zoomove;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import static com.team.radical.zoomove.MainActivity.currentCharacter;

/**
 * This activity holds all characters to be selected as "Current Character"
 * It is reached by clicking the character on the main menu.
 * Clicking the stats button on this page brings up the selcted character's stats.
 */
public class CharSelectActivity extends AppCompatActivity {

    TextView textView;
    CharSelectAdapter charSelectAdapter;

    /**
     * Puts a grid view on the character select screen
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.char_select_activity);

        // Make grid. Set adapter to provide data.
        GridView gridview = (GridView) findViewById(R.id.gridView);
        charSelectAdapter = new CharSelectAdapter(this, allCharacters);
        gridview.setAdapter(charSelectAdapter);

        // Displays which character is currently selected
        textView = (TextView) findViewById(R.id.tv_selected_character);

        // Select the first character by default
        selectDefaultCharacter();

        // When an item is clicked.
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Select that character. Deselect all other characters.
                selectCharacter(position);
            }
        });

    }

    /**
     * Make the default selection be the character in play
     * TODO: Is there a better way to do this than to find the one with the same image?
     */
    private void selectDefaultCharacter() {
        // Deselect all other characters
        for (Character ch : allCharacters) { ch.deselect(); }

        // Select the character with the SAME IMAGE is the current character
        for (Character ch : allCharacters) {
            if (ch.getImageResource() == currentCharacter.getImageResource()) {
                ch.select();
                break;
            }
        }
    }

    /**
     * Select character on tap. Deselect all others.
     * Only one character should have isSelected set to true.
     */
    private void selectCharacter(int position) {

        // Toast message for position tapped
        Toast.makeText(CharSelectActivity.this, "" + position,
                Toast.LENGTH_SHORT).show();

        // If selcted, display message
        textView.setText("Character at position " + position + " is selected.");

        // Deselect all characters
        for (Character ch : allCharacters) {
            ch.deselect();
        }

        // Select the character that was tapped
        Character thisCharacter = allCharacters[position];
        thisCharacter.select();

        // Redraw grid
        charSelectAdapter.notifyDataSetChanged();
    }

    /**
     * An array holding all the characters.
     */
    public static Character[] allCharacters = {
            new Character("Beedrill", R.drawable.beedrill),
            new Character("Charizard", R.drawable.charizardy),
            new Character("Delphox", R.drawable.delphox),
            new Character("Flareon", R.drawable.flareon),
            new Character("Jolteon", R.drawable.jolteon),
            new Character("Squirtle", R.drawable.squirtle),
            new Character("Mareep", R.drawable.mareep),
            new Character("Pidgeot", R.drawable.pidgeot),
            new Character("Staryu", R.drawable.staryu)
    };

    /**
     * Go back to the main menu without changing Current Character
     * @param view
     */
    public void backButton(View view)
    {
        final Intent backIntent = new Intent(this, MainActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);

        for (Character ch : allCharacters) {
            if (ch.getIsSelected()) {
                if (ch.getImageResource() == currentCharacter.getImageResource()) {
                    // If currentCharacter is already selected
                    startActivity(backIntent);
                } else {

                    // Trying to go back without saving character
                    AlertDialog.Builder builder = new AlertDialog.Builder(CharSelectActivity.this);
                    builder.setMessage("Current selection will not be saved.")
                            .setTitle("Cancel character select?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Go back to main activity with no changes
                                    startActivity(backIntent);
                                }
                            })
                            .setNegativeButton("Keep Choosing", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Stay on character select screen
                                    dialog.cancel();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        }


    }

    /**
     * Go back to the main menu, saving the newly selected character as Current Character
     * @param view
     */
    public void chooseButton(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);

        // Find the character that is selected and set it to currentCharacter
        for (Character ch : allCharacters) {
            if (ch.getIsSelected()) {
                currentCharacter = ch;
            }
        }

        startActivity(intent);
    }

    /**
     * Go to the selected character's stats page
     * @param view
     */
    public void statsButton(View view)
    {
        Intent intent = new Intent(this, StatsActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable();
//        intent.putExtras(bundle);
        startActivity(intent);
    }
}




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
//        setContentView(R.layout.char_select_activity);
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
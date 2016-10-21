package com.team.radical.zoomove;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by kempm on 8/4/2016.
 */
public class CharSelectActivity extends AppCompatActivity {

    GridView character_grid;
    CharacterSelectAdapter character_select_adapter;
    ArrayList<Character> character_array_list;

    // TEMPORARY STRING ARRAY OF CHARACTER NAMES
    static final String[] character_string_list = new String[] { "Bear", "Dog", "Falcon", "Fox", "Horse",
            "Kangaroo", "Koala", "Ostrich", "Tiger" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.char_select_activity);

        // ADD EACH CHARACTER NAME TO AN ARRAY LIST OF CHARACTERS
        character_array_list = new ArrayList<Character>();
        for (String name : character_string_list)
        {
            // STATE 0 FOR UNCHECKED, NAME IS NAME
            character_array_list.add(new Character(0, name));
        }

        // GRID OF CHARACTERS
        character_grid = (GridView) findViewById(R.id.gridView);

        // ADAPTER SET UP
        character_select_adapter = new CharacterSelectAdapter(this);
        character_grid.setAdapter(character_select_adapter);


        //character_grid.setChoiceMode(GridView.CHOICE_MODE_SINGLE);


        // ON CHARACTER SELECT
        character_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                for (Character character : character_array_list)
                {
                    character.setChecked(false);
                }

                character_array_list.get(position).setChecked(true);
                character_select_adapter.notifyDataSetChanged();
            }
        });
    }

    public void backButton(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    public void chooseButton(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    public void statsButton(View view)
    {
        Intent intent = new Intent(this, StatsActivity.class);
        //Bundle bundle = new Bundle();
        //intent.putExtras(bundle);
        startActivity(intent);
    }


    class CharacterSelectAdapter extends BaseAdapter
    {
        Context context;

        /**
         * Initializes characterArrayList of character_string_list.
         */
        CharacterSelectAdapter(Context context) {
            this.context = context;
            character_array_list = new ArrayList<Character>();

            // CHARACTER NAMES
            Resources resources = context.getResources();
            String[] tempCharNames = resources.getStringArray(R.array.characters);

            // CHARACTER IMAGES
            int[] charImages = {R.drawable.bear, R.drawable.dog, R.drawable.falcon,
                    R.drawable.fox, R.drawable.horse, R.drawable.kangaroo,
                    R.drawable.koala, R.drawable.ostrich, R.drawable.tiger};

            // ADD CHARACTERS TO LIST
            for (int i = 0; i < 9; i++)
            {
                Character tempChar = new Character(charImages[i], tempCharNames[i]);
                character_array_list.add(tempChar);
            }
        }

        /**
         * Returns size of array characterArrayList.
         * @return
         */
        @Override
        public int getCount() {
            return character_array_list.size();
        }

        /**
         * Get item at given position i.
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return character_array_list.get(position);
        }

        /**
         * Used mostly in database-related apps.
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Instead of finding the image view every time in the getView method,
         * Use this class to find the image view once.
         * To perform once time initialization of the expensive task findviewbyid.
         */
        class ViewHolder
        {
            ImageView character_image;

            // FINDVIEWBYID GETS CALLED WHEN OBJECT IS INITIALIZED IN THE CONSTRUCTOR
            ViewHolder (View v)
            {
                character_image = (ImageView) v.findViewById(R.id.character_image_view);
            }
        }

        /**
         * When the grid view wants to display an item to the user, it calls this method with:
         * @param position : position of the ite
         * @param view : is this the first time the item is created?
         * @param parent : reference to the parent grid view
         * @return : view to character_grid in OnCreate
         */
        @Override
        public View getView(int position, View view, ViewGroup parent) {

            // If view is null, it is being created for the first time.
            View row = view;
            ViewHolder holder = null;
            //Character character_image = character_array_list.get(position);

            // IF CREATING FOR THE FIRST TIME, INFLATE
            if (row == null)
            {

                // BRINGS CHARACTER FROM XML TO JAVA OBJECT. THIS IS AN EXPENSIVE OPERATION.
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.single_character_item, parent, false);

                // ROW NOW CONTAINS RELATIVE LAYOUT. PASS TO MAKE NEW HOLDER
                holder = new ViewHolder(row);

                // STORE HOLDER INSIDE VIEW OBJECT (NO LONGER NULL)
                row.setTag(holder);

            }
            // OTHERWISE, RECYCLE
            else
            {
                // GET HOLDER FROM ROW.
                // This way, findviewbyid doesn't get called again.
                holder = (ViewHolder) row.getTag();
            }

            Character character = character_array_list.get(position);
            holder.character_image.setImageResource(character.id);

            // TINTS CHARACTER IF SELECTED
            if (character.isChecked())
            {
                holder.character_image.setImageResource(R.drawable.tiger);
                //holder.character_image.setTint
                //holder.text.setBackgroundColor(Color.GREEN);
            }

            return row;
        }
    }
}

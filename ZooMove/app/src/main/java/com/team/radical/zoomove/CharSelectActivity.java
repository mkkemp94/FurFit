package com.team.radical.zoomove;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by kempm on 8/4/2016.
 */
public class CharSelectActivity extends AppCompatActivity {

    GridView character_grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.char_select_activity);

        character_grid = (GridView) findViewById(R.id.gridView);
        character_grid.setAdapter(new CharacterSelectAdapter(this));
    }

    class Character
    {
        int charId;
        String charName;

        Character (int charId, String charName)
        {
            this.charId = charId;
            this.charName = charName;
        }
    }

    class CharacterSelectAdapter extends BaseAdapter
    {
        ArrayList<Character> list;
        Context context;

        /**
         * Initializes list of characters.
         */
        CharacterSelectAdapter(Context context) {
            this.context = context;
            list = new ArrayList<Character>();

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
                list.add(tempChar);
            }
        }

        /**
         * Returns size of array list.
         * @return
         */
        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * Get item at given position i.
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return list.get(position);
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
        // TODO: Watch videos about view holder to explain in depth
        class ViewHolder
        {
            ImageView myCharacter;

            ViewHolder (View v)
            {
                myCharacter = (ImageView) v.findViewById(R.id.character_image_view);
            }
        }

        /**
         * When the grid view wants to display an item to the user, it calls this method with:
         * @param position : position of the ite
         * @param view : is this the first time the item is created?
         * @param parent : reference to the parent grid view
         * @return : view to character_grid in OnCreate
         */
        // TODO: Watch seperate video on list view optimization and view recycling
        @Override
        public View getView(int position, View view, ViewGroup parent) {

            View row = view;
            ViewHolder holder = null;

            // IF CREATING FOR THE FIRST TIME, INFLATE
            if (row == null)
            {

                // BRINGS CHARACTER FROM XML TO JAVA OBJECT
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // TODO: Watch layout inflater video
                row = inflater.inflate(R.layout.single_character_item, parent, false);

                // ROW NOW CONTAINS RELATIVE LAYOUT. PASS TO MAKE NEW HOLDER
                holder = new ViewHolder(row);

                // STORE HOLDER INSIDE VIEW OBJECT (NO LONGER NULL)
                row.setTag(holder);

            }
            else
            {
                // GET HOLDER FROM ROW
                // This way, findviewbyid doesn't get called again.
                holder = (ViewHolder) row.getTag();
            }

            Character temp = list.get(position);
            holder.myCharacter.setImageResource(temp.charId);

            return row;
        }
    }
}

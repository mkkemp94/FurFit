package com.team.radical.zoomove;

/**
 * Created by kempm on 11/25/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class for drawing character select grid
 */
class CharacterSelectAdapter extends BaseAdapter {

    private Context mContext;
    private final Character[] characters;

    public CharacterSelectAdapter(Context c, Character[] characters) {
        mContext = c;
        this.characters = characters;
    }

    /**
     * Count of character icons array
     */
    public int getCount() {
        return characterIconArray.length;
    }

    /**
     * Returns this specific character icon
     * Unimplemented here
     */
    public Object getItem(int position) {
        return null;
    }

    /**
     * Returns this specific character id
     * Unimplemented here
     */
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Create a new ImageView for each item referenced by the Adapter
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        // If there's nothing inside convert view yet
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_single_character, null);

            // Initialize character image
            final ImageView characterIcon = // new ImageView(mContext);
                    (ImageView) convertView.findViewById(R.id.iv_character_icon);

            final TextView characterName =
                    (TextView) convertView.findViewById(R.id.tv_char_name);

            // Store character image into a view holder
            final ViewHolder viewHolder = new ViewHolder(characterIcon, characterName);
            convertView.setTag(viewHolder);
        }

        // Get character icon from view holder
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        // Get the character at this position
        Character character = characters[position];

        // Mark character as favorited or not
        if (character.getIsSelected()) {
            viewHolder.characterIcon.setImageResource(R.drawable.pokeball);
        } else {
            viewHolder.characterIcon.setImageResource(characterIconArray[position]);
        }

        // Display the character's name
        viewHolder.characterName.setText(character.getName());

        // Return the updated view
        return convertView;
    }

    /**
     * References to our images
     */
    private Integer[] characterIconArray = {
            R.drawable.beedrill, R.drawable.charizardy,
            R.drawable.delphox, R.drawable.flareon,
            R.drawable.jolteon, R.drawable.squirtle,
            R.drawable.mareep, R.drawable.pidgeot,
            R.drawable.staryu
    };

    /**
     * Class that holds reference to subview. Used for recycling
     * ---------------------------------------------------------
     */
    private class ViewHolder {
        private final ImageView characterIcon;
        private final TextView characterName;

        public ViewHolder(ImageView characterIcon, TextView characterName) {
            this.characterIcon = characterIcon;
            this.characterName = characterName;
        }
    }
}






//        // To hold this specific icon
//        ImageView imageView;
//        TextView textView;
//
//
//        final Character character = characters[position];
//
//        if (convertView == null) {
//
//            imageView = new ImageView(mContext);
//            //textView = new TextView(mContext);
//
//            // resize and crop images
//            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//
//            // crop to the center
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//            //textView = (TextView) convertView;
//        }
//
//        // If book is favorited, star it
////            imageView.setImageResource(
////                    character.getIsSelected() ?  R.drawable.fox : characterIconArray[position]
////            );
//
//        if (character.getIsSelected()) {
//            imageView.setImageResource(R.drawable.fox);
//            //textView.setText("Hi");
//        } else {
//            imageView.setImageResource(characterIconArray[position]);
//        }
//
//        //imageView.setImageResource(characterIconArray[position]);
//        return imageView;
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
class CharSelectAdapter extends BaseAdapter {

    private Context mContext;
    private final Character[] characterArray;

    public CharSelectAdapter(Context c, Character[] characterArray) {
        this.mContext = c;
        this.characterArray = characterArray;
    }

    /**
     * Count of character icons array
     * Not really used
     */
    public int getCount() {
        return 9;
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

            // Inflate the single character item layout
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_single_character, null);

            // Get this image view and text view
            final ImageView characterIcon = // new ImageView(mContext);
                    (ImageView) convertView.findViewById(R.id.iv_character_icon);

            final TextView characterName =
                    (TextView) convertView.findViewById(R.id.tv_char_name);

            // Store character image and name into view holder
            final ViewHolder viewHolder = new ViewHolder(characterIcon, characterName);
            convertView.setTag(viewHolder);
        }

        // Get character icon and name from view holder
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        // Get the character at this position
        Character thisCharacter = characterArray[position];

        // Mark character as favorited or not
        if (thisCharacter.getIsSelected())
            viewHolder.characterIcon.setImageResource(R.drawable.pokeball);
        else viewHolder.characterIcon.setImageResource(thisCharacter.getImageResource());

        // Display the character's name
        viewHolder.characterName.setText(thisCharacter.getName());

        // Return the updated view
        return convertView;
    }


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






//        viewHolder.characterIcon.setImageResource(thisCharacter.getIsSelected() ? R.drawable.pokeball : thisCharacter.getImageResource());

//        if (thisCharacter.getIsSelected()) {
//            viewHolder.characterIcon.setImageResource(R.drawable.pokeball);
//        } else {
//            viewHolder.characterIcon.setImageResource(thisCharacter.getImageResource()); //characterIconArray[position]);
//        }


//        // To hold this specific icon
//        ImageView imageView;
//        TextView textView;



/**
 * References to our images
 */
//    private Integer[] characterIconArray = {
//            R.drawable.beedrill, R.drawable.charizardy,
//            R.drawable.delphox, R.drawable.flareon,
//            R.drawable.jolteon, R.drawable.squirtle,
//            R.drawable.mareep, R.drawable.pidgeot,
//            R.drawable.staryu
//    };



//
//
//        final Character character = characterArray[position];
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
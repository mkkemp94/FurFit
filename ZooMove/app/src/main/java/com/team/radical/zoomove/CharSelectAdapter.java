package com.team.radical.zoomove;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Class for drawing character select grid.
 * Created by kempm on 11/25/2016.
 */
class CharSelectAdapter extends BaseAdapter {

    // Context of the character select activity
    private Context mContext;

    // Lis of all characters
    private final List<Character> mCharacterList;

    /**
     * CONSTRUCTOR
     * @param c contect of the application this adapter is being used for
     * @param characterList list of all selectable character
     */
    public CharSelectAdapter(Context c, List<Character> characterList) {
        this.mContext = c;
        this.mCharacterList = characterList;
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * Character count.
     * Not really used yet.
     * @param
     */
    public int getCount() {
        return 9;
    }

    /**
     * Returns this specific character icon.
     * Not really used yet.
     * @param position of tapped character in grid view
     * @return character at the tapped position
     */
    public Object getItem(int position) {
        return mCharacterList.get(position);
    }

    /**
     * Returns this specific character id.
     * Unimplemented here.
     * @param position of tapped character in grid view
     * @return 0 for now
     */
    public long getItemId(int position) {
        return 0;
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * Create a new ImageView for each item referenced by the Adapter.
     * @param position of the tapped character
     * @param convertView the view holding each single character item
     * @param parent of the single character item. I think this refers to the char select activity.
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        // If there's nothing inside convert view yet
        if (convertView == null) {

            // Inflate the single character item layout
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_single_character, null);

            // Get thie Image View for the character icon
            final ImageView imageView =
                    (ImageView) convertView.findViewById(R.id.iv_character_icon);

            // Get the text view for the character name
            final TextView textView =
                    (TextView) convertView.findViewById(R.id.tv_char_name);

            // Store views into a view holder for later access
            ViewHolder viewHolder = new ViewHolder(imageView, textView);

            // Set a tag to the view holder so we can access it later
            convertView.setTag(viewHolder);
        }

        // Get the old saved view holder's views
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        // Get the character at this tapped position
        Character thisCharacter = mCharacterList.get(position);

        // Set the image view to a pokeball if this is the selected character
        if (thisCharacter.getIsSelected()) {
            viewHolder.imageView.setImageResource(R.drawable.pokeball); }

        // Set the image view to this character's face if not selected
        else { viewHolder.imageView.setImageResource(thisCharacter.getImageResource()); }

        // Set text view to display the character's name
        viewHolder.textView.setText(thisCharacter.getName());

        // Return the updated view
        return convertView;
    }


    /**
     * Class that holds reference to subview. Used for recycling
     * ---------------------------------------------------------
     */
    private class ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(ImageView imageView, TextView textView) {
            this.imageView = imageView;
            this.textView = textView;
        }
    }
}











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
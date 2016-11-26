package com.team.radical.zoomove;

/**
 * The Character object holds data for each character in the game.
 * Each character has one Character Object.
 */
public class Character  //} implements Checkable
{

    //int id;
    private final String name;
    private boolean isSelected = false;
    private final int imageResource;

    Character (String name, int imageResource)
    {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean getIsSelected() { return isSelected; }

    public void toggleSelected() { isSelected = !isSelected; }

}

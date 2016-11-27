package com.team.radical.zoomove;

/**
 * The Character object holds data for each character in the game.
 * Each character has one Character Object.
 */
public class Character  //} implements Checkable
{

    private String name;
    private int imageResource;
    private boolean isSelected = false;

    Character (String name, int imageResource)
    {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public int getImageResource() {
        return imageResource;
    }

    public boolean getIsSelected() { return isSelected; }
    public void deselect() { isSelected = false; }
    public void select() { isSelected = true; }

}

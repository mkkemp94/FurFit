package com.team.radical.zoomove;

import java.io.Serializable;

/**
 * The Character object holds data for each character in the game.
 * Each character has one Character Object.
 */
public class Character implements Serializable {

    private int mCharacterId;
    private String mName;
    private int mImageResource;
    private boolean isSelected = false;

    private int mGeneralTime;
    private int mRunningTime;
    private int mStrengthTime;
    private int totalTime;

    Character (int id, String name, int imageResource)
    {
        this.mCharacterId = id;
        this.mName = name;
        this.mImageResource = imageResource;

        this.mGeneralTime = 0;
        this.mRunningTime = 0;
        this.mStrengthTime = 0;
    }

    public String getName() {
        return this.mName;
    }
    public void setName(String name) { this.mName = name; }

    public int getImageResource() {
        return this.mImageResource;
    }

    public boolean getIsSelected() { return isSelected; }
    public void deselect() { this.isSelected = false; }
    public void select() { this.isSelected = true; }

    public int getRunningTime() {
        return this.mRunningTime;
    }
    public int getGeneralTime() {
        return this.mGeneralTime;
    }
    public int getmStrengthTime() {
        return this.mStrengthTime;
    }
    public int getTotalTime() {
        return this.mRunningTime + this.mGeneralTime + this.mStrengthTime;
    }
}

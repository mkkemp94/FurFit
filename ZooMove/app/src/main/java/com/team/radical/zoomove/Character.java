package com.team.radical.zoomove;

import java.io.Serializable;

/**
 * The Character object holds data for each character in the game.
 * Each character has one Character Object.
 */
public class Character implements Serializable {

    private int id;
    private String name;
    private int imageResource;
    private boolean isSelected = false;

    private int generalTime;
    private int runningTime;
    private int strengthTime;
    private int totalTime;

    Character (int id, String name, int imageResource)
    {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.generalTime = 100;
        this.runningTime = 50;
        this.strengthTime = 20;
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

    public int getRunningTime() {
        return runningTime;
    }
    public int getGeneralTime() {
        return generalTime;
    }
    public int getStrengthTime() {
        return strengthTime;
    }
    public int getTotalTime() {
        return runningTime + generalTime + strengthTime;
    }
}

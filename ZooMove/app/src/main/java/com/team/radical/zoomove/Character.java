package com.team.radical.zoomove;

import android.widget.Checkable;

/**
 * Created by kempm on 8/18/2016.
 */
public class Character implements Checkable
{

    int id;
    boolean checked;
    String name;

    Character (int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    private void refreshDrawableState() {
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}

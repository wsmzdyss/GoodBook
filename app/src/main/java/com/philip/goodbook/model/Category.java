package com.philip.goodbook.model;

import android.widget.ImageView;

/**
 * Created by admin on 2017/1/6.
 */

public class Category {
    private String name;

    private int icon;

    public Category() {
    }

    public Category(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

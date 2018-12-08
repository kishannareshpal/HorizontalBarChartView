package com.kishannareshpal.horizontalbarchartview.models;

/**
 * Model
 * Created by Kishan Nareshpal Jadav. 2018.
 * Description: contains the storage details.
 */
public class Data {
    private int id;
    private int color;
    private int percentage;

    public Data(int id, int color, int percentage) {
        this.id = id;
        this.color = color;
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}

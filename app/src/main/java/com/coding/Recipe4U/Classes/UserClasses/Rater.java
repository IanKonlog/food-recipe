package com.coding.Recipe4U.Classes.UserClasses;

import java.util.ArrayList;

public class Rater {
    private int userId;
    private ArrayList<Rating> ratings;

    public Rater(int userId, ArrayList<Rating> ratings) {
        this.userId = userId;
        this.ratings = ratings;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }
}

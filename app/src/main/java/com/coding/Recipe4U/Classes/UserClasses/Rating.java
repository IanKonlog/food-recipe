package com.coding.Recipe4U.Classes.UserClasses;

public class Rating {
    private int recipeId;
    private double rating;

    public Rating(int recipeId, double rating) {
        this.recipeId = recipeId;
        this.rating = rating;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

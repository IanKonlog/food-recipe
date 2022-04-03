package com.coding.Recipe4U.Classes;

import java.util.ArrayList;

public class Favorite {

    private ArrayList<Recipes> recipes;

    public Favorite() {
        recipes = new ArrayList<>();
    }

    public ArrayList<Recipes> getRecipes() {
        return recipes;
    }

    //TODO add Favorite to database
    public void addFavorite(){

    }

    //TODO remove favorite from database
    public void removeFavorite(int userId, int recipeId){
        recipes.remove(recipeId);
    }
}

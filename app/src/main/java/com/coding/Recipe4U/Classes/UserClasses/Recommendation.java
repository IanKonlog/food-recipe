package com.coding.Recipe4U.Classes.UserClasses;

import java.util.ArrayList;
import java.util.HashMap;

public class Recommendation {
    private int recomId;

    public Recommendation(int recomId) {
        this.recomId = recomId;
    }

    public int getRecomId() {
        return recomId;
    }

    //TODO: implement weight calculation for collaborative filtering
    public HashMap<UserLog, Integer> calculateWeightOfViews(UserLog userLog, UserPreferences userPreferences){
        return new HashMap<>();
    }

    //TODO: implement recipe search per caculated weights
    public ArrayList<Recipes> searchRecipeWeight(HashMap<UserLog, Integer> weights){
        return new ArrayList<>();
    }

    //TODO: check similarity ratings
    public ArrayList<Recipes> similarityRatings(Rater rater){
        return new ArrayList<>();
    }

    //TODO: Recommend recipes based on previous calculations
    public ArrayList<Recipes> recommendRecipes(){
        return new ArrayList<>();
    }
}

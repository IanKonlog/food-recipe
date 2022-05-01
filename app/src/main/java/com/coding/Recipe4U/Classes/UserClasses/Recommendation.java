package com.coding.Recipe4U.Classes.UserClasses;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.coding.Recipe4U.Classes.ApiModelClasses.Recipe;
import com.coding.Recipe4U.Classes.ApiModelClasses.Result;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Recommendation {
    private int recomId;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String ID;

    public Recommendation() {
    }

    public Recommendation(int recomId) {
        this.recomId = recomId;
    }

    public int getRecomId() {
        return recomId;
    }

    public ArrayList<Recipe> sortByPopularity(ArrayList<Recipe> recipes) {

        for (int i = 0; i < recipes.size() - 1; i++) {
            for (int j = i + 1; j < recipes.size(); j++) {
                if (recipes.get(i).aggregateLikes < recipes.get(j).aggregateLikes) {
                    Recipe r = recipes.get(i);
                    recipes.set(i, recipes.get(j));
                    recipes.set(j, r);
                }
            }
        }
        return recipes;
    }

    public ArrayList<Result> sortByPopularity1(ArrayList<Result> recipes) {

        for (int i = 0; i < recipes.size() - 1; i++) {
            for (int j = i + 1; j < recipes.size(); j++) {
                if (recipes.get(i).aggregateLikes < recipes.get(j).aggregateLikes) {
                    Result r = recipes.get(i);
                    recipes.set(i, recipes.get(j));
                    recipes.set(j, r);
                }
            }
        }
        return recipes;
    }

    public String recommendDish(HashMap<String, Integer> log) {
        String dish = "";
        int weight = 0;
        //HashMap<String, Integer> logs = log.getLogs();

        for (HashMap.Entry mapElement : log.entrySet()) {
            if ((Integer) mapElement.getValue() > weight) {
                dish = (String) mapElement.getKey();
                weight = (Integer) mapElement.getValue();
            }
        }
        Log.d(TAG, "recommendDish: " + dish);
        return dish;
    }

    public HashMap<UserLog, Integer> calculateWeightOfViews(UserLog userLog, UserPreferences userPreferences) {
        return new HashMap<>();
    }

    public ArrayList<Recipe> searchRecipeWeight(UserLog log) {

        return new ArrayList<>();
    }

    public ArrayList<Recipes> similarityRatings(Rater rater) {
        return new ArrayList<>();
    }

    public ArrayList<Recipes> recommendRecipes() {
        return new ArrayList<>();
    }
}

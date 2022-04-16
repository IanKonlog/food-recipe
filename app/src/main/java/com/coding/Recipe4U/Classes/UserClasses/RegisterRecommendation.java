package com.coding.Recipe4U.Classes.UserClasses;

import java.util.ArrayList;

public class RegisterRecommendation {

    private int regRecomId;

    public RegisterRecommendation(int regRecomId) {
        this.regRecomId = regRecomId;
    }

    public int getRegRecomId() {
        return regRecomId;
    }

    public void setRegRecomId(int regRecomId) {
        this.regRecomId = regRecomId;
    }

    //TODO: implement search recipes per user preferences at registration
    public ArrayList<Recipes> searchRecipe(UserPreferences userPreferences){
        return new ArrayList<>();

    }
}

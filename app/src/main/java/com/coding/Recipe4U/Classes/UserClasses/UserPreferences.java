package com.coding.Recipe4U.Classes.UserClasses;

public class UserPreferences {
    private int userPreferenceId;
    private String cuisine, dishType;

    public UserPreferences(int userPreferenceId, String cuisine, String dishType) {
        this.userPreferenceId = userPreferenceId;
        this.cuisine = cuisine;
        this.dishType = dishType;
    }

    public int getUserPreferenceId() {
        return userPreferenceId;
    }

    public void setUserPreferenceId(int userPreferenceId) {
        this.userPreferenceId = userPreferenceId;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }
}

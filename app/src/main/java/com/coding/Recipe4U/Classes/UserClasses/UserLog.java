package com.coding.Recipe4U.Classes.UserClasses;

import java.util.ArrayList;

public class UserLog {

    private String searchQuery;
    private ArrayList<String> dishTypes;
    private ArrayList<String> cuisines;

    public UserLog(String searchQuery, ArrayList<String> dishTypes, ArrayList<String> cuisines) {
        this.searchQuery = searchQuery;
        this.dishTypes = dishTypes;
        this.cuisines = cuisines;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public ArrayList<String> getDishTypes() {
        return dishTypes;
    }

    public void setDishTypes(ArrayList<String> dishTypes) {
        this.dishTypes = dishTypes;
    }

    public ArrayList<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(ArrayList<String> cuisines) {
        this.cuisines = cuisines;
    }
}

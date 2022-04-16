package com.coding.Recipe4U.Classes.UserClasses;

import com.coding.Recipe4U.Classes.ApiModelClasses.Recipe;

import java.util.ArrayList;
import java.util.Map;

public class User {
    private String name, userName, email, password, phoneNumber, profilePicture;
    private Map<String, Recipe> favoriteRecipes;
    private Map<String,Recipes> userCreatedRecipes;
    private ArrayList<UserLog> userLogs;


    public User(){}

    public User(String name, String userName, String email, String password, String phoneNumber
            , String profilePicture, Map<String, Recipe> userFavoriteRecipes, Map<String,Recipes> userCreatedRecipes
            , ArrayList<UserLog> userLogs) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.favoriteRecipes = userFavoriteRecipes;
        this.userCreatedRecipes = userCreatedRecipes;
        this.userLogs = userLogs;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Map<String, Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(Map<String, Recipe> userFavoriteRecipes) {
        this.favoriteRecipes = userFavoriteRecipes;
    }

    public Map<String,Recipes> getUserCreatedRecipes() {
        return userCreatedRecipes;
    }

    public void setUserCreatedRecipes(Map<String,Recipes> userCreatedRecipes) {
        this.userCreatedRecipes = userCreatedRecipes;
    }

    public ArrayList<UserLog> getUserLogs() {
        return userLogs;
    }

    public void setUserLogs(ArrayList<UserLog> userLogs) {
        this.userLogs = userLogs;
    }
}

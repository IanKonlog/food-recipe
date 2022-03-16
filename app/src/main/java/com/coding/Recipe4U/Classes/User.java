package com.coding.Recipe4U.Classes;

import java.util.ArrayList;

public class User {
    private String name, userName, email, password, phoneNumber, profilePicture;
    private ArrayList<Recipe> userFavoriteRecipes;

    public User(String name, String userName, String email, String password, String phoneNumber, String profilePicture, ArrayList<Recipe> userFavoriteRecipes) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.userFavoriteRecipes = userFavoriteRecipes;
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

    public ArrayList<Recipe> getUserFavoriteRecipes() {
        return userFavoriteRecipes;
    }

    public void setUserFavoriteRecipes(ArrayList<Recipe> userFavoriteRecipes) {
        this.userFavoriteRecipes = userFavoriteRecipes;
    }
}

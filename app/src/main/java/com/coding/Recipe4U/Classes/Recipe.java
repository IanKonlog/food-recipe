package com.coding.Recipe4U.Classes;

import java.util.ArrayList;

public class Recipe {

    private int recipeId, time, userId;
    private String name, description, typeOfCuisine, recipePicUrl;

    private ArrayList<Ingredient> ingredients;
    private ArrayList<RecipeSteps> recipeSteps;


    public Recipe(int recipeId, int time, int userId, String name,
                  String description, String typeOfCuisine, String recipePicUrl,
                  ArrayList<Ingredient> ingredients, ArrayList<RecipeSteps> recipeSteps) {
        this.recipeId = recipeId;
        this.time = time;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.typeOfCuisine = typeOfCuisine;
        this.recipePicUrl = recipePicUrl;
        this.ingredients = ingredients;
        this.recipeSteps = recipeSteps;
    }

    //Getter and Setter
    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeOfCuisine() {
        return typeOfCuisine;
    }

    public void setTypeOfCuisine(String typeOfCuisine) {
        this.typeOfCuisine = typeOfCuisine;
    }

    public String getRecipePicUrl() {
        return recipePicUrl;
    }

    public void setRecipePicUrl(String recipePicUrl) {
        this.recipePicUrl = recipePicUrl;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<RecipeSteps> getRecipeSteps() {
        return recipeSteps;
    }


    //Add Ingredient to Recipe
    public void addIngredient(Ingredient I){
        ingredients.add(I);
    }

    //Add Recipe steps to Recipe
    public void addRecipeSteps (RecipeSteps R){
        recipeSteps.add(R);
    }

    //TODO Add new Recipe to database and post it to API
    //Add New Recipe
    public void addRecipe (int recipeId, String name, String description, String typeOfCuisine, int time, String recipePicUrl, int userId){

    }

    //TODO search with name in the API
    public ArrayList<Recipe> searchWithName(String name){
        return new ArrayList<Recipe>();
    }

    //TODO search with Ingredients API
    public ArrayList<Recipe> searchWithIngredients(ArrayList<Ingredient> I){
        return new ArrayList<Recipe>();
    }

    //TODO remove recipe to database
    public void deleteRecipe(int recipeId){
        ArrayList<Recipe> r = new ArrayList<>();

        r.remove(recipeId);
    }

}

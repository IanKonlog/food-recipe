package com.coding.Recipe4U.Classes;

import java.util.ArrayList;

public class Recipes {

    private int recipeId, time, userId,servings;
    private String name, description, typeOfCuisine, recipePicUrl;

    private ArrayList<Ingredient> ingredients;
    private ArrayList<RecipeSteps> recipeSteps;

    private ApiRequestManager manager;


    public Recipes(int recipeId, int time, int userId, String name,int servings,
                   String description, String typeOfCuisine, String recipePicUrl,
                   ArrayList<Ingredient> ingredients, ArrayList<RecipeSteps> recipeSteps) {
        this.recipeId = recipeId;
        this.time = time;
        this.userId = userId;
        this.name = name;
        this.servings = servings;
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
    public void addRecipe (){

    }

    //TODO remove recipe to database
    public void deleteRecipe(int recipeId){
        ArrayList<Recipes> r = new ArrayList<>();

        r.remove(recipeId);
    }

    @Override
    public String toString() {
        return "Recipes{" +
                "recipeId=" + recipeId +
                ", time=" + time +
                ", userId=" + userId +
                ", servings=" + servings +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", typeOfCuisine='" + typeOfCuisine + '\'' +
                ", recipePicUrl='" + recipePicUrl + '\'' +
                ", ingredients=" + ingredients +
                ", recipeSteps=" + recipeSteps +
                '}';
    }
}

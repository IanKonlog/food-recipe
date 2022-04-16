package com.coding.Recipe4U.Classes.UserClasses;

import java.util.ArrayList;

public class Recipes {

    private int time, servings;
    private String recipeId, name, description, typeOfCuisine, recipePicUrl;

    private ArrayList<Ingredient> ingredients;
    private ArrayList<RecipeSteps> recipeSteps;

    private ApiRequestManager manager;

    public Recipes(){}
    public Recipes(String recipeId, int time, String name,int servings,
                   String description, String typeOfCuisine, String recipePicUrl,
                   ArrayList<Ingredient> ingredients, ArrayList<RecipeSteps> recipeSteps) {
        this.recipeId = recipeId;
        this.time = time;
        this.name = name;
        this.servings = servings;
        this.description = description;
        this.typeOfCuisine = typeOfCuisine;
        this.recipePicUrl = recipePicUrl;
        this.ingredients = ingredients;
        this.recipeSteps = recipeSteps;
    }

    //Getter and Setter
    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
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

    //Add New Recipe
    public void addRecipe (){
        //Implemented in FragmentAddRecipe
    }

    //TODO remove recipe to database
    public void deleteRecipe(int recipeId){
        //Implemented in CreatedRecipesShowed
    }

    public ArrayList<Recipes> searchWithName(String name){
        return new ArrayList<>();

        //implemented in APIRequestManager class
    }

    public ArrayList<Recipes> searchWitIngredient(ArrayList<Ingredient> ingredients){
        return new ArrayList<>();

        //implemented in APIRequestManager class
    }

    @Override
    public String toString() {
        return "Recipes{" +
                "recipeId=" + recipeId +
                ", time=" + time +
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

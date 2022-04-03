package com.coding.Recipe4U.Classes.Listeners;


import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeByIngredientResponse;

public interface RecipeByNameListener {

    void didFetch(RecipeByIngredientResponse response, String message);
    void didError(String message);
}

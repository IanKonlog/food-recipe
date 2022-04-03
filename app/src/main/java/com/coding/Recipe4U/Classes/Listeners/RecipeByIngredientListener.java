package com.coding.Recipe4U.Classes.Listeners;


import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeByIngredientResponse;

import java.util.ArrayList;

public interface RecipeByIngredientListener {

    void didFetch(RecipeByIngredientResponse response, String message);
    void didError(String message);
}

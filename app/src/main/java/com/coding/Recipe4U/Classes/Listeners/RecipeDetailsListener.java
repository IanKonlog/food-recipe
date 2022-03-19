package com.coding.Recipe4U.Classes.Listeners;

import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}

package com.coding.Recipe4U.Classes.Listeners;

import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeInstructionResponse;

import java.util.ArrayList;

public interface RecipeInstructionsListener {

    void didFetch(ArrayList<RecipeInstructionResponse> response, String message);
    void didError(String message);
}

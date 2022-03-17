package com.coding.Recipe4U.Classes.Listeners;

import com.coding.Recipe4U.Classes.ApiModelClasses.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {

    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}

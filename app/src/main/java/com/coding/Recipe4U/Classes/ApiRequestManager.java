package com.coding.Recipe4U.Classes;

import android.content.Context;

import com.coding.Recipe4U.Classes.ApiModelClasses.RandomRecipeApiResponse;
import com.coding.Recipe4U.Classes.Listeners.RandomRecipeResponseListener;
import com.coding.Recipe4U.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiRequestManager {

    private Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(" https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiRequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, ArrayList<String> tags){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "10", tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }

                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipes{
        //Create Call method
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") ArrayList<String> tags
                );
    }
}

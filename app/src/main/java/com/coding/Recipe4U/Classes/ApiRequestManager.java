package com.coding.Recipe4U.Classes;

import android.content.Context;

import com.coding.Recipe4U.Classes.ApiModelClasses.RandomRecipeApiResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeDetailsResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeInstructionResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeSimilarResponse;
import com.coding.Recipe4U.Classes.Listeners.RandomRecipeResponseListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeDetailsListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeInstructionsListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeSimilarListener;
import com.coding.Recipe4U.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id,context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }

                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }

    public void getRecipeSimilar(RecipeSimilarListener listener, int id){
        CallRecipeSimilar callRecipeSimilar = retrofit.create(CallRecipeSimilar.class);
        Call<ArrayList<RecipeSimilarResponse>> call = callRecipeSimilar.callRecipeSimilar(id, "5", context.getString(R.string.api_key));
        call.enqueue(new Callback<ArrayList<RecipeSimilarResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeSimilarResponse>> call, Response<ArrayList<RecipeSimilarResponse>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }

                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeSimilarResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getRecipeInstructions(RecipeInstructionsListener listener, int id, boolean stepBreakdown){
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<ArrayList<RecipeInstructionResponse>> call = callInstructions.callRecipeInstructions(id, true, context.getString(R.string.api_key));
        call.enqueue(new Callback<ArrayList<RecipeInstructionResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeInstructionResponse>> call, Response<ArrayList<RecipeInstructionResponse>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeInstructionResponse>> call, Throwable t) {
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

    private interface CallRecipeDetails{
        //Create Call method
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
                );
    }

    private interface CallRecipeSimilar{
        //Create Call method
        @GET("recipes/{id}/similar")
        Call<ArrayList<RecipeSimilarResponse>> callRecipeSimilar(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey
                );
    }

    private interface CallInstructions{
        //Create Call method
        @GET("recipes/{id}/analyzedInstructions")
        Call<ArrayList<RecipeInstructionResponse>> callRecipeInstructions(
                @Path("id") int id,
                @Query("stepBreakdown") Boolean stepBreakdown,
                @Query("apiKey") String apiKey
                );
    }


}

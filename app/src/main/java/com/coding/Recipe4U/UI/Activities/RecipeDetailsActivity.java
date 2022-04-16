package com.coding.Recipe4U.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeDetailsResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeInstructionResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeSimilarResponse;
import com.coding.Recipe4U.Classes.UserClasses.ApiRequestManager;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeDetailsListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeInstructionsListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeSimilarListener;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Adapaters.IngredientAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeInstructionAdapater;
import com.coding.Recipe4U.UI.Adapaters.RecipeSimilarAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {

    int id;
    boolean stepBreakdown;
    TextView textRecipeName, textRecipeSource, textRecipeSummary;
    ImageView imageRecipeDetails;
    RecyclerView recyclerRecipeIngredients, recyclerRecipeSimilar, recyclerRecipeInstructions;
    ApiRequestManager manager;
    ProgressDialog dialog;
    IngredientAdapter ingredientAdapter;
    RecipeSimilarAdapter recipeSimilarAdapter;
    RecipeInstructionAdapater recipeInstructionAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe_details);
        hooks();

        id = Integer.parseInt(getIntent().getStringExtra("id"));

        manager = new ApiRequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener,id);

        manager.getRecipeSimilar(recipeSimilarListener,id);

        manager.getRecipeInstructions(recipeInstructionsListener, id, stepBreakdown);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details.....");
        dialog.show();

    }

    private void hooks() {
        textRecipeName = findViewById(R.id.text_recipe_name);
        textRecipeSource = findViewById(R.id.text_recipe_source);
        textRecipeSummary = findViewById(R.id.text_recipe_summary);
        imageRecipeDetails = findViewById(R.id.image_recipe_details);
        recyclerRecipeIngredients = findViewById(R.id.recycler_recipe_ingredients);
        recyclerRecipeSimilar = findViewById(R.id.recycler_recipe_similar);
        recyclerRecipeInstructions = findViewById(R.id.recycler_recipe_instructions);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textRecipeName.setText(response.title);
            textRecipeSource.setText(response.sourceName);
            textRecipeSummary.setText(response.summary);

            Picasso.get().load(response.image).into(imageRecipeDetails);

            recyclerRecipeIngredients.setHasFixedSize(true);
            recyclerRecipeIngredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL,false));

            ingredientAdapter = new IngredientAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
            recyclerRecipeIngredients.setAdapter(ingredientAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeSimilarListener recipeSimilarListener = new RecipeSimilarListener() {
        @Override
        public void didFetch(ArrayList<RecipeSimilarResponse> response, String message) {
            recyclerRecipeSimilar.setHasFixedSize(true);
            recyclerRecipeSimilar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));

            recipeSimilarAdapter = new RecipeSimilarAdapter(RecipeDetailsActivity.this, response, recipeClickListener);
            recyclerRecipeSimilar.setAdapter(recipeSimilarAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class)
            .putExtra("id", id));
        }
    };

    private final RecipeInstructionsListener recipeInstructionsListener = new RecipeInstructionsListener() {
        @Override
        public void didFetch(ArrayList<RecipeInstructionResponse> response, String message) {
            recyclerRecipeInstructions.setHasFixedSize(true);
            recyclerRecipeInstructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            recipeInstructionAdapater = new RecipeInstructionAdapater(RecipeDetailsActivity.this,response);
            recyclerRecipeInstructions.setAdapter(recipeInstructionAdapater);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}
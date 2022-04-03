package com.coding.Recipe4U.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeByIngredientResponse;
import com.coding.Recipe4U.Classes.ApiRequestManager;
import com.coding.Recipe4U.Classes.Listeners.RecipeByIngredientListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Adapaters.RecipeAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeByIngredientAdapter;

import java.util.ArrayList;

public class RecipeByIngredient extends AppCompatActivity {

    ProgressDialog dialog;
    ApiRequestManager manager;
    String query;
    RecipeByIngredientAdapter recipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    ArrayList<String> tags = new ArrayList<>();
    SearchView searchView;
    Button ingredientSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_by_ingredient);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading.....");

        query = getIntent().getStringExtra("ingre");
        manager = new ApiRequestManager(this);
        manager.getRecipeByIngredients(recipeByIngredientListener, query,"10");
        dialog.show();
    }

    private final RecipeByIngredientListener recipeByIngredientListener = new RecipeByIngredientListener() {
        @Override
        public void didFetch(RecipeByIngredientResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_by_ingredient);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(RecipeByIngredient.this, 1));


            recipeAdapter = new RecipeByIngredientAdapter(RecipeByIngredient.this, response.results, recipeClickListener);

            recyclerView.setAdapter(recipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeByIngredient.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeByIngredient.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };
}
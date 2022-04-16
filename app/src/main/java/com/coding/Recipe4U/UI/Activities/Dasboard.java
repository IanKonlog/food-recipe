package com.coding.Recipe4U.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.ApiModelClasses.RandomRecipeApiResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeByIngredientResponse;
import com.coding.Recipe4U.Classes.UserClasses.ApiRequestManager;
import com.coding.Recipe4U.Classes.Listeners.RandomRecipeResponseListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeByNameListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Adapaters.RecipeAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeByIngredientAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Dasboard extends AppCompatActivity {

    ProgressDialog dialog;
    ApiRequestManager manager;
    RecipeAdapter recipeAdapter;
    RecipeByIngredientAdapter recipeByIngredientAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    ArrayList<String> tags = new ArrayList<>();
    SearchView searchView;
    Button ingredientSearchButton;
    ImageView recipeImage;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dasboard);
        manager = new ApiRequestManager(this);

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dasboard.this, AddRecipe.class);
                startActivity(intent);
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading.....");

        searchView = findViewById(R.id.search_dash);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //tags.clear();
                query.trim();
                //tags.add(query);
                manager.getRecipeByName(recipeByNameListener, query, "20");
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        /*spinner = findViewById(R.id.tags_spinner);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );

        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        manager = new ApiRequestManager(this);
        //manager.getRandomRecipes(randomRecipeResponseListener);
        //dialog.show();*/

        ingredientSearchButton = findViewById(R.id.ingredient_search_btn);
        ingredientSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dasboard.this, SearchByIngredients.class);
                startActivity(intent);
            }
        });

        //recipeImage = findViewById(R.id.recipe_image);


    }

    private final RecipeByNameListener recipeByNameListener =  new RecipeByNameListener() {
        @Override
        public void didFetch(RecipeByIngredientResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(Dasboard.this, 1));


            recipeByIngredientAdapter = new RecipeByIngredientAdapter(Dasboard.this, response.results, recipeClickListener);

            recyclerView.setAdapter(recipeByIngredientAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(Dasboard.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(Dasboard.this, 1));
            //recipeAdapter = new RecipeAdapter(Dasboard.this, response.recipes, recipeClickListener);

            recyclerView.setAdapter(recipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(Dasboard.this, message, Toast.LENGTH_SHORT).show();
        }
    };


   /* private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            manager.getRandomRecipes(randomRecipeResponseListener,tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };*/

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(Dasboard.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };
}
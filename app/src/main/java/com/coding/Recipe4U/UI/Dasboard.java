package com.coding.Recipe4U.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.ApiModelClasses.RandomRecipeApiResponse;
import com.coding.Recipe4U.Classes.ApiRequestManager;
import com.coding.Recipe4U.Classes.Listeners.RandomRecipeResponseListener;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Adapaters.RecipeAdapter;

import java.util.ArrayList;

public class Dasboard extends AppCompatActivity {

    ProgressDialog dialog;
    ApiRequestManager manager;
    RecipeAdapter recipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    ArrayList<String> tags = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading.....");

        searchView = findViewById(R.id.search_dash);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getRandomRecipes(randomRecipeResponseListener, tags);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        spinner = findViewById(R.id.tags_spinner);
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
        //dialog.show();

    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(Dasboard.this, 1));


            recipeAdapter = new RecipeAdapter(Dasboard.this, response.recipes);

            recyclerView.setAdapter(recipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(Dasboard.this, message, Toast.LENGTH_SHORT).show();
        }
    };


    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
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
    };
}
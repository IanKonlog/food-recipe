package com.coding.Recipe4U.UI.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeDetailsResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeInstructionResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeSimilarResponse;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeDetailsListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeInstructionsListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeSimilarListener;
import com.coding.Recipe4U.Classes.UserClasses.ApiRequestManager;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Activities.RecipeDetailsActivity;
import com.coding.Recipe4U.UI.Adapaters.IngredientAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeInstructionAdapater;
import com.coding.Recipe4U.UI.Adapaters.RecipeSimilarAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragmentRecipeDetails extends Fragment {

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
    String key;

    public FragmentRecipeDetails() {
        // Required empty public constructor
    }

    public static FragmentRecipeDetails newInstance(String param1, String param2) {
        FragmentRecipeDetails fragment = new FragmentRecipeDetails();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        Bundle bundle = getArguments();
        String keyId = bundle.getString("key");

        id = Integer.parseInt(keyId);

        manager = new ApiRequestManager(getContext());
        manager.getRecipeDetails(recipeDetailsListener,id);

        manager.getRecipeSimilar(recipeSimilarListener,id);

        manager.getRecipeInstructions(recipeInstructionsListener, id, stepBreakdown);

        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading Details.....");
        dialog.show();

        textRecipeName = view.findViewById(R.id.fragment_text_recipe_name);
        textRecipeSource = view.findViewById(R.id.fragment_text_recipe_source);
        textRecipeSummary = view.findViewById(R.id.fragment_text_recipe_summary);
        imageRecipeDetails = view.findViewById(R.id.fragment_image_recipe_details);
        recyclerRecipeIngredients = view.findViewById(R.id.fragment_recycler_recipe_ingredients);
        recyclerRecipeSimilar = view.findViewById(R.id.fragment_recycler_recipe_similar);
        recyclerRecipeInstructions = view.findViewById(R.id.fragment_recycler_recipe_instructions);


        return view;

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
            recyclerRecipeIngredients.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

            ingredientAdapter = new IngredientAdapter(getContext(), response.extendedIngredients);
            recyclerRecipeIngredients.setAdapter(ingredientAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeSimilarListener recipeSimilarListener = new RecipeSimilarListener() {
        @Override
        public void didFetch(ArrayList<RecipeSimilarResponse> response, String message) {
            recyclerRecipeSimilar.setHasFixedSize(true);
            recyclerRecipeSimilar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            recipeSimilarAdapter = new RecipeSimilarAdapter(getContext(), response, recipeClickListener);
            recyclerRecipeSimilar.setAdapter(recipeSimilarAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            //startActivity(new Intent(getContext(), RecipeDetailsActivity.class)
                    //.putExtra("id", id));

            key = id;
            replaceFragment(new FragmentRecipeDetails());
        }
    };

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment);
        fragmentTransaction.commit();
    }

    private final RecipeInstructionsListener recipeInstructionsListener = new RecipeInstructionsListener() {
        @Override
        public void didFetch(ArrayList<RecipeInstructionResponse> response, String message) {
            recyclerRecipeInstructions.setHasFixedSize(true);
            recyclerRecipeInstructions.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recipeInstructionAdapater = new RecipeInstructionAdapater(getContext(),response);
            recyclerRecipeInstructions.setAdapter(recipeInstructionAdapater);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };
}
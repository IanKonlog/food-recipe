package com.coding.Recipe4U.UI.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeByIngredientResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.Result;
import com.coding.Recipe4U.Classes.Listeners.RecipeByIngredientListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.Classes.UserClasses.ApiRequestManager;
import com.coding.Recipe4U.Classes.UserClasses.Recommendation;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Activities.RecipeByIngredient;
import com.coding.Recipe4U.UI.Activities.RecipeDetailsActivity;
import com.coding.Recipe4U.UI.Adapaters.RecipeByIngredientAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class FragmentRecipeByIngredient extends Fragment {

    ProgressDialog dialog;
    ApiRequestManager manager;
    String query;
    RecipeByIngredientAdapter recipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    ArrayList<String> tags = new ArrayList<>();
    SearchView searchView;
    Button ingredientSearchButton;
    String key;


    public FragmentRecipeByIngredient() {
        // Required empty public constructor
    }

    public static FragmentRecipeByIngredient newInstance(String param1, String param2) {
        FragmentRecipeByIngredient fragment = new FragmentRecipeByIngredient();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_by_ingredient, container, false);

        recyclerView = view.findViewById(R.id.fragment_recycler_by_ingredient);

        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading.....");
        dialog.show();

        Bundle bundle = getArguments();
        query = bundle.getString("ingr");
        manager = new ApiRequestManager(getContext());
        manager.getRecipeByIngredients(recipeByIngredientListener, query,"10");
        dialog.show();


        return view;
    }

    private final RecipeByIngredientListener recipeByIngredientListener = new RecipeByIngredientListener() {
        @Override
        public void didFetch(RecipeByIngredientResponse response, String message) {
            dialog.dismiss();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            Recommendation recommendation = new Recommendation();
            ArrayList<Result> resu = recommendation.sortByPopularity1(response.results);
            recipeAdapter = new RecipeByIngredientAdapter(getContext(), resu, recipeClickListener);
            recyclerView.setAdapter(recipeAdapter);
        }

        @Override
        public void didError(String message) {
            //Snackbar.make(getView(),message)
            //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            //startActivity(new Intent(getContext(), RecipeDetailsActivity.class)
            //      .putExtra("id", id));
            key = id;
            replaceFragment(new FragmentRecipeDetails());
        }
    };

    public final AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            return true;
        }
    };

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}
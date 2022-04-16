package com.coding.Recipe4U.UI.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.coding.Recipe4U.Classes.ApiModelClasses.Recipe;
import com.coding.Recipe4U.Classes.ApiModelClasses.Result;
import com.coding.Recipe4U.Classes.UserClasses.ApiRequestManager;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.Classes.UserClasses.User;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Adapaters.FavoriteRecipeAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeByIngredientAdapter;
import com.coding.Recipe4U.UI.Activities.RecipeDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;


public class FavRecipesShowed extends Fragment {

    ProgressDialog dialog;
    ApiRequestManager manager;
    RecipeAdapter recipeAdapter;
    RecipeByIngredientAdapter recipeByIngredientAdapter;
    FavoriteRecipeAdapter favoriteRecipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    ArrayList<String> tags = new ArrayList<>();
    SearchView searchView;
    Button ingredientSearchButton;
    ImageView recipeImage;
    ArrayList<Result> results;
    ArrayList<Recipe> recipesFavorites;
    String key;

    DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String uID;
    HashMap<String, Recipe> userFavRecipes;


    public FavRecipesShowed() {
        // Required empty public constructor
    }


    public static FavRecipesShowed newInstance(String param1, String param2) {
        FavRecipesShowed fragment = new FavRecipesShowed();
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
        View view = inflater.inflate(R.layout.fragment_fav_recipes_showed, container, false);
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading.....");



        reference = FirebaseDatabase.getInstance().getReference("User");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // User user =  new User();
        uID = firebaseUser.getUid().toString();

        reference.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    if (user.getFavoriteRecipes() != null) {
                        userFavRecipes = new HashMap<>(user.getFavoriteRecipes());
                        recipesFavorites = new ArrayList<>(userFavRecipes.values());

                        System.out.println("Inside");
                        recyclerView = view.findViewById(R.id.recycler_random_fav);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        favoriteRecipeAdapter = new FavoriteRecipeAdapter(getContext(),recipesFavorites,recipeClickListener);
                        recyclerView.setAdapter(favoriteRecipeAdapter);
                        //System.out.println(recipesFavorites);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //recipeByIngredientAdapter = new RecipeByIngredientAdapter(getContext(), response.results, recipeClickListener);
        //results = new ArrayList<>(response.results);

        //

        return view;
    }

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            //startActivity(new Intent(getContext(), RecipeDetailsActivity.class)
              //      .putExtra("id", id));
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

    public final AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            return true;
        }
    };
}
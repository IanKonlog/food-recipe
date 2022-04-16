package com.coding.Recipe4U.UI.Fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.coding.Recipe4U.Classes.ApiModelClasses.Result;
import com.coding.Recipe4U.Classes.UserClasses.ApiRequestManager;
import com.coding.Recipe4U.Classes.UserClasses.Ingredient;
import com.coding.Recipe4U.Classes.UserClasses.RecipeSteps;
import com.coding.Recipe4U.Classes.UserClasses.Recipes;
import com.coding.Recipe4U.Classes.UserClasses.User;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Adapaters.CreatedRecipeAdapter;
import com.coding.Recipe4U.UI.Adapaters.CreatedRecipeAdapterIngredients;
import com.coding.Recipe4U.UI.Adapaters.CreatedRecipeAdapterInstructions;
import com.coding.Recipe4U.UI.Adapaters.FavoriteRecipeAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeByIngredientAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CreatedRecipeDetails extends Fragment {

    ProgressDialog dialog;
    ApiRequestManager manager;
    RecipeAdapter recipeAdapter;
    RecipeByIngredientAdapter recipeByIngredientAdapter;
    FavoriteRecipeAdapter favoriteRecipeAdapter;
    CreatedRecipeAdapter createdRecipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    ArrayList<String> tags = new ArrayList<>();
    SearchView searchView;
    Button ingredientSearchButton;
    ImageView recipeImage;
    ArrayList<Result> results;
    ArrayList<Recipes> recipesCreated;

    CreatedRecipeAdapterIngredients createdRecipeAdapterIngredients;
    CreatedRecipeAdapterInstructions createdRecipeAdapterInstructions;

    DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String uID;
    HashMap<String, Recipes> userCreatedRecipes;

    TextView createdRecipeName, createdRecipeSummary;
    ImageView createdRecipeImage;
    RecyclerView createdRecyclerIngredient, createdRecyclerInstructions;


    Recipes recipesCreated1;


    public CreatedRecipeDetails() {
        // Required empty public constructor
    }


    public static CreatedRecipeDetails newInstance(String param1, String param2) {
        CreatedRecipeDetails fragment = new CreatedRecipeDetails();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_created_recipe_details, container, false);

        Bundle bundle = getArguments();
        String key = bundle.getString("key");

        createdRecipeName = view.findViewById(R.id.created_text_recipe_name);
        createdRecipeSummary = view.findViewById(R.id.created_text_recipe_summary);
        createdRecipeImage = view.findViewById(R.id.created_image_recipe_details);
        createdRecyclerIngredient = view.findViewById(R.id.recycler_created_recipe_ingredients);
        createdRecyclerInstructions = view.findViewById(R.id.recycler_created_recipe_instructions);

        reference = FirebaseDatabase.getInstance().getReference("User");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // User user =  new User();
        uID = firebaseUser.getUid().toString();

        reference.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                HashMap <String,Recipes> recipesCreated = (HashMap<String, Recipes>)user.getUserCreatedRecipes();
                System.out.println(recipesCreated);

                for (Map.Entry mapElement : recipesCreated.entrySet()) {
                    //String key = (String) mapElement.getKey();
                    Recipes CreaRec = (Recipes) mapElement.getValue();
                    String CreaId = String.valueOf(CreaRec.getRecipePicUrl());

                    if (CreaId.equals(key)){
                        recipesCreated1 = (Recipes)mapElement.getValue();
                        System.out.println(mapElement.getKey());
                        break;
                    }
                }

                createdRecipeName.setText(recipesCreated1.getName());
                createdRecipeSummary.setText(recipesCreated1.getDescription());

                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                StorageReference imageRef = storageReference.child("createdRecipes/" + recipesCreated1.getRecipePicUrl());
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(createdRecipeImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                ArrayList<Ingredient> ingredients = new ArrayList<>(recipesCreated1.getIngredients());
                ArrayList<RecipeSteps> recipeSteps = new ArrayList<>(recipesCreated1.getRecipeSteps());

                createdRecyclerIngredient.setHasFixedSize(true);
                createdRecyclerIngredient.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                createdRecipeAdapterIngredients = new CreatedRecipeAdapterIngredients(getContext(),ingredients);
                createdRecyclerIngredient.setAdapter(createdRecipeAdapterIngredients);

                createdRecyclerInstructions.setHasFixedSize(true);
                createdRecyclerInstructions.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                createdRecipeAdapterInstructions = new CreatedRecipeAdapterInstructions(getContext(),recipeSteps);
                createdRecyclerInstructions.setAdapter(createdRecipeAdapterInstructions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
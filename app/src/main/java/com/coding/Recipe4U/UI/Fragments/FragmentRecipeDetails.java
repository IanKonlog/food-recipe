package com.coding.Recipe4U.UI.Fragments;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.util.Log;
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
import com.coding.Recipe4U.Classes.UserClasses.Recipes;
import com.coding.Recipe4U.Classes.UserClasses.User;
import com.coding.Recipe4U.Classes.UserClasses.UserLog;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Activities.RecipeDetailsActivity;
import com.coding.Recipe4U.UI.Adapaters.IngredientAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeInstructionAdapater;
import com.coding.Recipe4U.UI.Adapaters.RecipeSimilarAdapter;
import com.google.android.material.snackbar.Snackbar;
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

    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String ID;

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

        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));

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

            reference = FirebaseDatabase.getInstance().getReference("User");
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            ID = firebaseUser.getUid().toString();
            ArrayList<String> dishes = response.dishTypes;

            reference.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    if (user != null) {
                        if (user.getUserLogs() != null) {
                            HashMap<String, Integer> logs = user.getUserLogs();
                            //HashMap<String, Integer> log = (HashMap<String, Integer>)logs.getLogs();
                            Log.d(TAG, "onDataChange: " + logs.toString());

                            for (String dish : dishes) {
                                if (logs.containsKey(dish)) {
                                    logs.put(dish, logs.get(dish) + 1);
                                } else {
                                    logs.put(dish, 1);
                                }
                            }
                            reference.child(ID).child("userLogs").setValue(logs);
                        } else {
                            HashMap<String, Integer> log = new HashMap<>();
                            for (String dish : dishes) {
                                log.put(dish, 1);
                            }

                            reference.child(ID).child("userLogs").setValue(log);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Snackbar.make(getView(), "Failed to load data", Snackbar.LENGTH_SHORT);
                    //Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_LONG).show();
                }
            });

            Picasso.get().load(response.image).into(imageRecipeDetails);

            recyclerRecipeIngredients.setHasFixedSize(true);
            recyclerRecipeIngredients.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

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
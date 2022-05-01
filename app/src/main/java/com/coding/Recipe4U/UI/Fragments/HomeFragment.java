package com.coding.Recipe4U.UI.Fragments;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.ApiModelClasses.RandomRecipeApiResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeByIngredientResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.Result;
import com.coding.Recipe4U.Classes.Listeners.RandomRecipeResponseListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeByNameListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.Classes.UserClasses.ApiRequestManager;
import com.coding.Recipe4U.Classes.UserClasses.Recommendation;
import com.coding.Recipe4U.Classes.UserClasses.User;
import com.coding.Recipe4U.Classes.UserClasses.UserLog;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Adapaters.RecipeAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeByIngredientAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    FloatingActionButton floatingActionButton;
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
    ArrayList<Result> results;
    String key, query;

    DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    String uID;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        floatingActionButton = view.findViewById(R.id.fab);

        Bundle bundle = getArguments();
        if (bundle != null) {
            boolean keyAdded = bundle.getBoolean("AddRecipe");
            Log.d(TAG, "onCreateView: Home fragment" + keyAdded);
            if (keyAdded) {
                //Snackbar.make(getParentFragment().getView(), "Recipe added", Snackbar.LENGTH_SHORT);
            }
        }

        manager = new ApiRequestManager(getContext());
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Recommending....");
        //dialog.show();
        //tags.add("main course");
        // manager.getRandomRecipes(randomRecipeResponseListener,tags);

        reference = FirebaseDatabase.getInstance().getReference("User");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uID = firebaseUser.getUid().toString();
        query = "";
        //manager.getRecipeByName(recipeByNameListener, query, "20");

        reference.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                dialog.show();
                if (user != null) {
                    if (user.getUserLogs() != null) {
                        HashMap<String, Integer> logs = (HashMap<String, Integer>) user.getUserLogs();
                        //UserLog logs = new UserLog (user.getUserLogs().getLogs());
                        Log.d(TAG, "onDataChange Home: " + logs.toString());
                        dialog.dismiss();
                        Recommendation recommendation = new Recommendation();
                        query = recommendation.recommendDish(logs);
                        manager.getRecipeByName(recipeByNameListener, query, "20");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(getView(), "Failed to load data", Snackbar.LENGTH_SHORT);
                //Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_LONG).show();
            }
        });

        recyclerView = view.findViewById(R.id.recycler_random_home);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getContext(), AddRecipe.class);
                //startActivity(intent);
                replaceFragment(new FragmentAddRecipes());
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                );
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    private final RecipeByNameListener recipeByNameListener = new RecipeByNameListener() {
        @Override
        public void didFetch(RecipeByIngredientResponse response, String message) {
            dialog.dismiss();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            Recommendation recommendation = new Recommendation();
            ArrayList<Result> resu = recommendation.sortByPopularity1(response.results);
            recipeByIngredientAdapter = new RecipeByIngredientAdapter(getContext(), resu, recipeClickListener);
            results = new ArrayList<>(response.results);
            recyclerView.setAdapter(recipeByIngredientAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            dialog.dismiss();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            recipeAdapter = new RecipeAdapter(getContext(), response.recipes, recipeClickListener, onItemLongClickListener);

            recyclerView.setAdapter(recipeAdapter);
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

    public final AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            return true;
        }
    };

}
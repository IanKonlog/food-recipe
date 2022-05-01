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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.ApiModelClasses.RandomRecipeApiResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.Recipe;
import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeByIngredientResponse;
import com.coding.Recipe4U.Classes.ApiModelClasses.Result;
import com.coding.Recipe4U.Classes.UserClasses.ApiRequestManager;
import com.coding.Recipe4U.Classes.Listeners.RandomRecipeResponseListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeByNameListener;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.Classes.UserClasses.Recommendation;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Adapaters.RecipeAdapter;
import com.coding.Recipe4U.UI.Adapaters.RecipeByIngredientAdapter;
import com.coding.Recipe4U.UI.Activities.RecipeDetailsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SAVED_RECYCLER_VIEW_STATUS_ID = "";
    private static final String SAVED_RECYCLER_VIEW_DATASET_ID = "";

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
    String key;

    DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String uID;

    FloatingActionButton floatingActionButton;
    //RecipeClickListener recipeClickListener;
    //RecipeByNameListener recipeByNameListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

       recyclerView.getLayoutManager().onSaveInstanceState();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.search_dash);
        manager = new ApiRequestManager(getContext());
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Loading....");
        dialog.show();
        manager.getRandomRecipes(randomRecipeResponseListener, tags);

        recyclerView = view.findViewById(R.id.recycler_random);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //tags.clear();
                query.trim();
                //tags.add(query);
                manager.getRecipeByName(recipeByNameListener, query, "20");

                //initRecyclerView(view);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    //private void initRecyclerView(View view) {

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
            Recommendation recommendation = new Recommendation();
            ArrayList<Recipe> resu = recommendation.sortByPopularity(response.recipes);
            recipeAdapter = new RecipeAdapter(getContext(), resu, recipeClickListener, onItemLongClickListener);

            recyclerView.setAdapter(recipeAdapter);
        }

        @Override
        public void didError(String message) {
            //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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

    public final AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            return true;
        }
    };

    //}
}
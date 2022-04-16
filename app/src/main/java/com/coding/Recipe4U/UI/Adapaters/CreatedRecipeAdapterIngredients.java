package com.coding.Recipe4U.UI.Adapaters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.Recipe4U.Classes.UserClasses.Ingredient;
import com.coding.Recipe4U.Classes.UserClasses.Recipes;
import com.coding.Recipe4U.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class CreatedRecipeAdapterIngredients extends RecyclerView.Adapter<CreatedRecipeIngredientViewHolder>{
    Context context;
    ArrayList<Recipes> recipes;
    ArrayList<Ingredient> ingredient;
    DatabaseReference reference;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String uID;
    HashMap<String, Recipes> userCreatedRecipes;

    public CreatedRecipeAdapterIngredients(Context context, ArrayList<Ingredient> ingredient) {
        this.context = context;
        this.ingredient = ingredient;
    }

    @NonNull
    @Override
    public CreatedRecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CreatedRecipeIngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.created_recipe_ingredients, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CreatedRecipeIngredientViewHolder holder, int position) {
        holder.ingredientName.setText(ingredient.get(position).getIngredientName());
        holder.ingredientQty.setText(ingredient.get(position).getIngredientQty());
    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }
}

class CreatedRecipeIngredientViewHolder extends RecyclerView.ViewHolder {

    TextView ingredientQty, ingredientName;

    public CreatedRecipeIngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredientQty = itemView.findViewById(R.id.created_text_ingredient_quantity);
        ingredientName = itemView.findViewById(R.id.created_text_ingredient_name);
    }

}

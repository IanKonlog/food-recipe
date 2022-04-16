package com.coding.Recipe4U.UI.Adapaters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.Recipe4U.Classes.UserClasses.RecipeSteps;
import com.coding.Recipe4U.Classes.UserClasses.Recipes;
import com.coding.Recipe4U.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class CreatedRecipeAdapterInstructions extends RecyclerView.Adapter<CreatedRecipeAdapterInstructionsViewHolder>{

    Context context;
    ArrayList<Recipes> recipes;
    ArrayList<RecipeSteps> recipeSteps;
    DatabaseReference reference;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String uID;
    HashMap<String, Recipes> userCreatedRecipes;

    public CreatedRecipeAdapterInstructions(Context context, ArrayList<RecipeSteps> recipes) {
        this.context = context;
        this.recipeSteps = recipes;
    }

    @NonNull
    @Override
    public CreatedRecipeAdapterInstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CreatedRecipeAdapterInstructionsViewHolder(LayoutInflater.from(context).inflate(R.layout.created_recipe_instructions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CreatedRecipeAdapterInstructionsViewHolder holder, int position) {
        holder.instructionTitle.setText(recipeSteps.get(position).getStepDescription());
        holder.instructionNo.setText(String.valueOf(recipeSteps.get(position).getStepNo()+1));
    }

    @Override
    public int getItemCount() {
        return recipeSteps.size();
    }
}

class CreatedRecipeAdapterInstructionsViewHolder extends RecyclerView.ViewHolder {

    TextView instructionNo, instructionTitle;

    public CreatedRecipeAdapterInstructionsViewHolder(@NonNull View itemView) {
        super(itemView);
        instructionNo = itemView.findViewById(R.id.created_text_instruction_step_number);
        instructionTitle = itemView.findViewById(R.id.created_text_instruction_step_title);
    }

}

package com.coding.Recipe4U.UI.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.Recipe4U.Classes.ApiModelClasses.Ingredient;
import com.coding.Recipe4U.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeInstructionIngredientAdapater extends RecyclerView.Adapter<RecipeInstructionIngredientViewHolder>{

    Context context;
    ArrayList<Ingredient> ingredients;

    public RecipeInstructionIngredientAdapater(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public RecipeInstructionIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeInstructionIngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_instructions_step_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeInstructionIngredientViewHolder holder, int position) {
        holder.textInstructionStepItem.setText(ingredients.get(position).name);
        holder.textInstructionStepItem.setSelected(true);

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+ingredients.get(position).image).into(holder.imageInstructionStepItem);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}

class RecipeInstructionIngredientViewHolder extends RecyclerView.ViewHolder{

    ImageView imageInstructionStepItem;
    TextView textInstructionStepItem;

    public RecipeInstructionIngredientViewHolder(@NonNull View itemView) {
        super(itemView);

        imageInstructionStepItem = itemView.findViewById(R.id.image_instructions_step_item);
        textInstructionStepItem = itemView.findViewById(R.id.text_instruction_step_item);
    }
}

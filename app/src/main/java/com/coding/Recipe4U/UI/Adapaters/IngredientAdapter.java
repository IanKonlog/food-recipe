package com.coding.Recipe4U.UI.Adapaters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.Recipe4U.Classes.ApiModelClasses.ExtendedIngredient;
import com.coding.Recipe4U.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder>{

    Context context;
    ArrayList<ExtendedIngredient> extendedIngredients;

    public IngredientAdapter(Context context, ArrayList<ExtendedIngredient> extendedIngredients) {
        this.context = context;
        this.extendedIngredients = extendedIngredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_ingredients, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.textIngredientName.setText(extendedIngredients.get(position).name);
        holder.textIngredientQuantity.setText(extendedIngredients.get(position).original);
        holder.textIngredientName.setSelected(true);
        holder.textIngredientQuantity.setSelected(true);

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+ extendedIngredients.get(position).image).into(holder.imageIngredient);
    }

    @Override
    public int getItemCount() {
        return extendedIngredients.size();
    }
}

class IngredientViewHolder extends RecyclerView.ViewHolder {

    TextView textIngredientQuantity,textIngredientName;
    ImageView imageIngredient;

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);

        textIngredientQuantity = itemView.findViewById(R.id.text_ingredient_quantity);
        textIngredientName = itemView.findViewById(R.id.text_ingredient_name);
        imageIngredient = itemView.findViewById(R.id.image_ingredient);
    }
}

package com.coding.Recipe4U.UI.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.Recipe4U.Classes.ApiModelClasses.Recipe;
import com.coding.Recipe4U.Classes.ApiModelClasses.Result;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeByIngredientAdapter extends RecyclerView.Adapter<RecipeByIngredientViewHolder>{

    Context context;
    ArrayList<Result> recipes;
    RecipeClickListener listener;

    public RecipeByIngredientAdapter(Context context, ArrayList<Result> recipes, RecipeClickListener listener) {
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecipeByIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeByIngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeByIngredientViewHolder holder, int position) {
        holder.recipeTitle.setText(recipes.get(position).title);
        holder.recipeTitle.setSelected(true);
        holder.textLike.setText(recipes.get(position).aggregateLikes+" Likes");
        holder.textServing.setText(recipes.get(position).servings+" Servings");
        holder.textTime.setText(recipes.get(position).readyInMinutes+" Minutes");

        Picasso.get().load(recipes.get(position).image).into(holder.recipeImage);

        holder.randomListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(recipes.get(holder.getAdapterPosition()).id));
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}

class RecipeByIngredientViewHolder extends RecyclerView.ViewHolder {

     CardView randomListContainer;
     TextView recipeTitle, textServing, textLike, textTime;
     ImageView recipeImage, favImage;

    public RecipeByIngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        hooks();


    }

    public void hooks() {
        randomListContainer = itemView.findViewById(R.id.recipe_list_container);
        recipeTitle = itemView.findViewById(R.id.recipe_title);
        textServing = itemView.findViewById(R.id.servings_text);
        textLike = itemView.findViewById(R.id.likes_text);
        textTime = itemView.findViewById(R.id.time_text);
        recipeImage = itemView.findViewById(R.id.recipe_image);
        favImage = itemView.findViewById(R.id.fav_image);

    }
}

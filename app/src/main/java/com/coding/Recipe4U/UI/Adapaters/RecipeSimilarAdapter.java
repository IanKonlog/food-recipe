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

import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeSimilarResponse;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeSimilarAdapter extends RecyclerView.Adapter<RecipeSimilarViewHolder> {

    Context context;
    ArrayList<RecipeSimilarResponse> recipeSimilarResponses;
    RecipeClickListener listener;

    public RecipeSimilarAdapter(Context context, ArrayList<RecipeSimilarResponse> recipeSimilarResponses, RecipeClickListener listener) {
        this.context = context;
        this.recipeSimilarResponses = recipeSimilarResponses;
        this.listener = listener;
    }



    @NonNull
    @Override
    public RecipeSimilarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeSimilarViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_semilar,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSimilarViewHolder holder, int position) {
        holder.textSimilarTitle.setText(recipeSimilarResponses.get(position).title);
        holder.textSimilarTitle.setSelected(true);

        holder.textSimilarServing.setText(recipeSimilarResponses.get(position).servings + " Persons");
        holder.textSimilarServing.setSelected(true);

        Picasso.get().load(" https://spoonacular.com/recipeImages/"+recipeSimilarResponses.get(position).id+"-556x370."+recipeSimilarResponses.get(position).imageType).into(holder.imageSimilar);

        holder.similarRecipeHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(recipeSimilarResponses.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeSimilarResponses.size();
    }
}

class RecipeSimilarViewHolder extends  RecipeViewHolder{
    CardView similarRecipeHolder;
    TextView textSimilarTitle, textSimilarServing;
    ImageView imageSimilar;

    public RecipeSimilarViewHolder(@NonNull View itemView) {
        super(itemView);

        similarRecipeHolder = itemView.findViewById(R.id.similar_recipe_holder);
        textSimilarTitle = itemView.findViewById(R.id.text_similar_title);
        textSimilarServing = itemView.findViewById(R.id.text_similar_serving);
        imageSimilar = itemView.findViewById(R.id.image_similar);
    }
}

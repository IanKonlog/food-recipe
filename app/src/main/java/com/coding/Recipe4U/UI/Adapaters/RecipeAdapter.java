package com.coding.Recipe4U.UI.Adapaters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.Recipe4U.Classes.ApiModelClasses.Recipe;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    Context context;
    ArrayList<Recipe> recipes;
    RecipeClickListener listener;

    DatabaseReference reference;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String uID;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes, RecipeClickListener listener, AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
    }



    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipeTitle.setText(recipes.get(position).title);
        holder.recipeTitle.setSelected(true);
        holder.textLike.setText(recipes.get(position).aggregateLikes+"");
        holder.textServing.setText(recipes.get(position).servings+"");
        holder.textTime.setText(recipes.get(position).readyInMinutes+" Mins");

        Picasso.get().load(recipes.get(position).image).into(holder.recipeImage);

        holder.recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(recipes.get(holder.getAdapterPosition()).id));
            }
        });

        holder.randomListContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("User");
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                // User user =  new User();
                uID = firebaseUser.getUid().toString();

                String key = reference.child(uID).child("favoriteRecipes").push().getKey();
                reference.child(uID).child("favoriteRecipes").child(key).setValue(recipes.get(holder.getAdapterPosition()));

                ImageView image = view.findViewById(R.id.fav_image);
                image.setImageResource(R.drawable.ic_baseline_favorite_24_red);
                Toast.makeText(view.getContext(), "Added to Favorites", Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}


class RecipeViewHolder extends RecyclerView.ViewHolder {

     CardView randomListContainer;
     TextView recipeTitle, textServing, textLike, textTime;
     ImageView recipeImage, favImage;

    public RecipeViewHolder(@NonNull View itemView) {
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

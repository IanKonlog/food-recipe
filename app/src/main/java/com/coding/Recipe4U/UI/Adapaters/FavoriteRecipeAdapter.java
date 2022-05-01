package com.coding.Recipe4U.UI.Adapaters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.Recipe4U.Classes.ApiModelClasses.Recipe;
import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.Classes.UserClasses.User;
import com.coding.Recipe4U.R;
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
import java.util.Map;

public class FavoriteRecipeAdapter extends RecyclerView.Adapter<FavoriteRecipeViewHolder> {

    Context context;
    ArrayList<Recipe> recipes;
    RecipeClickListener listener;

    DatabaseReference reference;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String uID;
    HashMap<String, Recipe> userFavRecipes;

    public FavoriteRecipeAdapter(Context context, ArrayList<Recipe> recipes, RecipeClickListener listener) {
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
    }


    @NonNull
    @Override
    public FavoriteRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_fragment_fav_recipes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteRecipeViewHolder holder, int position) {
        holder.recipeTitle.setText(recipes.get(position).title);
        holder.recipeTitle.setSelected(true);
        holder.textLike.setText(recipes.get(position).aggregateLikes+"");
        holder.textServing.setText(recipes.get(position).servings+"");
        holder.textTime.setText(recipes.get(position).readyInMinutes +" Mins");

        Picasso.get().load(recipes.get(position).image).into(holder.recipeImage);

        holder.recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(recipes.get(holder.getAdapterPosition()).id));
            }
        });

        holder.delImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("User");
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                // User user =  new User();
                uID = firebaseUser.getUid().toString();


                reference.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            if (user.getFavoriteRecipes() != null) {
                                userFavRecipes = new HashMap<>(user.getFavoriteRecipes());

                                for (Map.Entry mapElement : userFavRecipes.entrySet()) {
                                    String key = (String) mapElement.getKey();
                                    Recipe favRec = (Recipe) mapElement.getValue();
                                    String favId = String.valueOf(favRec.id);

                                    if (favId.equals(String.valueOf(recipes.get(holder.getAdapterPosition()).id))) {
                                        System.out.println(key);
                                        reference.child(uID).child("favoriteRecipes").child(key).removeValue(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                recipes.remove(holder.getAdapterPosition());
                                                notifyDataSetChanged();
                                                Snackbar.make(view, "Recipe Removed successfully", Snackbar.LENGTH_SHORT).show();
                                                //Toast.makeText(view.getContext(), "Removed Successfully", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        break;
                                    }
                                }

                            }

                            if (user.getUserLogs() != null) {
                                ArrayList<String> dishes = recipes.get(holder.getAdapterPosition()).dishTypes;
                                HashMap<String, Integer> logs = (HashMap<String, Integer>) user.getUserLogs();

                                for (String dish : dishes) {
                                    if (logs.containsKey(dish) && logs.get(dish) > 0) {
                                        logs.put(dish, logs.get(dish) - 1);
                                    }
                                }

                                reference.child(uID).child("userLogs").setValue(logs);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Snackbar.make(view, "Failed to load data", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(view.getContext(), "Failed to load data", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

}

class FavoriteRecipeViewHolder extends RecyclerView.ViewHolder {

    CardView recipeFavListContainer;
    TextView recipeTitle, textServing, textLike, textTime;
    ImageView recipeImage, delImage;

    public FavoriteRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeFavListContainer = itemView.findViewById(R.id.recipe_fav_list_container);
        recipeTitle = itemView.findViewById(R.id.recipe_fav_title);
        textServing = itemView.findViewById(R.id.text_fav_servings);
        textLike = itemView.findViewById(R.id.text_fav_likes);
        textTime = itemView.findViewById(R.id.text_fav_time);
        recipeImage = itemView.findViewById(R.id.recipe_fav_image);
        delImage = itemView.findViewById(R.id.del_fav_image);
        //hooks();

    }

    public void hooks() {

    }
}


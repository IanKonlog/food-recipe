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

import com.coding.Recipe4U.Classes.Listeners.RecipeClickListener;
import com.coding.Recipe4U.Classes.UserClasses.Recipes;
import com.coding.Recipe4U.Classes.UserClasses.User;
import com.coding.Recipe4U.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class CreatedRecipeAdapter extends RecyclerView.Adapter<CreatedRecipeViewHolder> {

    Context context;
    ArrayList<Recipes> recipes;
    RecipeClickListener listener;

    DatabaseReference reference;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String uID;
    HashMap<String, Recipes> userCreatedRecipes;

    public CreatedRecipeAdapter(Context context, ArrayList<Recipes> recipes, RecipeClickListener listener) {
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CreatedRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CreatedRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_fragment_fav_recipes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CreatedRecipeViewHolder holder, int position) {
        holder.recipeTitle.setText(recipes.get(position).getName());
        holder.recipeTitle.setSelected(true);
        holder.textLike.setText("0");
        holder.textServing.setText(recipes.get(position).getServings()+"");
        holder.textTime.setText(recipes.get(position).getTime() +" Mins");

        String url = recipes.get(position).getRecipePicUrl();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference imageRef = storageReference.child("createdRecipes/" + url);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.recipeImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        //Picasso.get().load(recipes.get(position).image).into(holder.recipeImage);

        holder.recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = recipes.get(holder.getAdapterPosition()).getRecipePicUrl();
                //int length = url.length();
                //url = url.substring(5,length-21);
                listener.onRecipeClicked(url);
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
                            if (user.getUserCreatedRecipes() != null) {
                                userCreatedRecipes = new HashMap<>(user.getUserCreatedRecipes());

                                for (Map.Entry mapElement : userCreatedRecipes.entrySet()) {
                                    String key = (String) mapElement.getKey();
                                    Recipes CreaRec = (Recipes) mapElement.getValue();
                                    String CreaId = String.valueOf(CreaRec.getRecipeId());

                                    if (CreaId.equals(String.valueOf(recipes.get(holder.getAdapterPosition()).getRecipeId()))) {
                                        System.out.println(key);
                                        reference.child(uID).child("userCreatedRecipes").child(key).removeValue(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                recipes.remove(holder.getAdapterPosition());
                                                notifyDataSetChanged();
                                                Snackbar.make(view, "Recipe removed successfully", Snackbar.LENGTH_SHORT).show();
                                                //Toast.makeText(view.getContext(), "Removed Successfully", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        break;
                                    }
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(view.getContext(), "Failed to load data", Toast.LENGTH_LONG).show();
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

class CreatedRecipeViewHolder extends RecyclerView.ViewHolder {

    CardView recipeFavListContainer;
    TextView recipeTitle, textServing, textLike, textTime;
    ImageView recipeImage, delImage;

    public CreatedRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeFavListContainer = itemView.findViewById(R.id.recipe_fav_list_container);
        recipeTitle = itemView.findViewById(R.id.recipe_fav_title);
        textServing = itemView.findViewById(R.id.text_fav_servings);
        textLike = itemView.findViewById(R.id.text_fav_likes);
        textTime = itemView.findViewById(R.id.text_fav_time);
        recipeImage = itemView.findViewById(R.id.recipe_fav_image);
        delImage = itemView.findViewById(R.id.del_fav_image);
    }

}


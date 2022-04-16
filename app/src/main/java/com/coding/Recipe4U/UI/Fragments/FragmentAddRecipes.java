package com.coding.Recipe4U.UI.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.UserClasses.Ingredient;
import com.coding.Recipe4U.Classes.Misc.CSVFile;
import com.coding.Recipe4U.Classes.UserClasses.RecipeSteps;
import com.coding.Recipe4U.Classes.UserClasses.Recipes;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Activities.TestActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentAddRecipes extends Fragment {

    TextView addRecipeCuisine;
    LinearLayout layoutList, layoutListStep, layoutListPicture;
    Button addRecipeIngredientAddBtn, addRecipeStepsAddBtn, addRecipeImageAddBtn,addRecipeCreateBtn;
    ImageView addRecipeImage;
    final int REQUEST_IMAGE_CAPTURE = 1;

    String imageFileName;
    DatabaseReference reference;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    EditText addRecipeName, addRecipeSummary, addRecipeTime, addRecipeServings;

    ArrayList<String> cuisines;
    Dialog dialog;
    ArrayList<String> ingredients;
    ArrayList<Ingredient> addedIngre;
    ArrayList<RecipeSteps> recipeSteps;
    ArrayList<String[]> ingredientsWithId;
    ArrayList<Recipes> recipesCreated;
    int id;
    String uID;

    public FragmentAddRecipes() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentAddRecipes newInstance(String param1, String param2) {
        FragmentAddRecipes fragment = new FragmentAddRecipes();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipes, container, false);
        reference = FirebaseDatabase.getInstance().getReference("User");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        addRecipeCuisine = view.findViewById(R.id.fragment_add_recipe_cuisine);
        layoutList = view.findViewById(R.id.layout_list);
        layoutListStep = view.findViewById(R.id.layout_list_steps);
        addRecipeIngredientAddBtn = view.findViewById(R.id.fragment_add_recipe_ingredient_addBtn);
        addRecipeStepsAddBtn = view.findViewById(R.id.fragment_add_recipe_steps_addBtn);
        addRecipeImageAddBtn = view.findViewById(R.id.fragment_add_recipe_image_addBtn);
        layoutListPicture = view.findViewById(R.id.layout_list_Picture);
        addRecipeImage = view.findViewById(R.id.fragment_add_recipe_image);
        addRecipeCreateBtn = view.findViewById(R.id.fragment_add_recipe_createBtn);

        addRecipeName = view.findViewById(R.id.fragment_add_recipe_name);
        addRecipeSummary = view.findViewById(R.id.fragment_add_recipe_summary);
        addRecipeTime = view.findViewById(R.id.fragment_add_recipe_time);
        addRecipeServings = view.findViewById(R.id.fragment_add_recipe_servings);

        addedIngre = new ArrayList<>();
        recipeSteps = new ArrayList<>();
        recipesCreated = new ArrayList<>();

        id = -1;
        imageFileName = "";

        addRecipeIngredientAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView();
            }
        });

        addRecipeStepsAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStepsView();
            }
        });


        addRecipeImageAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage(view);
            }
        });

        //Add Recipe
        addRecipeCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(proceedDetails() && proceedIng() && proceedSteps() && !imageFileName.equals("")){

                    reference = FirebaseDatabase.getInstance().getReference("User");
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    // User user =  new User();
                    uID = firebaseUser.getUid().toString();

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    Recipes addedRecipe = new Recipes(uID+timeStamp,Integer.parseInt(addRecipeTime.getText().toString()),
                            addRecipeName.getText().toString(),Integer.parseInt(addRecipeServings.getText().toString()),addRecipeSummary.getText().toString(), addRecipeCuisine.getText().toString(),
                            imageFileName, addedIngre, recipeSteps);

                    recipesCreated.add(addedRecipe);
                    System.out.println(addedRecipe);

                    //reference.child(uID).child("userCreatedRecipes").push().getKey().setValue(recipesCreated);
                    String key = reference.child(uID).child("userCreatedRecipes").push().getKey();
                    reference.child(uID).child("userCreatedRecipes").child(key).setValue(addedRecipe);

                    Toast.makeText(getContext(), "Recipe Created", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getContext(), TestActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });


        InputStream inputStream = getResources().openRawResource(R.raw.ingredients);
        CSVFile csvFile = new CSVFile(inputStream);
        ingredients = csvFile.read();
        cuisines = new ArrayList<>();

        InputStream inputStream1 = getResources().openRawResource(R.raw.ingredients);
        CSVFile csvFile1 = new CSVFile(inputStream1);
        ingredientsWithId = csvFile1.read1();

        AddCuisines();
        listenerAddCuisines();

        return view;
    }

    private boolean proceedDetails() {

        if (!addRecipeName.getText().toString().equals("") && !addRecipeSummary.getText().toString().equals("")
                && !addRecipeCuisine.getText().toString().equals("") && !String.valueOf(addRecipeTime.getText().toString()).equals("")
                && !String.valueOf(addRecipeServings.getText().toString()).equals("")){

            return true;
        }

        return false;
    }

    private boolean proceedSteps() {
        recipeSteps.clear();
        boolean proceed = true;
        String instruc;

        for (int i=0; i<layoutListStep.getChildCount(); i++){
            View stepView = layoutListStep.getChildAt(i);

            EditText instruction = (EditText) stepView.findViewById(R.id.add_recipe_instruction);

            if(!instruction.getText().toString().equals("")){
                instruc = instruction.getText().toString();
            }
            else{
                proceed = false;
                break;
            }

            RecipeSteps recipeStep = new RecipeSteps(i, instruc);
            recipeSteps.add(recipeStep);
        }

        return proceed;
    }

    private boolean proceedIng() {
        addedIngre.clear();
        boolean proceed = true;
        String[] ingredientAndId;
        String ingredientId;
        String quant;
        String ing;

        for (int i=0; i<layoutList.getChildCount(); i++){
            View ingredientView = layoutList.getChildAt(i);
            EditText ingred = (EditText) ingredientView.findViewById(R.id.recipe_add_ingredient);
            EditText qty = (EditText) ingredientView.findViewById(R.id.add_recipe_quantity);

            id = ingredients.indexOf(ingred.getText().toString()) + 1;

            if(!ingred.getText().toString().equals("")){
                ing = ingred.getText().toString();
            }
            else{
                proceed = false;
                break;
            }
            if(!qty.getText().toString().equals("")) {
                quant = qty.getText().toString();
            }
            else{
                proceed = false;
                break;
            }

            if(id != -1) {
                ingredientAndId = ingredientsWithId.get(id);
                ingredientId = ingredientAndId[1];
            }
            else{
                proceed = false;
                break;
            }

            // Ingredient ingredient = new Ingredient(Integer.parseInt(ingredientId),ing,quant);
            Ingredient ingredient = new Ingredient(id,ing,quant);
            addedIngre.add(ingredient);
        }

        return proceed;
    }


    private void loadImage(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.setType("image/*");
        //intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            addRecipeImage.setImageBitmap(imageBitmap);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            reference = FirebaseDatabase.getInstance().getReference("User");
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            // User user =  new User();
            uID = firebaseUser.getUid().toString();
            imageFileName = "JPEG_"+uID+timeStamp+"_.jpg";

            StorageReference createdRec = storageReference.child("createdRecipes/"+imageFileName);
            addRecipeImage.setDrawingCacheEnabled(true);
            addRecipeImage.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) addRecipeImage.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data1 = baos.toByteArray();

            UploadTask uploadTask = createdRec.putBytes(data1);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });
        }
    }

    private void uploadPicture(Uri imageUri) {

        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Creating Recipe....");


        StorageReference profileRef = storageReference.child("CreatedRecipes/").child(imageFileName);
        reference = FirebaseDatabase.getInstance().getReference("User");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // User user =  new User();
        uID = firebaseUser.getUid().toString();

        profileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                //uploadedPicture = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getContext(), "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress: " + (int) progressPercent + "%");
            }
        });

    }

    private void addStepsView() {
        View stepAdded = getLayoutInflater().inflate(R.layout.row_add_recipe_steps, null, false);
        EditText recipeAddedStep = (EditText) stepAdded.findViewById(R.id.add_recipe_instruction);
        ImageView removeRecipeStep = (ImageView) stepAdded.findViewById(R.id.remove_recipe_step);

        layoutListStep.addView(stepAdded);
        removeRecipeStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeStepView(stepAdded);
            }
        });
    }

    private void removeStepView(View stepAdded) {
        layoutListStep.removeView(stepAdded);
    }

    private void removeView(View view) {
        layoutList.removeView(view);
    }

    private void addView() {
        View ingredientAdded = getLayoutInflater().inflate(R.layout.row_add_ingredient, null, false);
        EditText recipeAddIngredient = (EditText) ingredientAdded.findViewById(R.id.recipe_add_ingredient);
        ImageView removeRecipeIngredient = (ImageView) ingredientAdded.findViewById(R.id.remove_recipe_ingredient);

        layoutList.addView(ingredientAdded);

        listenerAddIngredient(recipeAddIngredient);

        removeRecipeIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(ingredientAdded);
            }
        });
    }



    private void listenerAddIngredient(EditText recipeAddIngredient) {
        recipeAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getContext());

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ingredients);
                listIng.setAdapter(adapter);

                searchIng.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listIng.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        recipeAddIngredient.setText(adapter.getItem(i).toString());
                        //addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void listenerAddCuisines() {
        addRecipeCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getContext());

                dialog.setContentView(R.layout.cuisine_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_cuisine);
                ListView listIng = dialog.findViewById(R.id.list_view_cuisine);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, cuisines);
                listIng.setAdapter(adapter);

                searchIng.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listIng.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        addRecipeCuisine.setText(adapter.getItem(i).toString());
                        //addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void AddCuisines() {
        cuisines.add("African");
        cuisines.add("American");
        cuisines.add("British");
        cuisines.add("Cajun");
        cuisines.add("Carribean");
        cuisines.add("Chinese");
        cuisines.add("Eastern European");
        cuisines.add("European");
        cuisines.add("French");
        cuisines.add("German");
        cuisines.add("Greek");
        cuisines.add("Indian");
        cuisines.add("Irish");
        cuisines.add("Italian");
        cuisines.add("Japanese");
        cuisines.add("Jewish");
        cuisines.add("Korean");
        cuisines.add("Latin American");
        cuisines.add("Mediterranean");
        cuisines.add("Mexican");
        cuisines.add("Middle Eastern");
        cuisines.add("Nordic");
        cuisines.add("Southern");
        cuisines.add("Spanish");
        cuisines.add("Thai");
        cuisines.add("Vietnamese");
    }

}
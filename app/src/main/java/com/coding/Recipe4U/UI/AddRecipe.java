package com.coding.Recipe4U.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coding.Recipe4U.Classes.Ingredient;
import com.coding.Recipe4U.Classes.Misc.CSVFile;
import com.coding.Recipe4U.Classes.RecipeSteps;
import com.coding.Recipe4U.Classes.Recipes;
import com.coding.Recipe4U.R;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddRecipe extends AppCompatActivity {

    TextView addRecipeCuisine;
    LinearLayout layoutList, layoutListStep, layoutListPicture;
    Button addRecipeIngredientAddBtn, addRecipeStepsAddBtn, addRecipeImageAddBtn,addRecipeCreateBtn;
    ImageView addRecipeImage;
    final int REQUEST_IMAGE_CAPTURE = 1;
    Uri imageUri;
    String imageFileName;

    EditText addRecipeName, addRecipeSummary, addRecipeTime, addRecipeServings;

    ArrayList<String> cuisines;
    Dialog dialog;
    ArrayList<String> ingredients;
    ArrayList<Ingredient> addedIngre;
    ArrayList<RecipeSteps> recipeSteps;
    ArrayList<String[]> ingredientsWithId;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        hooks();
        addedIngre = new ArrayList<>();
        recipeSteps = new ArrayList<>();
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
                changeProfile(view);
            }
        });

        addRecipeCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(proceedDetails() && proceedIng() && proceedSteps() && !imageFileName.equals("")){
                    Recipes addedRecipe = new Recipes(1,Integer.parseInt(addRecipeTime.getText().toString()),
                            1,addRecipeName.getText().toString(),Integer.parseInt(addRecipeServings.getText().toString()),addRecipeSummary.getText().toString(), addRecipeCuisine.getText().toString(),
                            imageFileName, addedIngre, recipeSteps);

                    System.out.println(addedRecipe);
                }

                //else{
                    //Toast.makeText(AddRecipe.this, "Please fill Out all the fields", Toast.LENGTH_SHORT).show();
                //}
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


    private void changeProfile(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.setType("image/*");
        //intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode ==RESULT_OK && data != null){

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            addRecipeImage.setImageBitmap(imageBitmap);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            imageFileName = "JPEG_"+timeStamp+"_";
            //imageUri = data.getData();
            //addRecipeImage.setImageURI(imageUri);

            //uploadPicture();
        }
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
                dialog = new Dialog(AddRecipe.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddRecipe.this, android.R.layout.simple_list_item_1, ingredients);
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
                dialog = new Dialog(AddRecipe.this);

                dialog.setContentView(R.layout.cuisine_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_cuisine);
                ListView listIng = dialog.findViewById(R.id.list_view_cuisine);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddRecipe.this, android.R.layout.simple_list_item_1, cuisines);
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

    private void hooks() {
        addRecipeCuisine = findViewById(R.id.add_recipe_cuisine);
        layoutList = findViewById(R.id.layout_list);
        layoutListStep = findViewById(R.id.layout_list_steps);
        addRecipeIngredientAddBtn = findViewById(R.id.add_recipe_ingredient_addBtn);
        addRecipeStepsAddBtn = findViewById(R.id.add_recipe_steps_addBtn);
        addRecipeImageAddBtn = findViewById(R.id.add_recipe_image_addBtn);
        layoutListPicture = findViewById(R.id.layout_list_Picture);
        addRecipeImage = findViewById(R.id.add_recipe_image);
        addRecipeCreateBtn = findViewById(R.id.add_recipe_createBtn);

        addRecipeName = findViewById(R.id.add_recipe_name);
        addRecipeSummary = findViewById(R.id.add_recipe_summary);
        addRecipeTime = findViewById(R.id.add_recipe_time);
        addRecipeServings = findViewById(R.id.add_recipe_servings);
    }
}
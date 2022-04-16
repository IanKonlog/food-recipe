package com.coding.Recipe4U.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.coding.Recipe4U.Classes.Misc.CSVFile;
import com.coding.Recipe4U.R;
import com.coding.Recipe4U.UI.Adapaters.RecipeAdapter;

import java.io.InputStream;
import java.util.ArrayList;

public class SearchByIngredients extends AppCompatActivity {

    TextView ingredientSelect1, ingredientSelect2, ingredientSelect3, ingredientSelect4, ingredientSelect5,
            ingredientSelect6, ingredientSelect7, ingredientSelect8, ingredientSelect9,ingredientSelect10;

    Button searchByIng;

    ArrayList<String> ingredients;
    Dialog dialog;
    ArrayList<String> addedIngredients;

    RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_by_ingredients);

        hooks();

        InputStream inputStream = getResources().openRawResource(R.raw.ingredients);
        CSVFile csvFile = new CSVFile(inputStream);

        ingredients = csvFile.read();
        ingredients.add(0,"");

        addedIngredients = new ArrayList<>();


        listener1();
        listener2();
        listener3();
        listener4();
        listener5();
        listener6();
        listener7();
        listener8();
        listener9();
        listener10();

        //Todo: send recipe details to recycler view and display with api call
        searchByIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ing = stringApiPassing();
                addedIngredients.clear();
                ingredientSelect1.setText(ingredients.get(0));
                ingredientSelect2.setText(ingredients.get(0));
                ingredientSelect3.setText(ingredients.get(0));
                ingredientSelect4.setText(ingredients.get(0));
                ingredientSelect5.setText(ingredients.get(0));
                ingredientSelect6.setText(ingredients.get(0));
                ingredientSelect7.setText(ingredients.get(0));
                ingredientSelect8.setText(ingredients.get(0));
                ingredientSelect9.setText(ingredients.get(0));
                ingredientSelect10.setText(ingredients.get(0));
                System.out.println(ing);
                Intent intent = new Intent(SearchByIngredients.this, RecipeByIngredient.class);
                startActivity(intent.putExtra("ingre", ing));
            }
        });

    }

    private String stringApiPassing() {

        String returnedString = "";
        if(!addedIngredients.isEmpty()){
            for (String str: addedIngredients){
                if (!str.equals("")){
                    returnedString += str+",";
                }
            }

            returnedString = returnedString.substring(0,returnedString.length()-1);
        }

        return returnedString;
    }

    private void listener1() {
        ingredientSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SearchByIngredients.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);



                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchByIngredients.this, android.R.layout.simple_list_item_1, ingredients);
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
                        ingredientSelect1.setText(adapter.getItem(i).toString());
                        addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void listener2() {
        ingredientSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SearchByIngredients.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchByIngredients.this, android.R.layout.simple_list_item_1, ingredients);
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
                        ingredientSelect2.setText(adapter.getItem(i).toString());
                        addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void listener3() {
        ingredientSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SearchByIngredients.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchByIngredients.this, android.R.layout.simple_list_item_1, ingredients);
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
                        ingredientSelect3.setText(adapter.getItem(i).toString());
                        addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void listener4() {
        ingredientSelect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SearchByIngredients.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchByIngredients.this, android.R.layout.simple_list_item_1, ingredients);
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
                        ingredientSelect4.setText(adapter.getItem(i).toString());
                        addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void listener5() {
        ingredientSelect5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SearchByIngredients.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchByIngredients.this, android.R.layout.simple_list_item_1, ingredients);
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
                        ingredientSelect5.setText(adapter.getItem(i).toString());
                        addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void listener6() {
        ingredientSelect6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SearchByIngredients.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchByIngredients.this, android.R.layout.simple_list_item_1, ingredients);
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
                        ingredientSelect6.setText(adapter.getItem(i).toString());
                        addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void listener7() {
        ingredientSelect7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SearchByIngredients.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchByIngredients.this, android.R.layout.simple_list_item_1, ingredients);
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
                        ingredientSelect7.setText(adapter.getItem(i).toString());
                        addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void listener8() {
        ingredientSelect8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SearchByIngredients.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchByIngredients.this, android.R.layout.simple_list_item_1, ingredients);
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
                        ingredientSelect8.setText(adapter.getItem(i).toString());
                        addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void listener9() {
        ingredientSelect9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SearchByIngredients.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchByIngredients.this, android.R.layout.simple_list_item_1, ingredients);
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
                        ingredientSelect9.setText(adapter.getItem(i).toString());
                        addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void listener10() {
        ingredientSelect10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SearchByIngredients.this);

                dialog.setContentView(R.layout.ingredient_search_spinner);

                dialog.getWindow().setLayout(700,900);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                EditText searchIng = dialog.findViewById(R.id.edit_ing);
                ListView listIng = dialog.findViewById(R.id.list_view_ing);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchByIngredients.this, android.R.layout.simple_list_item_1, ingredients);
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
                        ingredientSelect10.setText(adapter.getItem(i).toString());
                        addedIngredients.add(adapter.getItem(i).toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }




    private void hooks() {
        //TODO: Setup dynamic hooks
        ingredientSelect1 = findViewById(R.id.ing_select1);
        ingredientSelect2 = findViewById(R.id.ing_select2);
        ingredientSelect3 = findViewById(R.id.ing_select3);
        ingredientSelect4 = findViewById(R.id.ing_select4);
        ingredientSelect5 = findViewById(R.id.ing_select5);
        ingredientSelect6 = findViewById(R.id.ing_select6);
        ingredientSelect7 = findViewById(R.id.ing_select7);
        ingredientSelect8 = findViewById(R.id.ing_select8);
        ingredientSelect9 = findViewById(R.id.ing_select9);
        ingredientSelect10 = findViewById(R.id.ing_select10);

        searchByIng = findViewById(R.id.search_by_ing);
    }
}
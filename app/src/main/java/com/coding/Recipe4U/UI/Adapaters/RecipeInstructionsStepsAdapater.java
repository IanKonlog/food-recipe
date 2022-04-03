package com.coding.Recipe4U.UI.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.Recipe4U.Classes.ApiModelClasses.Step;
import com.coding.Recipe4U.R;

import java.util.ArrayList;

public class RecipeInstructionsStepsAdapater extends RecyclerView.Adapter<RecipeInstructionsStepsViewHolder>{

    Context context;
    ArrayList<Step> steps;

    public RecipeInstructionsStepsAdapater(Context context, ArrayList<Step> steps) {
        this.context = context;
        this.steps = steps;
    }


    @NonNull
    @Override
    public RecipeInstructionsStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeInstructionsStepsViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_instructions_steps,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeInstructionsStepsViewHolder holder, int position) {
        holder.textInstructionStepNumber.setText(String.valueOf(steps.get(position).number));
        holder.textInstructionStepTitle.setText(steps.get(position).step);
        //holder.textInstructionStepLength.setText(String.valueOf(steps.get(position).length.number) + " "+ steps.get(position).length.unit);

        holder.recyclerInstructionIngredients.setHasFixedSize(true);
        holder.recyclerInstructionIngredients.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        RecipeInstructionIngredientAdapater recipeInstructionIngredientAdapater = new RecipeInstructionIngredientAdapater(context, steps.get(position).ingredients);
        holder.recyclerInstructionIngredients.setAdapter(recipeInstructionIngredientAdapater);


        holder.recyclerInstructionEquipment.setHasFixedSize(true);
        holder.recyclerInstructionEquipment.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        RecipeInstructionEquipmentAdapater recipeInstructionEquipmentAdapater = new RecipeInstructionEquipmentAdapater(context, steps.get(position).equipment);
        holder.recyclerInstructionEquipment.setAdapter(recipeInstructionEquipmentAdapater);

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
}

class RecipeInstructionsStepsViewHolder extends RecyclerView.ViewHolder{

    TextView textInstructionStepNumber, textInstructionStepTitle, textInstructionStepLength;
    RecyclerView recyclerInstructionEquipment, recyclerInstructionIngredients;

    public RecipeInstructionsStepsViewHolder(@NonNull View itemView) {
        super(itemView);
        textInstructionStepNumber = itemView.findViewById(R.id.text_instruction_step_number);
        textInstructionStepTitle = itemView.findViewById(R.id.text_instruction_step_title);
        //textInstructionStepLength = itemView.findViewById(R.id.text_instruction_step_length);
        recyclerInstructionEquipment = itemView.findViewById(R.id.recycler_instruction_equipment);
        recyclerInstructionIngredients = itemView.findViewById(R.id.recycler_instruction_Ingredients);
    }
}

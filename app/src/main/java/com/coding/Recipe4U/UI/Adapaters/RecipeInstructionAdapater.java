package com.coding.Recipe4U.UI.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.Recipe4U.Classes.ApiModelClasses.RecipeInstructionResponse;
import com.coding.Recipe4U.R;

import java.util.ArrayList;

public class RecipeInstructionAdapater extends RecyclerView.Adapter<RecipeInstructionViewHolder>{

    public RecipeInstructionAdapater(Context context, ArrayList<RecipeInstructionResponse> recipeInstructionResponses) {
        this.context = context;
        this.recipeInstructionResponses = recipeInstructionResponses;
    }

    Context context;
    ArrayList<RecipeInstructionResponse> recipeInstructionResponses;


    @NonNull
    @Override
    public RecipeInstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeInstructionViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_instructions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeInstructionViewHolder holder, int position) {
        holder.textInstructionName.setText(recipeInstructionResponses.get(position).name);
        holder.recycleInstructionSteps.setHasFixedSize(true);
        holder.recycleInstructionSteps.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        RecipeInstructionsStepsAdapater recipeInstructionsStepsAdapater = new RecipeInstructionsStepsAdapater(context, recipeInstructionResponses.get(position).steps);
        holder.recycleInstructionSteps.setAdapter(recipeInstructionsStepsAdapater);
    }

    @Override
    public int getItemCount() {
        return recipeInstructionResponses.size();
    }
}

class RecipeInstructionViewHolder extends RecyclerView.ViewHolder {
    TextView textInstructionName;
    RecyclerView recycleInstructionSteps;

    public RecipeInstructionViewHolder(@NonNull View itemView) {
        super(itemView);

        textInstructionName = itemView.findViewById(R.id.text_instruction_name);
        recycleInstructionSteps = itemView.findViewById(R.id.recycler_instructions_steps);
    }
}

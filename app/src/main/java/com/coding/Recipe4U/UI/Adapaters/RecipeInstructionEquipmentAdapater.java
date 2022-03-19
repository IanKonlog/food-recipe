package com.coding.Recipe4U.UI.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.Recipe4U.Classes.ApiModelClasses.Equipment;
import com.coding.Recipe4U.Classes.ApiModelClasses.Ingredient;
import com.coding.Recipe4U.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeInstructionEquipmentAdapater extends RecyclerView.Adapter<RecipeInstructionEquipmentViewHolder>{

    Context context;
    ArrayList<Equipment> equipment;

    public RecipeInstructionEquipmentAdapater(Context context, ArrayList<Equipment> equipment) {
        this.context = context;
        this.equipment = equipment;
    }

    @NonNull
    @Override
    public RecipeInstructionEquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeInstructionEquipmentViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_instructions_step_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeInstructionEquipmentViewHolder holder, int position) {
        holder.textInstructionStepItem.setText(equipment.get(position).name);
        holder.textInstructionStepItem.setSelected(true);

        Picasso.get().load("https://spoonacular.com/cdn/equipment_100x100/"+equipment.get(position).image).into(holder.imageInstructionStepItem);
    }

    @Override
    public int getItemCount() {
        return equipment.size();
    }
}

class RecipeInstructionEquipmentViewHolder extends RecyclerView.ViewHolder{

    ImageView imageInstructionStepItem;
    TextView textInstructionStepItem;

    public RecipeInstructionEquipmentViewHolder(@NonNull View itemView) {
        super(itemView);

        imageInstructionStepItem = itemView.findViewById(R.id.image_instructions_step_item);
        textInstructionStepItem = itemView.findViewById(R.id.text_instruction_step_item);
    }
}

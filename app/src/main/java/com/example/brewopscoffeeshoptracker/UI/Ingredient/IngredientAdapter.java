package com.example.brewopscoffeeshoptracker.UI.Ingredient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    public interface onIngredientClickListener {
        void onIngredientClick(Ingredient ingredient);
        void onEditClick(Ingredient ingredient);
    }

    private List<Ingredient> ingredients;
    private final onIngredientClickListener listener;

    public IngredientAdapter(List<Ingredient> ingredients, onIngredientClickListener listener){
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.name.setText(ingredient.getName());

        holder.itemView.setOnClickListener(v -> listener.onIngredientClick(ingredient));

        holder.editIcon.setVisibility(View.VISIBLE);
        holder.editIcon.setOnClickListener(v -> listener.onEditClick(ingredient));
    }
    @Override
    public int getItemCount(){
        return ingredients.size();
    }
    public void updateList(List<Ingredient> newIngredients){
        this.ingredients = newIngredients;
        notifyDataSetChanged();
    }
    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView editIcon;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ingredient_name);
            editIcon = itemView.findViewById(R.id.ingredient_edit_icon);
        }
    }
}

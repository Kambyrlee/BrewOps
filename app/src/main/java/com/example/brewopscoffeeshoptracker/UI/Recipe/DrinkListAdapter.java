package com.example.brewopscoffeeshoptracker.UI.Recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.entities.Drink;

import java.util.List;

public class DrinkListAdapter extends RecyclerView.Adapter<DrinkListAdapter.DrinkViewHolder> {

    public interface OnDrinkClickListener {
        void onDrinkClick(Drink drink);
        void onEditClick(Drink drink);
    }
    private List<Drink> drinks;
    private final OnDrinkClickListener listener;
    private boolean isManager = false;

    public DrinkListAdapter(List<Drink> drinks, OnDrinkClickListener listener, boolean isManager) {
        this.drinks = drinks;
        this.listener = listener;
        this.isManager = isManager;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drink_list_item, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        Drink drink = drinks.get(position);
        holder.name.setText(drink.getName());
        holder.itemView.setOnClickListener(v -> listener.onDrinkClick(drink));

        if (isManager) {
            holder.editIcon.setVisibility(View.VISIBLE);
            holder.editIcon.setOnClickListener(v-> listener.onEditClick(drink));
        } else {
            holder.editIcon.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return drinks.size();
    }
    public void updateList(List<Drink> newDrinks) {
        drinks = newDrinks;
        notifyDataSetChanged();
    }
    static class DrinkViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView editIcon;
        DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.drink_name);
            editIcon = itemView.findViewById(R.id.edit_icon);
        }
    }
}

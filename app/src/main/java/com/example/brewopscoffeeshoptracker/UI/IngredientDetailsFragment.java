package com.example.brewopscoffeeshoptracker.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.Repository;
import com.example.brewopscoffeeshoptracker.database.entities.Drink;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;

import java.util.List;

public class IngredientDetailsFragment extends Fragment {
    private Repository repository;
    private TextView ingredientName, unitPrice, servings, pricePerServing;
    private LinearLayout drinksContainer;
    private int ingredientID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_details, container, false);

        ingredientName = view.findViewById(R.id.ingredient_name);
        drinksContainer = view.findViewById(R.id.drinks_container);
        unitPrice = view.findViewById(R.id.ingredient_unit_price);
        servings = view.findViewById(R.id.ingredient_servings);
        pricePerServing = view.findViewById(R.id.ingredient_price_per_serving);

        repository = new Repository(requireActivity().getApplication());

        if (getArguments() != null) {
            ingredientID = getArguments().getInt("ingredientID");
        }

        loadIngredientDetails();
        return view;
    }
    private void loadIngredientDetails() {
        new Thread(() -> {
            Ingredient ingredient = repository.getIngredientByID(ingredientID);
            List<String> drinks = repository.getDrinkNamesByIngredient(ingredientID);

            requireActivity().runOnUiThread(() -> {
                if (ingredient != null) {
                    ingredientName.setText(ingredient.getName());
                    unitPrice.setText(String.format("Unit Price: $%.2f", ingredient.getUnitPrice()));
                    servings.setText("Servings per Unit: " + ingredient.getServingsPerUnit());
                    pricePerServing.setText(String.format("Price per Serving: $%.2f",
                            ingredient.getPricePerServing()));
                }

                drinksContainer.removeAllViews();
                if (drinks == null || drinks.isEmpty()) {
                    TextView noDrinksText = new TextView(getContext());
                    noDrinksText.setText("No drinks use this ingredient.");
                    noDrinksText.setTextSize(16f);
                    noDrinksText.setPadding(16, 8, 16, 8);
                    drinksContainer.addView(noDrinksText);
                } else {
                    for (String name : drinks) {
                        TextView drinkItem = new TextView(getContext());
                        drinkItem.setText("• " + name);
                        drinkItem.setTextSize(16f);
                        drinkItem.setPadding(16, 8, 16, 8);
                        drinksContainer.addView(drinkItem);
                    }
                }
            });
        }).start();
    }

}

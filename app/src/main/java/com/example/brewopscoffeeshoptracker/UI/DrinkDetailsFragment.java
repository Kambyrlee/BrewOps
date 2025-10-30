package com.example.brewopscoffeeshoptracker.UI;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.Repository;
import com.example.brewopscoffeeshoptracker.database.entities.Drink;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;
import com.example.brewopscoffeeshoptracker.database.relations.CoffeeDrinkWithIngredients;
import com.example.brewopscoffeeshoptracker.database.relations.OtherDrinkWithIngredients;
import com.example.brewopscoffeeshoptracker.database.relations.TeaDrinkWithIngredients;

import java.util.ArrayList;
import java.util.List;

public class DrinkDetailsFragment extends Fragment {
    private TextView name, ingredients, directions, details;
    private Repository repository;
    private int drinkID;
    private String drinkType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink_details, container, false);
        name = view.findViewById(R.id.drink_detail_name);
        ingredients = view.findViewById(R.id.drink_detail_ingredients);
        directions = view.findViewById(R.id.drink_detail_directions);
        repository = new Repository(requireActivity().getApplication());
        details = view.findViewById(R.id.drink_detail_extra);

        if (getArguments() != null) {
            drinkID = getArguments().getInt("drinkID");
            drinkType = getArguments().getString("drinkType");
        }
        loadDrink();

        return view;
    }
    private void loadDrink() {
        new Thread(() -> {
            Drink drink = null;
            List<Ingredient> ingredientList = new ArrayList<>();

            switch (drinkType) {
                case "Coffee":
                    CoffeeDrinkWithIngredients coffee = repository.getCoffeeDrinkWithIngredients(drinkID);
                    if (coffee != null) {
                        drink = coffee.drink;
                        ingredientList = coffee.ingredientList;
                    }
                    break;

                case "Tea":
                    TeaDrinkWithIngredients tea = repository.getTeaDrinkWithIngredients(drinkID);
                    if (tea != null) {
                        drink = tea.drink;
                        ingredientList = tea.ingredientList;
                    }
                    break;

                case "Other":
                    OtherDrinkWithIngredients other = repository.getOtherDrinkWithIngredients(drinkID);
                    if (other != null) {
                        drink = other.drink;
                        ingredientList = other.ingredientList;
                    }
                    break;
            }

            Drink finalDrink = drink;
            List<Ingredient> finalIngredients = ingredientList;
            requireActivity().runOnUiThread(() -> showDrinkDetails(finalDrink, finalIngredients));

        }).start();
    }
    private void showDrinkDetails(Drink drink, List<Ingredient> ingredientList) {
        if (drink != null) {
                name.setText(drink.getName());
                String recipeDetails = drink.getRecipe();

            SpannableStringBuilder bulletList = new SpannableStringBuilder();

            if (ingredientList != null && !ingredientList.isEmpty()) {
                for (Ingredient ingredient : ingredientList) {
                    String itemText = "1 serving " + ingredient.getName();
                    SpannableString bulletItem = new SpannableString(itemText + "\n");
                    bulletItem.setSpan(new BulletSpan(20), 0, bulletItem.length(), 0);
                    bulletList.append(bulletItem);
                }
            } else {
                bulletList.append("No ingredients listed.");
            }
            ingredients.setText(bulletList);
            directions.setText(drink.getDirections());
            details.setText(recipeDetails);
        }
    }
}

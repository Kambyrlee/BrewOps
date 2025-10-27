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
    private void loadDrink(){
        Drink drink = null;

        switch (drinkType) {
            case "Coffee":
                drink = repository.getCoffeeDrinkByID(drinkID);
                break;
            case "Tea":
                drink = repository.getTeaDrinkByID(drinkID);
                break;
            case "Other":
                drink = repository.getOtherDrinkByID(drinkID);
                break;
        }

        if (drink != null) {
            name.setText(drink.getName());
            String recipeDetails = drink.getRecipe();

            List<Ingredient> ingredientList = drink.getIngredients();
            SpannableStringBuilder bulletList = new SpannableStringBuilder();

            for(Ingredient ingredient : ingredientList) {
                String itemText = "- 1 Serving " + ingredient.getName();

                SpannableString bulletItem = new SpannableString(itemText + "\n");
                bulletItem.setSpan(new BulletSpan(20), 0, bulletItem.length(), 0);
                bulletList.append(bulletItem);
            }
            ingredients.setText(bulletList);
            directions.setText(drink.getDirections());
            details.setText(recipeDetails);
        }
    }
}

package com.example.brewopscoffeeshoptracker.database.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrink;
import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;

import java.util.List;

public class CoffeeDrinkWithIngredients {
    @Embedded
    public CoffeeDrink drink;

    @Relation(
            parentColumn = "drinkID",
            entityColumn = "ingredientID",
            associateBy = @Junction(CoffeeDrinkIngredientCrossRef.class)
    )
    public List<Ingredient> ingredientList;
}

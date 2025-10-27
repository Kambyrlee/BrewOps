package com.example.brewopscoffeeshoptracker.database.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrink;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrinkIngredientCrossRef;

import java.util.List;

public class OtherDrinkWithIngredients {
    @Embedded
    public CoffeeDrink drink;

    @Relation(
            parentColumn = "drinkID",
            entityColumn = "ingredientID",
            associateBy = @Junction(OtherDrinkIngredientCrossRef.class)
    )
    public List<Ingredient> ingredientList;
}

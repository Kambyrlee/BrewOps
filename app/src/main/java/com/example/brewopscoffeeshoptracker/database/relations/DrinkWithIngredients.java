package com.example.brewopscoffeeshoptracker.database.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.brewopscoffeeshoptracker.database.entities.Drink;
import com.example.brewopscoffeeshoptracker.database.entities.DrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;

import java.util.List;

public class DrinkWithIngredients {
    @Embedded
    public Drink drink;

    @Relation(
            parentColumn = "drinkID",
            entityColumn = "ingredientID",
            associateBy = @Junction(DrinkIngredientCrossRef.class)
    )
    public List<Ingredient> ingredients;
}

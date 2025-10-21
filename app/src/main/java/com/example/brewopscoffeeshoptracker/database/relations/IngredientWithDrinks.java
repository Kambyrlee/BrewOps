package com.example.brewopscoffeeshoptracker.database.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.brewopscoffeeshoptracker.database.entities.Drink;
import com.example.brewopscoffeeshoptracker.database.entities.DrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;

import java.util.List;

public class IngredientWithDrinks {
    @Embedded
    public Ingredient ingredient;

    @Relation(
            parentColumn = "ingredientID",
            entityColumn = "drinkID",
            associateBy = @Junction(DrinkIngredientCrossRef.class)
    )
    public List<Drink> drinks;
}

package com.example.brewopscoffeeshoptracker.database.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrink;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrink;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrinkIngredientCrossRef;

import java.util.List;

public class TeaDrinkWithIngredients {
    @Embedded
    public TeaDrink drink;

    @Relation(
            parentColumn = "drinkID",
            entityColumn = "ingredientID",
            associateBy = @Junction(TeaDrinkIngredientCrossRef.class)
    )
    public List<Ingredient> ingredientList;
}


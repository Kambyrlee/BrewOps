package com.example.brewopscoffeeshoptracker.database.entities;

import androidx.room.Entity;

@Entity(
        primaryKeys = {"drinkID", "ingredientID"},
        tableName = "drink_ingredient_cross_ref"
)
public class DrinkIngredientCrossRef {
    public int drinkID;
    public int ingredientID;
    public DrinkIngredientCrossRef(int drinkID, int ingredientID) {
        this.drinkID =drinkID;
        this.ingredientID =ingredientID;
    }
}

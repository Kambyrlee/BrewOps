package com.example.brewopscoffeeshoptracker.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "tea_ingredients",
        primaryKeys = {"drinkID", "ingredientID"},
        foreignKeys = {
                @ForeignKey(entity = TeaDrink.class, parentColumns = "drinkID", childColumns = "drinkID", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Ingredient.class, parentColumns = "ingredientID", childColumns = "ingredientID", onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index(value = "drinkID"),
                @Index(value = "ingredientID")
        }
)
public class TeaDrinkIngredientCrossRef {
    public int drinkID;
    public int ingredientID;

    public TeaDrinkIngredientCrossRef(int drinkID, int ingredientID) {
        this.drinkID = drinkID;
        this.ingredientID = ingredientID;
    }
}

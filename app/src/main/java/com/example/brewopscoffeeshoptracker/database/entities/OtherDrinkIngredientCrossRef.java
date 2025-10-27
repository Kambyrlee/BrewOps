package com.example.brewopscoffeeshoptracker.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "other_ingredients",
        primaryKeys = {"drinkID", "ingredientID"},
        foreignKeys = {
                @ForeignKey(entity = OtherDrink.class, parentColumns = "drinkID", childColumns = "drinkID", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Ingredient.class, parentColumns = "ingredientID", childColumns = "ingredientID", onDelete = ForeignKey.CASCADE)

        },
        indices = {
                @Index(value = "drinkID"),
                @Index(value = "ingredientID")
        }
)
public class OtherDrinkIngredientCrossRef {
    public int drinkID;
    public int ingredientID;

    public OtherDrinkIngredientCrossRef(int drinkID, int ingredientID) {
        this.drinkID = drinkID;
        this.ingredientID = ingredientID;
    }
}

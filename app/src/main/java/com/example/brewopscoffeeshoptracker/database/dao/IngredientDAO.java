package com.example.brewopscoffeeshoptracker.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;
import com.example.brewopscoffeeshoptracker.database.relations.IngredientWithDrinks;

import java.util.List;

@Dao
public interface IngredientDAO {
    @Insert
    void insertIngredient(Ingredient ingredient);
    @Update
    void updateIngredient(Ingredient ingredient);
    @Delete
    void deleteIngredient(Ingredient ingredient);

    @Query("SELECT * FROM ingredients ORDER BY name ASC")
    List<Ingredient> getAllIngredients();

    @Query("SELECT * FROM ingredients WHERE ingredientID = :id")
    Ingredient getIngredientByID(int id);

    @Transaction
    @Query("SELECT * FROM ingredients WHERE ingredientID = :id")
    List<IngredientWithDrinks> getIngredientWithDrinks(int id);

    @Transaction
    @Query("SELECT * FROM ingredients")
    List<IngredientWithDrinks> getAllIngredientsWithDrinks();

    @Transaction
    @Query("SELECT ingredients.* " +
            "FROM ingredients " +
            "INNER JOIN drink_ingredient_cross_ref " +
            "ON ingredients.ingredientID = drink_ingredient_cross_ref.ingredientID " +
            "WHERE drink_ingredient_cross_ref.drinkID = :drinkID")
    List<Ingredient> getIngredientsByDrinkID(int drinkID);
}

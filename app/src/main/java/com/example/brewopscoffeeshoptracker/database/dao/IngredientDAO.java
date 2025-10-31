package com.example.brewopscoffeeshoptracker.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.brewopscoffeeshoptracker.database.entities.Customer;
import com.example.brewopscoffeeshoptracker.database.entities.Drink;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;

import java.util.List;

@Dao
public interface IngredientDAO {
    @Insert
    long insertIngredient(Ingredient ingredient);
    @Update
    void updateIngredient(Ingredient ingredient);
    @Delete
    void deleteIngredient(Ingredient ingredient);

    @Query("SELECT * FROM ingredients ORDER BY name ASC")
    List<Ingredient> getAllIngredients();

    @Query("SELECT * FROM ingredients WHERE ingredientID = :id")
    Ingredient getIngredientByID(int id);

    @Query("SELECT * FROM ingredients WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    List<Ingredient> searchIngredients(String query);


    @Query("SELECT DISTINCT name FROM (" +
            "SELECT name FROM coffee_drinks WHERE drinkID IN " +
            "(SELECT drinkID FROM coffee_ingredients WHERE ingredientID = :ingredientID) " +
            "UNION ALL " +
            "SELECT name FROM tea_drinks WHERE drinkID IN " +
            "(SELECT drinkID FROM tea_ingredients WHERE ingredientID = :ingredientID) " +
            "UNION ALL " +
            "SELECT name FROM other_drinks WHERE drinkID IN " +
            "(SELECT drinkID FROM other_ingredients WHERE ingredientID = :ingredientID)" +
            ")")
    List<String> getDrinkNamesByIngredient(int ingredientID);




}

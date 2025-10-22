package com.example.brewopscoffeeshoptracker.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.brewopscoffeeshoptracker.database.entities.Drink;
import com.example.brewopscoffeeshoptracker.database.entities.DrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.relations.DrinkWithIngredients;

import java.util.List;

@Dao
public interface DrinkDAO {

    @Insert
    void insertDrink(Drink drink);
    @Update
    void updateDrink(Drink drink);
    @Delete
    void deleteDrink(Drink drink);

    //Get all drinks
    @Query("SELECT * FROM drinks ORDER BY name ASC")
    List<Drink> getAllDrinks();

    //Get a specific drink by ID
    @Query("SELECT * FROM drinks WHERE drinkID = :id")
    Drink getDrinkByID(int id);

    @Transaction
    @Query("SELECT * FROM drinks WHERE drinkID = :id")
    DrinkWithIngredients getDrinkWithIngredients(int id);

    @Transaction
    @Query("SELECT * FROM drinks")
    List<DrinkWithIngredients> getAllDrinksWithIngredients();

    @Query("SELECT * FROM drinks WHERE type = :type")
    List<Drink> getDrinksByType(String type);

}

package com.example.brewopscoffeeshoptracker.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.brewopscoffeeshoptracker.database.entities.DrinkIngredientCrossRef;

@Dao
public interface CrossRefDAO {
    @Insert
    void insertCrossRef(DrinkIngredientCrossRef crossRef);
    @Update
    void updateCrossRef(DrinkIngredientCrossRef crossRef);
    @Delete
    void deleteCrossRef(DrinkIngredientCrossRef crossRef);
    @Query("DELETE FROM drink_ingredient_cross_ref WHERE drinkID = :id")
    void deleteAllForDrink(int id);
}

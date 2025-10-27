package com.example.brewopscoffeeshoptracker.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrinkIngredientCrossRef;

@Dao
public interface CrossRefDAO {

    //COFFEE DRINKS
    @Insert
    void insertCoffeeCrossRef(CoffeeDrinkIngredientCrossRef crossRef);

    @Query("DELETE FROM coffee_ingredients WHERE drinkID = :drinkID")
    void deleteIngredientsFromCoffeeDrink(int drinkID);

    @Query("DELETE FROM coffee_ingredients WHERE ingredientID = :ingredientID")
    void deleteAllCoffeeDrinksWithIngredient(int ingredientID);

    //TEA DRINKS

    @Insert
    void insertTeaCrossRef(TeaDrinkIngredientCrossRef crossRef);

    @Query("DELETE FROM tea_ingredients WHERE drinkID = :drinkID")
    void deleteIngredientsFromTeaDrink(int drinkID);
    @Query("DELETE FROM tea_ingredients WHERE ingredientID = :ingredientID")
    void deleteAllTeaDrinksWithIngredient(int ingredientID);

    // OTHER DRINKS

    @Insert
    void insertOtherCrossRef(OtherDrinkIngredientCrossRef crossRef);

    @Query("DELETE FROM other_ingredients WHERE drinkID = :drinkID")
    void deleteIngredientsFromOtherDrink(int drinkID);

    @Query("DELETE FROM other_ingredients WHERE ingredientID = :ingredientID")
    void deleteAllOtherDrinksWithIngredient(int ingredientID);


}

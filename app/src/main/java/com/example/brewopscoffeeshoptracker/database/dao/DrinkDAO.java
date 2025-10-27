package com.example.brewopscoffeeshoptracker.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrink;
import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrink;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrink;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.relations.CoffeeDrinkWithIngredients;
import com.example.brewopscoffeeshoptracker.database.relations.OtherDrinkWithIngredients;
import com.example.brewopscoffeeshoptracker.database.relations.TeaDrinkWithIngredients;

import java.util.List;

@Dao
public interface DrinkDAO {

    // COFFEE DRINKS
    @Insert
    long insertCoffeeDrink(CoffeeDrink coffeeDrink);
    @Delete
    void deleteCoffeeDrink(CoffeeDrink coffeeDrink);
    @Update
    void updateCoffeeDrink(CoffeeDrink coffeeDrink);
    @Query("SELECT * FROM coffee_drinks")
    List<CoffeeDrink> getAllCoffeeDrinks();

    @Query("SELECT * FROM coffee_drinks WHERE drinkID = :drinkID")
    CoffeeDrink getCoffeeDrinkByID(int drinkID);

    @Transaction
    @Query("SELECT * FROM coffee_drinks WHERE drinkID = :drinkID")
    CoffeeDrinkWithIngredients getCoffeeDrinkWithIngredients(int drinkID);

    // TEA DRINKS
    @Insert
    long insertTeaDrink(TeaDrink teaDrink);
    @Delete
    void deleteTeaDrink(TeaDrink teaDrink);
    @Update
    void updateTeaDrink(TeaDrink teaDrink);
    @Query("SELECT * FROM tea_drinks")
    List<TeaDrink> getAllTeaDrinks();

    @Query("SELECT * FROM tea_drinks WHERE drinkID = :drinkID")
    TeaDrink getTeaDrinkByID(int drinkID);

    @Transaction
    @Query("SELECT * FROM tea_drinks WHERE drinkID = :drinkID")
    TeaDrinkWithIngredients getTeaDrinkWithIngredients(int drinkID);


    // OTHER DRINKS
    @Insert
    long insertOtherDrink(OtherDrink otherDrink);
    @Delete
    void deleteOtherDrink(OtherDrink otherDrink);
    @Update
    void updateOtherDrink(OtherDrink otherDrink);
    @Query("SELECT * FROM other_drinks")
    List<OtherDrink> getAllOtherDrinks();

    @Query("SELECT * FROM other_drinks WHERE drinkID = :drinkID")
    OtherDrink getOtherDrinkByID(int drinkID);

    @Transaction
    @Query("SELECT * FROM other_drinks WHERE drinkID = :drinkID")
    OtherDrinkWithIngredients getOtherDrinkWithIngredients(int drinkID);


}

package com.example.brewopscoffeeshoptracker.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.brewopscoffeeshoptracker.database.dao.DrinkDAO;
import com.example.brewopscoffeeshoptracker.database.dao.IngredientDAO;
import com.example.brewopscoffeeshoptracker.database.entities.Drink;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;
import com.example.brewopscoffeeshoptracker.database.relations.DrinkWithIngredients;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final DrinkDAO drinkDAO;
    private final IngredientDAO ingredientDAO;
    private final ExecutorService executor;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        drinkDAO = db.drinkDAO();
        ingredientDAO = db.ingredientDAO();
        executor = Executors.newSingleThreadExecutor();
    }
    public void insertDrink(Drink drink) {
        executor.execute(() -> drinkDAO.insertDrink(drink));
    }
    public void deleteDrink(Drink drink) {
        executor.execute(()-> drinkDAO.deleteDrink(drink));
    }
    public void updateDrink(Drink drink) {
        executor.execute(()-> drinkDAO.updateDrink(drink));
    }
    public List<Drink> getAllDrinks(){
        return drinkDAO.getAllDrinks();
    }
    public List<DrinkWithIngredients> getDrinksWithIngredients(){
        return drinkDAO.getAllDrinksWithIngredients();
    }
    public List<Drink> getDrinksByType(String type){
        return drinkDAO.getDrinksByType(type);
    }

    public void insertIngredient(Ingredient ingredient){
        executor.execute(()-> ingredientDAO.insertIngredient(ingredient));
    }
    public void deleteIngredient(Ingredient ingredient){
        executor.execute(()->ingredientDAO.deleteIngredient(ingredient));
    }
    public void updateIngredient(Ingredient ingredient){
        executor.execute(()->ingredientDAO.updateIngredient(ingredient));
    }
    public List<Ingredient> getAllIngredients(){
        return ingredientDAO.getAllIngredients();
    }
    public List<Ingredient> getIngredientsForDrink(int drinkID){
        return ingredientDAO.getIngredientsByDrinkID(drinkID);
    }
}

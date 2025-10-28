package com.example.brewopscoffeeshoptracker.database;

import android.app.Application;

import com.example.brewopscoffeeshoptracker.database.dao.CrossRefDAO;
import com.example.brewopscoffeeshoptracker.database.dao.CustomerDAO;
import com.example.brewopscoffeeshoptracker.database.dao.DrinkDAO;
import com.example.brewopscoffeeshoptracker.database.dao.IngredientDAO;
import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrink;
import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.Customer;
import com.example.brewopscoffeeshoptracker.database.entities.Drink;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrink;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrink;
import com.example.brewopscoffeeshoptracker.database.relations.CoffeeDrinkWithIngredients;
import com.example.brewopscoffeeshoptracker.database.relations.OtherDrinkWithIngredients;
import com.example.brewopscoffeeshoptracker.database.relations.TeaDrinkWithIngredients;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final DrinkDAO drinkDAO;
    private final IngredientDAO ingredientDAO;
    private final CustomerDAO customerDAO;
    private final CrossRefDAO crossRefDAO;
    private final ExecutorService executor;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        drinkDAO = db.drinkDAO();
        ingredientDAO = db.ingredientDAO();
        crossRefDAO = db.crossRefDAO();
        customerDAO = db.customerDAO();
        executor = Executors.newSingleThreadExecutor();
    }
    public List<Drink> getAllDrinks() {
        List<Drink> allDrinks = new ArrayList<>();
        allDrinks.addAll(drinkDAO.getAllCoffeeDrinks());
        allDrinks.addAll(drinkDAO.getAllTeaDrinks());
        allDrinks.addAll(drinkDAO.getAllOtherDrinks());
        return allDrinks;
    }

    // COFFEE DRINKS
    public List<CoffeeDrink> getAllCoffeeDrinks() { return drinkDAO.getAllCoffeeDrinks();}
    public CoffeeDrink getCoffeeDrinkByID(int id) { return drinkDAO.getCoffeeDrinkByID(id);}
    public CoffeeDrinkWithIngredients getCoffeeDrinkWithIngredients(int id) { return drinkDAO.getCoffeeDrinkWithIngredients(id);}
    public void insertCoffeeDrink(CoffeeDrink drink) {
        executor.execute(()-> drinkDAO.insertCoffeeDrink(drink));}

    public void updateCoffeeDrink(CoffeeDrink drink) {
        executor.execute(()-> drinkDAO.updateCoffeeDrink(drink));
    }


    //TEA DRINKS
    public List<TeaDrink> getAllTeaDrinks() { return drinkDAO.getAllTeaDrinks(); }
    public TeaDrink getTeaDrinkByID(int id) { return drinkDAO.getTeaDrinkByID(id); }
    public TeaDrinkWithIngredients getTeaDrinkWithIngredients(int id) { return drinkDAO.getTeaDrinkWithIngredients(id); }
    public void insertTeaDrink(TeaDrink drink) {
        executor.execute(()-> drinkDAO.insertTeaDrink(drink)); }
    public void updateTeaDrink(TeaDrink drink) {
        executor.execute(()-> drinkDAO.updateTeaDrink(drink));
    }

    // OTHER DRINKS
    public List<OtherDrink> getAllOtherDrinks() { return drinkDAO.getAllOtherDrinks(); }
    public OtherDrink getOtherDrinkByID(int id) { return drinkDAO.getOtherDrinkByID(id); }
    public OtherDrinkWithIngredients getOtherDrinkWithIngredients(int id) { return drinkDAO.getOtherDrinkWithIngredients(id); }
    public void insertOtherDrink(OtherDrink drink) {
        executor.execute(()-> drinkDAO.insertOtherDrink(drink)); }
    public void updateOtherDrink(OtherDrink drink) {
        executor.execute(() -> drinkDAO.updateOtherDrink(drink));
    }

    //INGREDIENTS
    public void insertIngredient(Ingredient ingredient){
        executor.execute(()-> ingredientDAO.insertIngredient(ingredient));
    }
    public void deleteIngredient(Ingredient ingredient) {
        executor.execute(() -> {
            crossRefDAO.deleteAllCoffeeDrinksWithIngredient(ingredient.getIngredientID());
            crossRefDAO.deleteAllTeaDrinksWithIngredient(ingredient.getIngredientID());
            crossRefDAO.deleteAllOtherDrinksWithIngredient(ingredient.getIngredientID());
            ingredientDAO.deleteIngredient(ingredient);
        });
    }
    public void updateIngredient(Ingredient ingredient){
        executor.execute(()->ingredientDAO.updateIngredient(ingredient));
    }
    public List<Ingredient> getAllIngredients(){
        return ingredientDAO.getAllIngredients();
    }

    //CROSS REFS
    public void addIngredientToCoffeeDrink(int drinkID, int ingredientID) {
        executor.execute(()-> crossRefDAO.insertCoffeeCrossRef(new CoffeeDrinkIngredientCrossRef(drinkID, ingredientID)));
    }

    public void removeIngredientsFromCoffeeDrink(int drinkID) {
        executor.execute(()-> crossRefDAO.deleteIngredientsFromCoffeeDrink(drinkID));

    }

    public void removeAllIngredientsFromCoffeeDrink(int drinkID) {
        executor.execute(()-> crossRefDAO.deleteIngredientsFromCoffeeDrink(drinkID));
    }

    public void removeAllDrinksUsingIngredient(int ingredientID) {
        executor.execute(()-> crossRefDAO.deleteAllCoffeeDrinksWithIngredient(ingredientID));
        executor.execute(()-> crossRefDAO.deleteAllTeaDrinksWithIngredient(ingredientID));
        executor.execute(()-> crossRefDAO.deleteAllOtherDrinksWithIngredient(ingredientID));
    }

    // CUSTOMERS

    public List<Customer> getAllCustomers(){
        return customerDAO.getAllCustomers();
    }
    public void insertCustomer(Customer customer){
        executor.execute(()-> customerDAO.insert(customer));
    }
    public void updateCustomer(Customer customer) {
        customerDAO.update(customer);
    }
    public void deleteCustomer(Customer customer) {
        executor.execute(()-> customerDAO.delete(customer));
    }
    public List<Customer> searchCustomers(String query) {
        return customerDAO.searchCustomers(query);
    }

}

package com.example.brewopscoffeeshoptracker.database.entities;

import androidx.room.Entity;

@Entity(tableName = "coffee_drinks")
public class CoffeeDrink extends Drink{

    public CoffeeDrink(String name, double price, String directions){
        super(name, "Coffee", price, directions);
    }
    @Override
    public String getRecipe(){
        return "FIXME: THIS IS A COFFEE RECIPE";
    }
}

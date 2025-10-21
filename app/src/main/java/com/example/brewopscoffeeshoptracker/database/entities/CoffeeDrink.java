package com.example.brewopscoffeeshoptracker.database.entities;

public class CoffeeDrink extends Drink{

    public CoffeeDrink(String name, double price, String directions){
        super(name, "Coffee", price, directions);
    }
    @Override
    public String getRecipe(){
        return "FIXME: THIS IS A COFFEE RECIPE";
    }
}

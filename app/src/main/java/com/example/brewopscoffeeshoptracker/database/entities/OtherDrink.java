package com.example.brewopscoffeeshoptracker.database.entities;

import androidx.room.Entity;

@Entity(tableName = "other_drinks")
public class OtherDrink extends Drink{
    public OtherDrink(String name, double price, String directions){
        super(name, "Other", price, directions);
    }
    @Override
    public String getRecipe(){
        return "FIXME: This is an Other Drink recipe";
    }
}

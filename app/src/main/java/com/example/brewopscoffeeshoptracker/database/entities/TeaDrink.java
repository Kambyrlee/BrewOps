package com.example.brewopscoffeeshoptracker.database.entities;

import androidx.room.Entity;

@Entity(tableName = "tea_drinks")
public class TeaDrink extends Drink{

    private String teaType; // Black, Green, Herbal, etc.

    public TeaDrink(String name, String teaType, double price, String directions) {
        super(name, "Tea", price, directions);
        this.teaType = teaType;
    }

    @Override
    public String getRecipe(){
        return "~Sleepy Seal Tea: Gentle waves of flavor for a peaceful seal nap.~";
    }

    public String getTeaType() {
        return teaType;
    }
    public void setTeaType(String teaType) {
        this.teaType = teaType;
    }
}

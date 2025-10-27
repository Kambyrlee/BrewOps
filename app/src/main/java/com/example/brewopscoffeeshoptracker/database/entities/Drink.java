package com.example.brewopscoffeeshoptracker.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

public abstract class Drink {
    @PrimaryKey(autoGenerate = true)
    private int drinkID;
    private String name;
    private double price;
    private String type;    // Coffee, Tea, Lemonade
    private String directions;
    @Ignore
    private List<String> ingredients;
    @Ignore
    public Drink(String name, String type, double price, String directions) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.directions = directions;
    }
    public Drink(){};

    public int getDrinkID() {
        return drinkID;
    }
    public void setDrinkID(int drinkID) {
        this.drinkID = drinkID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getDirections(){
        return directions;
    }
    public void setDirections(String directions) {
        this.directions = directions;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public abstract String getRecipe();
}

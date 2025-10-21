package com.example.brewopscoffeeshoptracker.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredients")
public class Ingredient {
        @PrimaryKey(autoGenerate = true)
        private int ingredientID;
        private String name;
        private double unitPrice;
        private int servingsPerUnit;

        @Ignore
        public Ingredient(String name, double unitPrice, int servingsPerUnit){
                this.name = name;
                this.unitPrice = unitPrice;
                this.servingsPerUnit = servingsPerUnit;
        }
        public Ingredient(){};

        public int getIngredientID() {
                return ingredientID;
        }

        public void setIngredientID(int ingredientID) {
                this.ingredientID = ingredientID;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public int getServingsPerUnit() {
                return servingsPerUnit;
        }

        public void setServingsPerUnit(int servingsPerUnit) {
                this.servingsPerUnit = servingsPerUnit;
        }

        public double getUnitPrice() {
                return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
                this.unitPrice = unitPrice;
        }

        public double getPricePerServing() {
                if (servingsPerUnit == 0) {
                        return 0.0;
                }
                return unitPrice / (servingsPerUnit * 1.0);
        }

}

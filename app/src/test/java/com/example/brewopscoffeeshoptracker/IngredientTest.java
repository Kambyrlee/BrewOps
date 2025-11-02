package com.example.brewopscoffeeshoptracker;

import static org.junit.Assert.assertEquals;

import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;

import org.junit.Test;

public class IngredientTest {
    @Test
    public void testGetPricePerServing(){
        Ingredient testIngredient = new Ingredient("Test Ingredient", 3.00, 6);
        // Unit Price = $3.00, Servings Per Unit = 6

        double pricePerServing = testIngredient.getPricePerServing();

        assertEquals(0.50, pricePerServing, 0.001);
        // Expected price per serving is $0.50, $3.00 / 6 servings
    }
    @Test
    public void testGetPricePerServingWithZeroServings() {
        Ingredient testIngredient = new Ingredient("Sugar", 2.00, 0);

        double pricePerServing = testIngredient.getPricePerServing();

        // Expect 0 to avoid divide-by-zero crash
        assertEquals(0.0, pricePerServing, 0.001);
    }
}

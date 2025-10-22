package com.example.brewopscoffeeshoptracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.brewopscoffeeshoptracker.database.dao.CrossRefDAO;
import com.example.brewopscoffeeshoptracker.database.dao.DrinkDAO;
import com.example.brewopscoffeeshoptracker.database.dao.IngredientDAO;
import com.example.brewopscoffeeshoptracker.database.entities.Drink;
import com.example.brewopscoffeeshoptracker.database.entities.DrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;

@Database(
        entities = {Drink.class, Ingredient.class, DrinkIngredientCrossRef.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DrinkDAO drinkDAO();
    public abstract IngredientDAO ingredientDAO();
    public abstract CrossRefDAO crossRefDAO();

    public static volatile AppDatabase INSTANCE;
    public static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "BrewOps_Database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

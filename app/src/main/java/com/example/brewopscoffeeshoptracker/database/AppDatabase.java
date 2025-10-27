package com.example.brewopscoffeeshoptracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.brewopscoffeeshoptracker.database.dao.CrossRefDAO;
import com.example.brewopscoffeeshoptracker.database.dao.DrinkDAO;
import com.example.brewopscoffeeshoptracker.database.dao.IngredientDAO;
import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrink;
import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrink;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrink;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrinkIngredientCrossRef;

@Database(
        entities = {CoffeeDrink.class,
                TeaDrink.class,
                OtherDrink.class,
                Ingredient.class,
                CoffeeDrinkIngredientCrossRef.class,
                TeaDrinkIngredientCrossRef.class,
                OtherDrinkIngredientCrossRef.class},
        version = 2,
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

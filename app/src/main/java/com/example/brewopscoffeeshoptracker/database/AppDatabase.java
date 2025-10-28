package com.example.brewopscoffeeshoptracker.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrink;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrinkIngredientCrossRef;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {CoffeeDrink.class,
                TeaDrink.class,
                OtherDrink.class,
                Ingredient.class,
                Customer.class,
                CoffeeDrinkIngredientCrossRef.class,
                TeaDrinkIngredientCrossRef.class,
                OtherDrinkIngredientCrossRef.class},
        version = 2,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DrinkDAO drinkDAO();
    public abstract IngredientDAO ingredientDAO();
    public abstract CrossRefDAO crossRefDAO();
    public abstract CustomerDAO customerDAO();
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWrite = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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
                            .addCallback(prepopulateCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback prepopulateCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWrite.execute(()-> {
                AppDatabase database = INSTANCE;
                IngredientDAO ingredientDAO = database.ingredientDAO();
                DrinkDAO drinkDAO = database.drinkDAO();
                CrossRefDAO crossRefDAO = database.crossRefDAO();
                CustomerDAO customerDAO = database.customerDAO();

                // PREPOPULATE INGREDIENTS

                Ingredient coffeeGrounds = new Ingredient("Coffee Grounds", 10.00, 48);
                long coffeeGroundsID = ingredientDAO.insertIngredient(coffeeGrounds);

                Ingredient sugar = new Ingredient("Sugar", 10.00, 400);
                long sugarID = ingredientDAO.insertIngredient(sugar);

                Ingredient water = new Ingredient("Water", 0.00, 100);
                long waterID = ingredientDAO.insertIngredient(water);

                Ingredient milk = new Ingredient("Whole Milk", 4.00, 16);
                long milkID = ingredientDAO.insertIngredient(milk);

                Ingredient vanillaSyrup = new Ingredient("Vanilla Syrup", 9.00, 100);
                long vanillaSyrupID = ingredientDAO.insertIngredient(vanillaSyrup);

                Ingredient chaiConcentrate = new Ingredient("Chai Concentrate", 6.00, 8);
                long chaiID = ingredientDAO.insertIngredient(chaiConcentrate);

                Ingredient herbalTeaBag = new Ingredient("Herbal Tea Bag", 4.00, 18);
                long herbalBagID = ingredientDAO.insertIngredient(herbalTeaBag);

                Ingredient lemonJuice = new Ingredient("Lemon Juice", 3.00, 2);
                long lemonJuiceID = ingredientDAO.insertIngredient(lemonJuice);

                Ingredient hotChocolateMix = new Ingredient("Hot Chocolate Mix", 3.50, 8);
                long hotChocolateMixID = ingredientDAO.insertIngredient(hotChocolateMix);

                // PREPOPULATE DRINKS
                CoffeeDrink americano = new CoffeeDrink("Americano", 5.00, "Mix water and espresso shot.");
                long americanoID = drinkDAO.insertCoffeeDrink(americano);

                CoffeeDrink latte = new CoffeeDrink("Latte", 8.50, "Mix steamed milk and espresso shot.");
                long latteID = drinkDAO.insertCoffeeDrink(latte);

                TeaDrink chaiLatte = new TeaDrink("Chai Latte", "Chai", 8.50, "Mix steamed milk and chai concentrate.");
                long chaiLatteID = drinkDAO.insertTeaDrink(chaiLatte);

                TeaDrink herbalTea = new TeaDrink("Herbal Tea", "Herbal", 2.00, "Allow tea bag to steep in hot water for several minutes.");
                long herbalTeaID = drinkDAO.insertTeaDrink(herbalTea);

                OtherDrink lemonade = new OtherDrink("Lemonade", 5.00, "Mix hot water with lemon juice and sugar. Pour over ice.");
                long lemonadeID = drinkDAO.insertOtherDrink(lemonade);

                OtherDrink hotChocolate = new OtherDrink("Hot Chocolate", 3.50, "Mix warm milk and hot chocolate mix.");
                long hotChocolateID = drinkDAO.insertOtherDrink(hotChocolate);


                // PREPOPULATE CROSS REFS

                crossRefDAO.insertCoffeeCrossRef(new CoffeeDrinkIngredientCrossRef((int)americanoID, (int)waterID));
                crossRefDAO.insertCoffeeCrossRef(new CoffeeDrinkIngredientCrossRef((int)americanoID, (int)coffeeGroundsID));

                crossRefDAO.insertCoffeeCrossRef(new CoffeeDrinkIngredientCrossRef((int)latteID, (int)milkID));
                crossRefDAO.insertCoffeeCrossRef(new CoffeeDrinkIngredientCrossRef((int)latteID, (int)coffeeGroundsID));

                crossRefDAO.insertTeaCrossRef(new TeaDrinkIngredientCrossRef((int)chaiLatteID, (int)milkID));
                crossRefDAO.insertTeaCrossRef(new TeaDrinkIngredientCrossRef((int)chaiLatteID, (int)chaiID));

                crossRefDAO.insertOtherCrossRef(new OtherDrinkIngredientCrossRef((int)lemonadeID, (int)waterID));
                crossRefDAO.insertOtherCrossRef(new OtherDrinkIngredientCrossRef((int)lemonadeID, (int)sugarID));
                crossRefDAO.insertOtherCrossRef(new OtherDrinkIngredientCrossRef((int)lemonadeID, (int)lemonJuiceID));

                crossRefDAO.insertOtherCrossRef(new OtherDrinkIngredientCrossRef((int)hotChocolateID, (int)milkID));
                crossRefDAO.insertOtherCrossRef(new OtherDrinkIngredientCrossRef((int)hotChocolateID, (int)hotChocolateMixID));

                // PREPOPULATE CUSTOMERS

                customerDAO.insert(new Customer("Yuji Itadori", "Hot Chocolate", Date.from(LocalDate.of(2025,04,16).atStartOfDay(ZoneId.systemDefault()).toInstant())));
                customerDAO.insert(new Customer("Suguru Gojo", "Americano", Date.from(LocalDate.of(2025,06,19).atStartOfDay(ZoneId.systemDefault()).toInstant())));
                customerDAO.insert(new Customer("Nobara Kugisaki", "Chai Latte", Date.from(LocalDate.of(2025,10,22).atStartOfDay(ZoneId.systemDefault()).toInstant())));
            });
        }
    };
}

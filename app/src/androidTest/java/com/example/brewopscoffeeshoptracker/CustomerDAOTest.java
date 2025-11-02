package com.example.brewopscoffeeshoptracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.brewopscoffeeshoptracker.database.AppDatabase;
import com.example.brewopscoffeeshoptracker.database.dao.CustomerDAO;
import com.example.brewopscoffeeshoptracker.database.entities.Customer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class CustomerDAOTest {
    private AppDatabase db;
    private CustomerDAO customerDAO;

    @Before
    public void createDb() {
        db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase.class
        ).allowMainThreadQueries().build();

        customerDAO = db.customerDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertAndRetrieveCustomer() {
        Customer testCustomer = new Customer("Testy McTest", "Latte", new Date());
        customerDAO.insert(testCustomer);

        List<Customer> allCustomers = customerDAO.getAllCustomers();
        assertNotNull(allCustomers);
        assertEquals(1, allCustomers.size());
        assertEquals("Testy McTest", allCustomers.get(0).getCustomerName());
        assertEquals("Latte", allCustomers.get(0).getDrinkOrder());
    }
}

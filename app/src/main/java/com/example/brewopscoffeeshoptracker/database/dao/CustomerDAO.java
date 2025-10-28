package com.example.brewopscoffeeshoptracker.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.brewopscoffeeshoptracker.database.entities.Customer;

import java.util.List;

@Dao
public interface CustomerDAO {
    @Insert
    void insert(Customer customer);
    @Update
    void update(Customer customer);
    @Delete
    void delete(Customer customer);

    @Query("SELECT * FROM customers ORDER BY orderDate DESC")
    List<Customer> getAllCustomers();

    @Query("SELECT * FROM customers WHERE customerID = :id")
    Customer getCustomerByID(int id);
    // List of orders instead?

    @Query("SELECT * FROM customers WHERE customerName LIKE '%' || :query || '%' ORDER BY orderDate DESC")
    List<Customer> searchCustomers(String query);


}

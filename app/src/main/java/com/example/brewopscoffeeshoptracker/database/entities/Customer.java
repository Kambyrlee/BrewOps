package com.example.brewopscoffeeshoptracker.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "customers")
public class Customer {
    @PrimaryKey(autoGenerate = true)
    private int customerID;
    private String customerName;
    private String drinkOrder;
    private Date orderDate;

    public Customer(String customerName, String drinkOrder, Date orderDate) {
        this.customerName = customerName;
        this.drinkOrder = drinkOrder;
        this.orderDate = orderDate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDrinkOrder() {
        return drinkOrder;
    }

    public void setDrinkOrder(String drinkOrder) {
        this.drinkOrder = drinkOrder;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}

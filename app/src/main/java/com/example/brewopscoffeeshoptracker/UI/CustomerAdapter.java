package com.example.brewopscoffeeshoptracker.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.entities.Customer;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>{

    private List<Customer> customers;

    public CustomerAdapter(List<Customer> customers){
        this.customers = customers;
    }

    @NonNull
    @Override
    public CustomerAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_list_item, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.name.setText(customer.getCustomerName());
        holder.order.setText("Order: " + customer.getDrinkOrder());

        Date date = customer.getOrderDate();
        String dateStr = (date != null)
                ? DateFormat.getDateInstance().format(date)
                : "No date";
        holder.date.setText("Date: " + dateStr);

    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
    public void updateList(List<Customer> newCustomers) {
        this.customers = newCustomers;
        notifyDataSetChanged();
    }
    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView name, order, date;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.customer_name);
            order = itemView.findViewById(R.id.customer_order);
            date = itemView.findViewById(R.id.customer_date);
        }
    }
}

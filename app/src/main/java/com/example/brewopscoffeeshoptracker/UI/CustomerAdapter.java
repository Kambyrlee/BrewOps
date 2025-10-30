package com.example.brewopscoffeeshoptracker.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.entities.Customer;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>{
    public interface OnCustomerClickListener {
        void onCustomerClick(Customer customer);
        void onEditClick(Customer customer);
    }

    private List<Customer> customers;
    private boolean isManager;
    private final OnCustomerClickListener listener;

    public CustomerAdapter(List<Customer> customers, OnCustomerClickListener listener, boolean isManager){
        this.customers = customers;
        this.listener = listener;
        this.isManager = isManager;
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

        holder.itemView.setOnClickListener(v -> listener.onCustomerClick(customer));
        if (isManager) {
            holder.editIcon.setVisibility(View.VISIBLE);
            holder.editIcon.setOnClickListener(v -> listener.onEditClick(customer));
        } else {
            holder.editIcon.setVisibility(View.GONE);
        }

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
        ImageView editIcon;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.customer_name);
            order = itemView.findViewById(R.id.customer_order);
            date = itemView.findViewById(R.id.customer_date);
            editIcon = itemView.findViewById(R.id.customer_edit_icon);
        }
    }
}

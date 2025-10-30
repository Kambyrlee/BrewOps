package com.example.brewopscoffeeshoptracker.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.Repository;
import com.example.brewopscoffeeshoptracker.database.entities.Customer;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomerEditorFragment extends Fragment {

    private EditText customerName, customerOrder;
    private Button saveCustomerButton, deleteCustomerButton;
    private Repository repository;
    private boolean isEditMode = false;
    private int customerID;
    private TextView customerDateField;
    private Date selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_editor, container, false);

        customerName = view.findViewById(R.id.edit_customer_name);
        customerOrder = view.findViewById(R.id.edit_customer_order);
        saveCustomerButton = view.findViewById(R.id.button_save_customer);
        deleteCustomerButton = view.findViewById(R.id.button_delete_customer);
        customerDateField = view.findViewById(R.id.edit_customer_date);

        repository = new Repository(requireActivity().getApplication());

        if (getArguments() != null && getArguments().containsKey("customerID")) {
            isEditMode = true;
            customerID = getArguments().getInt("customerID");
            loadCustomerForEdit();
        }

        selectedDate = new Date();
        DateFormat df = DateFormat.getDateInstance();
        customerDateField.setText(df.format(selectedDate));

        customerDateField.setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(selectedDate);
            new DatePickerDialog(requireContext(),
                    (picker, year, month, day) -> {
                        cal.set(year, month, day);
                        selectedDate = cal.getTime();
                        customerDateField.setText(df.format(selectedDate));
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        saveCustomerButton.setOnClickListener(v -> saveCustomer());
        deleteCustomerButton.setOnClickListener(v -> confirmDelete());

        return view;
    }
    private void loadCustomerForEdit(){
        new Thread(() -> {
            Customer c = repository.getCustomerByID(customerID);
            requireActivity().runOnUiThread(() -> {
                if (c != null) {
                    customerName.setText(c.getCustomerName());
                    customerOrder.setText(c.getDrinkOrder());
                    deleteCustomerButton.setVisibility(View.VISIBLE);
                    customerDateField.setText(DateFormat.getDateInstance().format(c.getOrderDate()));
                    selectedDate = c.getOrderDate();

                }
            });
        }).start();

    }
    private void confirmDelete() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Customer")
                .setMessage("Are you sure you want to delete this customer?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    new Thread(() -> {
                        Customer c = repository.getCustomerByID(customerID);
                        if (c != null) repository.deleteCustomer(c);
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), "Customer deleted", Toast.LENGTH_SHORT).show();
                            Bundle result = new Bundle();
                            result.putBoolean("refreshNeeded", true);
                            requireActivity().getSupportFragmentManager().setFragmentResult("customerListUpdate", result);
                            requireActivity().getSupportFragmentManager().popBackStack();
                        });
                    }).start();
                })
                .setNegativeButton("Cancel", null)
                .show();

    }
    private void saveCustomer(){
        String name = customerName.getText().toString().trim();
        String order = customerOrder.getText().toString().trim();
        if (name.isEmpty() || order.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditMode) {
            Customer c = new Customer(name, order, selectedDate);
            c.setCustomerID(customerID);
            repository.updateCustomer(c);
            Toast.makeText(getContext(), "Customer updated", Toast.LENGTH_SHORT).show();
        } else {
            repository.insertCustomer(new Customer(name, order, selectedDate));
            Toast.makeText(getContext(), "Customer added", Toast.LENGTH_SHORT).show();
        }


        Bundle result = new Bundle();
        result.putBoolean("refreshNeeded", true);
        requireActivity().getSupportFragmentManager().setFragmentResult("customerListUpdate", result);
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    }

package com.example.brewopscoffeeshoptracker.UI.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.Repository;
import com.example.brewopscoffeeshoptracker.database.entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerListFragment extends Fragment {
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private Repository repository;
    private boolean isManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        recyclerView = view.findViewById(R.id.customer_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repository = new Repository(requireActivity().getApplication());

        if (getArguments() != null) {
            isManager = getArguments().getBoolean("isManager", false);
        }

        adapter = new CustomerAdapter(new ArrayList<>(),
                new CustomerAdapter.OnCustomerClickListener() {
                    @Override
                    public void onCustomerClick(Customer customer) {
                        return;
                    }

                    @Override
                    public void onEditClick(Customer customer) {
                        if (isManager) openCustomerEditor(customer);
                    }
                },
                isManager
        );
        recyclerView.setAdapter(adapter);

        requireActivity().getSupportFragmentManager()
                .setFragmentResultListener("customerListUpdate", this, (key, bundle) -> {
                    if (bundle.getBoolean("refreshNeeded", false)) loadCustomers();
                });

        loadCustomers();

        return view;
    }
    private void loadCustomers(){

        new Thread(() -> {
            List<Customer> customers = new ArrayList<>();
            String query = null;
            if (getArguments() != null) {
                query = getArguments().getString("searchQuery");
            }

            if (query != null && !query.isEmpty()) {
                customers.addAll(repository.searchCustomers(query)); // new DAO method
            } else {
                customers.addAll(repository.getAllCustomers());
            }

            requireActivity().runOnUiThread(() -> adapter.updateList(customers));
        }).start();
    }
    private void openCustomerEditor(Customer customer) {
        Bundle bundle = new Bundle();
        bundle.putInt("customerID", customer.getCustomerID());

        CustomerEditorFragment editor = new CustomerEditorFragment();
        editor.setArguments(bundle);

        requireActivity().findViewById(R.id.manager_fragment_container).setVisibility(View.VISIBLE);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.manager_fragment_container, editor)
                .addToBackStack(null)
                .commit();
    }
}

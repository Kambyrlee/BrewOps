package com.example.brewopscoffeeshoptracker.UI.Customer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brewopscoffeeshoptracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CustomerManagerFragment extends Fragment {
    private FloatingActionButton addCustomerFab;
    private EditText searchBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_manager, container, false);

        addCustomerFab = view.findViewById(R.id.fab_add_customer);
        searchBar = view.findViewById(R.id.customer_search_bar_manager);
        addCustomerFab.setOnClickListener(v -> openCustomerEditor());
        addCustomerFab.bringToFront();

        openCustomerList(null);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                openCustomerList(s.length() > 0 ? s.toString() : null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }
    private void openCustomerEditor() {
        CustomerEditorFragment editor = new CustomerEditorFragment();
        requireActivity().findViewById(R.id.manager_fragment_container).setVisibility(View.VISIBLE);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.manager_fragment_container, editor)
                .addToBackStack(null)
                .commit();
    }
    private void openCustomerList(String query) {
        CustomerListFragment listFragment = new CustomerListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isManager", true);
        if (query != null) bundle.putString("searchQuery", query);
        listFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.customer_content_container, listFragment)
                .addToBackStack(null)
                .commit();
    }

}

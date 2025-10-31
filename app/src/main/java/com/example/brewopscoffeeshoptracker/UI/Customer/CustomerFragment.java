package com.example.brewopscoffeeshoptracker.UI.Customer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brewopscoffeeshoptracker.R;

public class CustomerFragment extends Fragment {
    private EditText searchBar;
    private LinearLayout allCustomersLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        searchBar = view.findViewById(R.id.customer_search_bar);
        allCustomersLayout = view.findViewById(R.id.customer_list_container);

        allCustomersLayout.setOnClickListener(v->openCustomerList(null));

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    openCustomerList(s.toString());
                }

            }
        });
        return view;
    }
    private void openCustomerList(String query) {
        Bundle bundle = new Bundle();
        if (query != null) bundle.putString("searchQuery", query);
        CustomerListFragment fragment = new CustomerListFragment();
        fragment.setArguments(bundle);

        View frame = getView().findViewById(R.id.customer_list_frame);
        frame.setVisibility(View.VISIBLE);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.customer_list_frame, fragment)
                .addToBackStack(null)
                .commit();
    }
}

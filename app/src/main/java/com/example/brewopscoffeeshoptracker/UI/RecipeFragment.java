package com.example.brewopscoffeeshoptracker.UI;

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

public class RecipeFragment extends Fragment {
    private EditText searchBar;
    private LinearLayout coffeeType, teaType, otherType;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_recipe, container, false);

        searchBar = view.findViewById(R.id.recipe_search_bar);
        coffeeType = view.findViewById(R.id.coffee_type);
        teaType = view.findViewById(R.id.tea_type);
        otherType = view.findViewById(R.id.other_type);

        coffeeType.setOnClickListener(v -> openDrinkList("Coffee"));
        teaType.setOnClickListener(v -> openDrinkList("Tea"));
        otherType.setOnClickListener(v -> openDrinkList("Other"));

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    showSearchResults(s.toString());
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });


        return view;
    }

    protected void openDrinkList(String type){
        Bundle bundle = new Bundle();
        bundle.putString("drinkType", type);
        bundle.putBoolean("isManager", false);
        DrinkListFragment fragment = new DrinkListFragment();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.recipe_content_container, fragment)
                .addToBackStack(null)
                .commit();

    }
    private void showSearchResults(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("searchQuery", query);
        bundle.putBoolean("isManager", false);
        DrinkListFragment fragment = new DrinkListFragment();
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.recipe_content_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}

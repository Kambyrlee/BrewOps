package com.example.brewopscoffeeshoptracker.UI.Recipe;

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

import com.example.brewopscoffeeshoptracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipeManagerFragment extends RecipeFragment{

    private FloatingActionButton addRecipe;
    private EditText searchBar;
    private LinearLayout coffeeType, teaType, otherType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_manager, container, false);

        addRecipe = view.findViewById(R.id.fab_add_recipe);

        addRecipe.setOnClickListener(v -> {
            openRecipeEditor();
        });
        addRecipe.bringToFront();
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
    private void openRecipeEditor() {
        requireActivity().findViewById(R.id.manager_fragment_container).setVisibility(View.VISIBLE);
        RecipeEditorFragment fragment = new RecipeEditorFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.manager_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    @Override
    protected void openDrinkList(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("drinkType", type);
        bundle.putBoolean("isManager", true);
        DrinkListFragment fragment = new DrinkListFragment();
        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.recipe_content_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void showSearchResults(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("searchQuery", query);
        bundle.putBoolean("isManager", true);
        DrinkListFragment fragment = new DrinkListFragment();
        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.recipe_content_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}

package com.example.brewopscoffeeshoptracker.UI.Ingredient;

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

public class IngredientFragment extends Fragment {

    private FloatingActionButton addIngredientFab;
    private EditText searchBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);

        addIngredientFab = view.findViewById(R.id.fab_add_ingredient);
        searchBar = view.findViewById(R.id.ingredient_search_bar);

        addIngredientFab.setOnClickListener(v-> openIngredientEditor());
        addIngredientFab.bringToFront();

        openIngredientList(null);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                openIngredientList(s.length() > 0 ? s.toString() : null);
            }
        });

        return view;
    }

    private void openIngredientEditor() {
        IngredientEditorFragment editor = new IngredientEditorFragment();
        requireActivity().findViewById(R.id.manager_fragment_container).setVisibility(View.VISIBLE);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.manager_fragment_container, editor)
                .addToBackStack(null)
                .commit();
    }

    private void openIngredientList(String query) {
        IngredientListFragment listFragment = new IngredientListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isManager", true);
        if (query != null) bundle.putString("searchQuery", query);
        listFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.ingredient_content_container, listFragment)
                .commit();
    }
}

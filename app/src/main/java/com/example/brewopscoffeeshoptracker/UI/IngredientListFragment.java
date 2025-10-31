package com.example.brewopscoffeeshoptracker.UI;

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
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientListFragment extends Fragment {
    private RecyclerView recyclerView;
    private IngredientAdapter adapter;
    private Repository repository;
    private String query;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        recyclerView = view.findViewById(R.id.ingredient_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        repository = new Repository(requireActivity().getApplication());

        if (getArguments() != null) {
            query = getArguments().getString("searchQuery");
        }

        adapter = new IngredientAdapter(new ArrayList<>(), new IngredientAdapter.onIngredientClickListener() {
            @Override
            public void onIngredientClick(Ingredient ingredient) {
                openIngredientDetails(ingredient);
            }

            @Override
            public void onEditClick(Ingredient ingredient) {
                openIngredientEditor(ingredient);
            }
        });

        recyclerView.setAdapter(adapter);
        getParentFragmentManager().setFragmentResultListener("ingredientListUpdate", this, (requestKey, bundle) -> {
            boolean refreshNeeded = bundle.getBoolean("refreshNeeded", false);
            if (refreshNeeded) {
                loadIngredients();
            }
        });

        loadIngredients();

        return view;
    }
    private void loadIngredients() {
        new Thread(() -> {
            List<Ingredient> ingredients;
            if (query != null && !query.isEmpty()) {
                ingredients = repository.searchIngredients(query);
            } else {
                ingredients = repository.getAllIngredients();
            }

            requireActivity().runOnUiThread(() -> adapter.updateList(ingredients));
        }).start();
    }
    private void openIngredientEditor(Ingredient ingredient) {
        IngredientEditorFragment editor = new IngredientEditorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ingredientID", ingredient.getIngredientID());
        editor.setArguments(bundle);

        requireActivity().findViewById(R.id.manager_fragment_container).setVisibility(View.VISIBLE);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.manager_fragment_container, editor)
                .addToBackStack(null)
                .commit();
    }

    private void openIngredientDetails(Ingredient ingredient) {
        IngredientDetailsFragment details = new IngredientDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ingredientID", ingredient.getIngredientID());
        details.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.ingredient_content_container, details)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void onResume() {
        super.onResume();
        loadIngredients();
    }
}


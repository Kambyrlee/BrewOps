package com.example.brewopscoffeeshoptracker.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.Repository;
import com.example.brewopscoffeeshoptracker.database.entities.Drink;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DrinkListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView noResults;
    private DrinkListAdapter adapter;
    private Repository repository;
    private String drinkType;
    private String searchQuery;
    private boolean isManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink_list, container, false);
        recyclerView = view.findViewById(R.id.drink_recycler_view);
        noResults = view.findViewById(R.id.no_results_text);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repository = new Repository(requireActivity().getApplication());

        if (getArguments() != null) {
            drinkType = getArguments().getString("drinkType");
            searchQuery = getArguments().getString("searchQuery");
            isManager = getArguments().getBoolean("isManager", false);
        }

        adapter = new DrinkListAdapter(
                new ArrayList<>(),
                new DrinkListAdapter.OnDrinkClickListener() {
                    @Override
                    public void onDrinkClick(Drink drink) {
                        openDrinkDetails(drink);
                    }

                    @Override
                    public void onEditClick(Drink drink) {
                        if (isManager) openRecipeEditor(drink);
                    }
                },
                isManager
        );
        recyclerView.setAdapter(adapter);
        getParentFragmentManager().setFragmentResultListener("drinkListUpdate", this, (requestKey, bundle) -> {
            boolean refreshNeeded = bundle.getBoolean("refreshNeeded", false);
            if (refreshNeeded) {
                loadDrinks(); //
            }
        });

        return view;
    }

    private void loadDrinks() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Drink> drinks = new ArrayList<>();

            if (searchQuery != null && !searchQuery.isEmpty()) {
                for (Drink d : repository.getAllDrinks()) {
                    if (d.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                        drinks.add(d);
                    }
                }
            } else if (drinkType != null) {
                switch (drinkType) {
                    case "Coffee":
                        drinks.addAll(repository.getAllCoffeeDrinks());
                        break;
                    case "Tea":
                        drinks.addAll(repository.getAllTeaDrinks());
                        break;
                    case "Other":
                        drinks.addAll(repository.getAllOtherDrinks());
                        break;
                }
            }

            requireActivity().runOnUiThread(() -> {
                if (drinks.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    noResults.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noResults.setVisibility(View.GONE);
                    adapter.updateList(drinks);
                }
            });
        });
    }

    private void openDrinkDetails(Drink drink) {
        Bundle bundle = new Bundle();
        bundle.putInt("drinkID", drink.getDrinkID());
        bundle.putString("drinkType", drinkType);

        DrinkDetailsFragment fragment = new DrinkDetailsFragment();
        fragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.recipe_content_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void openRecipeEditor(Drink drink) {
        Bundle bundle = new Bundle();
        bundle.putInt("drinkID", drink.getDrinkID());
        bundle.putString("drinkType", drinkType);

        RecipeEditorFragment editor = new RecipeEditorFragment();
        editor.setArguments(bundle);
        requireActivity().findViewById(R.id.manager_fragment_container).setVisibility(View.VISIBLE);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.manager_fragment_container, editor)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void onResume() {
        super.onResume();
        loadDrinks();
    }
}

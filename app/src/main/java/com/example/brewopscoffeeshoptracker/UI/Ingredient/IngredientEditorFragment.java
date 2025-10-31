package com.example.brewopscoffeeshoptracker.UI.Ingredient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.Repository;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;

public class IngredientEditorFragment extends Fragment {
    private EditText ingredientName, ingredientUnitPrice, ingredientServings;
    private Button saveButton, deleteButton;
    private Repository repository;
    private boolean isEditMode = false;
    private int ingredientID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_editor, container, false);

        ingredientName = view.findViewById(R.id.edit_ingredient_name);
        ingredientUnitPrice = view.findViewById(R.id.edit_ingredient_unit_price);
        ingredientServings = view.findViewById(R.id.edit_ingredient_servings);
        saveButton = view.findViewById(R.id.button_save_ingredient);
        deleteButton = view.findViewById(R.id.button_delete_ingredient);

        repository = new Repository(requireActivity().getApplication());

        if (getArguments() != null && getArguments().containsKey("ingredientID")) {
            isEditMode = true;
            ingredientID = getArguments().getInt("ingredientID");
            loadIngredientForEdit();
        }

        saveButton.setOnClickListener(v -> saveIngredient());
        deleteButton.setOnClickListener(v -> confirmDelete());

        return view;
    }
    private void loadIngredientForEdit() {
        new Thread(() -> {
            Ingredient ingredient = repository.getIngredientByID(ingredientID);
            requireActivity().runOnUiThread(() -> {
                if (ingredient != null) {
                    ingredientName.setText(ingredient.getName());
                    ingredientUnitPrice.setText(String.valueOf(ingredient.getUnitPrice()));
                    ingredientServings.setText(String.valueOf(ingredient.getServingsPerUnit()));
                    deleteButton.setVisibility(View.VISIBLE);
                }
            });
        }).start();
    }
    private void saveIngredient() {
        String name = ingredientName.getText().toString().trim();
        String priceText = ingredientUnitPrice.getText().toString().trim();
        String servingsText = ingredientServings.getText().toString().trim();

        if (name.isEmpty() || priceText.isEmpty() || servingsText.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double unitPrice;
        int servingsPerUnit;

        try {
            unitPrice = Double.parseDouble(priceText);
            servingsPerUnit = Integer.parseInt(servingsText);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid number format", Toast.LENGTH_SHORT).show();
            return;
        }

        Ingredient ingredient = new Ingredient(name, unitPrice, servingsPerUnit);
        if (isEditMode) ingredient.setIngredientID(ingredientID);

        new Thread(() -> {
            if (isEditMode) {
                repository.updateIngredient(ingredient);
            } else {
                repository.insertIngredient(ingredient);
            }

            requireActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(),
                        isEditMode ? "Ingredient updated" : "Ingredient added",
                        Toast.LENGTH_SHORT).show();

                Bundle result = new Bundle();
                result.putBoolean("refreshNeeded", true);
                requireActivity().getSupportFragmentManager()
                        .setFragmentResult("ingredientListUpdate", result);
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }).start();
    }
    private void confirmDelete() {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Ingredient")
                .setMessage("Are you sure you want to delete this ingredient?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    new Thread(() -> {
                        Ingredient ingredient = repository.getIngredientByID(ingredientID);
                        if (ingredient != null) repository.deleteIngredient(ingredient);
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), "Ingredient deleted", Toast.LENGTH_SHORT).show();
                            Bundle result = new Bundle();
                            result.putBoolean("refreshNeeded", true);
                            requireActivity().getSupportFragmentManager()
                                    .setFragmentResult("ingredientListUpdate", result);
                            requireActivity().getSupportFragmentManager().popBackStack();
                        });
                    }).start();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}

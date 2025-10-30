package com.example.brewopscoffeeshoptracker.UI;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.AppDatabase;
import com.example.brewopscoffeeshoptracker.database.Repository;
import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrink;
import com.example.brewopscoffeeshoptracker.database.entities.CoffeeDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrink;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrink;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrinkIngredientCrossRef;

import java.util.ArrayList;
import java.util.List;

public class RecipeEditorFragment extends Fragment {

    private EditText drinkName, drinkDesc, drinkPrice;
    private AutoCompleteTextView drinkTypeDropdown, teaTypeDropdown;
    private Repository repository;
    private List<Ingredient> ingredientList;
    private String[] ingredientNames;
    private Spinner ingredientSpinner;
    private boolean[] selectedIngredients;
    private ArrayList<Integer> selectedIngredientIDs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_editor, container, false);

        drinkName = view.findViewById(R.id.edit_recipe_name);
        drinkDesc = view.findViewById(R.id.edit_recipe_description);
        drinkPrice = view.findViewById(R.id.edit_recipe_price);
        drinkTypeDropdown = view.findViewById(R.id.dropdown_recipe_type);
        teaTypeDropdown = view.findViewById(R.id.dropdown_tea_type);
        ingredientSpinner = view.findViewById(R.id.spinner_ingredients);
        Button saveRecipe = view.findViewById(R.id.button_save_recipe);

        repository = new Repository(requireActivity().getApplication());
        loadIngredients();

        String[] drinkTypes = {"Coffee", "Tea", "Other"};
        ArrayAdapter<String> drinkTypeAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                drinkTypes
        );
        drinkTypeDropdown.setAdapter(drinkTypeAdapter);
        drinkTypeDropdown.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                drinkTypeDropdown.showDropDown();
            }
            return false;
        });

        String[] teaTypes = {"Green", "Black", "Chai", "Herbal"};
        ArrayAdapter<String> teaTypeAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                teaTypes
        );
        teaTypeDropdown.setAdapter(teaTypeAdapter);
        teaTypeDropdown.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                teaTypeDropdown.showDropDown();
            }
            return false;
        });

        drinkTypeDropdown.setOnItemClickListener((parent, view1, position, id) -> {
            String selected = parent.getItemAtPosition(position).toString();
            if (selected.equalsIgnoreCase("Tea")) {
                teaTypeDropdown.setVisibility(View.VISIBLE);
            } else {
                teaTypeDropdown.setVisibility(View.GONE);
            }
        });

        saveRecipe.setOnClickListener(v -> saveRecipe());

        return view;
    }

    private void loadIngredients(){
        new Thread(()-> {
            ingredientList = AppDatabase.getDatabase(getContext())
                    .ingredientDAO()
                    .getAllIngredients();
            requireActivity().runOnUiThread(()-> setupIngredientSelector());
        }).start();
    }

    private void setupIngredientSelector() {
        ingredientNames = new String[ingredientList.size()];
        selectedIngredients = new boolean[ingredientList.size()];
        for (int i = 0; i < ingredientList.size(); i++) {
            ingredientNames[i] = ingredientList.get(i).getName();
        }

        ingredientSpinner.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) { showIngredientDialog(); }
            return true;
        });
    }

    private void showIngredientDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Ingredients:");
        builder.setMultiChoiceItems(ingredientNames, selectedIngredients, (dialog, which, isChecked) -> {
            if (isChecked) {
                selectedIngredientIDs.add(ingredientList.get(which).getIngredientID());
            } else {
                selectedIngredientIDs.remove(Integer.valueOf(ingredientList.get(which).getIngredientID()));
            }
        });
        builder.setPositiveButton("Done", (dialog, which) -> {
            String selected = selectedIngredientIDs.size() + " ingredients selected";
            TextView label = getView().findViewById(R.id.ingredient_selected_text);
            if (label != null) label.setText(selected);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }
    private void saveRecipe() {
        String name = drinkName.getText().toString().trim();
        String type = drinkTypeDropdown.getText().toString().trim();
        String priceText = drinkPrice.getText().toString().trim();
        String desc = drinkDesc.getText().toString().trim();

        if (name.isEmpty() || type.isEmpty() || priceText.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid price value", Toast.LENGTH_SHORT).show();
            return;
        }

        // Collect selected ingredient IDs from your multiselect dropdown
        List<Integer> selectedIngredientIDs = this.selectedIngredientIDs;

        switch (type.toLowerCase()) {
            case "coffee":
                CoffeeDrink coffee = new CoffeeDrink(name, price, desc);
                repository.insertCoffeeDrink(coffee, drinkID -> {
                    for (Integer ingredientID : selectedIngredientIDs) {
                        repository.insertCoffeeCrossRef(
                                new CoffeeDrinkIngredientCrossRef(drinkID.intValue(), ingredientID)
                        );
                    }
                });
                break;

            case "tea":
                String teaType = teaTypeDropdown.getText().toString().trim();
                TeaDrink tea = new TeaDrink(name, teaType, price, desc);
                repository.insertTeaDrink(tea, drinkID -> {
                    for (Integer ingredientID : selectedIngredientIDs) {
                        repository.insertTeaCrossRef(
                                new TeaDrinkIngredientCrossRef(drinkID.intValue(), ingredientID)
                        );
                    }
                });
                break;

            default:
                OtherDrink other = new OtherDrink(name, price, desc);
                repository.insertOtherDrink(other, drinkID -> {
                    for (Integer ingredientID : selectedIngredientIDs) {
                        repository.insertOtherCrossRef(
                                new OtherDrinkIngredientCrossRef(drinkID.intValue(), ingredientID)
                        );
                    }
                });
                break;
        }

        Toast.makeText(getContext(), "Recipe saved!", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

}

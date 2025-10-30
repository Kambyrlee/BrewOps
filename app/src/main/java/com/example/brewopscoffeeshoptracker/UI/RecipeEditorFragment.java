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
import com.example.brewopscoffeeshoptracker.database.entities.Drink;
import com.example.brewopscoffeeshoptracker.database.entities.Ingredient;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrink;
import com.example.brewopscoffeeshoptracker.database.entities.OtherDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrink;
import com.example.brewopscoffeeshoptracker.database.entities.TeaDrinkIngredientCrossRef;
import com.example.brewopscoffeeshoptracker.database.relations.CoffeeDrinkWithIngredients;
import com.example.brewopscoffeeshoptracker.database.relations.OtherDrinkWithIngredients;
import com.example.brewopscoffeeshoptracker.database.relations.TeaDrinkWithIngredients;

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
    private TextView ingredientSelectedText;
    private boolean isEditMode = false;
    private int drinkID = -1;
    private String drinkType = "";

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
        ingredientSelectedText = view.findViewById(R.id.ingredient_selected_text);
        Button saveRecipe = view.findViewById(R.id.button_save_recipe);

        repository = new Repository(requireActivity().getApplication());
        loadIngredients();

        if (getArguments() != null && getArguments().containsKey("drinkID")) {
            isEditMode = true;
            drinkID = getArguments().getInt("drinkID");
            drinkType = getArguments().getString("drinkType", "");
            loadDrinkDetailsForEdit(drinkID, drinkType);
        }
        Button deleteButton = view.findViewById(R.id.button_delete_recipe);
        if (isEditMode) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> confirmDelete());
        } else {
            deleteButton.setVisibility(View.GONE);
        }

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
    private void confirmDelete() {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    switch (drinkType.toLowerCase()) {
                        case "coffee":
                            repository.deleteCoffeeDrink(drinkID);
                            break;
                        case "tea":
                            repository.deleteTeaDrink(drinkID);
                            break;
                        default:
                            repository.deleteOtherDrink(drinkID);
                            break;
                    }
                    Toast.makeText(getContext(), "Recipe deleted", Toast.LENGTH_SHORT).show();
                    Bundle result = new Bundle();
                    result.putBoolean("refreshNeeded", true);
                    requireActivity().getSupportFragmentManager().setFragmentResult("drinkListUpdate", result);
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void loadDrinkDetailsForEdit(int drinkID, String drinkType) {
        new Thread(() -> {
            List<Ingredient> ingredients = new ArrayList<>();
            Drink baseDrink = null;

            switch (drinkType.toLowerCase()) {
                case "coffee":
                    CoffeeDrinkWithIngredients coffeeWith = repository.getCoffeeDrinkWithIngredients(drinkID);
                    if (coffeeWith != null) {
                        baseDrink = coffeeWith.drink;
                        if (coffeeWith.ingredientList != null)
                            ingredients = coffeeWith.ingredientList;
                    }
                    break;
                case "tea":
                    TeaDrinkWithIngredients teaWith = repository.getTeaDrinkWithIngredients(drinkID);
                    if (teaWith != null) {
                        baseDrink = teaWith.drink;
                        if (teaWith.ingredientList != null)
                            ingredients = teaWith.ingredientList;
                    }
                    break;
                default:
                    OtherDrinkWithIngredients otherWith = repository.getOtherDrinkWithIngredients(drinkID);
                    if (otherWith != null) {
                        baseDrink = otherWith.drink;
                        if (otherWith.ingredientList != null)
                            ingredients = otherWith.ingredientList;
                    }
                    break;
            }

            Drink finalDrink = baseDrink;
            List<Ingredient> finalIngredients = ingredients;

            requireActivity().runOnUiThread(() -> {
                if (finalDrink != null) {
                    drinkName.setText(finalDrink.getName());
                    drinkPrice.setText(String.valueOf(finalDrink.getPrice()));
                    drinkDesc.setText(finalDrink.getRecipe());
                    drinkTypeDropdown.setText(drinkType, false);

                    if (finalDrink instanceof TeaDrink) {
                        teaTypeDropdown.setVisibility(View.VISIBLE);
                        teaTypeDropdown.setText(((TeaDrink) finalDrink).getTeaType(), false);
                    }
                    for (Ingredient ingredient : finalIngredients) {
                        for (int i = 0; i < ingredientList.size(); i++) {
                            if (ingredientList.get(i).getIngredientID() == ingredient.getIngredientID()) {
                                selectedIngredients[i] = true;
                                if (!selectedIngredientIDs.contains(ingredient.getIngredientID()))
                                    selectedIngredientIDs.add(ingredient.getIngredientID());
                            }
                        }
                    }

                    String selected = selectedIngredientIDs.size() + " ingredients selected";
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            new String[]{selected}
                    );
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ingredientSpinner.setAdapter(spinnerAdapter);
                    ((TextView) requireView().findViewById(R.id.ingredient_selected_text)).setText(selected);
                }
            });
        }).start();
    }

    private void loadExistingRecipe(int drinkID, String drinkType) {
        new Thread(() -> {
            switch (drinkType) {
                case "Coffee":
                    CoffeeDrinkWithIngredients coffee = repository.getCoffeeDrinkWithIngredients(drinkID);
                    requireActivity().runOnUiThread(() -> populateFields(coffee.drink, drinkType, coffee.ingredientList));
                    break;
                case "Tea":
                    TeaDrinkWithIngredients tea = repository.getTeaDrinkWithIngredients(drinkID);
                    requireActivity().runOnUiThread(() -> populateFields(tea.drink, drinkType, tea.ingredientList));
                    break;
                case "Other":
                    OtherDrinkWithIngredients other = repository.getOtherDrinkWithIngredients(drinkID);
                    requireActivity().runOnUiThread(() -> populateFields(other.drink, drinkType, other.ingredientList));
                    break;
            }
        }).start();
    }

    private void populateFields(Drink drink, String type, List<Ingredient> ingredientsList) {
        if (drink == null) return;

        drinkName.setText(drink.getName());
        drinkDesc.setText(drink.getDirections());
        drinkPrice.setText(String.valueOf(drink.getPrice()));
        drinkTypeDropdown.setText(type, false);

        if (type.equalsIgnoreCase("Tea") && drink instanceof TeaDrink) {
            teaTypeDropdown.setVisibility(View.VISIBLE);
            teaTypeDropdown.setText(((TeaDrink) drink).getTeaType(), false);
        }
        if (ingredientList != null && ingredientsList != null) {
            selectedIngredientIDs.clear();

            for (Ingredient existing : ingredientsList) {
                for (int i = 0; i < ingredientList.size(); i++) {
                    if (ingredientList.get(i).getIngredientID() == existing.getIngredientID()) {
                        selectedIngredients[i] = true;
                        selectedIngredientIDs.add(existing.getIngredientID());
                        break;
                    }
                }
            }
            String selected = selectedIngredientIDs.isEmpty()
                    ? "No ingredients selected"
                    : selectedIngredientIDs.size() + " ingredients selected";

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    new String[]{selected}
            );
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ingredientSpinner.setAdapter(spinnerAdapter);
            ingredientSelectedText.setText(selected);

        }
    }


    private void loadIngredients(){
        new Thread(()-> {
            ingredientList = AppDatabase.getDatabase(getContext())
                    .ingredientDAO()
                    .getAllIngredients();
            requireActivity().runOnUiThread(()-> setupIngredientSelector());

            if (getArguments() != null && getArguments().containsKey("drinkID")) {
                int drinkID = getArguments().getInt("drinkID");
                String drinkType = getArguments().getString("drinkType");
                loadExistingRecipe(drinkID, drinkType);
            }
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
    private void updateRecipe() {
        String name = drinkName.getText().toString().trim();
        String desc = drinkDesc.getText().toString().trim();
        double price = Double.parseDouble(drinkPrice.getText().toString().trim());
        String type = drinkTypeDropdown.getText().toString().trim();

        switch (type.toLowerCase()) {
            case "coffee":
                CoffeeDrink coffee = new CoffeeDrink(name, price, desc);
                coffee.setDrinkID(drinkID);
                repository.updateCoffeeDrink(coffee);
                break;
            case "tea":
                String teaType = teaTypeDropdown.getText().toString().trim();
                TeaDrink tea = new TeaDrink(name, teaType, price, desc);
                tea.setDrinkID(drinkID);
                repository.updateTeaDrink(tea);
                break;
            default:
                OtherDrink other = new OtherDrink(name, price, desc);
                other.setDrinkID(drinkID);
                repository.updateOtherDrink(other);
                break;
        }

        Toast.makeText(getContext(), "Recipe updated!", Toast.LENGTH_SHORT).show();
        Bundle result = new Bundle();
        result.putBoolean("refreshNeeded", true);
        requireActivity().getSupportFragmentManager().setFragmentResult("drinkListUpdate", result);
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void saveRecipe() {
        if (isEditMode) {
            updateRecipe();
            return;
        }
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
        Bundle result = new Bundle();
        result.putBoolean("refreshNeeded", true);
        requireActivity().getSupportFragmentManager().setFragmentResult("drinkListUpdate", result);
        requireActivity().getSupportFragmentManager().popBackStack();
    }

}

package com.example.brewopscoffeeshoptracker.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.brewopscoffeeshoptracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipeManagerFragment extends RecipeFragment{

    private FloatingActionButton addRecipe;

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

        return view;
    }
    private void openRecipeEditor() {
        requireActivity().findViewById(R.id.manager_fragment_container).setVisibility(View.VISIBLE);
        RecipeEditorFragment fragment = new RecipeEditorFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.manager_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}

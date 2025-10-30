package com.example.brewopscoffeeshoptracker.UI;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.brewopscoffeeshoptracker.UI.Recipe.RecipeManagerFragment;
import com.example.brewopscoffeeshoptracker.UI.Recipe.ReportingFragment;

public class ManagerAdapter extends FragmentStateAdapter {
    public ManagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new RecipeManagerFragment();
            case 1:
                return new CustomerManagerFragment();
            case 2:
                return new IngredientFragment();
            case 3:
                return new ReportingFragment();
            default:
                return new RecipeManagerFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}

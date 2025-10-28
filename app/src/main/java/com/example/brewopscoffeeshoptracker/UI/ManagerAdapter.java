package com.example.brewopscoffeeshoptracker.UI;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ManagerAdapter extends FragmentStateAdapter {
    public ManagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new RecipeFragment();
            case 1:
                return new CustomerFragment();
            case 2:
                return new IngredientFragment();
            case 3:
                return new ReportingFragment();
            default:
                return new RecipeFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}

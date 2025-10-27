package com.example.brewopscoffeeshoptracker.UI;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class EmployeeAdapter extends FragmentStateAdapter {
    public EmployeeAdapter(@NonNull FragmentActivity fragmentActivity) {
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
            default:
                return new RecipeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

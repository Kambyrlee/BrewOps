package com.example.brewopscoffeeshoptracker.UI;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.brewopscoffeeshoptracker.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ManagerHome extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);

        ViewPager2 viewPager = findViewById(R.id.manager_view_pager);
        TabLayout tabLayout = findViewById(R.id.manager_tab_nav);

        ManagerAdapter adapter = new ManagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Recipe Book");
                    break;
                case 1:
                    tab.setText("Customers");
                    break;
                case 2:
                    tab.setText("Ingredients");
                    break;
                case 3:
                    tab.setText("Reporting");
                    break;
            }
        }).attach();
    }
}

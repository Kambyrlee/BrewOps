package com.example.brewopscoffeeshoptracker.UI;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.brewopscoffeeshoptracker.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class EmployeeHome extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private EmployeeAdapter employeeAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.employee_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int extraPadding = getResources().getDimensionPixelSize(R.dimen.horizontal_padding_extra);

            v.setPadding(
                    systemBars.left + extraPadding,
                    systemBars.top,
                    systemBars.right + extraPadding,
                    systemBars.bottom
            );
            return insets;
        });

        tabLayout = findViewById(R.id.employee_tab_nav);
        viewPager = findViewById(R.id.employee_view_pager);
        employeeAdapter = new EmployeeAdapter(this);
        viewPager.setAdapter(employeeAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Recipe Book");
                    break;
                case 1:
                    tab.setText("Customers");
                    break;
            }
        }).attach();
    }
}

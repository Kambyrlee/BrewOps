package com.example.brewopscoffeeshoptracker.UI.Reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brewopscoffeeshoptracker.R;

public class ReportsFragment extends Fragment {
    private Button costReport, popularReport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        costReport = view.findViewById(R.id.button_cost_report);
        popularReport = view.findViewById(R.id.button_popular_report);

        costReport.setOnClickListener(v -> openReport(new DrinkCostReportFragment()));
        popularReport.setOnClickListener(v -> openReport(new PopularDrinkReportFragment()));

        return view;
    }
    private void openReport(Fragment fragment){
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.report_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}

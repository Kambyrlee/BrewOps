package com.example.brewopscoffeeshoptracker.UI.Reports;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brewopscoffeeshoptracker.R;
import com.example.brewopscoffeeshoptracker.database.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DrinkCostReportFragment extends Fragment {
    private Repository repository;
    private TableLayout tableLayout;
    private TextView title, timestamp, description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_details, container, false);

        repository = new Repository(requireActivity().getApplication());
        tableLayout = view.findViewById(R.id.report_table);
        title = view.findViewById(R.id.report_title);
        timestamp = view.findViewById(R.id.report_timestamp);
        description = view.findViewById(R.id.report_description);

        title.setText("Drink Production Cost vs. Sell Price");
        description.setText("This report compares the total material cost or Production Cost (Prod. Cost) of all ingredients in a recipe with the drink's menu price.");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        timestamp.setText("Generated: " + sdf.format(new Date()));

        generateReport();

        return view;
    }
    private void generateReport(){
        new Thread(() -> {
            List<ReportRow> reportRows = new ArrayList<>();

            for (var coffee : repository.getAllCoffeeDrinks()) {
                double totalCost = 0.0;
                var coffeeWith = repository.getCoffeeDrinkWithIngredients(coffee.getDrinkID());
                if (coffeeWith != null && coffeeWith.ingredientList != null) {
                    for (var i : coffeeWith.ingredientList) {
                        totalCost += i.getUnitPrice() / i.getServingsPerUnit();
                    }
                }
                reportRows.add(new ReportRow( coffee.getName(), totalCost, coffee.getPrice()));

            }

            for (var tea : repository.getAllTeaDrinks()) {
                double totalCost = 0.0;
                var teaWith = repository.getTeaDrinkWithIngredients(tea.getDrinkID());
                if (teaWith != null && teaWith.ingredientList != null) {
                    for (var i : teaWith.ingredientList) {
                        totalCost += i.getUnitPrice() / i.getServingsPerUnit();
                    }
                }
                reportRows.add(new ReportRow( tea.getName(), totalCost, tea.getPrice()));
            }

            for (var other : repository.getAllOtherDrinks()) {
                double totalCost = 0.0;
                var otherWith = repository.getOtherDrinkWithIngredients(other.getDrinkID());
                if (otherWith != null && otherWith.ingredientList != null) {
                    for (var i : otherWith.ingredientList) {
                        totalCost += i.getUnitPrice() / i.getServingsPerUnit();
                    }
                }
                reportRows.add(new ReportRow( other.getName(), totalCost, other.getPrice()));
            }

            requireActivity().runOnUiThread(() -> populateTable(reportRows));
        }).start();
    }
    private void populateTable(List<ReportRow> reportRows) {

        tableLayout.removeAllViews();

        TableRow header = new TableRow(getContext());
        header.addView(makeHeaderCell("Drink Name"));
        header.addView(makeHeaderCell("Prod. Cost"));
        header.addView(makeHeaderCell("Sell Price"));
        tableLayout.addView(header);

        for (ReportRow row : reportRows) {
            TableRow tr = new TableRow(getContext());
            tr.addView(makeCell(row.name));
            tr.addView(makeCell(String.format("$%.2f", row.cost)));
            tr.addView(makeCell(String.format("$%.2f", row.price)));
            tableLayout.addView(tr);
        }
    }
    private TextView makeHeaderCell(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setPadding(16, 8, 16, 8);
        return tv;
    }

    private TextView makeCell(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setPadding(16, 8, 16, 8);
        return tv;
    }
    private static class ReportRow {
        String name;
        double cost;
        double price;

        ReportRow(String name, double cost, double price) {
            this.name = name;
            this.cost = cost;
            this.price = price;
        }
    }
}

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
import com.example.brewopscoffeeshoptracker.database.entities.Customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class PopularDrinkReportFragment extends Fragment {
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

        title.setText("Most Popular Drinks Report");
        description.setText("This report shows the top three most popular drinks by order frequency.");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        timestamp.setText("Generated: " + sdf.format(new Date()));

        generateReport();

        return view;
    }
    private void generateReport() {
        new Thread(() -> {
            List<Customer> customers = repository.getAllCustomers();

            Map<String, Integer> orderCount = new HashMap<>();
            for (Customer c : customers) {
                String order = c.getDrinkOrder();
                if (order != null && !order.isEmpty()) {
                    orderCount.put(order, orderCount.getOrDefault(order, 0) + 1);
                }
            }

            List<ReportRow> reportRows = orderCount.entrySet()
                    .stream()
                    .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                    .limit(3)
                    .map(entry -> new ReportRow(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

            requireActivity().runOnUiThread(() -> populateTable(reportRows));
        }).start();
    }
    private void populateTable(List<ReportRow> reportRows) {
        tableLayout.removeAllViews();

        TableRow header = new TableRow(getContext());
        header.addView(makeHeaderCell("Rank"));
        header.addView(makeHeaderCell("Drink Name"));
        header.addView(makeHeaderCell("Orders"));
        tableLayout.addView(header);

        int rank = 1;
        for (ReportRow row : reportRows) {
            TableRow tr = new TableRow(getContext());
            tr.addView(makeCell(String.valueOf(rank++)));
            tr.addView(makeCell(row.name));
            tr.addView(makeCell(String.valueOf(row.orders)));
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
        int orders;

        ReportRow(String name, int orders) {
            this.name = name;
            this.orders = orders;
        }
    }
}

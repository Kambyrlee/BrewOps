package com.example.brewopscoffeeshoptracker.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.brewopscoffeeshoptracker.R;

public class MainActivity extends AppCompatActivity {

    private static final String MANAGER_PASSCODE = "1118";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.employee_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmployeeHome.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.manager_button);
        button2.setOnClickListener(v-> showManagerPasscodeDialog());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void showManagerPasscodeDialog() {
        final EditText input = new EditText(this);
        input.setHint("Enter manager passcode...");
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        input.setPadding(60, 40, 60, 40);

        new AlertDialog.Builder(this)
                .setTitle("Manager Access")
                .setMessage("Please enter your manager passcode to continue")
                .setView(input)
                .setPositiveButton("Enter", (dialog, which)-> {
                    String enteredCode = input.getText().toString().trim();
                    if (enteredCode.equals(MANAGER_PASSCODE)) {
                        Intent intent = new Intent(MainActivity.this, ManagerHome.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Incorrect passcode", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }
}
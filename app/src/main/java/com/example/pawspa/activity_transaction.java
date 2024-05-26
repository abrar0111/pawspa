package com.example.pawspa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class activity_transaction extends AppCompatActivity {

    private EditText etNumberOfDays;
    private TextView tvTotalAmount;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        etNumberOfDays = findViewById(R.id.etNumberOfDays);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    calculateTotalAmount();
                }
            }
        });
    }

    private boolean validateInputs() {
        String numberOfDays = etNumberOfDays.getText().toString().trim();

        if (numberOfDays.isEmpty()) {
            etNumberOfDays.setError("Number of Days is required");
            etNumberOfDays.requestFocus();
            return false;
        }

        return true;
    }

    private void calculateTotalAmount() {
        int numberOfDays = Integer.parseInt(etNumberOfDays.getText().toString().trim());
        int totalAmount = numberOfDays * 2;
        tvTotalAmount.setText("Total Amount: " + totalAmount);
    }
}

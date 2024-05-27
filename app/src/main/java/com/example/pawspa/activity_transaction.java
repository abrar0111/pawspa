package com.example.pawspa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class activity_transaction extends AppCompatActivity {

    private EditText etNumberOfAppointments;
    private TextView tvTotalAmount;
    private Button btnCalculate, btnAdminPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        etNumberOfAppointments = findViewById(R.id.etNumberOfAppointments);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnAdminPage = findViewById(R.id.btnAdminPage);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    calculateTotalAmount();
                }
            }
        });

        btnAdminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_transaction.this, activity_admin.class));
            }
        });
    }

    private boolean validateInputs() {
        String numberOfAppointments = etNumberOfAppointments.getText().toString().trim();

        if (numberOfAppointments.isEmpty()) {
            etNumberOfAppointments.setError("Number of Appointments is required");
            etNumberOfAppointments.requestFocus();
            return false;
        }

        return true;
    }

    private void calculateTotalAmount() {
        int numberOfAppointments = Integer.parseInt(etNumberOfAppointments.getText().toString().trim());
        int totalAmount = numberOfAppointments * 2; // Assuming the rate is 2 currency units per appointment
        tvTotalAmount.setText("Total Amount: " + totalAmount);
    }
}

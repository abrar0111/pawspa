package com.example.pawspa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activity_admin extends AppCompatActivity {

    private Button btnAddRecord, btnDeleteRecord, btnUpdateRecord, btnViewRecord, btnReset, btnCalculate, btnClose;
    private EditText etRecordId, etField1, etField2; // Assume you have these fields in your layout

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = FirebaseFirestore.getInstance();

        btnAddRecord = findViewById(R.id.btnAddRecord);
        btnDeleteRecord = findViewById(R.id.btnDeleteRecord);
        btnUpdateRecord = findViewById(R.id.btnUpdateRecord);
        btnViewRecord = findViewById(R.id.btnViewRecord);
        btnReset = findViewById(R.id.btnReset);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClose = findViewById(R.id.btnClose);

        etRecordId = findViewById(R.id.etRecordId);
        etField1 = findViewById(R.id.etField1);
        etField2 = findViewById(R.id.etField2);

        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecord();
            }
        });

        btnDeleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecord();
            }
        });

        btnUpdateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecord();
            }
        });

        btnViewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecord();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTransaction();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addRecord() {
        String field1 = etField1.getText().toString();
        String field2 = etField2.getText().toString();

        Map<String, Object> record = new HashMap<>();
        record.put("field1", field1);
        record.put("field2", field2);

        db.collection("records")
                .add(record)
                .addOnSuccessListener(documentReference -> Toast.makeText(activity_admin.this, "Record added", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(activity_admin.this, "Error adding record", Toast.LENGTH_SHORT).show());
    }

    private void deleteRecord() {
        String recordId = etRecordId.getText().toString();

        db.collection("records").document(recordId)
                .delete()
                .addOnSuccessListener(aVoid -> Toast.makeText(activity_admin.this, "Record deleted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(activity_admin.this, "Error deleting record", Toast.LENGTH_SHORT).show());
    }

    private void updateRecord() {
        String recordId = etRecordId.getText().toString();
        String field1 = etField1.getText().toString();
        String field2 = etField2.getText().toString();

        Map<String, Object> updates = new HashMap<>();
        updates.put("field1", field1);
        updates.put("field2", field2);

        db.collection("records").document(recordId)
                .update(updates)
                .addOnSuccessListener(aVoid -> Toast.makeText(activity_admin.this, "Record updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(activity_admin.this, "Error updating record", Toast.LENGTH_SHORT).show());
    }

    private void viewRecord() {
        String recordId = etRecordId.getText().toString();

        db.collection("records").document(recordId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String field1 = documentSnapshot.getString("field1");
                        String field2 = documentSnapshot.getString("field2");
                        etField1.setText(field1);
                        etField2.setText(field2);
                        Toast.makeText(activity_admin.this, "Record found", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity_admin.this, "No such record", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(activity_admin.this, "Error fetching record", Toast.LENGTH_SHORT).show());
    }

    private void resetFields() {
        etRecordId.setText("");
        etField1.setText("");
        etField2.setText("");
        Toast.makeText(this, "Fields reset", Toast.LENGTH_SHORT).show();
    }

    private void calculateTransaction() {
        String field1 = etField1.getText().toString();
        String field2 = etField2.getText().toString();

        // Example calculation logic
        int result = Integer.parseInt(field1) + Integer.parseInt(field2);
        Toast.makeText(this, "Calculation result: " + result, Toast.LENGTH_SHORT).show();
    }
}

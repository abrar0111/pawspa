package com.example.pawspa;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class activity_admin extends AppCompatActivity {

    private static final String TAG = "activity_admin";

    private Button btnAddRecord, btnDeleteRecord, btnUpdateRecord, btnViewRecord, btnReset, btnCalculate, btnClose;
    private EditText etRecordId, etPetName, etOwnerName, etDate;
    private RecyclerView recyclerView;
    private item_record recordAdapter;

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
        etPetName = findViewById(R.id.etPetName);
        etOwnerName = findViewById(R.id.etOwnerName);
        etDate = findViewById(R.id.etDate);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordAdapter = new item_record();
        recyclerView.setAdapter(recordAdapter);

        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields(false)) {
                    addRecord();
                }
            }
        });

        btnDeleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields(true)) {
                    deleteRecord();
                }
            }
        });

        btnUpdateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields(true)) {
                    updateRecord();
                }
            }
        });

        btnViewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRecords();
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
                Log.d(TAG, "Close button clicked");
                finish();
            }
        });
    }

    private boolean validateFields(boolean includeRecordId) {
        if (includeRecordId) {
            String recordId = etRecordId.getText().toString().trim();
            if (recordId.isEmpty()) {
                etRecordId.setError("Record ID is required");
                etRecordId.requestFocus();
                return false;
            }
        }

        String petName = etPetName.getText().toString().trim();
        String ownerName = etOwnerName.getText().toString().trim();
        String date = etDate.getText().toString().trim();

        if (petName.isEmpty()) {
            etPetName.setError("Pet Name is required");
            etPetName.requestFocus();
            return false;
        }

        if (ownerName.isEmpty()) {
            etOwnerName.setError("Owner Name is required");
            etOwnerName.requestFocus();
            return false;
        }

        if (date.isEmpty()) {
            etDate.setError("Date is required");
            etDate.requestFocus();
            return false;
        }

        return true;
    }

    private void addRecord() {
        String petName = etPetName.getText().toString();
        String ownerName = etOwnerName.getText().toString();
        String date = etDate.getText().toString();

        Map<String, Object> record = new HashMap<>();
        record.put("petName", petName);
        record.put("ownerName", ownerName);
        record.put("date", date);

        db.collection("groomingRecords")
                .add(record)
                .addOnSuccessListener(documentReference -> Toast.makeText(activity_admin.this, "Record added", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(activity_admin.this, "Error adding record: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error adding record", e);
                });
    }

    private void deleteRecord() {
        String recordId = etRecordId.getText().toString();

        db.collection("groomingRecords").document(recordId)
                .delete()
                .addOnSuccessListener(aVoid -> Toast.makeText(activity_admin.this, "Record deleted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(activity_admin.this, "Error deleting record: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error deleting record", e);
                });
    }

    private void updateRecord() {
        String recordId = etRecordId.getText().toString();
        String petName = etPetName.getText().toString();
        String ownerName = etOwnerName.getText().toString();
        String date = etDate.getText().toString();

        Map<String, Object> updates = new HashMap<>();
        updates.put("petName", petName);
        updates.put("ownerName", ownerName);
        updates.put("date", date);

        db.collection("groomingRecords").document(recordId)
                .set(updates)
                .addOnSuccessListener(aVoid -> Toast.makeText(activity_admin.this, "Record updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(activity_admin.this, "Error updating record: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error updating record", e);
                });
    }

    private void loadRecords() {
        db.collection("groomingRecords")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    recordAdapter.setRecords(querySnapshot);
                    Toast.makeText(activity_admin.this, "Records loaded", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity_admin.this, "Error loading records: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error loading records", e);
                });
    }

    private void resetFields() {
        etRecordId.setText("");
        etPetName.setText("");
        etOwnerName.setText("");
        etDate.setText("");
        Toast.makeText(this, "Fields reset", Toast.LENGTH_SHORT).show();
    }

    private void calculateTransaction() {
        String petName = etPetName.getText().toString();
        String ownerName = etOwnerName.getText().toString();

        if (petName.isEmpty() || ownerName.isEmpty()) {
            Toast.makeText(this, "Please enter Pet Name and Owner Name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Example calculation logic, you can customize this as needed
        int result = petName.length() + ownerName.length();
        Toast.makeText(this, "Calculation result: " + result, Toast.LENGTH_SHORT).show();
    }
}

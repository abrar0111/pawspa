package com.example.pawspa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class item_record extends RecyclerView.Adapter<item_record.RecordViewHolder> {

    private List<DocumentSnapshot> records = new ArrayList<>();

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new RecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        DocumentSnapshot record = records.get(position);
        holder.tvPetName.setText(record.getString("petName"));
        holder.tvOwnerName.setText(record.getString("ownerName"));
        holder.tvServiceType.setText(record.getString("serviceType"));
        holder.tvDate.setText(record.getString("date"));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void setRecords(QuerySnapshot querySnapshot) {
        records = querySnapshot.getDocuments();
        notifyDataSetChanged();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView tvPetName, tvOwnerName, tvServiceType, tvDate;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPetName = itemView.findViewById(R.id.tvPetName);
            tvOwnerName = itemView.findViewById(R.id.tvOwnerName);
            tvServiceType = itemView.findViewById(R.id.tvServiceType);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}

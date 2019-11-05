package com.example.mystrathmoreapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UpdatesListAdapter extends RecyclerView.Adapter<UpdatesListAdapter.ViewHolder> {

    public List<Updates> updatesList;

    public UpdatesListAdapter(List<Updates> updatesList){
        this.updatesList = updatesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.updates_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.updateUnit.setText(updatesList.get(position).getUpdateUnit());
        holder.updateDescription.setText(updatesList.get(position).getUpdateDescription());
    }

    @Override
    public int getItemCount() {
        return updatesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public TextView updateUnit;
        public TextView updateDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            updateDescription = mView.findViewById(R.id.updateDescription);
            updateUnit = mView.findViewById(R.id.update_unit);



        }
    }
}

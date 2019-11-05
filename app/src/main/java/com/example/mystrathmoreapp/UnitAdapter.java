package com.example.mystrathmoreapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.ViewHolder> {

    public List<Unit> unitList;
    public Context context;

    public UnitAdapter (Context context, List<Unit> unitList){
        this.unitList = unitList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.textViewLecturer_name.setText(unitList.get(position).getLecturer_name());
        holder.textViewUnit_name.setText(unitList.get(position).getUnit_name());
        holder.textViewClass_time.setText(unitList.get(position).getClass_time());

        final String unit_name = unitList.get(position).getUnit_name();

        final String unit_id = unitList.get(position).unitId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Onclick","clicked");
                unit_activity(unit_id,unit_name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        TextView textViewLecturer_name;
        TextView textViewUnit_name;
        TextView textViewClass_time;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            textViewLecturer_name = (TextView) mView.findViewById(R.id.lecturer_name);
            textViewUnit_name = (TextView) mView.findViewById(R.id.unit_name);
            textViewClass_time = (TextView) mView.findViewById(R.id.class_time);
        }
    }

    public void unit_activity(String id, String unit_name){
        Intent intent = new Intent(context,unitView.class);
        intent.putExtra("UNIT_NAME",unit_name);
        intent.putExtra("COURSE_ID",id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

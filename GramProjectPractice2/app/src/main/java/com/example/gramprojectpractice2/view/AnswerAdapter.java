package com.example.gramprojectpractice2.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramprojectpractice2.AnswerActivity;
import com.example.gramprojectpractice2.R;
import com.example.gramprojectpractice2.model.AnswerData;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.CustomViewHolder> {

    private ArrayList<AnswerData> arrayList;

    public AnswerAdapter(AnswerActivity addActivity, ArrayList<AnswerData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AnswerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newitem_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.answer.setText(arrayList.get(position).getName()+" : "+arrayList.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {return (null != arrayList ? arrayList.size():0);}
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView answer;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.answer = itemView.findViewById(R.id.newName);
        }
    }
}

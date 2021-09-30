package com.example.gramprojectpractice2.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gramprojectpractice2.AnswerActivity;
import com.example.gramprojectpractice2.MainActivity;
import com.example.gramprojectpractice2.R;
import com.example.gramprojectpractice2.model.MainData;
import com.example.gramprojectpractice2.model.SeeResponse;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CutomViewHolder> {
    private Context context;
    ArrayList<MainData> arrayList;
    MainActivity mainActivity = new MainActivity();
    public MainAdapter(Context context, ArrayList<MainData> arrayList){
        this.context=context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public CutomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CutomViewHolder holder = new CutomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CutomViewHolder holder, int position) {

        holder.itemTitle.setText(arrayList.get(position).getTitle());
        holder.itemMain.setText(arrayList.get(position).getContent());
        holder.itemName.setText(arrayList.get(position).getName());
        holder.itemid_pk.setText(arrayList.get(position).getId_pk());

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size():0);
    }

    public class CutomViewHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected TextView itemMain;
        protected TextView itemName;
        protected TextView itemid_pk;
        public CutomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemTitle = itemView.findViewById(R.id.item_Title);
            this.itemMain = itemView.findViewById(R.id.item_Main);
            this.itemName = itemView.findViewById(R.id.item_Name);
            this.itemid_pk = itemView.findViewById(R.id.item_id_pk);


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String sendItemTitle = itemTitle.getText().toString();
                    String sendItemMain = itemMain.getText().toString();
                    String sendItemName = itemName.getText().toString();
                    String sendItemid_pk = itemid_pk.getText().toString();

                    Intent intent = new Intent(v.getContext(), AnswerActivity.class);
                    intent.putExtra("itemTitle",sendItemTitle);
                    intent.putExtra("itemmain",sendItemMain);
                    intent.putExtra("item_id_pk",sendItemid_pk);
                    intent.putExtra("itemName",sendItemName);
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

}

package com.example.marketingapp.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketingapp.R;

import java.util.ArrayList;


public class adapter extends RecyclerView.Adapter<adapter.holder> {

    ArrayList<model> data;
    Context context;

    public adapter(ArrayList<model> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_sr,null,false);
        return new adapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.itemName.setText(data.get(position).getItemName());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class holder extends RecyclerView.ViewHolder{

        TextView itemName;
        public holder(@NonNull View itemView) {
            super(itemView);

            itemName=itemView.findViewById(R.id.item_name);
        }
    }
}

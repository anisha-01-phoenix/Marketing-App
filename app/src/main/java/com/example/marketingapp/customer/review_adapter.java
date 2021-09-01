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
import java.util.zip.Inflater;

public class review_adapter extends RecyclerView.Adapter<review_adapter.reviewHolder> {

    ArrayList<review_model>data;
    Context context;

    public review_adapter(ArrayList<review_model> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public reviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_sr,parent,false);
        return new review_adapter.reviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewHolder holder, int position) {

        holder.body.setText(data.get(position).getBody());
        holder.orderid.setText(data.get(position).getOrderid());
        holder.date.setText(data.get(position).getDate());
        holder.username.setText(data.get(position).getUsername());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class reviewHolder extends RecyclerView.ViewHolder{

        TextView body,date,username,orderid;

        public reviewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.username);
            body=itemView.findViewById(R.id.review);
            date=itemView.findViewById(R.id.date);
            orderid=itemView.findViewById(R.id.orderId);

        }
    }
}

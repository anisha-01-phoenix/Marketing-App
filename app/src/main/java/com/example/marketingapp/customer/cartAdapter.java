package com.example.marketingapp.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.viewHolder> {

    ArrayList<cartmodel>data;
    Context context;


    public cartAdapter(ArrayList<cartmodel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.check_cart_sr,parent,false);
        return  new cartAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
holder.name.setText(data.get(position).getName());
holder.price.setText(data.get(position).getPrice());

holder.remove.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId").child(data.get(position).getName()).child("status");
        reference.setValue("0");
        notifyDataSetChanged();



    }
});


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView name,price,qnty;
        Button remove;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            remove=itemView.findViewById(R.id.removeCart);
        }
    }
}

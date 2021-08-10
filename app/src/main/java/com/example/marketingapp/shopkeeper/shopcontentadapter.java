package com.example.marketingapp.shopkeeper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketingapp.R;

import java.util.ArrayList;

public class shopcontentadapter extends RecyclerView.Adapter<shopcontentadapter.viewholder> {
   ArrayList<model_shopcontent>totallist;
    Context context;

    public shopcontentadapter(ArrayList<model_shopcontent> totallist, Context context) {
        this.totallist = totallist;
        this.context = context;
    }

    @NonNull
    @Override
    public shopcontentadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_shopcontent,null,false);
        return  new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shopcontentadapter.viewholder holder, int position) {

           holder.prodname.setText(totallist.get(position).getName());
           holder.price.setText(totallist.get(position).getPrice());
           holder.quantity.setText(totallist.get(position).getQtavailable());
            Log.v("jjjk", totallist.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return totallist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView prodname,price,quantity;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.shopitemimage);
            prodname=itemView.findViewById(R.id.shopcontenttitle);
            quantity=itemView.findViewById(R.id.shopcontenquantityavailable);
            price=itemView.findViewById(R.id.shopcontentprice);
        }
    }
}

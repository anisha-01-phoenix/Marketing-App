package com.example.marketingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketingapp.classes.Order;
import com.example.marketingapp.databinding.OrderLayoutBinding;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewholder> {

    Context context;
    ArrayList<Order> list;

    public OrdersAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public OrdersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        OrderLayoutBinding orderLayoutBinding = OrderLayoutBinding.inflate(layoutInflater, parent, false);
        return new OrdersViewholder(orderLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewholder holder, int position) {

        holder.layoutBinding.setOrder(list.get(position));
        holder.layoutBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class OrdersViewholder extends RecyclerView.ViewHolder {
        OrderLayoutBinding layoutBinding;

        public OrdersViewholder(OrderLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            this.layoutBinding = layoutBinding;
        }
    }
}

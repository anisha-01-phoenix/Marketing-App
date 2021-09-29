package com.example.marketingapp.shopkeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketingapp.R;
import com.example.marketingapp.classes.Order;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.customer.ModelCart_Customer;
import com.example.marketingapp.customer.ProductListAdapter;
import com.example.marketingapp.customer.cartmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderVH> {

    ArrayList<Order> list;
    Context context;

    public OrderAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_id, null, false);
        return new OrderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderVH holder, int position) {
        Order model = list.get(position);

        holder.orderid.setText("Order ID: "+model.getOrderId());

        List <ModelCart_Customer> modelCart_customerList=model.getList();
        OrderRecyclerAdapter adapter=new OrderRecyclerAdapter(context,model.getOrderId(),modelCart_customerList);
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setReverseLayout(true);
        holder.recyclerView.setLayoutManager(manager);
        holder.recyclerView.setAdapter(adapter);

        switch (position%4)
        {
            case 0:
                holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card1));
                break;
            case 1:
                holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card2));
                break;
            case 2:
                holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card3));
                break;
            case 3:
                holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card4));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrderVH extends RecyclerView.ViewHolder {
        TextView orderid;
        RecyclerView recyclerView;
        CardView cv;

        public OrderVH(@NonNull View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.orderid);
            cv=itemView.findViewById(R.id.cv_order_id);
            recyclerView=itemView.findViewById(R.id.orderid_rv);
        }
    }
}

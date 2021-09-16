package com.example.marketingapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketingapp.activities.OrderList;
import com.example.marketingapp.classes.Order;
import com.example.marketingapp.databinding.OrderLayoutBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewholder> {

    Context context;
    ArrayList<Order> list;
    private String status;

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
        status=holder.layoutBinding.productAvailable.getText().toString();
        holder.layoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            private int c=0;

            @Override
            public void onClick(View v) {
                final String[] statArray={"Available","Pending","Processing","Not Available"};
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
                alertDialog.setTitle("SET STATUS");
                alertDialog.setSingleChoiceItems(statArray, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        status=statArray[which];
                        c=which;

                    }
                });

                alertDialog.setPositiveButton("SET STATUS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Orders").child(holder.layoutBinding.getOrder().getUniqueId());
                        Order order=new Order();
                        order.setProductName(holder.layoutBinding.productName.getText().toString());
                        order.setQuantity(holder.layoutBinding.productQty.getText().toString());
                        order.setOrderStatus(c);
                        order.setDate(holder.layoutBinding.productDate.getText().toString());
                        order.setPrice(holder.layoutBinding.productPrice.getText().toString());
                        order.setUniqueId(holder.layoutBinding.getOrder().getUniqueId());
                        db.setValue(order);
                    }
                });

                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });
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

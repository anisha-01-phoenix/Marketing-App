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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketingapp.R;
import com.example.marketingapp.classes.Order;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.customer.ModelCart_Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.OrderRecyclerVH> {

    Context context;
    String orderid;
    List<ModelCart_Customer> list;

    public OrderRecyclerAdapter(Context context, String orderid, List<ModelCart_Customer> list) {
        this.context = context;
        this.orderid = orderid;
        this.list = list;
    }

    @NonNull
    @Override
    public OrderRecyclerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, null, false);
        return new OrderRecyclerVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerVH holder, int position) {
        ModelCart_Customer model=list.get(position);
       FirebaseDatabase.getInstance().getReference("Products").child(model.getProductid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model_shopcontent modelShopcontent = snapshot.getValue(model_shopcontent.class);
                Glide.with(context).load(modelShopcontent.getProductUrl()).into(holder.img);
                holder.p_name.setText(modelShopcontent.getProductName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Customers").child(model.getCustomerid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.c_name.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.qty.setText(model.getQuantity());
        holder.price.setText(model.getPrice());
        holder.date.setText(model.getDate());

        switch (model.getStatus()) {
            case 0:
                holder.status.setTextColor(context.getResources().getColor(R.color.successColor));
                holder.status.setText("Available");
                break;
            case 1:
                holder.status.setTextColor(context.getResources().getColor(R.color.color1));
                holder.status.setText("Pending");
                break;
            case 2:
                holder.status.setTextColor(context.getResources().getColor(R.color.color2));
                holder.status.setText("Processing");
                break;
            case 3:
                holder.status.setTextColor(context.getResources().getColor(R.color.errorColor));
                holder.status.setText("Not Available");
                break;
        }

        holder.cv.setOnClickListener(new View.OnClickListener() {
            private int c;
            private String status;

            @Override
            public void onClick(View v) {


                final String[] statArray = {"Available", "Pending", "Processing", "Not Available"};
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("SET STATUS");
                alertDialog.setSingleChoiceItems(statArray, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        status = statArray[which];
                        c = which;

                    }
                });

                alertDialog.setPositiveButton("SET STATUS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.setStatus(c);
                        FirebaseDatabase.getInstance().getReference("Order").child(orderid).child("list").child(String.valueOf(position)).setValue(model);
                        Toasty.success(context, "Status Updated!").show();
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

    public class OrderRecyclerVH extends RecyclerView.ViewHolder{
        ImageView img;
        TextView c_name, p_name, qty, price, status, date;
        CardView cv;
        public OrderRecyclerVH(@NonNull View itemView) {
            super(itemView);
            cv=itemView.findViewById(R.id.cv_order);
            img = itemView.findViewById(R.id.img_order);
            c_name = itemView.findViewById(R.id.nme_order);
            p_name = itemView.findViewById(R.id.p_order);
            qty = itemView.findViewById(R.id.qt_order);
            price = itemView.findViewById(R.id.prc_order);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date_order);
        }
    }
}

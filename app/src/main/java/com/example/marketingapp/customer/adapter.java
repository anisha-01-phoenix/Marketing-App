package com.example.marketingapp.customer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class adapter extends RecyclerView.Adapter<adapter.holder> {


    ArrayList<model> data;
    Context context;
    List<model> dataAll;
    int pos = 0;

    public adapter(ArrayList<model> data, Context context) {
        this.data = data;
        this.context = context;
        this.dataAll = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_sr, parent, false);
        return new adapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.itemName.setText(data.get(position).getItemName());

        holder.qnty.setText(String.valueOf(data.get(position).getQnty()));
        holder.qnty.setText(String.valueOf(data.get(position).getQnty()));


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId").child(data.get(position).getItemName()).child("status");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String val = snapshot.getValue(String.class);


                if (val.equals("1")) {
                    holder.itemName.setTextColor(Color.parseColor("#00ff00"));
                } else {
                    holder.itemName.setTextColor(Color.parseColor("#A62121"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId").child(data.get(position).getItemName());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Object> str = (Map<String, Object>) snapshot.getValue();
                        String st = str.get("status").toString();
                        int q = Integer.parseInt(str.get("qnty").toString());
                        if (st.equals("1")) {
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId").child(data.get(position).getItemName()).child("status");
                            reference1.setValue(String.valueOf(0));


                            notifyDataSetChanged();
                        } else {

                            if (q == 0) {
                                Toast.makeText(context, "quantity can't be empty", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId").child(data.get(position).getItemName()).child("status");
                                reference1.setValue(String.valueOf(1));


                                notifyDataSetChanged();
                            }

                        }


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


        holder.plus.setOnClickListener(new View.OnClickListener() {

            int cnt = 0;

            @Override
            public void onClick(View v) {
                int val = data.get(position).getQnty();
                val++;
                data.get(position).setQnty(val);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId").child(data.get(position).getItemName());
                reference.child("qnty").setValue(val);


                notifyDataSetChanged();
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {

            int cnt = 0;

            @Override
            public void onClick(View v) {

                int val = data.get(position).getQnty();
                val--;
                if (val < 0) {
                    Toast.makeText(context, "Sorry", Toast.LENGTH_SHORT).show();
                } else {
                    data.get(position).setQnty(val);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId").child(data.get(position).getItemName());
                    reference.child("qnty").setValue(val);

                    notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filterList(ArrayList<model> filteredList) {
        data = filteredList;
        notifyDataSetChanged();

    }


    public class holder extends RecyclerView.ViewHolder {

        TextView itemName, plus, minus, qnty;
        Button cart;

        public holder(@NonNull View itemView) {
            super(itemView);

            cart = itemView.findViewById(R.id.cart);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            qnty = itemView.findViewById(R.id.qty);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }
}
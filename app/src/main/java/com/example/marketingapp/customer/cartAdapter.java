package com.example.marketingapp.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketingapp.R;
import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.shopkeeper.model_shopcontent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.viewHolder> {

    ArrayList<ModelCart_Customer>data;
    Context context;


    public cartAdapter(ArrayList<ModelCart_Customer> data, Context context) {
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
        ModelCart_Customer model= data.get(position);
        FirebaseDatabase.getInstance().getReference("Products").child(model.getProductid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model_shopcontent model=snapshot.getValue(model_shopcontent.class);
                holder.name.setText(model.getProductName());
                Glide.with(context).load(model.getProductUrl()).into(holder.url);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Shopkeeper").child(model.getShopid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shopkeeper model=snapshot.getValue(Shopkeeper.class);
                holder.shop.setText(model.getShopName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.price.setText(model.getPrice());
        holder.date.setText(model.getDate());
        holder.qnty.setText(model.getQuantity());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Customer_Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(data.get(position).getProductid());
                reference.setValue(null);
                DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("finalOrder").child(data.get(position).getShopid()).child(data.get(position).getProductid());
                reference1.setValue(null);
                notifyDataSetChanged();



            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView name,price,qnty,shop,date;
        ImageView url;
        Button remove;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.nme2);
            price=itemView.findViewById(R.id.prc2);
            qnty=itemView.findViewById(R.id.qt2);
            url=itemView.findViewById(R.id.img2);
            date=itemView.findViewById(R.id.date_2);
            shop=itemView.findViewById(R.id.snme2);
            remove=itemView.findViewById(R.id.removeCart);
        }
    }
}
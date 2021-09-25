package com.example.marketingapp.customer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketingapp.Dashboard;
import com.example.marketingapp.R;
import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.shopkeeper.model_shopcontent;
import com.example.marketingapp.shopkeeper.shopcontentadapter;
import com.example.marketingapp.shopkeeper.shopkeepercontent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductVH> {
    ArrayList<model_shopcontent>totallist;
    Context context;

    public ProductListAdapter(ArrayList<model_shopcontent> totallist, Context context) {
        this.totallist = totallist;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,null,false);
        return  new ProductListAdapter.ProductVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVH holder, int position) {

        model_shopcontent modelShopcontent=totallist.get(position);
        holder.nme1.setText(modelShopcontent.getProductName());
//        holder.des1.setText(modelShopcontent.getProductDesc());
        holder.qt1.setText("Quantity: "+modelShopcontent.getProductQty());
        holder.prc1.setText(modelShopcontent.getProductPrice());
        Glide.with(context).load(modelShopcontent.getProductUrl()).into(holder.img1);

        switch(position%4)
        {
            case 0: holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card1)); break;
            case 1: holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card2)); break;
            case 2: holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card3)); break;
            default: holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card4)); break;
        }


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Shopkeeper");
        reference.child(modelShopcontent.getShopID()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shopkeeper shopkeeper = snapshot.getValue(Shopkeeper.class);
                    holder.snme1.setText(shopkeeper.getShopName());
                    holder.adrs1.setText(shopkeeper.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,placeOrder.class);
                intent.putExtra("shopid",modelShopcontent.getShopID());
                intent.putExtra("productid",modelShopcontent.getProductID());
                intent.putExtra("url",modelShopcontent.getProductUrl());
                context.startActivity(intent);
            }
        });

    }

    public void filterList(ArrayList<model_shopcontent> filteredList) {
        totallist = filteredList;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return totallist.size();
    }

    public class ProductVH extends RecyclerView.ViewHolder{
        ImageView img1;
        TextView nme1,qt1,snme1,prc1,adrs1;
        CardView cv;
        public ProductVH(@NonNull View itemView) {
            super(itemView);
            img1=itemView.findViewById(R.id.img1);
            nme1=itemView.findViewById(R.id.nme1);
            qt1=itemView.findViewById(R.id.qt1);
            snme1=itemView.findViewById(R.id.snme1);
            prc1=itemView.findViewById(R.id.prc1);
            cv=itemView.findViewById(R.id.cv1);
            adrs1=itemView.findViewById(R.id.adrs1);
        }
    }
}

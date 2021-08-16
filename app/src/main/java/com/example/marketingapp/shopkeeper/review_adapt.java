package com.example.marketingapp.shopkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class review_adapt extends RecyclerView.Adapter<review_adapt.recviewholder> {
    ArrayList<model_reviews_shop>list;
    Context context;

    public review_adapt(ArrayList<model_reviews_shop> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public recviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_row_reviews_shop_items,parent,false);
        return new recviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recviewholder holder, int position) {





        holder.customername.setText();
        holder.feedback.setText();
        holder.ratingBar.setRating();


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class recviewholder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        TextView feedback,customername;
        public recviewholder(@NonNull View itemView) {
            super(itemView);
            ratingBar=itemView.findViewById(R.id.rate_);
            feedback=itemView.findViewById(R.id.feedback);
            customername=itemView.findViewById(R.id.customername);
        }
    }
}

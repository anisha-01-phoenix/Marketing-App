package com.example.marketingapp.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketingapp.R;
import com.example.marketingapp.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class review_adapter extends RecyclerView.Adapter<review_adapter.reviewHolder> {

    ArrayList<review_model>data;
    Context context;

    public review_adapter(ArrayList<review_model> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public reviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_sr,parent,false);
        return new review_adapter.reviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewHolder holder, int position) {

        holder.body.setText(data.get(position).getBody());
        holder.date.setText(data.get(position).getDate());
        holder.rate.setText(data.get(position).getRating());
        float x=Float.parseFloat(data.get(position).getRating());
        if (x>2.5)
            holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card1));
        else
            holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card4));

        FirebaseDatabase.getInstance().getReference("Customers").child(data.get(position).getCustomerID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                holder.username.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class reviewHolder extends RecyclerView.ViewHolder{

        TextView body,date,username,rate;
        CardView cv;

        public reviewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.username);
            body=itemView.findViewById(R.id.review);
            date=itemView.findViewById(R.id.date);
            rate=itemView.findViewById(R.id.rateStar);
            cv=itemView.findViewById(R.id.cv_review);

        }
    }
}

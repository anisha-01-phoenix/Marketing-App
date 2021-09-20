package com.example.marketingapp.customer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketingapp.R;
import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.shopkeeper.OrderAdapter;
import com.example.marketingapp.shopkeeper.bottom_frag_add_items;
import com.example.marketingapp.shopkeeper.model_shopcontent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.CustomerOrderVH> {

    ArrayList<ModelCart_Customer> list;
    Context context;

    public CustomerOrderAdapter(ArrayList<ModelCart_Customer> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerOrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, null, false);
        return new CustomerOrderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrderVH holder, int position) {

        ModelCart_Customer model = list.get(position);
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

        FirebaseDatabase.getInstance().getReference("Shopkeeper").child(model.getShopid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shopkeeper shop = snapshot.getValue(Shopkeeper.class);
                holder.c_name.setText(shop.getShopName());
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
                holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card1));
                holder.status.setText("Available");
                break;
            case 1:
                holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card2));
                holder.status.setText("Pending");
                break;
            case 2:
                holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card3));
                holder.status.setText("Processing");
                break;
            case 3:
                holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card4));
                holder.status.setText("Not Available");
                break;
        }


        holder.rate.setVisibility(View.VISIBLE);
        holder.rate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("RATE THE PRODUCT/SHOP");
                final RatingBar ratingBar = new RatingBar(context);
                ratingBar.setNumStars(5);
                ratingBar.setMax(5);
                ratingBar.setProgressTintList(context.getColorStateList(R.color.color2));
                final EditText input = new EditText(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                lp.leftMargin = 5;
                lp.rightMargin = 5;
                lp.gravity = 0;
                LinearLayout linearLayout = new LinearLayout(context);
                input.setHint("Enter Your Reviews Here....");
                input.setHintTextColor(context.getResources().getColor(R.color.light));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(ratingBar, lp);
                linearLayout.addView(input, lp);
                alertDialog.setView(linearLayout);
                alertDialog.setIcon(R.drawable.ic_baseline_star_rate_24);
                alertDialog.setIconAttribute(context.getResources().getColor(R.color.color2));


                alertDialog.setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().isEmpty())
                        {
                            input.setError("Enter Your Reviews!");
                            input.requestFocus();
                            return;
                        }
                        if (ratingBar.getRating()<=0.0F)
                        {
                            Toasty.error(context,"Rate Us").show();
                            return;
                        }
                        review_model model1 = new review_model(model.getDate(), input.getText().toString(), String.valueOf(ratingBar.getRating()),model.getProductid(), model.getShopid(),model.customerid);
                        FirebaseDatabase.getInstance().getReference("CustomerReviews").child(model.getShopid()).child(model.getProductid()).setValue(model1);
                        Toasty.success(context, "Thanks For Rating Us!").show();

                    }
                });

                alertDialog.setNegativeButton("NO THANKS", new DialogInterface.OnClickListener() {
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

    public class CustomerOrderVH extends RecyclerView.ViewHolder {

        ImageView img, rate;
        TextView c_name, p_name, qty, price, status, date;
        CardView cv;

        public CustomerOrderVH(@NonNull View itemView) {
            super(itemView);
            rate = itemView.findViewById(R.id.rateus);
            cv = itemView.findViewById(R.id.cv_order);
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

package com.example.marketingapp.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.marketingapp.Dashboard;
import com.example.marketingapp.R;
import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.databinding.ActivityPlaceOrderBinding;
import com.example.marketingapp.shopkeeper.model_shopcontent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class placeOrder extends AppCompatActivity {

    private String shopid, productid, url, customerid, date;
    int status = 0;
    ActivityPlaceOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        shopid = getIntent().getStringExtra("shopid");
        productid = getIntent().getStringExtra("productid");
        url = getIntent().getStringExtra("url");

        customerid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy  HH:mm");
        date = dateFormat.format(calendar.getTime());

        Glide.with(this).load(url).into(binding.pImg);

        FirebaseDatabase.getInstance().getReference("CustomerReviews").child(shopid).child(productid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    review_model model = snapshot.getValue(review_model.class);
                    binding.pRate.setText(model.getRating());
                } else
                    binding.pRate.setText("Unrated");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.child(productid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model_shopcontent model = snapshot.getValue(model_shopcontent.class);
                binding.pName.setText(model.getProductName());
                binding.pDesc.setText(model.getProductDesc());

                String price = model.getProductPrice();
                int x = Integer.parseInt(binding.qty1.getText().toString());
                binding.pPrice.setText(price);
                binding.pQty.setText(model.getProductQty());
                binding.oPrice.setText(model.getOfferPrice());
                //     binding.totalqty.setText(String.valueOf(Integer.parseInt(model.getProductQty())*x));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Shopkeeper");
        ref.child(shopid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shopkeeper model = snapshot.getValue(Shopkeeper.class);
                binding.pRate.setText(String.valueOf((int) model.getRating()));
                binding.sNme.setText(model.getShopName());
                binding.sAdrs.setText(model.getAddress());
                binding.cat.setText(model.getShopCategory());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addtocart(View view) {

        Toasty.success(getApplicationContext(), "Product Added to Your Cart!").show();
        startActivity(new Intent(placeOrder.this, Dashboard.class));
        ModelCart_Customer modelCart_customer = new ModelCart_Customer(shopid, productid, customerid, status, binding.pPrice.getText().toString(), binding.qty1.getText().toString(), date);
        FirebaseDatabase.getInstance().getReference("Customer_Cart").child(customerid).child(productid).setValue(modelCart_customer);
    }

    public void backfrmedit(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(placeOrder.this, Dashboard.class));
    }
}
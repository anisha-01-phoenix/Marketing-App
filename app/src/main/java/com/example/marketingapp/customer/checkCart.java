package com.example.marketingapp.customer;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.marketingapp.databinding.ActivityCheckCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class checkCart extends AppCompatActivity {

    ActivityCheckCartBinding binding;

    cartAdapter cartAdapter;
    ArrayList<cartmodel> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        data=new ArrayList<>();
        binding.cartItems.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter=new cartAdapter(data,this);
        binding.cartItems.setAdapter(cartAdapter);

        getCartOrders();


        binding.finalizeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalizeOrder();

            }
        });


    }

    private void finalizeOrder() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId");
        ref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot s : snapshot.getChildren()) {

                    Map<String, String> map = (Map<String, String>) s.getValue();

                    String status = map.get("status");
                    if (status.equals("1")) {

                        String name=map.get("name");
                        String price=map.get("price");
                        String qnty=String.valueOf(map.get("qnty"));
                        String date = java.time.LocalDate.now().toString();
                        Map<String ,Object >map1=new HashMap<>();
                        map1.put("name",name);
                        map1.put("price",price);
                        map1.put("qnty",qnty);
                        map1.put("date",date);


                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("finalOrder").child("uid").child("shopid");
                        reference.push().setValue(map1);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

    private void getCartOrders() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ;
        // String uid= user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                data.clear();
                for (DataSnapshot s : snapshot.getChildren()) {

                    Map<String, String> map = (Map<String, String>) s.getValue();

                    String status = map.get("status");
                    if (status.equals("1")) {

                        String name=map.get("name");
                        String price=map.get("price");

                        cartmodel cartmodel=new cartmodel();
                        cartmodel.setName(name);
                        cartmodel.setPrice(price);
                        data.add(cartmodel);

                    }

                }
                cartAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
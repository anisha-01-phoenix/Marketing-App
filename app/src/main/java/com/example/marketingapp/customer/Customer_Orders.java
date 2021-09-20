package com.example.marketingapp.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.marketingapp.Dashboard;
import com.example.marketingapp.R;
import com.example.marketingapp.databinding.ActivityCustomerOrdersBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Customer_Orders extends AppCompatActivity {

    ActivityCustomerOrdersBinding binding;

    CustomerOrderAdapter adapter;
    ArrayList<ModelCart_Customer> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustomerOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid= user.getUid();
        data=new ArrayList<>();
        binding.rvUser.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer_Cart").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot s:snapshot.getChildren())
                {
                    ModelCart_Customer model=s.getValue(ModelCart_Customer.class);
                    data.add(model);
                }
                adapter =new CustomerOrderAdapter(data, Customer_Orders.this);
                binding.rvUser.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void backfrmedt(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Customer_Orders.this, Dashboard.class));
    }
}
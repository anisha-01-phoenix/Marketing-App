package com.example.marketingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.marketingapp.adapters.ViewPagerAdapter;
import com.example.marketingapp.classes.Order;
import com.example.marketingapp.databinding.ActivityOrderListBinding;
import com.example.marketingapp.fragments.Fragment_Order;
import com.example.marketingapp.fragments.Fragment_Pending;
import com.example.marketingapp.fragments.Fragment_Processing;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderList extends AppCompatActivity {
    ActivityOrderListBinding activityOrderListBinding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderListBinding = ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(activityOrderListBinding.getRoot());


        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

//        Order order = new Order("2", "125", "127", 0, "03/09/2021", "4pm", "500", "atta","5kg");
//        databaseReference.child(order.getUniqueId()).setValue(order);



        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.add(new Fragment_Order(), "Order");
        adapter.add(new Fragment_Pending(), "Pending");
        adapter.add(new Fragment_Processing(), "Processing");
        activityOrderListBinding.viewPager.setAdapter(adapter);
        activityOrderListBinding.tablayout.setupWithViewPager(activityOrderListBinding.viewPager);


    }
}
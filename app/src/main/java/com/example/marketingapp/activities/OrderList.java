package com.example.marketingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.example.marketingapp.adapters.ViewPagerAdapter;
import com.example.marketingapp.classes.Item;
import com.example.marketingapp.classes.Order;
import com.example.marketingapp.databinding.ActivityOrderListBinding;
import com.example.marketingapp.fragments.Fragment_Order;
import com.example.marketingapp.fragments.Fragment_Pending;
import com.example.marketingapp.fragments.Fragment_Processing;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderList extends AppCompatActivity {
    ActivityOrderListBinding activityOrderListBinding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderListBinding = ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(activityOrderListBinding.getRoot());

        //   FirebaseApp.InitializeApp(Application.Context);
        getSupportActionBar().hide();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.add(new Fragment_Order(), "Order");
        adapter.add(new Fragment_Pending(), "Pending");
        adapter.add(new Fragment_Processing(), "Processing");
        activityOrderListBinding.viewPager.setAdapter(adapter);
        activityOrderListBinding.tablayout.setupWithViewPager(activityOrderListBinding.viewPager);
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");


        HashMap<String, Object> map = new HashMap<>();
        map.put("uniqueIdShop", "12345");
        map.put("uniqueIdUser", "1236");
        map.put("orderStatus", 1);
        map.put("date", "09/08/21");
        map.put("time", "4pm");
        map.put("price", "Rs. 200");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("productName", "name");
        hashMap.put("quantity", "5kg");
        databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                databaseReference.child("itemsList").setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Item item = new Item();
                        List<Item> itemList = new ArrayList<>();
                        itemList.add(item);
                        Order order = new Order("123", "12345", "1236", itemList, 1, "09/08/2021", "time", "500");
                        databaseReference.child(databaseReference.push().getKey()).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Tag", "Item ordered!");
                            }
                        });


                    }
                });
            }
        });


    }
}
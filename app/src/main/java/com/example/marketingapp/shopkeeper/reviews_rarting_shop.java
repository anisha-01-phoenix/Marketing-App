package com.example.marketingapp.shopkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.marketingapp.R;
import com.example.marketingapp.databinding.ActivityFirstScreenBinding;
import com.example.marketingapp.databinding.ActivityReviewsRartingShopBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class reviews_rarting_shop extends AppCompatActivity {
ActivityReviewsRartingShopBinding binding;
    ArrayList<model_reviews_shop> listreviews;
    review_adapt adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewsRartingShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.revRecv.setLayoutManager(new LinearLayoutManager(this));
        listreviews=new ArrayList<>();
        adapter=new review_adapt(listreviews,this);
        binding.revRecv.setAdapter(adapter);
        additeminrecv();
    }

    private void additeminrecv() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("CustomerReviews").child("shopId");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listreviews.clear();
                for(DataSnapshot s:snapshot.getChildren()){
                    Map<String, String> map = (Map<String, String>) s.getValue();
                    model_reviews_shop model=new model_reviews_shop();
                    model.setReview(map.get("body"));
                    model.setOrederid(map.get("orderid"));
                    String xd=map.get("rating");
                    int xint=(xd.charAt(0))-48;

                    model.setRatebar(xint);
                    model.setCustomername(map.get("username"));
                    model.setDate1(map.get("date"));
                    listreviews.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
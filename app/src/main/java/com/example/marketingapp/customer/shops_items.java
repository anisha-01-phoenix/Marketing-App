package com.example.marketingapp.customer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.marketingapp.R;
import com.example.marketingapp.databinding.ActivityShopsItemsBinding;

public class shops_items extends AppCompatActivity {

    private ActivityShopsItemsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopsItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        binding.ll.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().add(R.id.shopContainer, new products(), null).commit();


        binding.shopReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ll.setVisibility(View.GONE);

                binding.shopReview.setBackgroundResource(R.drawable.shopbuttonwhite);
                binding.shopReview.setTextColor(Color.parseColor("#A62121"));
                binding.shopProduct.setBackgroundResource(R.drawable.shopbuttonred);
                binding.shopProduct.setTextColor(Color.parseColor("#FFFFFF"));
                getSupportFragmentManager().beginTransaction().add(R.id.shopContainer, new Reviews(), null).commit();


            }
        });

        binding.shopProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.shopProduct.setBackgroundResource(R.drawable.shopbuttonwhite);
                binding.shopProduct.setTextColor(Color.parseColor("#A62121"));
                binding.shopReview.setBackgroundResource(R.drawable.shopbuttonred);
                binding.shopReview.setTextColor(Color.parseColor("#FFFFFF"));

                binding.ll.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction().add(R.id.shopContainer, new products(), null).commit();

            }
        });


        binding.checkCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

startActivity(new Intent(getApplicationContext(),checkCart.class));


            }
        });

    }
}
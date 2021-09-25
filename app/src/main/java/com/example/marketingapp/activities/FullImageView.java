package com.example.marketingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.marketingapp.R;
import com.example.marketingapp.customer.placeOrder;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImageView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);
        String image = getIntent().getStringExtra("zoom");
        PhotoView photoView = findViewById(R.id.photo_view);
        Glide.with(this).load(image).into(photoView);
    }

    public void backto(View view) {
        onBackPressed();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(FullImageView.this, placeOrder.class));
//    }
}
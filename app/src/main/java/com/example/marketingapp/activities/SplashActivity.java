package com.example.marketingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.marketingapp.FirstScreen;
import com.example.marketingapp.R;
import com.example.marketingapp.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.splash.animate().translationX(2000).setDuration(2000).setStartDelay(2000);

        final Intent i=new Intent(SplashActivity.this, FirstScreen.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();

            }
        },3000);
    }
}
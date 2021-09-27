package com.example.marketingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.marketingapp.databinding.ActivityPaymentSuccessfulBinding;

public class PaymentSuccessful extends AppCompatActivity {

    private ActivityPaymentSuccessfulBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentSuccessfulBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void continue_to_dashboard ( View view )
    {
        finish();
    }

}
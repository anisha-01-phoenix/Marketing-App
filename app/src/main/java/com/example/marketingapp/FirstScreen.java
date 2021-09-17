package com.example.marketingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.marketingapp.Utils.Constants;
import com.example.marketingapp.databinding.ActivityFirstScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FirstScreen extends AppCompatActivity  {

    private ActivityFirstScreenBinding binding;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);

        if (currentUser != null) {
            Log.d("userid",currentUser.getUid());
            Log.d("phone",currentUser.getPhoneNumber());
            Log.d("type",sharedPreferences.getString(Constants.permission,""));
            startActivity(new Intent(FirstScreen.this,Dashboard.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=new Intent(FirstScreen.this,SignInActivity.class);
        binding.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.permission,"user");
                startActivity(intent);
            }
        });

        binding.shopkeeper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(Constants.permission,"shopkeeper");
                startActivity(intent);
            }
        });
    }

}
package com.example.marketingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.marketingapp.databinding.ActivityFirstScreenBinding;

public class FirstScreen extends AppCompatActivity implements View.OnClickListener {

    private ActivityFirstScreenBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("Market",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        binding.shopkeeper.setOnClickListener(this::onClick);
        binding.user.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.user:

                editor.putString("permission","user");
                break;

            case R.id.shopkeeper:

                editor.putString("permission","shopkeeper");
                break;
        }

        Intent intent = new Intent( FirstScreen.this , SignInActivity.class);
        editor.commit();
        startActivity(intent);
        finish();

    }
}
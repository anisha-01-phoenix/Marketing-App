package com.example.marketingapp.classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.marketingapp.R;
import com.example.marketingapp.shopkeeper.shopkeepercontent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tryer(View view) {
        Intent intent=new Intent(this, shopkeepercontent.class);
        startActivity(intent);
    }
}
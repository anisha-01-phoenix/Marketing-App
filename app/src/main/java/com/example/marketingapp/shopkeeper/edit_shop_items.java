package com.example.marketingapp.shopkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.marketingapp.R;
import com.example.marketingapp.databinding.ActivityEditShopItemsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_shop_items extends AppCompatActivity {
String name,price,qt;
ActivityEditShopItemsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditShopItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        price=intent.getStringExtra("price");
        qt=intent.getStringExtra("qt");


        binding.ediprodname.setText(name);
        binding.editdesc.setText("write desc");
        binding.editqt.setText(qt);
        binding.editprice.setText(price);
        binding.buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("shopid").child(name);
                ref.removeValue();
                DatabaseReference ref11= FirebaseDatabase.getInstance().getReference("shopid").child(binding.ediprodname.getText().toString());
                modelcontent_fbase modelcontentFbase=new modelcontent_fbase();
                modelcontentFbase.setName(binding.ediprodname.getText().toString());
                modelcontentFbase.setPrice(binding.editprice.getText().toString());
                modelcontentFbase.setQt_available(binding.editqt.getText().toString());
                ref11.setValue(modelcontentFbase);
                Toast.makeText(getApplicationContext(), "Details Updated", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
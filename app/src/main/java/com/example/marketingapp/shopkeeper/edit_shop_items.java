package com.example.marketingapp.shopkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.marketingapp.R;
import com.example.marketingapp.databinding.ActivityEditShopItemsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class edit_shop_items extends AppCompatActivity {
    String productID;
    String shopID;
    String productUrl;
    String productName;
    String productQty;
    String productDesc;
    String productPrice;
    String offerPrice;
ActivityEditShopItemsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditShopItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        productID=intent.getStringExtra("PRODUCT_ID");
        shopID=intent.getStringExtra("SHOPID");
        productName=intent.getStringExtra("PRODUCT_NAME");
        productPrice=intent.getStringExtra("PRICE");
        productQty=intent.getStringExtra("QUANTITY");
        offerPrice=intent.getStringExtra("OFFER");
        productDesc=intent.getStringExtra("DESCRIPTION");
        productUrl=intent.getStringExtra("PRODUCT_URL");


        Glide.with(this).load(productUrl).into(binding.itemimage);
        binding.ediprodname.getEditText().setText(productName);
        binding.editdesc.getEditText().setText(productDesc);
        binding.editqt.getEditText().setText(productQty);
        binding.editprice.getEditText().setText(productPrice);
        binding.editofferprice.getEditText().setText(offerPrice);

        binding.buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Products").child(productID);
//                ref.removeValue();
//                DatabaseReference ref11= FirebaseDatabase.getInstance().getReference("shopid").child(binding.ediprodname.getEditText().getText().toString());
//                modelcontent_fbase modelcontentFbase=new modelcontent_fbase();
//                modelcontentFbase.setName(binding.ediprodname.getEditText().getText().toString());
//                modelcontentFbase.setPrice(binding.editprice.getEditText().getText().toString());
//                modelcontentFbase.setQt_available(binding.editqt.getEditText().getText().toString());
                model_shopcontent model=new model_shopcontent(productID,shopID,productUrl,
                        binding.ediprodname.getEditText().getText().toString(),
                        binding.editqt.getEditText().getText().toString(),
                        binding.editdesc.getEditText().getText().toString(),"Rs."+
                        binding.editprice.getEditText().getText().toString(),
                        binding.editofferprice.getEditText().getText().toString()+"%");
                ref.setValue(model);
                Toasty.success(getApplicationContext(), "Details Updated!").show();
                startActivity(new Intent(edit_shop_items.this,shopkeepercontent.class));

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(edit_shop_items.this,shopkeepercontent.class));

    }

    public void item_review(View view) {
    }

    public void toastcreate(View view) {
        Toasty.error(getApplicationContext(),"Product Image cannot be edited!");
    }

    public void backfromedit(View view) {
        onBackPressed();
    }
}
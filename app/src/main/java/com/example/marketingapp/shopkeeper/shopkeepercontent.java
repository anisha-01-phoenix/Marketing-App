package com.example.marketingapp.shopkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.marketingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class shopkeepercontent extends AppCompatActivity {
RecyclerView rcv;
ArrayList<model_shopcontent>contentlist;
model_shopcontent modelShopcontent;
shopcontentadapter shopcontentadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeepercontent);
        rcv=findViewById(R.id.shopcontentrecv);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        contentlist=new ArrayList<>();


        shopcontentadapter=new shopcontentadapter(contentlist,this);
        rcv.setAdapter(shopcontentadapter);
        //Log.v("ddd","hjhjh");
        additem();
    }

    private void additem() {
        for(int i=0;i<12;i++){
            modelShopcontent=new model_shopcontent();
            modelShopcontent.setName("p"+i);
            modelShopcontent.setPrice("price"+i);
            modelShopcontent.setQtavailable(String.valueOf(i));
            contentlist.add(modelShopcontent);
        }
       // Log.v("ddd","hjhjh11111");
       // shopcontentadapter.notifyDataSetChanged();
    }


}
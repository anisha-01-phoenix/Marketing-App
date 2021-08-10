package com.example.marketingapp.shopkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.marketingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class shopkeepercontent extends AppCompatActivity {
RecyclerView rcv;
FloatingActionButton add;
ArrayList<model_shopcontent>contentlist;
model_shopcontent modelShopcontent;
shopcontentadapter shopcontentadapter;
modelcontent_fbase modelcontentFbase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeepercontent);
        rcv=findViewById(R.id.shopcontentrecv);
        add=findViewById(R.id.shopcontentadditem);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        contentlist=new ArrayList<>();


        shopcontentadapter=new shopcontentadapter(contentlist,this);
        rcv.setAdapter(shopcontentadapter);
        //Log.v("ddd","hjhjh");
        additem();

    }

    private void additem() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("shopid");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contentlist.clear();
                for(DataSnapshot s:snapshot.getChildren()){
                    modelcontentFbase=s.getValue(modelcontent_fbase.class);
                    modelShopcontent=new model_shopcontent();
                    modelShopcontent.setName(modelcontentFbase.getName());
                    modelShopcontent.setPrice(modelcontentFbase.getPrice());
                    modelShopcontent.setQtavailable(modelcontentFbase.getQt_available());
                    contentlist.add(modelShopcontent);
                }
                shopcontentadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    /*    for(int i=0;i<12;i++){
            modelShopcontent=new model_shopcontent();
            modelShopcontent.setName("p"+i);
            modelShopcontent.setPrice("price"+i);
            modelShopcontent.setQtavailable(String.valueOf(i));
            contentlist.add(modelShopcontent);
            modelcontentFbase=new modelcontent_fbase();
            modelcontentFbase.setName(modelShopcontent.getName());
            modelcontentFbase.setQt_available(modelShopcontent.getQtavailable());
            modelcontentFbase.setPrice(modelShopcontent.getPrice());
            ref1.child(String.valueOf(i)).setValue(modelcontentFbase);
        }

     */
       // Log.v("ddd","hjhjh11111");
       // shopcontentadapter.notifyDataSetChanged();
    }


    public void addproducts(View view) {
        bottom_frag_add_items bottom_frag_add_items=new bottom_frag_add_items();
        bottom_frag_add_items.show(getSupportFragmentManager(),bottom_frag_add_items.getTag());
    }
}
package com.example.marketingapp.shopkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.marketingapp.Dashboard;
import com.example.marketingapp.R;
import com.example.marketingapp.classes.Shopkeeper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class shopkeepercontent extends AppCompatActivity {
    RecyclerView rcv;
    Button add;
    ArrayList<model_shopcontent> contentlist;
    model_shopcontent modelShopcontent;
    shopcontentadapter shopcontentadapter;
    private model_shopcontent model;
    private String shopId=" ";
    LottieAnimationView view;
    private LinearLayout noitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeepercontent);
        rcv = findViewById(R.id.shopcontentrecv);
        add = findViewById(R.id.shopcontentadditem);
        noitem=findViewById(R.id.noItem);
        view=findViewById(R.id.view_product);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.INVISIBLE);
                rcv.setVisibility(View.VISIBLE);

            }
        },3000);

        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        contentlist = new ArrayList<>();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Shopkeeper");
        reference.child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shopkeeper shopkeeper = snapshot.getValue(Shopkeeper.class);
                shopId=shopkeeper.getUniqueId();
                additem(shopId,shopkeepercontent.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(shopkeepercontent.this, Dashboard.class));
    }

    private void additem(String shopID, Context context) {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Products");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contentlist.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    model = s.getValue(model_shopcontent.class);
                    if (model.getShopID().equals(shopID))
                        contentlist.add(model);
                }
                shopcontentadapter = new shopcontentadapter(contentlist, context);
                rcv.setAdapter(shopcontentadapter);
                if (contentlist.size()==0) {
                    noitem.setVisibility(View.VISIBLE);
                    view.setVisibility(View.INVISIBLE);
                }
                else noitem.setVisibility(View.INVISIBLE);
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
        bottom_frag_add_items bottom_frag_add_items = new bottom_frag_add_items();
        bottom_frag_add_items.show(getSupportFragmentManager(), bottom_frag_add_items.getTag());
    }

    public void goback(View view) {
        onBackPressed();
    }
}
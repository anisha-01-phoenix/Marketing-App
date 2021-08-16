package com.example.marketingapp.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.marketingapp.databinding.FragmentProductsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class products extends Fragment {
    FragmentProductsBinding fragmentProductsBinding;
    ArrayList<model> data;
    adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentProductsBinding = FragmentProductsBinding.inflate(inflater, container, false);

        fragmentProductsBinding.recView.setLayoutManager(new LinearLayoutManager(getContext()));
        data = new ArrayList<>();

fragmentProductsBinding.recView.hasFixedSize();
fragmentProductsBinding.recView.setNestedScrollingEnabled(false);
        adapter = new adapter(data, getContext());
        fragmentProductsBinding.recView.setAdapter(adapter);

        addData();
        return fragmentProductsBinding.getRoot();
    }

    private void addData() {

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("shopid");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
data.clear();
                for(DataSnapshot s:snapshot.getChildren()){

                    Map<String,String > mp=(Map)s.getValue();
                    String name=mp.get("name");
                    String price=mp.get("price");
                    String qt=mp.get("qt_available");

                    model model=new model();
                    model.setItemName(name);
                    data.add(model);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
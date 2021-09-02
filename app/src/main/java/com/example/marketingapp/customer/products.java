package com.example.marketingapp.customer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.HashMap;
import java.util.Locale;
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


        fragmentProductsBinding.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });


        return fragmentProductsBinding.getRoot();


    }

    private void filter(String text) {

        ArrayList<model> filteredList = new ArrayList<>();

        for (model item : data) {

            if (item.getItemName().toLowerCase().contains(text.toLowerCase(Locale.ROOT))) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);

    }


    private void addData() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("shopid");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot s : snapshot.getChildren()) {

                    Map<String, String> mp = (Map) s.getValue();
                    String name = mp.get("name");
                    String price = mp.get("price");
                    String qt = mp.get("qt_available");




                    model model = new model();
                    model.setItemName(name);
                    model.setQnty(0);
                    model.setStatus(0);
                    data.add(model);


                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId").child(name);
                    Map<String, Object> mp1 = new HashMap<>();
                    mp1.put("name", name);
                    mp1.put("price", price);
                    mp1.put("qnty", 0);
                    mp1.put("status",String.valueOf(0));  // 0 -nothing  1-cart 2-confirm
                    reference.setValue(mp1);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
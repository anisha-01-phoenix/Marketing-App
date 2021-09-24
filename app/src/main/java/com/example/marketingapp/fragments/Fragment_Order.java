package com.example.marketingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.marketingapp.classes.Order;
import com.example.marketingapp.customer.ModelCart_Customer;
import com.example.marketingapp.customer.cartAdapter;
import com.example.marketingapp.customer.cartmodel;
import com.example.marketingapp.customer.checkCart;
import com.example.marketingapp.databinding.FragmentOrderBinding;
import com.example.marketingapp.shopkeeper.OrderAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Order extends Fragment {

    FragmentOrderBinding fragmentOrderBinding;
    OrderAdapter adapter;
    ArrayList<cartmodel> data;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentOrderBinding = FragmentOrderBinding.inflate(getLayoutInflater());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid= user.getUid();
        data=new ArrayList<>();
        fragmentOrderBinding.rvOrders.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("finalOrder").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot s:snapshot.getChildren())
                {
                    cartmodel model=s.getValue(cartmodel.class);
                    data.add(model);
                    fragmentOrderBinding.noOrders.setVisibility(View.INVISIBLE);
                    fragmentOrderBinding.noordertext.setVisibility(View.INVISIBLE);
                }
                if (data.size()==0)
                {
                    fragmentOrderBinding.noOrders.setVisibility(View.VISIBLE);
                    fragmentOrderBinding.noordertext.setVisibility(View.VISIBLE);
                }
                adapter =new OrderAdapter(data, getActivity());
                fragmentOrderBinding.rvOrders.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return fragmentOrderBinding.getRoot();

    }
}
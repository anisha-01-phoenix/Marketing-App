package com.example.marketingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marketingapp.R;
import com.example.marketingapp.adapters.OrdersAdapter;
import com.example.marketingapp.classes.Order;
import com.example.marketingapp.databinding.FragmentPendingBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Pending extends Fragment {

    FragmentPendingBinding fragmentPendingBinding;
    OrdersAdapter adapter;
    DatabaseReference reference;
    ArrayList<Order> list ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentPendingBinding=FragmentPendingBinding.inflate(getLayoutInflater());
        View view=fragmentPendingBinding.getRoot();
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        fragmentPendingBinding.rvPending.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    if (order.getOrderStatus()==1)
                     list.add(order);
                }
                adapter = new OrdersAdapter(getContext(), list);
                fragmentPendingBinding.rvPending.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}
package com.example.marketingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marketingapp.R;
import com.example.marketingapp.classes.Order;
import com.example.marketingapp.customer.cartmodel;
import com.example.marketingapp.databinding.FragmentOrderBinding;
import com.example.marketingapp.databinding.FragmentPendingBinding;
import com.example.marketingapp.databinding.FragmentProcessingBinding;
import com.example.marketingapp.shopkeeper.OrderAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Fragment_Processing extends Fragment {
    FragmentProcessingBinding fragmentProcessingBinding;
    OrderAdapter adapter;
    ArrayList<cartmodel> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentProcessingBinding=FragmentProcessingBinding.inflate(getLayoutInflater());
        View view=fragmentProcessingBinding.getRoot();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid= user.getUid();
        data=new ArrayList<>();
        fragmentProcessingBinding.rvProcessing.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("finalOrder").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot s:snapshot.getChildren())
                {
                    cartmodel model=s.getValue(cartmodel.class);
                    if (model.getStatus()==2)
                    data.add(model);
                }
                adapter =new OrderAdapter(data, getActivity());
                fragmentProcessingBinding.rvProcessing.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
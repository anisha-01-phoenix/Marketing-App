package com.example.marketingapp.fragments;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.marketingapp.R;
import com.example.marketingapp.adapters.OrdersAdapter;
import com.example.marketingapp.classes.Order;
import com.example.marketingapp.databinding.FragmentOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Order extends Fragment {

    FragmentOrderBinding fragmentOrderBinding;
    OrdersAdapter adapter;
    DatabaseReference reference;
    ArrayList<Order> list ;
    Order order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentOrderBinding = FragmentOrderBinding.inflate(getLayoutInflater());
        View view = fragmentOrderBinding.getRoot();
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        fragmentOrderBinding.rvOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    order = dataSnapshot.getValue(Order.class);
                    list.add(order);
                }
                adapter = new OrdersAdapter(getContext(), list);
                fragmentOrderBinding.rvOrders.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }
}
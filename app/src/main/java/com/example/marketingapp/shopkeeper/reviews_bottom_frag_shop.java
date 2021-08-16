package com.example.marketingapp.shopkeeper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marketingapp.R;
import com.example.marketingapp.databinding.FragmentReviewsBottomFragShopBinding;

import java.util.ArrayList;

public class reviews_bottom_frag_shop extends Fragment {
FragmentReviewsBottomFragShopBinding binding;
ArrayList<model_reviews_shop>listreviews;
review_adapt adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentReviewsBottomFragShopBinding.inflate(inflater,container,false);
        binding.revRecv.setLayoutManager(new LinearLayoutManager(getContext()));
        listreviews=new ArrayList<>();
        adapter=new review_adapt(listreviews,getContext());
        binding.revRecv.setAdapter(adapter);
        additeminrecv();









        return binding.getRoot();
    }

    private void additeminrecv() {
        
    }
}
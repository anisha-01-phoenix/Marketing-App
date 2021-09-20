package com.example.marketingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marketingapp.R;
import com.example.marketingapp.databinding.FragmentFavouritesBinding;

public class Favourites extends Fragment {

    FragmentFavouritesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFavouritesBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}
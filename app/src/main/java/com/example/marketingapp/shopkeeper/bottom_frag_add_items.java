package com.example.marketingapp.shopkeeper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marketingapp.R;
import com.example.marketingapp.databinding.FragmentBottomFragAddItemsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class bottom_frag_add_items extends BottomSheetDialogFragment {

FragmentBottomFragAddItemsBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentBottomFragAddItemsBinding.inflate(inflater,container,false);
        binding.addprodctsFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("shopid");
                modelcontent_fbase modelcontentFbase=new modelcontent_fbase();
                modelcontentFbase.setPrice(binding.prodprice.getText().toString());
                modelcontentFbase.setName(binding.prodnamefrag.getText().toString());
                modelcontentFbase.setQt_available(binding.prodqtavailable.getText().toString());
                ref1.child(binding.prodnamefrag.getText().toString()).setValue(modelcontentFbase);
                dismiss();
            }
        });




        return binding.getRoot();
    }


}
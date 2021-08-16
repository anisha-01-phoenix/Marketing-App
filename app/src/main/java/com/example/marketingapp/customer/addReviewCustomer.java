package com.example.marketingapp.customer;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.example.marketingapp.databinding.FragmentAddReviewCustomerBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class addReviewCustomer extends BottomSheetDialogFragment {

    FragmentAddReviewCustomerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddReviewCustomerBinding.inflate(inflater, container, false);


        binding.addreview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


                String orderid = binding.orderid.getText().toString().trim();
                String body = binding.review.getText().toString().trim();

                if (orderid.isEmpty()) {
                    binding.orderid.setError("Can't be empty");
                    binding.orderid.requestFocus();
                    return;

                }
                if (body.isEmpty()) {
                    binding.review.setError("Can't be empty");
                    binding.review.requestFocus();
                    return;

                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CustomerReviews");

                String date = java.time.LocalDate.now().toString();
                review_model rmd=new review_model();
                rmd.setOrderid(orderid);
                rmd.setUsername("username");
                rmd.setDate(date);
                rmd.setBody(body);
                ref.push().setValue(rmd);


            }
        });


        return binding.getRoot();
    }
}
package com.example.marketingapp.customer;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.marketingapp.databinding.FragmentAddReviewCustomerBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;


public class addReviewCustomer extends BottomSheetDialogFragment {

    FragmentAddReviewCustomerBinding binding;
String rate="3";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddReviewCustomerBinding.inflate(inflater, container, false);


        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                rate = String.valueOf(rating);

            }
        });


        binding.addreview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String body = binding.review.getEditText().getText().toString().trim();

                if (body.isEmpty()) {
                    binding.review.setError("Enter the Reviews");
                    binding.review.requestFocus();
                    return;

                }



//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CustomerReviews").child("shopId");
//
//                String date = java.time.LocalDate.now().toString();
//                review_model rmd=new review_model();
//                rmd.setDate(date);
//                rmd.setRating(rate);
//                rmd.setBody(body);
//                ref.push().setValue(rmd);


            }
        });


        return binding.getRoot();
    }
}
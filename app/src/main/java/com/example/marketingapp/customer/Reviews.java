package com.example.marketingapp.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.marketingapp.databinding.FragmentReviewsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Reviews extends Fragment {
    FragmentReviewsBinding binding;
    review_adapter reviewAdapter;
    ArrayList<review_model> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentReviewsBinding.inflate(inflater, container, false);

        binding.recReview.setLayoutManager(new LinearLayoutManager(getContext()));
        data = new ArrayList<>();

        reviewAdapter = new review_adapter(data, getContext());

        binding.recReview.setAdapter(reviewAdapter);
        getData();

        binding.addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addReview();

            }
        });


        return binding.getRoot();
    }

    private void getData() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CustomerReviews");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot s : snapshot.getChildren()) {

                    review_model md = s.getValue(review_model.class);
                    data.add(md);


                }
                reviewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void addReview() {

        addReviewCustomer addReviewCustomer = new addReviewCustomer();

        addReviewCustomer.show(getChildFragmentManager(), addReviewCustomer.getTag());


    }
}
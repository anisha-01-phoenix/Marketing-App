package com.example.marketingapp.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.marketingapp.Dashboard;
import com.example.marketingapp.R;
import com.example.marketingapp.databinding.ActivityReviewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Review extends AppCompatActivity {
    ActivityReviewBinding binding;
    review_adapter reviewAdapter;
    ArrayList<review_model> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recReview.setLayoutManager(new LinearLayoutManager(this));
        data = new ArrayList<>();

        getData();


    }

    private void getData() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CustomerReviews").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot s : snapshot.getChildren()) {

                    review_model md = s.getValue(review_model.class);
                    data.add(md);


                }
                reviewAdapter = new review_adapter(data, Review.this);
                binding.recReview.setAdapter(reviewAdapter);
                reviewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void bckfrmedt(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Review.this, Dashboard.class));
    }
}
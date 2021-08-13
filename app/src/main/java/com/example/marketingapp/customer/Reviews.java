package com.example.marketingapp.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.marketingapp.R;
import com.example.marketingapp.databinding.FragmentReviewsBinding;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class Reviews extends Fragment {
    FragmentReviewsBinding binding;
    review_adapter reviewAdapter;
    ArrayList<review_model> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       binding=FragmentReviewsBinding.inflate(inflater,container,false);

       binding.recReview.setLayoutManager(new LinearLayoutManager(getContext()));
       data=new ArrayList<>();
       review_model m=new review_model();
       m.setBody("body");
       m.setDate("date");
       m.setUsername("username");
       m.setOrderid("orderid");

       data.add(m);
        reviewAdapter=new review_adapter(data,getContext());

       binding.recReview.setAdapter(reviewAdapter);

       binding.addReview.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               addReview();

           }
       });



        return binding.getRoot();
    }

    private void addReview() {




    }
}
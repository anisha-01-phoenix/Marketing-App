package com.example.marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;


import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignUpBinding binding;
    private String TAG = "PhoneNumber";
    private SharedPreferences sharedPreferences;
    private User user;
    private Shopkeeper shopkeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = new User();
        shopkeeper = new Shopkeeper();

        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
        set_Up_UI();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.signInActivity:

                break;

            case R.id.location:


                break;

            case R.id.get_otp:

                check_empty();

                if (sharedPreferences.getString("permission", "").equalsIgnoreCase("user")) {
                    user.setPhoneNumber(binding.phone.getText().toString());
                } else {
                    shopkeeper.setAddress(binding.address.getText().toString());
                    shopkeeper.setPhoneNo(binding.phone.getText().toString());
                    shopkeeper.setShopName(binding.shopNAme.getText().toString());
                }

                Intent intent = new Intent(SignUpActivity.this, VerifyPhoneNumber.class);

                if (sharedPreferences.getString("permission", "").equalsIgnoreCase("user"))
                    intent.putExtra("user", user);
                else
                    intent.putExtra("shopkeeper", shopkeeper);
                startActivity(intent);
                break;
        }
    }

    private void set_Up_UI() {
        if (sharedPreferences.getString("permission", "").equalsIgnoreCase("user")) {
            binding.addressRelative.setVisibility(View.GONE);
            binding.shopNameRelative.setVisibility(View.GONE);
        }
    }

    private void check_empty() {
        if (binding.phone.getText().toString().isEmpty()) {
            binding.phone.setError("Enter the phone Number");
        }

        if (sharedPreferences.getString("permission", "").equalsIgnoreCase("shopkeeper")) {

            if (binding.shopNAme.getText().toString().isEmpty()) {
                binding.shopNAme.setError("Enter the shop Name");
            }
            if (binding.address.getText().toString().isEmpty()) {
                binding.address.setError("Enter the shop Address");
            }

        }

    }
}
package com.example.marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("Market",MODE_PRIVATE);
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

                Intent intent = new Intent( SignUpActivity.this , VerifyPhoneNumber.class );
                intent.putExtra("phone",binding.phone.getText().toString());
                startActivity(intent);
                break;
        }
    }

    private void set_Up_UI ()
    {
        if ( sharedPreferences.getString("permission","").equalsIgnoreCase("user") )
        {
            binding.addressRelative.setVisibility(View.GONE);
            binding.shopNameRelative.setVisibility(View.GONE);
        }
    }
}
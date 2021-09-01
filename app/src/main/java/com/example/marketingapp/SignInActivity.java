package com.example.marketingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignInBinding binding;
    private User user;
    private Shopkeeper shopkeeper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = new User();
        shopkeeper = new Shopkeeper();
        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        binding.signIn.setOnClickListener(this::onClick);
        binding.signUpActivity.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.signIn:

                editor.putString("signup_login","login");
                editor.commit();

                if ( check_empty() )
                {
                    if (sharedPreferences.getString("permission", "").equalsIgnoreCase("user")) {
                        user.setPhoneNumber(binding.phone.getText().toString());
                    } else {
                        shopkeeper.setPhoneNo(binding.phone.getText().toString());
                    }

                    Intent intent = new Intent(SignInActivity.this, VerifyPhoneNumber.class);

                    if (sharedPreferences.getString("permission", "").equalsIgnoreCase("user"))
                        intent.putExtra("user", user);
                    else
                        intent.putExtra("shopkeeper", shopkeeper);
                    startActivity(intent);
                }

                break;

            case R.id.signUpActivity:

                binding.textView.setText("Sign Up");
                editor.putString("signup_login","signup");
                editor.commit();

                break;


        }

    }

    private Boolean check_empty() {
        if (binding.phone.getText().toString().isEmpty()) {
            binding.phone.setError("Enter the phone Number");
            return false;
        }
        else
        {
            return true;
        }
    }

}
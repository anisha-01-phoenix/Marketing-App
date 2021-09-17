package com.example.marketingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.example.marketingapp.Utils.Constants;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.databinding.ActivityCustomerSignUpBinding;

public class CustomerSignUp extends AppCompatActivity {
    ActivityCustomerSignUpBinding customerSignUpBinding;
    private User user;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CustomerSignUp.this, FirstScreen.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerSignUpBinding = ActivityCustomerSignUpBinding.inflate(getLayoutInflater());
        setContentView(customerSignUpBinding.getRoot());

        user = new User();
        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        customerSignUpBinding.signIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotologin();
            }
        });

//        user = (User) getIntent().getSerializableExtra("user");
        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
    }

    private void gotologin() {
        Intent intent = new Intent(CustomerSignUp.this, SignInActivity.class);
        intent.putExtra(Constants.permission, "user");
        Pair[] pairs = new Pair[3];
        pairs[0] = new Pair<View, String>(customerSignUpBinding.tv1, "heading");
        pairs[1] = new Pair<View, String>(customerSignUpBinding.thissignup, "signup");
        pairs[2] = new Pair<View, String>(customerSignUpBinding.signIn2, "signin");

        ActivityOptions optionsCompat = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent, optionsCompat.toBundle());
    }

    public void signupuser(View view) {
        if (check_empty()) {
            String phno = customerSignUpBinding.cppSignupC.getDefaultCountryCodeWithPlus() + customerSignUpBinding.customerPhone.getEditText().getText().toString();
//                user.setPhoneNumber(phno);
            editor.putString(Constants.permission, "user");
            editor.commit();
            Intent intent = new Intent(CustomerSignUp.this, VerifyPhoneNumber.class);
            intent.putExtra(Constants.phone, phno);
            intent.putExtra(Constants.auth, "signup");
            intent.putExtra(Constants.userdetails, "userdetails");
            intent.putExtra("Name", customerSignUpBinding.cName.getEditText().getText().toString());
            intent.putExtra("Place", customerSignUpBinding.cPlace.getEditText().getText().toString());
//                intent.putExtra("user", user);
            startActivity(intent);
//                        Intent intent = new Intent(SignUpActivity.this, MapsActivity.class);
//                        intent.putExtra("shopkeeper", shopkeeper);
//                        startActivity(intent);

        }

    }

    private boolean check_empty() {
        if (customerSignUpBinding.cName.getEditText().getText().toString().isEmpty()) {
            customerSignUpBinding.cName.setError("Enter your Name");
            customerSignUpBinding.cName.requestFocus();
            return false;
        } else if (customerSignUpBinding.cPlace.getEditText().getText().toString().isEmpty()) {
            customerSignUpBinding.cPlace.setError("Enter your Place");
            customerSignUpBinding.cPlace.requestFocus();
            return false;
        } else if (customerSignUpBinding.customerPhone.getEditText().getText().toString().isEmpty()) {
            customerSignUpBinding.customerPhone.setError("Enter the phone number");
            customerSignUpBinding.customerPhone.requestFocus();
            return false;
        } else
            return true;
    }
}
package com.example.marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.marketingapp.Utils.Constants;
import com.example.marketingapp.classes.Order;
import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignInBinding binding;
    private User user;
    private Shopkeeper shopkeeper;
    private String permission;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = new User();
        shopkeeper = new Shopkeeper();
        permission = getIntent().getStringExtra(Constants.permission);
        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        binding.signIn.setOnClickListener(this::onClick);
        binding.signUpActivity.setOnClickListener(this::onClick);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signIn:

                if (check_empty()) {

                    binding.loading.setVisibility(View.VISIBLE);
                    String phno = binding.cpp.getDefaultCountryCodeWithPlus() + binding.phone.getText().toString();

                    if (permission.equalsIgnoreCase("user")) {
                        editor.putString(Constants.permission, "user");
                        editor.commit();
//                        user.setPhoneNumber(phno);

                    } else {
                        editor.putString(Constants.permission, "shopkeeper");
                        editor.commit();
//                        shopkeeper.setPhoneNo(phno);
                    }
                    if (permission.equalsIgnoreCase("user")) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CustomerPhNo");
                        ValueEventListener listener = new ValueEventListener() {
                            Boolean find = true;

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String ph = String.valueOf(dataSnapshot.getValue());
                                    if (ph.equalsIgnoreCase(phno)) {
                                        find = true;
                                        binding.loading.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(SignInActivity.this, VerifyPhoneNumber.class);
                                        intent.putExtra(Constants.phone, phno);
                                        intent.putExtra(Constants.auth, "login");
                                        startActivity(intent);
                                        finish();
                                        break;
                                    } else {
                                        find = false;
                                    }
                                }
                                if (find==false)
                                {
                                    binding.loading.setVisibility(View.INVISIBLE);
                                    Toasty.error(getApplicationContext(),"Please SignUp before logging!").show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
                        ref.addListenerForSingleValueEvent(listener);


                    } else {

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ShopPhNo");
                        ValueEventListener listener = new ValueEventListener() {
                            Boolean find = true;

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String ph = String.valueOf(dataSnapshot.getValue());
                                    if (ph.equalsIgnoreCase(phno)) {
                                        find = true;
                                        binding.loading.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(SignInActivity.this, VerifyPhoneNumber.class);
                                        intent.putExtra(Constants.phone, phno);
                                        intent.putExtra(Constants.auth, "login");
                                        startActivity(intent);
                                        finish();
                                        break;
                                    } else {
                                        find = false;
                                    }
                                }
                                if (find==false)
                                {
                                    binding.loading.setVisibility(View.INVISIBLE);
                                    Toasty.error(getApplicationContext(),"Please SignUp before logging!").show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
                        ref.addListenerForSingleValueEvent(listener);

                    }

                    // Toast.makeText(getApplicationContext(), shopkeeper.getPhoneNo(), Toast.LENGTH_SHORT).show();
                   /* Intent intent = new Intent(SignInActivity.this, VerifyPhoneNumber.class);
                    intent.putExtra(Constants.phone, phno);
                    intent.putExtra(Constants.auth, "login");
//                    if (permission.equalsIgnoreCase("user"))
//                        intent.putExtra("user", user);
//                    else
//                        intent.putExtra("shopkeeper", shopkeeper);
                    startActivity(intent);*/

                }

                break;

            case R.id.signUpActivity:

                if (permission.equalsIgnoreCase("user")) {
                    editor.putString(Constants.permission, "user");
                    editor.commit();
                    Intent intent = new Intent(SignInActivity.this, CustomerSignUp.class);
//                    intent.putExtra("user", user);
                    Pair[] pairs = new Pair[3];
                    pairs[0] = new Pair<View, String>(binding.textView, "heading");
                    pairs[1] = new Pair<View, String>(binding.signUpActivity, "signup");
                    pairs[2] = new Pair<View, String>(binding.thisSignin, "signin");

                    ActivityOptions optionsCompat = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
                    startActivity(intent, optionsCompat.toBundle());
                } else {
                    editor.putString(Constants.permission, "shopkeeper");
                    editor.commit();
                    Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
//                    intent.putExtra("shopkeeper", shopkeeper);
                    Pair[] pairs = new Pair[3];
                    pairs[0] = new Pair<View, String>(binding.textView, "heading");
                    pairs[1] = new Pair<View, String>(binding.signUpActivity, "signup");
                    pairs[2] = new Pair<View, String>(binding.thisSignin, "signin");

                    ActivityOptions optionsCompat = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
                    startActivity(intent, optionsCompat.toBundle());
                }

                break;


        }

    }

    private Boolean check_empty() {
        if (binding.phone.getText().toString().isEmpty()) {
            binding.phone.setError("Enter the phone Number");
            binding.phone.requestFocus();
            return false;
        } else {
            return true;
        }
    }

}
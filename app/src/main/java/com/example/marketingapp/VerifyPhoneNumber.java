package com.example.marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.databinding.ActivityVerifyPhoneNumberBinding;
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

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber extends AppCompatActivity implements View.OnClickListener {

    private ActivityVerifyPhoneNumberBinding binding;
    private User user;
    private Shopkeeper shopkeeper;
    private String phoneNumber;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String TAG = "PhoneNumber";
    private String veri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
        user = new User();
        shopkeeper = new Shopkeeper();

        if (sharedPreferences.getString("permission", "").equalsIgnoreCase("user"))
        {
            user = (User) getIntent().getSerializableExtra("user");
            phoneNumber = user.getPhoneNumber();
        }
        else
        {
            shopkeeper = (Shopkeeper) getIntent().getSerializableExtra("shopkeeper");
            phoneNumber = shopkeeper.getPhoneNo();
        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("en");


        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @org.jetbrains.annotations.NotNull PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull @org.jetbrains.annotations.NotNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                veri = s;
                Log.d(TAG,s);
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser muser = task.getResult().getUser();

                            if (sharedPreferences.getString("permission", "").equalsIgnoreCase("user"))
                            {
                                user.setUniqueId(muser.getUid());
                            }
                            else
                            {
                                shopkeeper.setUniqueId(muser.getUid());
                            }

                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.signUp:

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(veri,binding.otp.getText().toString());
                signInWithPhoneAuthCredential(credential);

                break;
        }

    }
}
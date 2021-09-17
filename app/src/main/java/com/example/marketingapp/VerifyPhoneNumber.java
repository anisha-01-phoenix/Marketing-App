package com.example.marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.marketingapp.Utils.Constants;
import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.databinding.ActivityVerifyPhoneNumberBinding;
import com.example.marketingapp.map.MapsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class VerifyPhoneNumber extends AppCompatActivity {

    private ActivityVerifyPhoneNumberBinding binding;
    private User user;
    private Shopkeeper shopkeeper;
    private String phoneNumber;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String TAG = "PhoneNumber";
    private String veri;
    private SharedPreferences.Editor editor;
    private String shopname = "", shopaddress = "", shoptype = "", Customername = "", customerplace = "";
    private Boolean wholesell = false;
    private String auth;
    private Shopkeeper shop;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
//        user = new User();
//        shopkeeper = new Shopkeeper();
        phoneNumber = getIntent().getStringExtra(Constants.phone);
        auth = getIntent().getStringExtra(Constants.auth);

        if (getIntent().getStringExtra(Constants.shopdetails) != null) {
            shopname = getIntent().getStringExtra("SHOPNAME");
            shopaddress = getIntent().getStringExtra("SHOPADDRESS");
            shoptype = getIntent().getStringExtra("SHOP_TYPE");
            wholesell = getIntent().getBooleanExtra("WHOLESELLER", false);

        } else if (getIntent().getStringExtra(Constants.userdetails) != null) {
            Customername = getIntent().getStringExtra("Name");
            customerplace = getIntent().getStringExtra("Place");
        }
//        user = (User) getIntent().getSerializableExtra("user");
//        shopkeeper = (Shopkeeper) getIntent().getSerializableExtra("shopkeeper");
//
//        if (sharedPreferences.getString(Constants.permission, "").equalsIgnoreCase("user")) {
//            user = (User) getIntent().getSerializableExtra("user");
//            phoneNumber = user.getPhoneNumber();
//        } else {
//            shopkeeper = (Shopkeeper) getIntent().getSerializableExtra("shopkeeper");
//            phoneNumber = shopkeeper.getPhoneNo();
//        }
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("en");

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toasty.error(getApplicationContext(), e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                binding.otpPb.setVisibility(View.INVISIBLE);
                Toasty.normal(getApplicationContext(), "OTP has been sent...").show();
                veri = s;
                Log.d(TAG, s);
            }
        };

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            binding.otpPb.setVisibility(View.INVISIBLE);

                            FirebaseUser muser = task.getResult().getUser();
                            String uid = muser.getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            editor = sharedPreferences.edit();
                            if (auth.equalsIgnoreCase("signup")) {
                                Toasty.success(getApplicationContext(), "Registered Successfully!").show();
                                if (sharedPreferences.getString(Constants.permission, "").equalsIgnoreCase("user")) {
//                                user.setUniqueId(muser.getUid());
                                    String customerID = uid.substring(0, 4);
                                    user = new User(customerID, phoneNumber, Customername, customerplace);
                                    DatabaseReference reference = database.getReference("Customers");
                                    reference.child(uid).setValue(user);
                                    DatabaseReference reference1 = database.getReference("CustomerPhNo");
                                    reference1.push().setValue(phoneNumber);
                                    editor.putString(Constants.type, "user");
                                    editor.putString(Constants.phone, user.getPhoneNumber());
                                    editor.commit();
                                    Intent intent = new Intent(VerifyPhoneNumber.this, Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                } else {
//                                shopkeeper.setUniqueId(muser.getUid());
//                                shopkeeper=new Shopkeeper(uid,"","",phoneNumber,"",0,false);
                                    String shopID = uid.substring(0, 4);
                                    shopkeeper = new Shopkeeper(shopID, shopname, shopaddress, phoneNumber, shoptype, 0, wholesell);
                                    DatabaseReference reference = database.getReference("Shopkeeper");
                                    reference.child(uid).setValue(shopkeeper);
                                    DatabaseReference reference1 = database.getReference("ShopPhNo");
                                    reference1.push().setValue(phoneNumber);
//                                    setMapCoordinates(new Shopkeeper());
                                    editor.putString(Constants.type, "shopkeeper");
                                    editor.putString(Constants.phone, shopkeeper.getPhoneNo());
                                    editor.commit();
                                    Intent intent = new Intent(VerifyPhoneNumber.this, Dashboard.class);
                                    intent.putExtra("showmap","OpenMap");
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toasty.success(getApplicationContext(), "Welcome Back!").show();
                                Intent intent = new Intent(VerifyPhoneNumber.this, Dashboard.class);
                                startActivity(intent);
                                finish();
                            }



                            // Update UI
                        } else {
                            Toasty.error(getApplicationContext(), "Incorrect verification code!");
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                binding.otpPb.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
    }

    private void setMapCoordinates(Shopkeeper shopkeeper) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Shop Location");
        builder.setPositiveButton("Click Here To set Shop Location on Map", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(VerifyPhoneNumber.this, MapsActivity.class);
                intent.putExtra("shopkeeper", shopkeeper);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }


    public void chk(View view) {
        binding.otpPb.setVisibility(View.VISIBLE);
        String otp = binding.otp1.getText().toString() +
                binding.otp2.getText().toString() +
                binding.otp3.getText().toString() +
                binding.otp4.getText().toString() +
                binding.otp5.getText().toString() +
                binding.otp6.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(veri, otp);
        signInWithPhoneAuthCredential(credential);
    }
}
package com.example.marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.marketingapp.classes.Coordinates;
import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.databinding.ActivitySignUpBinding;
import com.example.marketingapp.map.MapsActivity;
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

    private Location location_App;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = new User();
        shopkeeper = new Shopkeeper();

        if (sharedPreferences.getString("permission", "").equalsIgnoreCase("user")) {
            user = (User) getIntent().getSerializableExtra("user");
        } else {
            shopkeeper = (Shopkeeper) getIntent().getSerializableExtra("shopkeeper");
        }

//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(@NonNull Location location) {
//
//                Log.d("ttttttttttt" , location.toString());
//                location_App = location;
//            }
//        };

//        if (ContextCompat.checkSelfPermission( this , Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
//        }
//        else
//        {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 ,0 ,locationListener);
//            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0 ,0 ,locationListener);
//        }

        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
        if (sharedPreferences.getString("permission", "").equalsIgnoreCase("user")) {
            Intent intent = new Intent(SignUpActivity.this, MapsActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }

        String[] items_spinner3 = new String[]{"Select Category", "GARMENT", "GENERAL STORE", "MEDICINE", "BAKERY"};
        ArrayAdapter<String> adapterSpinner3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_spinner3);
        binding.spinner1.setAdapter(adapterSpinner3);

        String[] items_spinner = new String[]{"Select Type", "WholeSeller", "Retailer"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_spinner);
        binding.spinner2.setAdapter(adapterSpinner);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.get_otp:

                if (check_empty()) {
                    if (sharedPreferences.getString("permission", "").equalsIgnoreCase("shopkeeper")) {
                        shopkeeper.setAddress(binding.address.getText().toString());
                        shopkeeper.setShopName(binding.shopNAme.getText().toString());
                        if ( binding.spinner2.getSelectedItem().toString().equalsIgnoreCase("WholeSeller") )
                            shopkeeper.setWholeSeller(true);
                        shopkeeper.setShopCategory(binding.spinner1.getSelectedItem().toString());
                        Intent intent = new Intent(SignUpActivity.this, MapsActivity.class);
                        intent.putExtra("shopkeeper", shopkeeper);
                        startActivity(intent);
                    }
                }
                break;
        }
    }


    private Boolean check_empty() {

        if (sharedPreferences.getString("permission", "").equalsIgnoreCase("shopkeeper")) {

            if (binding.shopNAme.getText().toString().isEmpty()) {
                binding.shopNAme.setError("Enter the shop Name");
                return false;
            } else if (binding.address.getText().toString().isEmpty()) {
                binding.address.setError("Enter the shop Address");
                return false;
            } else if (binding.spinner1.getSelectedItem() == null || binding.spinner1.getSelectedItem().toString().equals("Select Category")) {
                TextView errorText = (TextView)binding.spinner1.getSelectedView();
                errorText.requestFocus();
                errorText.setError("");
                errorText.setText("Category is Required");//changes the selected item text to this
                return false;
            } else if (binding.spinner2.getSelectedItem() == null || binding.spinner2.getSelectedItem().toString().equals("Select Type")) {
                TextView errorText = (TextView)binding.spinner2.getSelectedView();
                errorText.requestFocus();
                errorText.setError("");
                errorText.setText("Type is Required");//changes the selected item text to this
                return false;
            }else {
                return true;
            }

        } else {
            return true;
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//        {
//            if (ContextCompat.checkSelfPermission( this , Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
//            {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0 ,0 ,locationListener);
//                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0 ,0 ,locationListener);
//            }
//        }
//    }
}
package com.example.marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.marketingapp.Utils.Constants;
import com.example.marketingapp.activities.OrderList;
import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.customer.checkCart;
import com.example.marketingapp.databinding.ActivityDashboardBinding;
import com.example.marketingapp.map.MapsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityDashboardBinding activityDashboardBinding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Shopkeeper shopkeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
        setTitle("Welcome to your EShop!");
        setSupportActionBar(activityDashboardBinding.toolBar);
        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
        editor= sharedPreferences.edit();
        shopkeeper=new Shopkeeper();
        if (!getIntent().getStringExtra("showmap").isEmpty())
        {
//            Intent intent = new Intent(Dashboard.this, MapsActivity.class);
//            intent.putExtra("shopkeeper", shopkeeper);
//            startActivity(intent);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, activityDashboardBinding.drawerLayout, activityDashboardBinding.toolBar, R.string.Open, R.string.Close);
        activityDashboardBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        activityDashboardBinding.navView.setNavigationItemSelectedListener(this);
        Menu menu = activityDashboardBinding.navView.getMenu();
        if (sharedPreferences.getString(Constants.permission, "").equalsIgnoreCase("user")) {
            updateNavHeader();
            menu.findItem(R.id.s_about).setVisible(false);
            menu.findItem(R.id.s_feedback).setVisible(false);
            menu.findItem(R.id.s_order).setVisible(false);
            menu.findItem(R.id.s_wholesale).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.c_cart).setVisible(false);
            menu.findItem(R.id.c_favourites).setVisible(false);
            menu.findItem(R.id.c_feedback).setVisible(false);
            menu.findItem(R.id.c_order).setVisible(false);
            menu.findItem(R.id.c_map).setVisible(false);
            menu.findItem(R.id.c_products).setVisible(false);
            menu.findItem(R.id.c_shop).setVisible(false);
        }

    }

    private void updateNavHeader() {
        View view=activityDashboardBinding.navView.getHeaderView(0);
        TextView name=view.findViewById(R.id.name_header);
        name.setText("Customer");
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }

    private void showAlertDialog() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.s_about:
                break;
            case R.id.s_feedback:
                break;
            case R.id.s_order:
                startActivity(new Intent(Dashboard.this, OrderList.class));
                break;
//            case R.id.s_map:
//
            case R.id.s_wholesale:
                break;
            case R.id.c_cart:
                break;
            case R.id.c_favourites:
                break;
            case R.id.c_feedback:
                break;
            case R.id.c_products:
                break;
            case R.id.c_order:
                break;
            case R.id.c_map:
                break;
            case R.id.c_shop:
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this,FirstScreen.class));
                finish();


        }
        activityDashboardBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
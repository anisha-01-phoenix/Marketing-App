package com.example.marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.marketingapp.databinding.ActivityDashboardBinding;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityDashboardBinding activityDashboardBinding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
        setSupportActionBar(activityDashboardBinding.toolBar);
        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, activityDashboardBinding.drawerLayout, activityDashboardBinding.toolBar, R.string.Open, R.string.Close);
        activityDashboardBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        activityDashboardBinding.navView.setNavigationItemSelectedListener(this);
        Menu menu = activityDashboardBinding.navView.getMenu();
        if (sharedPreferences.getString("permission", "").equalsIgnoreCase("user")) {
            menu.findItem(R.id.s_about).setVisible(false);
            menu.findItem(R.id.s_feedback).setVisible(false);
            menu.findItem(R.id.s_map).setVisible(false);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.s_about:
                break;
            case R.id.s_feedback:
                break;
            case R.id.s_order:
                break;
            case R.id.s_map:
                break;
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

        }
        activityDashboardBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
package com.example.marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.marketingapp.Utils.Constants;
import com.example.marketingapp.activities.Profile;
import com.example.marketingapp.classes.Shopkeeper;
import com.example.marketingapp.classes.User;
import com.example.marketingapp.customer.Customer_Orders;
import com.example.marketingapp.customer.ProductListAdapter;
import com.example.marketingapp.customer.Review;
import com.example.marketingapp.customer.checkCart;
import com.example.marketingapp.databinding.ActivityDashboardBinding;
import com.example.marketingapp.fragments.Fragment_Order;
import com.example.marketingapp.shopkeeper.model_shopcontent;
import com.example.marketingapp.shopkeeper.shopkeepercontent;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityDashboardBinding activityDashboardBinding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Shopkeeper shopkeeper;
    ArrayList<model_shopcontent> userList;
    model_shopcontent model1;
    ProductListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
        setTitle("Welcome to your EShop!");
        setSupportActionBar(activityDashboardBinding.toolBar);
        sharedPreferences = getSharedPreferences("Market", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        shopkeeper = new Shopkeeper();
//        if (!getIntent().getStringExtra("showmap").equals(null))
//        {
////            Intent intent = new Intent(Dashboard.this, MapsActivity.class);
////            intent.putExtra("shopkeeper", shopkeeper);
////            startActivity(intent);
//        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, activityDashboardBinding.drawerLayout, activityDashboardBinding.toolBar, R.string.Open, R.string.Close);
        activityDashboardBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        activityDashboardBinding.navView.setNavigationItemSelectedListener(this);
        Menu menu = activityDashboardBinding.navView.getMenu();
        if (sharedPreferences.getString(Constants.permission, "").equalsIgnoreCase("user")) {
            updateNavHeader();



            activityDashboardBinding.viewcart.setVisibility(View.VISIBLE);
            activityDashboardBinding.viewcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Dashboard.this, checkCart.class));
                }
            });
            activityDashboardBinding.view1.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activityDashboardBinding.view1.setVisibility(View.INVISIBLE);
                    activityDashboardBinding.rvCustomer.setVisibility(View.VISIBLE);

                }
            }, 3000);

            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setReverseLayout(true);
            activityDashboardBinding.rvCustomer.setLayoutManager(new LinearLayoutManager(this));
            userList = new ArrayList<>();


            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Products");
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userList.clear();
                    for (DataSnapshot s : snapshot.getChildren()) {
                        model1 = s.getValue(model_shopcontent.class);
                        userList.add(model1);
                    }
                    adapter = new ProductListAdapter(userList, Dashboard.this);
                    activityDashboardBinding.rvCustomer.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            activityDashboardBinding.searchview.setVisibility(View.VISIBLE);
            activityDashboardBinding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    ArrayList<model_shopcontent> filteredList = new ArrayList<>();

                    for (model_shopcontent item : userList) {

                        if (item.getProductName().toLowerCase().contains(newText.toLowerCase(Locale.ROOT))) {
                            filteredList.add(item);
                        }
                    }
                    adapter.filterList(filteredList);

                    return false;
                }
            });



                menu.findItem(R.id.s_about).setVisible(false);
            menu.findItem(R.id.s_feedback).setVisible(false);
            menu.findItem(R.id.s_wholesale).setVisible(false);

            menu.findItem(R.id.s_order).setVisible(false);

        } else {
            activityDashboardBinding.mystore.setVisibility(View.VISIBLE);

            activityDashboardBinding.view1.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activityDashboardBinding.view1.setVisibility(View.INVISIBLE);
                    getSupportFragmentManager().beginTransaction().add(R.id.flayout, new Fragment_Order(), null).commit();
                }
            }, 3000);

//             menu.findItem(R.id.c_cart).setVisible(false);
            menu.findItem(R.id.c_favourites).setVisible(false);
//            menu.findItem(R.id.c_feedback).setVisible(false);
            menu.findItem(R.id.c_order).setVisible(false);
        }

    }



    private void updateNavHeader() {
        View view = activityDashboardBinding.navView.getHeaderView(0);
        TextView name = view.findViewById(R.id.name_header);
        ImageView profile=view.findViewById(R.id.profile);
        FirebaseDatabase.getInstance().getReference("ProfilePic").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        Glide.with(Dashboard.this).load(snapshot.getValue()).into(profile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });
        FirebaseDatabase.getInstance().getReference("Customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                name.setText(user.getName()+" ("+user.getPlace()+")");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.s_about:
                break;
            case R.id.s_feedback:
                startActivity(new Intent(Dashboard.this, Review.class));
                break;

//            case R.id.s_order:
//                startActivity(new Intent(Dashboard.this, OrderList.class));
//                break;
//            case R.id.s_map:
//
            case R.id.s_wholesale:
                break;

            case R.id.c_order:

                startActivity(new Intent(Dashboard.this, Customer_Orders.class));
                break;

            case R.id.s_order:
                getSupportFragmentManager().beginTransaction().add(R.id.flayout, new Fragment_Order(), null).commit();
                break;

//            case R.id.s_pending:
//                getSupportFragmentManager().beginTransaction().add(R.id.flayout, new Fragment_Pending(), null).commit();
//                break;
//
//            case R.id.s_processing:
//                getSupportFragmentManager().beginTransaction().add(R.id.flayout, new Fragment_Processing(), null).commit();
//                break;

//            case R.id.c_cart:
//                break;
//            case R.id.c_favourites:
//                getSupportFragmentManager().beginTransaction().add(R.id.flayout, new Favourites(), null).commit();
//                break;

//            case R.id.c_feedback:
//                getSupportFragmentManager().beginTransaction().add(R.id.flayout, new Reviews(), null).commit();
//                break;
//            case R.id.c_products:
//                break;
//            case R.id.c_order:
//                break;
//            case R.id.c_map:
//                break;
//            case R.id.c_shop:
//                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this, FirstScreen.class));
                finish();


        }
        activityDashboardBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showShopProduct(View view) {
        startActivity(new Intent(Dashboard.this, shopkeepercontent.class));
    }


}
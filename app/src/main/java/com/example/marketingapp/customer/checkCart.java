package com.example.marketingapp.customer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.marketingapp.Dashboard;
import com.example.marketingapp.R;
import com.example.marketingapp.databinding.ActivityCheckCartBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class checkCart extends AppCompatActivity {

    ActivityCheckCartBinding binding;

    cartAdapter adapter;
    ArrayList<ModelCart_Customer> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        data=new ArrayList<>();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.cartItems.setLayoutManager(layoutManager);
        getCartOrders();


        binding.finalizeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder=new AlertDialog.Builder(checkCart.this);
                builder.setTitle("Place Order")
                        .setMessage("Proceed to Place Your Orders?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s=finalizeOrder();
                                final Snackbar snackbar=Snackbar.make(binding.relativeLayout,"Your Order ID: "+s+"\n"+"Note it down for future reference",Snackbar.LENGTH_INDEFINITE)
                                        .setAction("NOTED", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toasty.success(getApplicationContext(),"Your Orders have been placed!").show();
                                                startActivity(new Intent(checkCart.this,Dashboard.class));

                                            }
                                        }).setActionTextColor(getResources().getColor(R.color.accept));
                                View snackview=snackbar.getView();
                                TextView textView=snackview.findViewById(R.id.snackbar_text);
                                textView.setTextColor(getResources().getColor(R.color.color2));
                                snackbar.show();
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
        });


    }

    private String finalizeOrder() {

//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders").child("uid").child("shopId");
//        ref.addValueEventListener(new ValueEventListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot s : snapshot.getChildren()) {
//
//                    Map<String, String> map = (Map<String, String>) s.getValue();
//
//                    String status = map.get("status");
//                    if (status.equals("1")) {
//
//                        String name=map.get("name");
//                        String price=map.get("price");
//                        String qnty=String.valueOf(map.get("qnty"));
//                        String date = java.time.LocalDate.now().toString();
//                        Map<String ,Object >map1=new HashMap<>();
//                        map1.put("name",name);
//                        map1.put("price",price);
//                        map1.put("qnty",qnty);
//                        map1.put("date",date);
//
//
//                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("finalOrder").child("uid").child("shopid");
//                        reference.push().setValue(map1);
//
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid= user.getUid();
        String orderId=getrandomstring(5);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("finalOrder");
        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("customerOrder");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer_Cart").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s:snapshot.getChildren())
                {
                    ModelCart_Customer model=s.getValue(ModelCart_Customer.class);
                    cartmodel cart=new cartmodel(model.getPrice(),model.getQuantity(),model.getShopid(),model.getCustomerid(),model.getProductid(),model.getDate(),orderId,model.getStatus());
                    reference.child(model.getShopid()).child(orderId).setValue(cart);
                    reference1.child(model.getCustomerid()).child(orderId).setValue(cart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        removeOrders(uid);
        return orderId;
    }

    private void removeOrders(String id) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer_Cart").child(id);
        ref.setValue(null);

    }

    private void getCartOrders() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

         String uid= user.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer_Cart").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot s:snapshot.getChildren())
                {
                    ModelCart_Customer model=s.getValue(ModelCart_Customer.class);
                    data.add(model);
                    if (model!=null)
                    binding.emptycart.setVisibility(View.INVISIBLE);
                }
                adapter =new cartAdapter(data,checkCart.this);
                binding.cartItems.setAdapter(adapter);
                if(data.size()==0)
                    binding.emptycart.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                data.clear();
//                for (DataSnapshot s : snapshot.getChildren()) {
//
//                    Map<String, String> map = (Map<String, String>) s.getValue();
//
//                    String status = map.get("status");
//                    if (status.equals("1")) {
//
//                        String name=map.get("name");
//                        String price=map.get("price");
//
//                        cartmodel cartmodel=new cartmodel();
//                        cartmodel.setName(name);
//                        cartmodel.setPrice(price);
//                        data.add(cartmodel);
//
//                    }
//
//                }
//                cartAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    public void bck(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(checkCart.this, Dashboard.class));
    }


    public static String getrandomstring(int i) {
        final String characters = "0123456789";
        StringBuilder result = new StringBuilder();
        while (i > 0) {
            Random rand = new Random();
            result.append(characters.charAt(rand.nextInt(characters.length())));
            i--;
        }
        return result.toString();
    }

}
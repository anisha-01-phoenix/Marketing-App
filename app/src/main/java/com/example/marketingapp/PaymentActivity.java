package com.example.marketingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketingapp.classes.Order;
import com.example.marketingapp.databinding.ActivityPaymentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private ActivityPaymentBinding binding;
    private String TAG = "Payment";
    private String orderId = "order_I1bj0c9wRTIXZ0";
    private String body;
    private Order order;
    private String date , time ;
    private FirebaseUser user;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("HH:mm:ss",Locale.getDefault()).format(new Date());

        user = FirebaseAuth.getInstance().getCurrentUser();
        order = (Order) getIntent().getSerializableExtra("order");

        body = "{  \"amount\": " + String.valueOf(order.getTotalPrice()*100) + ",  \"currency\": \"INR\",  \"receipt\": \"receipt#2\"}";

    }
    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setKeyID("rzp_test_Y7LgQIbW6tmUJV");
        checkout.setImage(R.drawable.icon);

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Marketing App");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderId);//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", order.getTotalPrice());//pass amount in currency subunits
            options.put("prefill.email", "anshtandonlmp@gmail.com");
            options.put("prefill.contact","8931902676");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(PaymentActivity.this, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    public void checkout (View view)
    {
        try {
            get_order_id();
        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    private void get_order_id () throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://api.razorpay.com/v1/orders", new JSONObject(body), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,response.toString());
                try {
                    orderId = response.get("id").toString();
                    Log.d(TAG,orderId);
                    startPayment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                map.put("Authorization","Basic cnpwX3Rlc3RfWTdMZ1FJYlc2dG1VSlY6Tml4QjRiRVppcndYOVd0YjkyRmY2RjZO");
                map.put("content-type","application/json");
                return map;
            }
        };
        queue.add(request);
    }

    @Override
    public void onPaymentSuccess(String s) {
        generate_order(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void generate_order ( String transactId )
    {
        order.setUniqueIdUser(user.getUid());
        order.setTransactionId(transactId);
        order.setOrderId(orderId);
        order.setDate(date);
        order.setTime(time);
        update_order_on_firebase();
    }

    private void clear_cart()
    {
        reference = FirebaseDatabase.getInstance().getReference("Customer_Cart");
        reference.child(user.getUid()).removeValue();
        Intent intent = new Intent(this, PaymentSuccessful.class);
        startActivity(intent);
        finish();
    }

    private void update_order_on_firebase ()
    {
        reference = FirebaseDatabase.getInstance().getReference("Order");
        reference.child(order.getOrderId()).setValue(order);
        clear_cart();
    }

}
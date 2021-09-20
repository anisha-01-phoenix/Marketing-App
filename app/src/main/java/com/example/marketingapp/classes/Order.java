package com.example.marketingapp.classes;

import android.graphics.Color;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private String uniqueId;
    private String uniqueIdShop;
    private String uniqueIdUser;
    private int orderStatus;
    private String date;
    private String price;
    private String productName;
    private String quantity;

    public Order() {
    }

    public Order(String uniqueId, String uniqueIdShop, String uniqueIdUser, int orderStatus, String date, String price, String productName, String quantity) {
        this.uniqueId = uniqueId;
        this.uniqueIdShop = uniqueIdShop;
        this.uniqueIdUser = uniqueIdUser;
        this.orderStatus = orderStatus;
        this.date = date;
        this.price = price;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @BindingAdapter("android:setText")
    public static void setText(TextView textView, int orderStatus) {
        switch (orderStatus) {
            case 0:
                textView.setText("Available");
                textView.setTextColor(Color.GREEN);
                break;
            case 1:
                textView.setText("Pending");
                textView.setTextColor(Color.BLUE);
                break;
            case 2:
                textView.setText("Processing");
                textView.setTextColor(Color.DKGRAY);
                break;
            case 3:
                textView.setText("Not Available");
                textView.setTextColor(Color.RED);
                break;
            default:
                textView.setTextColor(Color.BLACK);

        }
    }


}

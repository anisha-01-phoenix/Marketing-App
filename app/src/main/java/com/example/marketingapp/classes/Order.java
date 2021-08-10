package com.example.marketingapp.classes;

import android.graphics.Color;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private String uniqueId;
    private String uniqueIdShop;
    private String uniqueIdUser;
    private List<Item> itemsList;
    private int orderStatus;
    private String date;
    private String time;
    private String price;

    public Order() {
    }

    public Order(String uniqueId, String uniqueIdShop, String uniqueIdUser, List<Item> itemsList, int orderStatus, String date, String time, String price) {
        this.uniqueId = uniqueId;
        this.uniqueIdShop = uniqueIdShop;
        this.uniqueIdUser = uniqueIdUser;
        this.itemsList = itemsList;
        this.orderStatus = orderStatus;
        this.date = date;
        this.time = time;
        this.price = price;

    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
                textView.setTextColor(Color.CYAN);
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

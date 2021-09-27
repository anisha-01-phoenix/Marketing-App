package com.example.marketingapp.classes;

import com.example.marketingapp.customer.ModelCart_Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private String orderId;
    private String uniqueIdUser;
    private String transactionId;
    private int orderStatus;
    private String date;
    private String time;
    private int totalPrice;
    private List<ModelCart_Customer> list = new ArrayList<>();

    public Order() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getUniqueIdUser() {
        return uniqueIdUser;
    }

    public void setUniqueIdUser(String uniqueIdUser) {
        this.uniqueIdUser = uniqueIdUser;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ModelCart_Customer> getList() {
        return list;
    }

    public void setList(List<ModelCart_Customer> list) {
        this.list = list;
    }

    public void calculate_total_price() {
        int price = 0;
        for (int i = 0; i < list.size(); i++) {
            price = price + (Integer.parseInt(list.get(i).getPrice()) * Integer.parseInt(list.get(i).getQuantity()));
        }
        this.totalPrice = price;
    }

//    @BindingAdapter("android:setText")
//    public static void setText(TextView textView, int orderStatus) {
//        switch (orderStatus) {
//            case 0:
//                textView.setText("Available");
//                textView.setTextColor(Color.GREEN);
//                break;
//            case 1:
//                textView.setText("Pending");
//                textView.setTextColor(Color.BLUE);
//                break;
//            case 2:
//                textView.setText("Processing");
//                textView.setTextColor(Color.DKGRAY);
//                break;
//            case 3:
//                textView.setText("Not Available");
//                textView.setTextColor(Color.RED);
//                break;
//            default:
//                textView.setTextColor(Color.BLACK);
//
//        }
//    }


}

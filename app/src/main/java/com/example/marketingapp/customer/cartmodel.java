package com.example.marketingapp.customer;

import android.graphics.Color;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class cartmodel {

    String price,qnty,shopid,customerid,productid,date,orderid;
    int status;

    public cartmodel() {
    }

    public cartmodel(String price, String qnty, String shopid, String customerid, String productid, String date, String orderid, int status) {
        this.price = price;
        this.qnty = qnty;
        this.shopid = shopid;
        this.customerid = customerid;
        this.productid = productid;
        this.date = date;
        this.orderid = orderid;
        this.status = status;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQnty() {
        return qnty;
    }

    public void setQnty(String qnty) {
        this.qnty = qnty;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }



}

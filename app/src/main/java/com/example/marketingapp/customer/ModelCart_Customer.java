package com.example.marketingapp.customer;

public class ModelCart_Customer {

    String shopid;
    String productid;
    String customerid;
    int status;
    String price;
    String quantity;
    String date;

    public ModelCart_Customer(String shopid, String productid, String customerid, int status, String price, String quantity, String date) {
        this.shopid = shopid;
        this.productid = productid;
        this.customerid = customerid;
        this.status = status;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
    }

    public ModelCart_Customer() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

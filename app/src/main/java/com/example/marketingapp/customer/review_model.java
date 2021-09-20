package com.example.marketingapp.customer;

public class review_model {

    String date, body;
    String rating;
    String productID, shopID, customerID;

    public review_model() {
    }

    public review_model(String date, String body, String rating, String productID, String shopID, String customerID) {
        this.date = date;
        this.body = body;
        this.rating = rating;
        this.productID = productID;
        this.shopID = shopID;
        this.customerID = customerID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

package com.example.marketingapp.classes;

import java.io.Serializable;

public class Shopkeeper implements Serializable {

    private String uniqueId ;
    private String shopName ;
    private String address ;
    private String phoneNo ;
    private String shopCategory ;
    private long rating = 0;
    private boolean isWholeSeller = false;
    private Coordinates coordinates;


    public Shopkeeper()
    {

    }

    public Shopkeeper(String uniqueId, String shopName, String address, String phoneNo, String shopCategory, long rating, boolean isWholeSeller) {
        this.uniqueId = uniqueId;
        this.shopName = shopName;
        this.address = address;
        this.phoneNo = phoneNo;
        this.shopCategory = shopCategory;
        this.rating = rating;
        this.isWholeSeller = isWholeSeller;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public boolean isWholeSeller() {
        return isWholeSeller;
    }

    public void setWholeSeller(boolean wholeSeller) {
        isWholeSeller = wholeSeller;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

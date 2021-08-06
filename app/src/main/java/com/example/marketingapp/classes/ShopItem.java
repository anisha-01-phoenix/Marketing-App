package com.example.marketingapp.classes;

import java.io.Serializable;

public class ShopItem implements Serializable {

    private String productName;
    private String price;
    private String quantityAvailable;

    public ShopItem(String productName, String price, String quantityAvailable) {
        this.productName = productName;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(String quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
}

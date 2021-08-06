package com.example.marketingapp.classes;

import java.io.Serializable;

public class Item implements Serializable {

    private String productName;
    private String quantity;

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


}

package com.example.marketingapp.shopkeeper;

import java.util.ArrayList;
import java.util.Map;

public class model_shopcontent {
    String productID;
    String shopID;
    String productUrl;
    String productName;
    String productQty;
    String productDesc;
    String productPrice;
    String offerPrice;

    public model_shopcontent() {
    }

    public model_shopcontent(String productID,String shopID, String productUrl, String productName, String productQty, String productDesc, String productPrice, String offerPrice) {
        this.productID=productID;
        this.shopID = shopID;
        this.productUrl = productUrl;
        this.productName = productName;
        this.productQty = productQty;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.offerPrice = offerPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }
}

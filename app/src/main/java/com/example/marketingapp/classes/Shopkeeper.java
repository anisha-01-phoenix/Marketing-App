package com.example.marketingapp.classes;

import java.io.Serializable;

public class Shopkeeper implements Serializable {

    private String uniqueId;
    private String shopName;
    private String address;
    private String email;
    private String phoneNo;
    private long rating;
    private boolean isWholeSeller;
    private Coordinates coordinates;

}

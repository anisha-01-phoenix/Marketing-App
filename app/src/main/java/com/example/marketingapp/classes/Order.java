package com.example.marketingapp.classes;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private String uniqueId;
    private String uniqueIdShop;
    private String uniqueIdUser;
    private List<Item> itemsList;
    private String orderStatus;
    private String date;
    private String time;
    private String price;


}

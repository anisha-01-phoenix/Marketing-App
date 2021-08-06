package com.example.marketingapp.classes;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String uniqueId;
    private String email;
    private Coordinates coordinates;
    private List<String> favShops;
}

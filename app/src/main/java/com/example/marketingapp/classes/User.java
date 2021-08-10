package com.example.marketingapp.classes;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String uniqueId;
    private String phoneNumber;
    private Coordinates coordinates;
    private List<String> favShops;

    public User()
    {

    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> getFavShops() {
        return favShops;
    }

    public void setFavShops(List<String> favShops) {
        this.favShops = favShops;
    }
}

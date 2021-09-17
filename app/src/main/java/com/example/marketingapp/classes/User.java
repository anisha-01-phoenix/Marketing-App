package com.example.marketingapp.classes;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String uniqueId;
    private String phoneNumber;
    private String name;
    private String place;
    private Coordinates coordinates;
    private List<String> favShops;

    public User()
    {

    }

    public User(String uniqueId, String phoneNumber, String name, String place) {
        this.uniqueId = uniqueId;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

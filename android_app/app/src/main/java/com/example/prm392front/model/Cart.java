package com.example.prm392front.model;

import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("cartID")     private int cartID;
    @SerializedName("userID")     private int userID;
    @SerializedName("totalPrice") private double totalPrice;
    @SerializedName("status")     private String status;

    public int getCartID()        { return cartID; }
    public int getUserID()        { return userID; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus()     { return status; }
}

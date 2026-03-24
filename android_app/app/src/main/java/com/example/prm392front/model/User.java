package com.example.prm392front.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userID")     private int userID;
    @SerializedName("userName")   private String userName;
    @SerializedName("password")   private String password;
    @SerializedName("email")      private String email;
    @SerializedName("phoneNumber") private String phoneNumber;
    @SerializedName("address")    private String address;
    @SerializedName("role")       private String role;

    // Getters
    public int getUserID()         { return userID; }
    public String getUserName()    { return userName; }
    public String getPassword()    { return password; }
    public String getEmail()       { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress()     { return address; }
    public String getRole()        { return role; }

    // Setters
    public void setUserName(String v)    { this.userName = v; }
    public void setPassword(String v)    { this.password = v; }
    public void setEmail(String v)       { this.email = v; }
    public void setPhoneNumber(String v) { this.phoneNumber = v; }
    public void setAddress(String v)     { this.address = v; }
    public void setRole(String v)        { this.role = v; }
}

package com.example.prm392front.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("productID")               private int productID;
    @SerializedName("productName")             private String productName;
    @SerializedName("briefDescription")        private String briefDescription;
    @SerializedName("fullDescription")         private String fullDescription;
    @SerializedName("technicalSpecifications") private String technicalSpecifications;
    @SerializedName("price")                   private double price;
    @SerializedName("imageURL")                private String imageURL;
    @SerializedName("categoryID")              private int categoryID;

    public int getProductID()                   { return productID; }
    public String getProductName()              { return productName; }
    public String getBriefDescription()         { return briefDescription; }
    public String getFullDescription()          { return fullDescription; }
    public String getTechnicalSpecifications()  { return technicalSpecifications; }
    public double getPrice()                    { return price; }
    public String getImageURL()                 { return imageURL; }
    public int getCategoryID()                  { return categoryID; }
}

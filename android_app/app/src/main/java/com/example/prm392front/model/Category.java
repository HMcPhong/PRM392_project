package com.example.prm392front.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("categoryID")   private int categoryID;
    @SerializedName("categoryName") private String categoryName;

    public int getCategoryID()      { return categoryID; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String v) { this.categoryName = v; }
}

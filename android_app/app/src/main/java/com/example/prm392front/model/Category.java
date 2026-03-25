package com.example.prm392front.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("categoryId")   private int categoryId;
    @SerializedName("categoryName") private String categoryName;

    public int getCategoryId()      { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String v) { this.categoryName = v; }
}

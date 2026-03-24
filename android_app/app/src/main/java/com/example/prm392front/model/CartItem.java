package com.example.prm392front.model;

import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("cartItemID") private int cartItemID;
    @SerializedName("cartID")     private int cartID;
    @SerializedName("productID")  private int productID;
    @SerializedName("quantity")   private int quantity;
    @SerializedName("price")      private double price;
    // Often the API also nests the product object — add this if your API returns it:
    @SerializedName("product")    private Product product;

    public int getCartItemID()   { return cartItemID; }
    public int getCartID()       { return cartID; }
    public int getProductID()    { return productID; }
    public int getQuantity()     { return quantity; }
    public double getPrice()     { return price; }
    public Product getProduct()  { return product; }
}

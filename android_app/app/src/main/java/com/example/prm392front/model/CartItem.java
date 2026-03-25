package com.example.prm392front.model;

import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("cartItemId") private int cartItemId;
    @SerializedName("cartId")     private int cartId;
    @SerializedName("productId")  private int productId;
    @SerializedName("quantity")   private int quantity;
    @SerializedName("price")      private double price;
    // Often the API also nests the product object — add this if your API returns it:
    @SerializedName("product")    private Product product;

    public int getCartItemId()   { return cartItemId; }
    public int getCartId()       { return cartId; }
    public int getProductId()    { return productId; }
    public int getQuantity()     { return quantity; }
    public double getPrice()     { return price; }
    public Product getProduct()  { return product; }
}

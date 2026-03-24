package com.example.prm392front.api;

import com.example.prm392front.model.Cart;
import com.example.prm392front.model.CartItem;
import com.example.prm392front.model.Category;
import com.example.prm392front.model.Product;
import com.example.prm392front.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // ── AUTH ──────────────────────────────────────────────
    @GET("api/users/auth/login")
    Call<User> login(
            @Query("userName") String userName,
            @Query("password") String password
    );

    @POST("api/users/auth/customer/sign_up")
    Call<User> registerCustomer(@Body User user);

    @POST("api/users/auth/manager/sign_up")
    Call<User> registerManager(@Body User user);

    // ── USERS ─────────────────────────────────────────────
    @GET("api/users")
    Call<List<User>> getAllUsers();

    @GET("api/users/search/{userID}")
    Call<User> getUserById(@Path("userID") int userID);

    // ── CATEGORIES ────────────────────────────────────────
    @GET("api/categories")
    Call<List<Category>> getAllCategories();

    @GET("api/categories/search/{categoryID}")
    Call<Category> getCategoryById(@Path("categoryID") int categoryID);

    @POST("api/categories")
    Call<Category> createCategory(@Body Category category);

    // ── PRODUCTS ──────────────────────────────────────────
    @GET("api/products")
    Call<List<Product>> getAllProducts();

    @GET("api/products/search/{productID}")
    Call<Product> getProductById(@Path("productID") int productID);

    @GET("api/products/{categoryID}")
    Call<List<Product>> getProductsByCategory(@Path("categoryID") int categoryID);

    @GET("api/products/search/")
    Call<List<Product>> searchProducts(@Query("productName") String productName);

    @POST("api/products")
    Call<Product> createProduct(@Body Product product);

    // ── CART ──────────────────────────────────────────────
    @GET("api/cart/{userID}")
    Call<Cart> getCart(@Path("userID") int userID);

    @GET("api/cart/{userID}/items")
    Call<List<CartItem>> getCartItems(@Path("userID") int userID);

    @POST("api/cart/{userID}/add/{productID}")
    Call<Void> addToCart(
            @Path("userID") int userID,
            @Path("productID") int productID,
            @Query("quantity") int quantity
    );

    @PUT("api/cart/{userID}/{productID}")
    Call<Void> updateCartItem(
            @Path("userID") int userID,
            @Path("productID") int productID,
            @Query("quantity") int quantity
    );

    @DELETE("api/cart/{userID}/{productID}")
    Call<Void> removeCartItem(
            @Path("userID") int userID,
            @Path("productID") int productID
    );

    @DELETE("api/cart/{userID}")
    Call<Void> clearCart(@Path("userID") int userID);
}

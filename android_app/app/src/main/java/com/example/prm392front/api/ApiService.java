package com.example.prm392front.api;

import com.example.prm392front.model.Cart;
import com.example.prm392front.model.CartItem;
import com.example.prm392front.model.Category;
import com.example.prm392front.model.Product;
import com.example.prm392front.model.User;
import com.example.prm392front.respond.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // ── AUTH ──────────────────────────────────────────────
    @GET("api/users/auth/login")
    Call<ApiResponse<User>> login(
            @Query("userName") String userName,
            @Query("password") String password
    );

    @POST("api/users/auth/customer/sign_up")
    Call<ApiResponse<User>> registerCustomer(@Body User user);

    @POST("api/users/auth/manager/sign_up")
    Call<ApiResponse<User>> registerManager(@Body User user);

    // ── USERS ─────────────────────────────────────────────
    @GET("api/users")
    Call<ApiResponse<List<User>>> getAllUsers();

    @GET("api/users/search/{userID}")
    Call<ApiResponse<User>> getUserById(@Path("userID") int userID);

    // ── CATEGORIES ────────────────────────────────────────
    @GET("api/categories")
    Call<ApiResponse<List<Category>>> getAllCategories();

    @GET("api/categories/search/{categoryID}")
    Call<ApiResponse<Category>> getCategoryById(@Path("categoryID") int categoryID);

    @POST("api/categories")
    Call<ApiResponse<Category>> createCategory(@Body Category category);

    // ── PRODUCTS ──────────────────────────────────────────
    @GET("api/products")
    Call<ApiResponse<List<Product>>> getAllProducts();

    @GET("api/products/search")
    Call<ApiResponse<List<Product>>> searchProducts(@Query("productName") String productName);

    @GET("api/products/search/{productID}")
    Call<ApiResponse<Product>> getProductById(@Path("productID") int productID);

    @GET("api/products/{categoryID}")
    Call<ApiResponse<List<Product>>> getProductsByCategory(@Path("categoryID") int categoryID);

    @POST("api/products")
    Call<ApiResponse<Product>> createProduct(@Body Product product);

    // ── CART ──────────────────────────────────────────────
    @GET("api/cart/{userID}")
    Call<ApiResponse<Cart>> getCart(@Path("userID") int userID);

    @GET("api/cart/{userID}/items")
    Call<ApiResponse<List<CartItem>>> getCartItems(@Path("userID") int userID);

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

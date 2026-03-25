package com.example.prm392front.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392front.R;
import com.example.prm392front.adapter.CartAdapter;
import com.example.prm392front.api.ApiClient;
import com.example.prm392front.model.CartItem;
import com.example.prm392front.respond.ApiResponse;
import com.example.prm392front.utils.NotificationHelper;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCart;
    CartAdapter adapter;
    TextView tvTotal;
    Button btnCheckout, btnClearCart;
    int currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Cart");
        }

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        currentUserID = prefs.getInt("userID", -1);

        rvCart = findViewById(R.id.rvCart);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnClearCart = findViewById(R.id.btnClearCart);

        rvCart.setLayoutManager(new LinearLayoutManager(this));

        btnCheckout.setOnClickListener(v ->
                Toast.makeText(this, "Checkout coming soon!", Toast.LENGTH_SHORT).show()
        );

        btnClearCart.setOnClickListener(v -> {
            // Show confirmation dialog before clearing
            new androidx.appcompat.app.AlertDialog.Builder(CartActivity.this)
                    .setTitle("Clear Cart")
                    .setMessage("Are you sure you want to remove all items from your cart?")
                    .setPositiveButton("Yes, clear it", (dialog, which) -> clearCart())
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        loadCart();
    }

    private void loadCart() {
        ApiClient.getApiService().getCartItems(currentUserID)
                .enqueue(new Callback<ApiResponse<List<CartItem>>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<List<CartItem>>> call, @NonNull Response<ApiResponse<List<CartItem>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<CartItem> items = response.body().getData();
                            adapter = new CartAdapter(CartActivity.this, items, new CartAdapter.CartActionListener() {
                                @Override
                                public void onQuantityChanged(CartItem item, int newQty) {
                                    updateItem(item, newQty);
                                }

                                @Override
                                public void onDelete(CartItem item) {
                                    deleteItem(item);
                                }
                            });
                            rvCart.setAdapter(adapter);
                            recalculateTotal(items);

                            NotificationHelper.showCartNotification(
                                    CartActivity.this, items.size());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<List<CartItem>>> call, @NonNull Throwable t) {
                        Toast.makeText(CartActivity.this, "Failed to load cart", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateItem(CartItem item, int newQty) {
        ApiClient.getApiService()
                .updateCartItem(currentUserID, item.getProductId(), newQty)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        loadCart();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    }
                });
    }

    private void deleteItem(CartItem item) {
        ApiClient.getApiService()
                .removeCartItem(currentUserID, item.getProductId())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        loadCart();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    }
                });
    }

    private void clearCart() {
        ApiClient.getApiService().clearCart(currentUserID)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call,
                                           @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CartActivity.this,
                                    "Cart cleared!", Toast.LENGTH_SHORT).show();
                            // Cancel the notification badge since cart is now empty
                            NotificationHelper.cancelCartNotification(CartActivity.this);
                            // Reload to show empty cart
                            loadCart();
                        } else {
                            Toast.makeText(CartActivity.this,
                                    "Failed to clear cart", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(CartActivity.this,
                                "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("DefaultLocale")
    private void recalculateTotal(List<CartItem> items) {
        double total = 0;
        for (CartItem i : items) total += i.getPrice(); // ← remove * i.getQuantity()
        tvTotal.setText(NumberFormat.getNumberInstance(Locale.US).format(total) + "đ");
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}

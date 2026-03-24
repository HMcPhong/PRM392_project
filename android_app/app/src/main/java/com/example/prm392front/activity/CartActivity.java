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

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCart;
    CartAdapter adapter;
    TextView tvTotal;
    Button btnCheckout;
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

        rvCart      = findViewById(R.id.rvCart);
        tvTotal     = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        rvCart.setLayoutManager(new LinearLayoutManager(this));

        btnCheckout.setOnClickListener(v ->
                Toast.makeText(this, "Checkout coming soon!", Toast.LENGTH_SHORT).show()
        );

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
                .updateCartItem(currentUserID, item.getProductID(), newQty)
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
                .removeCartItem(currentUserID, item.getProductID())
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

    @SuppressLint("DefaultLocale")
    private void recalculateTotal(List<CartItem> items) {
        double total = 0;
        for (CartItem i : items) total += i.getPrice() * i.getQuantity();
        tvTotal.setText(String.format("$%.2f", total));
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}

package com.example.prm392front.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392front.respond.ApiResponse;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.example.prm392front.R;
import com.example.prm392front.adapter.ProductAdapter;
import com.example.prm392front.api.ApiClient;
import com.example.prm392front.model.Category;
import com.example.prm392front.model.Product;
import com.example.prm392front.utils.NotificationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rvProducts;
    ProductAdapter adapter;
    ChipGroup chipGroupCategories;
    TextInputEditText etSearch;
    int currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        NotificationHelper.createNotificationChannel(this);
        requestNotificationPermission();

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        currentUserID = prefs.getInt("userID", -1);

        rvProducts          = findViewById(R.id.rvProducts);
        chipGroupCategories = findViewById(R.id.chipGroupCategories);
        etSearch            = findViewById(R.id.etSearch);

        String userName = prefs.getString("userName", "U");
        TextView btnProfile = findViewById(R.id.btnProfile);

        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        btnProfile.setText(userName.substring(0, 1).toUpperCase());

        // ✅ Create adapter ONCE, never again anywhere else
        adapter = new ProductAdapter(this, new ArrayList<>(), product -> {
            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
            intent.putExtra("productID", product.getProductID());
            startActivity(intent);
        });
        rvProducts.setAdapter(adapter);

        // Nav buttons
        findViewById(R.id.btnCart).setOnClickListener(v ->
                startActivity(new Intent(this, CartActivity.class)));
        findViewById(R.id.btnProfile).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        // Search button click
        findViewById(R.id.btnSearch).setOnClickListener(v -> {
            String query = Objects.requireNonNull(etSearch.getText()).toString().trim();
            if (!query.isEmpty()) {
                searchProducts(query);
            } else {
                loadAllProducts();
            }
        });

        loadCategories();
        loadAllProducts();
    }

    // ✅ Only calls updateList() — never creates a new adapter
    private void loadAllProducts() {
        ApiClient.getApiService().getAllProducts().enqueue(new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<Product>>> call,
                                   @NonNull Response<ApiResponse<List<Product>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateList(response.body().getData());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<Product>>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(HomeActivity.this, "Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadCategories() {
        ApiClient.getApiService().getAllCategories().enqueue(new Callback<ApiResponse<List<Category>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<Category>>> call,
                                   @NonNull Response<ApiResponse<List<Category>>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    // "All" chip
                    Chip allChip = new Chip(HomeActivity.this);
                    allChip.setText("All");
                    allChip.setCheckable(true);
                    allChip.setChecked(true);
                    allChip.setOnClickListener(v -> loadAllProducts());
                    chipGroupCategories.addView(allChip);

                    for (Category cat : response.body().getData()) {
                        Chip chip = new Chip(HomeActivity.this);
                        chip.setText(cat.getCategoryName());
                        chip.setCheckable(true);
                        chip.setOnClickListener(v -> filterByCategory(cat.getCategoryId()));
                        chipGroupCategories.addView(chip);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<Category>>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(HomeActivity.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterByCategory(int categoryID) {
        ApiClient.getApiService().getProductsByCategory(categoryID)
                .enqueue(new Callback<ApiResponse<List<Product>>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<List<Product>>> call,
                                           @NonNull Response<ApiResponse<List<Product>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(HomeActivity.this, "Calling API...", Toast.LENGTH_SHORT).show();

                            adapter.updateList(response.body().getData());
                        } else {
                            Toast.makeText(HomeActivity.this, "Response failed: code " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<List<Product>>> call,
                                          @NonNull Throwable t) {
                        Toast.makeText(HomeActivity.this, "Failed to filter", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void searchProducts(String query) {
        ApiClient.getApiService().searchProducts(query)
                .enqueue(new Callback<ApiResponse<List<Product>>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<List<Product>>> call,
                                           @NonNull Response<ApiResponse<List<Product>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(HomeActivity.this, "Calling API...", Toast.LENGTH_SHORT).show();

                            adapter.updateList(response.body().getData());
                        } else {
                            Toast.makeText(HomeActivity.this, "Response failed: code " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<List<Product>>> call,
                                          @NonNull Throwable t) {
                        Toast.makeText(HomeActivity.this, "Search failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        100
                );
            }
        }
    }
}
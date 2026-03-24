package com.example.prm392front.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

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

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        currentUserID = prefs.getInt("userID", -1);

        rvProducts          = findViewById(R.id.rvProducts);
        chipGroupCategories = findViewById(R.id.chipGroupCategories);
        etSearch            = findViewById(R.id.etSearch);

        // 2-column grid
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        // Nav buttons
        findViewById(R.id.btnCart).setOnClickListener(v ->
                startActivity(new Intent(this, CartActivity.class)));
        findViewById(R.id.btnProfile).setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        // Search on keyboard "Search" button
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = Objects.requireNonNull(etSearch.getText()).toString().trim();
                if (!query.isEmpty()) searchProducts(query);
                return true;
            }
            return false;
        });

        loadCategories();
        loadAllProducts();
    }

    private void loadAllProducts() {
        ApiClient.getApiService().getAllProducts().enqueue(new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<Product>>> call, @NonNull Response<ApiResponse<List<Product>>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Product> products = response.body().getData();

                    adapter = new ProductAdapter(HomeActivity.this, products, product -> {
                        Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                        intent.putExtra("productID", product.getProductID());
                        startActivity(intent);
                    });

                    rvProducts.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<Product>>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadCategories() {
        ApiClient.getApiService().getAllCategories().enqueue(new Callback<ApiResponse<List<Category>>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<Category>>> call, @NonNull Response<ApiResponse<List<Category>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // "All" chip first
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
                        chip.setOnClickListener(v -> filterByCategory(cat.getCategoryID()));
                        chipGroupCategories.addView(chip);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<Category>>> call, @NonNull Throwable t) {
            }
        });
    }

    private void filterByCategory(int categoryID) {
        ApiClient.getApiService().getProductsByCategory(categoryID)
                .enqueue(new Callback<ApiResponse<List<Product>>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<List<Product>>> call, @NonNull Response<ApiResponse<List<Product>>> response) {
                        if (response.isSuccessful() && response.body() != null && adapter != null) {
                            adapter.updateList(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<List<Product>>> call, @NonNull Throwable t) {
                    }
                });
    }

    private void searchProducts(String query) {
        ApiClient.getApiService().searchProducts(query)
                .enqueue(new Callback<ApiResponse<List<Product>>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<List<Product>>> call, @NonNull Response<ApiResponse<List<Product>>> response) {
                        if (response.isSuccessful() && response.body() != null && adapter != null) {
                            adapter.updateList(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<List<Product>>> call, @NonNull Throwable t) {
                    }
                });
    }
}

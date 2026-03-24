package com.example.prm392front.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.prm392front.R;
import com.example.prm392front.api.ApiClient;
import com.example.prm392front.model.Product;
import com.example.prm392front.respond.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView tvName, tvPrice, tvDesc, tvSpecs, tvQuantity;
    Button btnMinus, btnPlus, btnAddToCart;
    int quantity = 1;
    int currentUserID;
    Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Enable back button in action bar
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        currentUserID = prefs.getInt("userID", -1);

        imgProduct   = findViewById(R.id.imgProductDetail);
        tvName       = findViewById(R.id.tvDetailName);
        tvPrice      = findViewById(R.id.tvDetailPrice);
        tvDesc       = findViewById(R.id.tvDetailDesc);
        tvSpecs      = findViewById(R.id.tvDetailSpecs);
        tvQuantity   = findViewById(R.id.tvQuantity);
        btnMinus     = findViewById(R.id.btnMinus);
        btnPlus      = findViewById(R.id.btnPlus);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        btnMinus.setOnClickListener(v -> { if (quantity > 1) tvQuantity.setText(String.valueOf(--quantity)); });
        btnPlus.setOnClickListener(v  -> tvQuantity.setText(String.valueOf(++quantity)));
        btnAddToCart.setOnClickListener(v -> addToCart());

        int productID = getIntent().getIntExtra("productID", -1);
        if (productID != -1) loadProduct(productID);
    }

    private void loadProduct(int productID) {
        ApiClient.getApiService().getProductById(productID).enqueue(new Callback<ApiResponse<Product>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Product>> call, @NonNull Response<ApiResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentProduct = response.body().getData();
                    tvName.setText(currentProduct.getProductName());
                    tvPrice.setText(String.format("$%.2f", currentProduct.getPrice()));
                    tvDesc.setText(currentProduct.getFullDescription());
                    tvSpecs.setText(currentProduct.getTechnicalSpecifications());
                    Glide.with(ProductDetailActivity.this)
                            .load(currentProduct.getImageURL())
                            .into(imgProduct);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Product>> call, @NonNull Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Failed to load product", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToCart() {
        if (currentProduct == null || currentUserID == -1) return;
        ApiClient.getApiService()
                .addToCart(currentUserID, currentProduct.getProductID(), quantity)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ProductDetailActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Could not add to cart", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(ProductDetailActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
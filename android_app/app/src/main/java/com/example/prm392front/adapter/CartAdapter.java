package com.example.prm392front.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm392front.R;
import com.example.prm392front.api.ApiClient;
import com.example.prm392front.model.CartItem;
import com.example.prm392front.model.Product;
import com.example.prm392front.respond.ApiResponse;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    //private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static final String BASE_URL = "http://192.168.1.3:8080/";
    public interface CartActionListener {
        void onQuantityChanged(CartItem item, int newQty);
        void onDelete(CartItem item);
    }

    private List<CartItem> items;
    private final Context context;
    private final CartActionListener listener;

    public CartAdapter(Context context, List<CartItem> items, CartActionListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder h, int position) {
        CartItem item = items.get(position);

        // Show quantity
        h.tvQty.setText(String.valueOf(item.getQuantity()));

        // Show price (already unit * qty from backend)
        h.tvPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(item.getPrice()) + "đ");

        // Show placeholder name while loading
        h.tvName.setText("Loading...");

        // Fetch product details to get name and image
        ApiClient.getApiService().getProductById(item.getProductId())
                .enqueue(new Callback<ApiResponse<Product>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<Product>> call,
                                           @NonNull Response<ApiResponse<Product>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Product product = response.body().getData();
                            // Make sure view is still on screen before updating
                            h.tvName.setText(product.getProductName());
                            Glide.with(context)
                                    .load(BASE_URL + product.getImageUrl())
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.ic_launcher_background)
                                    .into(h.img);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<Product>> call,
                                          @NonNull Throwable t) {
                        h.tvName.setText("Unknown product");
                    }
                });

        // Buttons
        h.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1)
                listener.onQuantityChanged(item, item.getQuantity() - 1);
        });
        h.btnPlus.setOnClickListener(v ->
                listener.onQuantityChanged(item, item.getQuantity() + 1));
        h.btnDelete.setOnClickListener(v ->
                listener.onDelete(item));
    }

    @Override
    public int getItemCount() { return items.size(); }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItems(List<CartItem> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvPrice, tvQty;
        Button btnMinus, btnPlus;
        ImageButton btnDelete;

        CartViewHolder(@NonNull View v) {
            super(v);
            img       = v.findViewById(R.id.imgCartItem);
            tvName    = v.findViewById(R.id.tvCartItemName);
            tvPrice   = v.findViewById(R.id.tvCartItemPrice);
            tvQty     = v.findViewById(R.id.tvCartQty);
            btnMinus  = v.findViewById(R.id.btnCartMinus);
            btnPlus   = v.findViewById(R.id.btnCartPlus);
            btnDelete = v.findViewById(R.id.btnCartDelete);
        }
    }
}
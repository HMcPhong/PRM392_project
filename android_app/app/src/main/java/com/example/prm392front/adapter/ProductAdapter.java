package com.example.prm392front.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.prm392front.R;
import com.example.prm392front.model.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    private List<Product> products;
    private final Context context;
    private final OnProductClickListener listener;

    public ProductAdapter(Context context, List<Product> products, OnProductClickListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = products.get(position);
        holder.tvName.setText(p.getProductName());
        holder.tvDesc.setText(p.getBriefDescription());
        holder.tvPrice.setText(String.format("$%.2f", p.getPrice()));

        Glide.with(context)
                .load(p.getImageURL())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgProduct);

        holder.itemView.setOnClickListener(v -> listener.onProductClick(p));
    }

    @Override
    public int getItemCount() { return products.size(); }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Product> newList) {
        this.products = newList;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvDesc, tvPrice;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName     = itemView.findViewById(R.id.tvProductName);
            tvDesc     = itemView.findViewById(R.id.tvProductDesc);
            tvPrice    = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}

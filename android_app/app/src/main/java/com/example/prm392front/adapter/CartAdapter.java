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
import com.example.prm392front.model.CartItem;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

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

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder h, int position) {
        CartItem item = items.get(position);
        h.tvQty.setText(String.valueOf(item.getQuantity()));
        h.tvPrice.setText(String.format("$%.2f", item.getPrice() * item.getQuantity()));

        if (item.getProduct() != null) {
            h.tvName.setText(item.getProduct().getProductName());
            Glide.with(context).load(item.getProduct().getImageURL())
                    .placeholder(R.drawable.ic_launcher_background).into(h.img);
        } else {
            h.tvName.setText("Product #" + item.getProductID());
        }

        h.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) listener.onQuantityChanged(item, item.getQuantity() - 1);
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
        this.items = newItems;
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

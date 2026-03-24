package com.PRM392.prm392.service.Interface;

import com.PRM392.prm392.entity.CartItem;
import com.PRM392.prm392.entity.Carts;

import java.util.List;

public interface CartService {

    Carts getCartByUserId(int userId);

    List<CartItem> getCartItemsByUserId(int userId);

    void addCart(int userId, int productId, int quantity);

    void updateCartItem(int userId, int productId, int quantity);

    void removeCartItem(int userId, int productId);

    void clearCart(int userId);

}

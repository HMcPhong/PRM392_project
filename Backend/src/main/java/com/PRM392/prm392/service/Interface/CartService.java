package com.PRM392.prm392.service.Interface;

import com.PRM392.prm392.entity.Carts;

public interface CartService {

    Carts getCartByUserId(int userId);

    void addCart(int userId, int productId, int quantity);

    void updateCartItem(int userId, int productId, int quantity);

}

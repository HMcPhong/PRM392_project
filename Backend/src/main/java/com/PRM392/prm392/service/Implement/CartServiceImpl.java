package com.PRM392.prm392.service.Implement;

import com.PRM392.prm392.entity.CartItem;
import com.PRM392.prm392.entity.Carts;
import com.PRM392.prm392.entity.Product;
import com.PRM392.prm392.repository.CartItemRepository;
import com.PRM392.prm392.repository.CartRepository;
import com.PRM392.prm392.repository.ProductRepository;
import com.PRM392.prm392.service.Interface.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Carts getCartByUserId(int userId) {
        return cartRepository.findByCustomerId(userId);
    }

    @Override
    public void addCart(int userId, int productId, int quantity) {
        Carts carts = getCartByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(carts.getCart_id());
        boolean prductExist = false;
        for (CartItem item : cartItems) {
            if (item.getProductId() == productId) {
                item.setQuantity(item.getQuantity()+quantity);
                updateCartItemPrice(item);
                cartItemRepository.save(item);
                prductExist = true;
                break;
            }
        }

        if (!prductExist) {
            CartItem cartItem = new CartItem();
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartItem.setCartId(carts.getCart_id());
            updateCartItemPrice(cartItem);
            cartItemRepository.save(cartItem);
        }

        carts.setTotal_price(getCartTotal(userId));
        cartRepository.save(carts);
    }

    @Override
    public void updateCartItem(int userId, int productId, int quantity) {
        Carts carts = getCartByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(carts.getCart_id());

        boolean prductExist = false;
        for (CartItem item : cartItems) {
            if (item.getProductId() == productId) {
                item.setQuantity(quantity);
                updateCartItemPrice(item);
                cartItemRepository.save(item);
                prductExist = true;
                break;
            }
        }
        if (!prductExist) {
            throw new RuntimeException("CartItem not found");
        }

        carts.setTotal_price(getCartTotal(userId));
        cartRepository.save(carts);
    }



    /* Calculate */

    public void updateCartItemPrice(CartItem cartItem) {
        Product product = productRepository.findByProductID(cartItem.getProductId());
        cartItem.setPrice(product.getPrice()*cartItem.getQuantity());
    }

    public Double getCartTotal(int userId) {
        Carts carts = getCartByUserId(userId);
        List<CartItem> items = cartItemRepository.findByCartId(carts.getCart_id());
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getPrice();
        }
        return total;
    }


}

package com.PRM392.prm392.controller;

import com.PRM392.prm392.entity.CartItem;
import com.PRM392.prm392.entity.Carts;
import com.PRM392.prm392.response.ResponseData;
import com.PRM392.prm392.service.Interface.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get cart for user
    @GetMapping("/{userID}")
    public ResponseEntity<ResponseData<?>> getCart(@PathVariable("userID") int userID) {
        try {
            Carts carts = cartService.getCartByUserId(userID);
            if (carts == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new ResponseData<>("Success", carts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    // Get cart items for user
    @GetMapping("/{userID}/items")
    public ResponseEntity<ResponseData<?>> getCartItems(@PathVariable("userID") int userID) {
        try {
            List<CartItem> cartItems = cartService.getCartItemsByUserId(userID);
            return ResponseEntity.ok(new ResponseData<>("Success", cartItems));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    // Add an item to a cart
    @PostMapping("{userID}/add/{productID}")
    public ResponseEntity<ResponseData<?>> addCart(@PathVariable("userID") int userID, @PathVariable("productID") int productID, @RequestParam int quantity) {
        try {
            cartService.addCart(userID, productID, quantity);
            return ResponseEntity.ok(new ResponseData<>("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    // Update items' quantity
    @PutMapping("/{userID}/{productID}")
    public ResponseEntity<ResponseData<?>> updateCartItem(@PathVariable("userID") int userID, @PathVariable("productID") int productID, @RequestParam int quantity) {
        try {
            cartService.updateCartItem(userID, productID, quantity);
            return ResponseEntity.ok(new ResponseData<>("Success", null));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    // Remove item from cart
    @DeleteMapping("/{userID}/{productID}")
    public ResponseEntity<ResponseData<?>> deleteCartItem(@PathVariable("userID") int userID, @PathVariable("productID") int productID) {
        try {
            cartService.removeCartItem(userID, productID);
            return ResponseEntity.ok(new ResponseData<>("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }

    // Clear cart
    @DeleteMapping("/{userID}")
    public ResponseEntity<ResponseData<?>> clearCart(@PathVariable("userID") int userID) {
        try {
            cartService.clearCart(userID);
            return ResponseEntity.ok(new ResponseData<>("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }



}

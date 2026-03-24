package com.PRM392.prm392.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "CartItems")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemID")
    Integer cartItemId;

    @JoinColumn(name = "CartID")
    Integer cartId;

    @JoinColumn(name = "ProductID")
    Integer productId;

    @Column(name = "Quantity", nullable = false)
    Integer quantity;

    @Column(name = "Price", nullable = false)
    Double price;

}

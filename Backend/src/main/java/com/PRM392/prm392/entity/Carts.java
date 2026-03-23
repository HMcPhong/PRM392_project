package com.PRM392.prm392.entity;

import com.PRM392.prm392.enums.CartStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "carts")
public class Carts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    Integer cart_id;

    @JoinColumn(name = "user_id")
    Integer user_id;

    @Column(name = "total_price")
    Double total_price;

    @Column(name = "status")
    CartStatus cartStatus;
}

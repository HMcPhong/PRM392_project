package com.PRM392.prm392.entity;

import com.PRM392.prm392.enums.Status.CartStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Carts")
public class Carts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    Integer cartId;

    @JoinColumn(name = "UserID")
    Integer userId;

    @Column(name = "TotalPrice")
    Double total_price;

    @Column(name = "Status")
    CartStatus cartStatus;
}

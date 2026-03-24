package com.PRM392.prm392.entity;

import com.PRM392.prm392.enums.PaymentMethod;
import com.PRM392.prm392.enums.Status.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Integer orderId;

    @JoinColumn(name = "cart_id")
    Integer cartId;

    @JoinColumn(name = "user_id")
    Integer userId;

    @Column(name = "payment_method")
    PaymentMethod paymentMethod;

    @Column(name = "billing_address")
    String billingAddress;

    @Column(name = "order_status")
    OrderStatus orderStatus;

    @Column(name = "order_date")
    LocalDateTime orderDate;

}

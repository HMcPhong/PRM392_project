package com.PRM392.prm392.entity;

import com.PRM392.prm392.enums.PaymentMethod;
import com.PRM392.prm392.enums.Status.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Integer order_id;

    @JoinColumn(name = "cart_id")
    Integer cart_id;

    @JoinColumn(name = "user_id")
    Integer user_id;

    @Column(name = "payment_method")
    PaymentMethod payment_method;

    @Column(name = "billing_address")
    String billing_address;

    @Column(name = "order_status")
    OrderStatus order_status;

    @Column(name = "order_date")
    Date order_date;

}

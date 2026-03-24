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
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    Integer orderId;

    @JoinColumn(name = "CartID")
    Integer cartId;

    @JoinColumn(name = "UserID")
    Integer userId;

    @Column(name = "PaymentMethod")
    PaymentMethod paymentMethod;

    @Column(name = "BillingAddress")
    String billingAddress;

    @Column(name = "OrderStatus")
    OrderStatus orderStatus;

    @Column(name = "OrderDate")
    LocalDateTime orderDate;

}

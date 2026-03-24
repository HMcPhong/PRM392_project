package com.PRM392.prm392.entity;

import com.PRM392.prm392.enums.Status.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    Integer paymentId;

    @JoinColumn(name = "order_id")
    Integer orderId;

    @Column(name = "amount")
    Double amount;

    @Column(name = "payment_date")
    LocalDateTime paymentDate;

    @Column(name = "payment_status")
    PaymentStatus paymentStatus;

}

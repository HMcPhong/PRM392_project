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
@Table(name = "Payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    Integer paymentId;

    @JoinColumn(name = "OrderID")
    Integer orderId;

    @Column(name = "Amount")
    Double amount;

    @Column(name = "PaymentDate")
    LocalDateTime paymentDate;

    @Column(name = "PaymentStatus")
    PaymentStatus paymentStatus;

}

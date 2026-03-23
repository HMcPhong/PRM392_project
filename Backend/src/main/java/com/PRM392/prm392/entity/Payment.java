package com.PRM392.prm392.entity;

import com.PRM392.prm392.enums.Status.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    Integer payment_id;

    @JoinColumn(name = "order_id")
    Integer order_id;

    @Column(name = "amount")
    Double amount;

    @Column(name = "payment_date")
    Date payment_date;

    @Column(name = "payment_status")
    PaymentStatus payment_status;

}

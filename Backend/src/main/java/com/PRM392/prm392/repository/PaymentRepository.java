package com.PRM392.prm392.repository;

import com.PRM392.prm392.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findPaymentByOrderId(Integer orderId);
}

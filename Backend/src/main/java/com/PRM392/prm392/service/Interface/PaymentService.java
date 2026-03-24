package com.PRM392.prm392.service.Interface;

import com.PRM392.prm392.entity.Order;

public interface PaymentService {

    void createPayment(Order order);

    String createPaymentUrl(Order order);

    void processPayment(int orderId, boolean success);

}

package com.PRM392.prm392.service.Implement;

import com.PRM392.prm392.entity.Carts;
import com.PRM392.prm392.entity.Order;
import com.PRM392.prm392.entity.Payment;
import com.PRM392.prm392.enums.Status.OrderStatus;
import com.PRM392.prm392.enums.Status.PaymentStatus;
import com.PRM392.prm392.repository.CartRepository;
import com.PRM392.prm392.repository.OrderRepository;
import com.PRM392.prm392.repository.PaymentRepository;
import com.PRM392.prm392.service.Interface.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, CartRepository cartRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void createPayment(Order order) {

        Payment payment = new Payment();
        Carts carts = cartRepository.findCartsByUserId(order.getUserId());

        payment.setOrderId(order.getOrderId());
        payment.setAmount(carts.getTotal_price());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);
    }

    @Override
    public String createPaymentUrl(Order order) {
        return "http://localhost:8080/api/payments/" + order.getOrderId();
    }

    @Override
    public void processPayment(int orderId, boolean success) {
        Payment payment = paymentRepository.findPaymentByOrderId(orderId);
        Order order = orderRepository.findByOrderId(orderId);

        if (success) {
            payment.setPaymentStatus(PaymentStatus.COMPLETE);
            order.setOrderStatus(OrderStatus.DELIVERING);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            order.setOrderStatus(OrderStatus.FAILED);
        }

        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);
        orderRepository.save(order);
    }


}

package com.PRM392.prm392.controller;

import com.PRM392.prm392.entity.Order;
import com.PRM392.prm392.request.create.Order.OrderCreateRequest;
import com.PRM392.prm392.service.Interface.OrderService;
import com.PRM392.prm392.service.Interface.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest request) {
        try {
            Order order = orderService.createOrder(request.getCartId(), request.getPaymentMethod(), request.getBillingAddress());

            paymentService.createPayment(order);

            String paymentUrl = paymentService.createPaymentUrl(order);

            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

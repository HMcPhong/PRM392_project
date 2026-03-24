package com.PRM392.prm392.controller;

import com.PRM392.prm392.request.create.Payment.PaymentCreateRequest;
import com.PRM392.prm392.response.ResponseData;
import com.PRM392.prm392.service.Interface.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/mock")
    public ResponseEntity<ResponseData<?>> mockPayment(@RequestBody PaymentCreateRequest request){
        try {
            int orderId = request.getOrderId();
            boolean success = true;

            paymentService.processPayment(orderId, success);

            return ResponseEntity.ok(new ResponseData<>("Payment successful", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData<>(e.getMessage(), null));
        }
    }



}

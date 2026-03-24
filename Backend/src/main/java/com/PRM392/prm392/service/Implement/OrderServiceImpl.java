package com.PRM392.prm392.service.Implement;

import com.PRM392.prm392.entity.Carts;
import com.PRM392.prm392.entity.Order;
import com.PRM392.prm392.enums.PaymentMethod;
import com.PRM392.prm392.enums.Status.OrderStatus;
import com.PRM392.prm392.repository.CartRepository;
import com.PRM392.prm392.repository.OrderRepository;
import com.PRM392.prm392.service.Interface.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(CartRepository cartRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public Order createOrder(int cartId, PaymentMethod paymentMethod, String billingAddress) {

        Carts carts = cartRepository.findCartsByCartId(cartId);

        Order order = new Order();
        order.setCartId(cartId);
        order.setUserId(carts.getUserId());
        order.setPaymentMethod(paymentMethod);
        order.setBillingAddress(billingAddress);

        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);

        return order;
    }
}

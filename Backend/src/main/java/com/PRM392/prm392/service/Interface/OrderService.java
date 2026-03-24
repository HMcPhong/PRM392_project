package com.PRM392.prm392.service.Interface;

import com.PRM392.prm392.entity.Order;
import com.PRM392.prm392.enums.PaymentMethod;

public interface OrderService {

    Order createOrder(int cartId, PaymentMethod paymentMethod, String billingAddress);

}

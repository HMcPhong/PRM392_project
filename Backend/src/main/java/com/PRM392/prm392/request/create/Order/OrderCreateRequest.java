package com.PRM392.prm392.request.create.Order;

import com.PRM392.prm392.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreateRequest {

    Integer cartId;

    Integer userId;

    PaymentMethod paymentMethod;

    @NotBlank
    String billingAddress;

}

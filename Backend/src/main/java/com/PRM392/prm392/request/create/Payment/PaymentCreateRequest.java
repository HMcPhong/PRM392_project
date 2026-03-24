package com.PRM392.prm392.request.create.Payment;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentCreateRequest {

    Integer orderId;

    Double amount;

}

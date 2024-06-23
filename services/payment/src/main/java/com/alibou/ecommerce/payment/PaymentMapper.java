package com.alibou.ecommerce.payment;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request) {
        if (request == null) {
            return null;
        }

        return Payment.builder()
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .orderId(request.orderId())
                .build();
    }
}

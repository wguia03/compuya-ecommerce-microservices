package com.compuya.ecommerce.orderline;

import java.math.BigDecimal;

public record OrderLineRequest(
        Integer orderId,
        Integer productId,
        double quantity,
        BigDecimal totalPrice
) {
}

package com.compuya.order.product;

import java.math.BigDecimal;

public record PurchaseResponse(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}

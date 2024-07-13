package com.compuya.order.orderline;

import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}

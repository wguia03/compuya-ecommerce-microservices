package com.compuya.ecommerce.orderline;

import com.compuya.ecommerce.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;
    private final OrderRepository orderRepository;

    public void saveOrderLine(OrderLineRequest request) {
        var orderLine = OrderLine.builder()
                .order(orderRepository.findById(request.orderId()).orElseThrow())
                .productId(request.productId())
                .quantity(request.quantity())
                .totalPrice(request.totalPrice())
                .build();
        repository.save(orderLine);
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}

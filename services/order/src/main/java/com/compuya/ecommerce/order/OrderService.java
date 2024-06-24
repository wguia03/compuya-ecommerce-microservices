package com.compuya.ecommerce.order;

import com.compuya.ecommerce.user.UserClient;
import com.compuya.ecommerce.exception.BusinessException;
import com.compuya.ecommerce.orderline.OrderLineRequest;
import com.compuya.ecommerce.orderline.OrderLineService;
import com.compuya.ecommerce.payment.PaymentClient;
import com.compuya.ecommerce.payment.PaymentRequest;
import com.compuya.ecommerce.product.ProductClient;
import com.compuya.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final UserClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        var customer = this.customerClient.findById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        var purchasedProducts = productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (PurchaseRequest purchaseRequest : request.products()) {
            var product = purchasedProducts.stream()
                .filter(p -> p.productId().equals(purchaseRequest.productId()))
                .findAny()
                .orElseThrow(() -> new BusinessException("Product not found in the purchased products list"));

            BigDecimal productPrice = product.price();
            BigDecimal orderLinePrice = productPrice.multiply(BigDecimal.valueOf(purchaseRequest.quantity()));
            totalAmount = totalAmount.add(orderLinePrice);
            orderLineService.saveOrderLine(
                new OrderLineRequest(
                        order.getId(),
                        purchaseRequest.productId(),
                        purchaseRequest.quantity(),
                        orderLinePrice
                )
            );
        }

        order.setTotalAmount(totalAmount);
        this.repository.save(order);

        var paymentRequest = new PaymentRequest(
                totalAmount,
                request.paymentMethod(),
                order.getId()
        );
        paymentClient.requestOrderPayment(paymentRequest);

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}

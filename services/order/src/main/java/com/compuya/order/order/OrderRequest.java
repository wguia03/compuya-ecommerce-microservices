package com.compuya.order.order;

import com.compuya.order.product.PurchaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonInclude(Include.NON_EMPTY)
public record OrderRequest(
    Integer id,
    @NotNull(message = "Payment method should be precised")
    PaymentMethod paymentMethod,
    @NotNull(message = "Customer should be present")
    Integer customerId,
    @NotEmpty(message = "You should at least purchase one product")
    List<PurchaseRequest> products
) {

}

package com.compuya.payment.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository repository;
  private final PaymentMapper mapper;

  public Integer createPayment(PaymentRequest request) {
    var payment = this.repository.save(this.mapper.toPayment(request));
    return payment.getId();
  }

  public Payment getPaymentByOrderId(Integer orderId) {
    return this.repository.findByOrderId(orderId);
  }
}

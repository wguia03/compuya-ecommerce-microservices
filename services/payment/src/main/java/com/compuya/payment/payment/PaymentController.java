package com.compuya.payment.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService service;

  @PostMapping
  public ResponseEntity<Integer> createPayment(@RequestBody @Valid PaymentRequest request) {
    return ResponseEntity.ok(this.service.createPayment(request));
  }

  @GetMapping("/byOrderId/{orderId}")
  public ResponseEntity<Payment> getPayment(@PathVariable Integer orderId) {
    return ResponseEntity.ok(this.service.getPaymentByOrderId(orderId));
  }
}

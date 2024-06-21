package com.alibou.ecommerce.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "product-service",
    url = "${application.config.payment-url}"
)
public interface NotificationClient {

  @PostMapping
  Integer requestOrderPayment(@RequestBody NotificationRequest request);
}

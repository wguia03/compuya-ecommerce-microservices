package com.compuya.ecommerce.user;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody @Valid UserRequest userRequest) {
    return new ResponseEntity<>(service.register(userRequest), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody @Valid LoginRequest userRequest) {
    return new ResponseEntity<>(service.login(userRequest), HttpStatus.OK);
  }

  @PutMapping("/auth")
  public ResponseEntity<Void> update(
      @RequestBody @Valid UserUpdateRequest request
  ) {
    this.service.updateCustomer(request);
    return ResponseEntity.accepted().build();
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> findAll() {
    return ResponseEntity.ok(this.service.findAllCustomers());
  }

  @GetMapping("/exists/{customer-id}")
  public ResponseEntity<Boolean> existsById(
      @PathVariable("customer-id") Integer customerId
  ) {
    return ResponseEntity.ok(this.service.existsById(customerId));
  }

  @GetMapping("/{customer-id}")
  public ResponseEntity<UserResponse> findById(
      @PathVariable("customer-id") Integer customerId
  ) {
    return ResponseEntity.ok(this.service.findById(customerId));
  }

  @DeleteMapping("/auth/{customer-id}")
  public ResponseEntity<Void> delete(
      @PathVariable("customer-id") Integer customerId
  ) {
    this.service.deleteCustomer(customerId);
    return ResponseEntity.accepted().build();
  }
}

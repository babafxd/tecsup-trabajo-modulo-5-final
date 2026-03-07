package com.tecsup.app.micro.payment.domain.repository;

import com.tecsup.app.micro.payment.domain.model.Payment;

import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(Long id);
    Payment save(Payment payment);
}

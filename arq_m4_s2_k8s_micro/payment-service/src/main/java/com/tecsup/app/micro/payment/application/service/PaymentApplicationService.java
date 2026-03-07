package com.tecsup.app.micro.payment.application.service;

import com.tecsup.app.micro.payment.application.usecase.*;
import com.tecsup.app.micro.payment.domain.model.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentApplicationService {

    private final GetPaymentByIdUseCase getPaymentByIdUseCase;
    private final CreatePaymentUseCase createPaymentUseCase;

    @Transactional
    public Payment payment(Payment payment) {
        return createPaymentUseCase.execute(payment);
    }

    @Transactional(readOnly = true)
    public Payment getPayment(Long id) {
        return getPaymentByIdUseCase.execute(id);
    }
}

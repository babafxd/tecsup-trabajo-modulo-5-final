package com.tecsup.app.micro.payment.application.usecase;

import com.tecsup.app.micro.payment.domain.event.PaymentApprovedEvent;
import com.tecsup.app.micro.payment.domain.event.PaymentRejectedEvent;
import com.tecsup.app.micro.payment.domain.exception.InvalidPaymentDataException;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.repository.PaymentRepository;
import com.tecsup.app.micro.payment.infrastructure.client.OrderClient;
import com.tecsup.app.micro.payment.shared.infraestructure.event.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;
    private final KafkaEventPublisher eventPublisher;

    public Payment execute(Payment payment) {

        if (!payment.isValidEnrollmentId()) {
            throw new InvalidPaymentDataException("Invalid payment data. OrderId are required.");
        }

        if (!payment.isValidAmount()) {
            throw new InvalidPaymentDataException("Invalid payment data. Ammount greater than zero.");
        }

        /*EnrollmentDTO user = orderClient.getEnrollmentById(payment.getEnrollmentId())
                .orElseThrow(() -> new EnrollmentNotFoundException("No se puede obtener el enrollment con Id:" + payment.getEnrollmentId()));*/

        payment.setStatus(Payment.PaymentStatus.APPROVED);
        Payment saved = paymentRepository.save(payment);
        log.info("Payment created successfully with id: {}", saved.getId());

        if (payment.isPaymentApproved()) {
            eventPublisher.publish(
                    new PaymentApprovedEvent(
                            saved.getId(),
                            saved.getEnrollmentId(),
                            saved.getAmount(),
                            saved.getStatus().toString(),
                            saved.getPaidAt()
                    )
            );

        } else {
            eventPublisher.publish(
                    new PaymentRejectedEvent(
                            saved.getId(),
                            saved.getEnrollmentId(),
                            saved.getAmount(),
                            saved.getStatus().toString(),
                            saved.getPaidAt()
                    )
            );
        }


        return saved;
    }


}

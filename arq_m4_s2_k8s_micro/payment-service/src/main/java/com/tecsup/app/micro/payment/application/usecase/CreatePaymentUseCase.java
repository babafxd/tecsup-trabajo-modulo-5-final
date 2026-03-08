package com.tecsup.app.micro.payment.application.usecase;

import com.tecsup.app.micro.payment.domain.event.PaymentApprovedEvent;
import com.tecsup.app.micro.payment.domain.event.PaymentRejectedEvent;
import com.tecsup.app.micro.payment.domain.exception.InvalidPaymentDataException;
import com.tecsup.app.micro.payment.domain.exception.OrderNotFoundException;
import com.tecsup.app.micro.payment.domain.model.Order;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.repository.PaymentRepository;
import com.tecsup.app.micro.payment.infrastructure.client.OrderClient;
import com.tecsup.app.micro.payment.shared.infraestructure.event.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;
    private final KafkaEventPublisher eventPublisher;

    public Payment execute(Payment payment, String jwtToken) {

        if (!payment.isValidOrderId()) {
            throw new InvalidPaymentDataException("Invalid payment data. OrderId are required.");
        }

        if (!payment.isValidAmount()) {
            throw new InvalidPaymentDataException("Invalid payment data. Ammount greater than zero.");
        }

        var orderInfo = orderClient.getOrderById(payment.getOrderId(), jwtToken)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for Id:" + payment.getOrderId()));

        if (orderInfo.getStatus().equals(Order.OrderStatus.CONFIRMED.name()) ||
                orderInfo.getStatus().equals(Order.OrderStatus.SHIPPED.name()) ||
                orderInfo.getStatus().equals(Order.OrderStatus.DELIVERED.name()
                )) {
            throw new InvalidPaymentDataException("The order has already been paid for.");
        }

        if (orderInfo.getStatus().equals(Order.OrderStatus.CANCELLED.name())) {
            throw new InvalidPaymentDataException("The order was cancelled.");
        }


        if (Objects.equals(orderInfo.getTotalAmount(), payment.getAmount())) {
            payment.setStatus(Payment.PaymentStatus.APPROVED);
        } else {
            payment.setStatus(Payment.PaymentStatus.REJECTED);
        }


        Payment saved = paymentRepository.save(payment);
        log.info("Payment created successfully with id: {}", saved.getId());

        if (saved.getStatus() == Payment.PaymentStatus.APPROVED) {
            eventPublisher.publish(
                    new PaymentApprovedEvent(
                            saved.getId(),
                            saved.getOrderId(),
                            saved.getAmount(),
                            saved.getStatus().toString(),
                            saved.getPaidAt()
                    )
            );

        } else {
            eventPublisher.publish(
                    new PaymentRejectedEvent(
                            saved.getId(),
                            saved.getOrderId(),
                            saved.getAmount(),
                            saved.getStatus().toString(),
                            saved.getPaidAt()
                    )
            );
        }


        return saved;
    }


}

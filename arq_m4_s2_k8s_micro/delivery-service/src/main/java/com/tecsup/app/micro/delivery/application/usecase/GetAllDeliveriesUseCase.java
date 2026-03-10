package com.tecsup.app.micro.delivery.application.usecase;

import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllDeliveriesUseCase {

    private final DeliveryRepository deliveryRepository;

    public List<Delivery> execute() {
        log.debug("Executing GetAllDeliveriesUseCase");
        return deliveryRepository.findAll();
    }

}

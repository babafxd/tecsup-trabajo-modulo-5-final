package com.tecsup.app.micro.order.application.service;

import com.tecsup.app.micro.order.application.usecase.CreateOrderUseCase;
import com.tecsup.app.micro.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderApplicationService {


    private final CreateOrderUseCase createOrderUseCase;

    @Transactional
    public Order createOrder(Order order) {
        return createOrderUseCase.execute(order);
    }


}

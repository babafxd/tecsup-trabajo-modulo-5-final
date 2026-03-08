package com.tecsup.app.micro.order.application.service;

import com.tecsup.app.micro.order.application.usecase.CreateOrderUseCase;
import com.tecsup.app.micro.order.application.usecase.GetOrderByIdUseCase;
import com.tecsup.app.micro.order.application.usecase.UpdateOrderUseCase;
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
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;

    @Transactional
    public Order createOrder(Order order, String jwtToken) {
        return createOrderUseCase.execute(order, jwtToken);
    }


    @Transactional(readOnly = true)
    public Order getOrderById(Long id, String jwtToken) {
        return getOrderByIdUseCase.execute(id, jwtToken);
    }

    @Transactional
    public Order updateOrder(Long id, Order.OrderStatus status) {
        return updateOrderUseCase.execute(id, status);
    }


}

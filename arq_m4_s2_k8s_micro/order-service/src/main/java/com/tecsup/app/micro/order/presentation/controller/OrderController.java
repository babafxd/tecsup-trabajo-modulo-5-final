package com.tecsup.app.micro.order.presentation.controller;

import com.tecsup.app.micro.order.OrderServiceApplication;
import com.tecsup.app.micro.order.application.service.OrderApplicationService;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.presentation.dto.CreateOrderRequest;
import com.tecsup.app.micro.order.presentation.dto.OrderResponse;
import com.tecsup.app.micro.order.presentation.mapper.OrderDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    private final OrderDtoMapper orderDtoMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("REST request to create order for userid: {}", request.getUserId());
        Order order = orderDtoMapper.toDomain(request);
        Order createdOrder = orderApplicationService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderDtoMapper.toResponse(createdOrder));
    }

}

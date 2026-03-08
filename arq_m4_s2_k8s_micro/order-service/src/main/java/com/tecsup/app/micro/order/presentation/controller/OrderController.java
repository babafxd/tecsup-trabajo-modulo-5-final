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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    private final OrderDtoMapper orderDtoMapper;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponse> createOrder(
                @Valid @RequestBody CreateOrderRequest request,
                @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for createOrder");
        }
        log.info("jwtToken extracted for createOrder: {}", jwtToken != null);

        log.info("REST request to create order for userid: {}", request.getUserId());
        Order order = orderDtoMapper.toDomain(request);
        Order createdOrder = orderApplicationService.createOrder(order, jwtToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderDtoMapper.toResponse(createdOrder));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id,
                                                          @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("REST request to get order by id: {}", id);

        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for getOrderById");
        }

        log.info("jwtToken extracted for getOrderById: {}", jwtToken != null);

        Order order = orderApplicationService.getOrderById(id, jwtToken);
        return ResponseEntity.ok(orderDtoMapper.toResponse(order));
    }

}

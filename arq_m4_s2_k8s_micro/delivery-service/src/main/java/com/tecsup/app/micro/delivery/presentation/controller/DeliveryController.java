package com.tecsup.app.micro.delivery.presentation.controller;

import com.tecsup.app.micro.delivery.application.service.DeliveryApplicationService;
import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.presentation.dto.CreateDeliveryRequest;
import com.tecsup.app.micro.delivery.presentation.dto.UpdateDeliveryRequest;
import com.tecsup.app.micro.delivery.presentation.dto.DeliveryResponse;
import com.tecsup.app.micro.delivery.presentation.mapper.DeliveryDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {
    
    private final DeliveryApplicationService deliveryApplicationService;
    private final DeliveryDtoMapper deliveryDtoMapper;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DeliveryResponse> createDelivery(
            @Valid @RequestBody CreateDeliveryRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for createDelivery");
        }
        log.info("jwtToken extracted for createDelivery: {}", jwtToken != null);

        //log.info("REST request to create order for userid: {}", request.get());
        Delivery order = deliveryDtoMapper.toDomain(request);
        Delivery createdOrder = deliveryApplicationService.createDelivery(order, jwtToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deliveryDtoMapper.toResponse(createdOrder));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable Long id,
                                                      @RequestHeader(value = "Authorization", required = false) String authHeader) {
        log.info("REST request to get delivery by id: {}", id);

        // Extraer JWT del header
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        } else {
            log.warn("No Authorization header with Bearer token found for getDeliveryById");
        }

        log.info("jwtToken extracted for getDeliveryById: {}", jwtToken != null);

        Delivery order = deliveryApplicationService.getDeliveryById(id);
        return ResponseEntity.ok(deliveryDtoMapper.toResponse(order));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Delivery Service running with Clean Architecture!");
    }
}

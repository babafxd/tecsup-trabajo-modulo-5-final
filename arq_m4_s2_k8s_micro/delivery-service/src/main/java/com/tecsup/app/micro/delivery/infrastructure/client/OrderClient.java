package com.tecsup.app.micro.delivery.infrastructure.client;

import com.tecsup.app.micro.delivery.domain.model.Order;
import com.tecsup.app.micro.delivery.infrastructure.client.dto.OrderDto;
import com.tecsup.app.micro.delivery.infrastructure.client.mapper.OrderDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderClient {

    private final RestTemplate restTemplate;
    private final OrderDtoMapper orderDtoMapper;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    public Optional<Order> getOrderById(Long orderId, String jwtToken) {

        log.info("Calling Order Service (PostgreSQL orderdb) to get order with id: {}", orderId);

        String url = this.orderServiceUrl + "/api/orders/" + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (jwtToken != null && !jwtToken.isEmpty()) {
            headers.setBearerAuth(jwtToken);
        } else {
            log.warn("No JWT token provided for User Service call");
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);


        try {
            ResponseEntity<OrderDto> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, OrderDto.class
            );
            log.info("Order retrieved successfully from order-service: {}", response.getBody());
            Order domainOrder = orderDtoMapper.toDomain(response.getBody());
            return Optional.of(domainOrder);

        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error calling Order Service: {}", e.getMessage());
            throw new RuntimeException("Error calling Order Service: " + e.getMessage());
        }

    }

}

package com.tecsup.app.micro.order.infrastructure.client;

import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.infrastructure.client.dto.ProductDto;
import com.tecsup.app.micro.order.infrastructure.client.mapper.ProductDtoMapper;
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
public class CatalogClient {

    private final RestTemplate restTemplate;
    private final ProductDtoMapper productDtoMapper;

    @Value("${catalog.service.url}")
    private String catalogServiceUrl;

    public Optional<Product> getProductById(Long productId, String jwtToken) {
        log.info("Calling Catalog Service (PostgreSQL productdb) to get product with id: {}", productId);

        String url = this.catalogServiceUrl + "/api/products/" + productId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (jwtToken != null && !jwtToken.isEmpty()) {
            headers.setBearerAuth(jwtToken);
        } else {
            log.warn("No JWT token provided for User Service call");
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);


        try {
            ResponseEntity<ProductDto> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, ProductDto.class
            );
            log.info("Catalog retrieved successfully from catalog-service: {}", response.getBody());
            Product domainProduct = productDtoMapper.toDomain(response.getBody());
            return Optional.of(domainProduct);

        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error calling Product Service: {}", e.getMessage());
            throw new RuntimeException("Error calling Product Service: " + e.getMessage());
        }

    }


}

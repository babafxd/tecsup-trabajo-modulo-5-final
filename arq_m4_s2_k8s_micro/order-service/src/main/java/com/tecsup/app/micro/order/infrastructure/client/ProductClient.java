package com.tecsup.app.micro.order.infrastructure.client;

import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.infrastructure.client.dto.ProductDto;
import com.tecsup.app.micro.order.infrastructure.client.mapper.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient {

    private final RestTemplate restTemplate;
    private final ProductDtoMapper productDtoMapper;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public Optional<Product> getProductById(Long productId) {
        log.info("Calling Product Service (PostgreSQL productdb) to get product with id: {}", productId);

        String url = this.productServiceUrl + "/api/products/" + productId;

        try {
            ProductDto product = restTemplate.getForObject(url, ProductDto.class);
            if (product == null) {
                log.warn("Product {} not found ", productId);
            }

            Product domainProduct = productDtoMapper.toDomain(product);
            return Optional.of(domainProduct);

        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error calling Product Service: {}", e.getMessage());
            throw new RuntimeException("Error calling Product Service: " + e.getMessage());
        }
    }


}

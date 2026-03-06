package com.tecsup.app.micro.order.presentation.mapper;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderItem;
import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.presentation.dto.CreateOrderRequest;
import com.tecsup.app.micro.order.presentation.dto.OrderItemResponse;
import com.tecsup.app.micro.order.presentation.dto.OrderResponse;
import com.tecsup.app.micro.order.presentation.dto.ProductDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderDtoMapper.class})
public interface OrderDtoMapper {


    Order toDomain(CreateOrderRequest request);

    OrderResponse toResponse(Order product);

    List<OrderResponse> toResponseList(List<Order> orders);

    OrderItemResponse toItemResponse(OrderItem item);

    ProductDetailResponse toProductDetailResponse(Product product);
}

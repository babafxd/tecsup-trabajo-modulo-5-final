package com.tecsup.app.micro.order.domain.repository;

import com.tecsup.app.micro.order.domain.model.Order;

public interface OrderRepository {

    Order save(Order order);

}

package com.example.demo.service;

import com.example.demo.model.Order;

public interface OrderService {

    Iterable<Order> getAllOrders();

    Order create(Order order);

    void update(Order order);
}

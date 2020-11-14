package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.OrderProduct;
import com.example.demo.model.OrderProductPK;

public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK> {
}

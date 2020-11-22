package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Order;
import com.example.demo.model.OrderProduct;
//import com.example.demo.model.OrderProductPK;
import com.example.demo.model.Product;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
	List<OrderProduct> findAllByorder(Order order);
	List<OrderProduct> findAllByproduct(Product product);
}

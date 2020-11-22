package com.example.demo.model;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.querydsl.QPageRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrderProduct {

//    @EmbeddedId
//    @JsonIgnore
//    private OrderProductPK pk;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    @Column(nullable = false) private Integer quantity;

    public OrderProduct() {
        super();
    }

    public OrderProduct(Order order, Product product, Integer quantity) {
//        pk = new OrderProductPK();
//        pk.setOrder(order);
//        pk.setProduct(product);
//        this.quantity = quantity;
    	this.order=order;
    	this.product=product;
    	this.quantity=quantity;
    }

//    @Transient
//    public Product getProduct() {
//        return this.pk.getProduct();
//    }

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }

//    public OrderProductPK getPk() {
//        return pk;
//    }
//
//    public void setPk(OrderProductPK pk) {
//        this.pk = pk;
//    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

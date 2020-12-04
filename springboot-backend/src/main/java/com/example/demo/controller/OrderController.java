package com.example.demo.controller;


import com.example.demo.report.OrderReportService;
import com.example.demo.report.ProductReportService;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.Exceptions.ElementNotFound;
import com.example.demo.dto.OrderProductDto;
import com.example.demo.model.Order;
import com.example.demo.model.OrderProduct;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderProductRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderProductService;
import com.example.demo.service.OrderService;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderProductService orderProductService;
    @Autowired
    OrderReportService reportService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderProductRepository orderProductRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
    
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long id) throws ElementNotFound {
    	Optional<Order> optionalOrder = orderRepository.findById(id);
    	if(optionalOrder.isEmpty()) {
    		throw new ElementNotFound("Product with " + id+ " id doesn't exist!");
    	}
		Order order = optionalOrder.get();
		
		List<OrderProduct> list = orderProductRepository.findAllByorder(order);
        for (OrderProduct orderProduct : list) {
        	orderProductRepository.delete(orderProduct);
		}
		orderRepository.delete(order);
		return new ResponseEntity<Object>(new Status("Success"),HttpStatus.ACCEPTED);
    }

    @PostMapping("orders")
    public ResponseEntity<Order> create(@RequestBody OrderForm form) {
        List<OrderProductDto> formDtos = form.getProductOrders();
        Order order = new Order();
        order = this.orderService.create(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto dto : formDtos) {
            orderProducts.add(orderProductService.create(new OrderProduct(order, productRepository.findById(dto
              .getProduct()
              .getId()).get(), dto.getQuantity())));
        }
        
        
        order.setOrderProducts(orderProducts);
        order.getTotalOrderPrice();
        this.orderService.update(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order/report")
    public ResponseEntity<Object> generateReport() throws FileNotFoundException, JRException {
        try {
            CompletableFuture<Void> pdfReprot = reportService.exportPDFReport();
            CompletableFuture<Void> htmlReport = reportService.exportHTMLReport();
            CompletableFuture.allOf(pdfReprot, htmlReport);
            logger.info("The reports was created");
        } catch (FileNotFoundException | JRException e) {
            logger.error("The report was not created");
            e.printStackTrace();
            return new ResponseEntity<Object>(new Status("Error"), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<Object>(new Status("Success"), HttpStatus.ACCEPTED);
    }

    public static class OrderForm {

        private List<OrderProductDto> productOrders;

        public List<OrderProductDto> getProductOrders() {
            return productOrders;
        }

        public void setProductOrders(List<OrderProductDto> productOrders) {
            this.productOrders = productOrders;
        }
    }
}

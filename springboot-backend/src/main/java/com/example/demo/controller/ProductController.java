package com.example.demo.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exceptions.ElementNotFound;
import com.example.demo.Exceptions.ValidationException;
import com.example.demo.model.Customer;
import com.example.demo.model.Employee;
import com.example.demo.model.OrderProduct;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderProductRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;




@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProductController {
class FieldValidator {
		
		private ArrayList<String> wrongFields;
		public FieldValidator(Product product) {
			ArrayList<String> wrongFields = new ArrayList<String>();
			if(product.getName() == "" || product.getName() == null) {
				wrongFields.add("name");
			}
			
			if(product.getPrice()==null) {
				wrongFields.add("price");
			}
			this.wrongFields = wrongFields;
		}
		
		public void setWrongFields(ArrayList<String> wrongFields) {
			this.wrongFields = wrongFields;
		}
		
		public ArrayList<String> getWrongFields() {
			return wrongFields;
		}
	}

    @Autowired
    private ProductRepository productRepository;
    @Autowired 
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/products")
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Long id) throws ElementNotFound {
    	Optional<Product> optionalProduct = productRepository.findById(id);
    	if(optionalProduct.isEmpty()) {
    		throw new ElementNotFound("Product with " + id+ " id doesn't exist!");
    	}
		Product product = optionalProduct.get();
		List<OrderProduct> orderProducts = orderProductRepository.findAllByproduct(product);
		
		if(!orderProducts.isEmpty()) {
			for (OrderProduct orderProduct : orderProducts) {
				orderProductRepository.delete(orderProduct);
			}
			//List<Order> orders = orderRepository.findAllById(ids)
		}
		productRepository.delete(product);
		Map<String, Boolean> responseMap = new HashMap<>();
		responseMap.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(responseMap);
    }
    @PostMapping("/products")
    public ResponseEntity<Object> addProduct(@RequestBody Product product) throws ValidationException {
    	FieldValidator fieldValidator = new FieldValidator(product);
    	
    	if(!fieldValidator.getWrongFields().isEmpty()) {
    		throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
    	}
    	productRepository.save(product);
    	return new ResponseEntity<Object>(new Status("Success"), HttpStatus.ACCEPTED);
    }
}

package com.example.demo.controller;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.example.demo.model.Customer;
import com.example.demo.report.ProductReportService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.EmployeeService;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Exceptions.ElementNotFound;
import com.example.demo.Exceptions.ValidationException;
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

			if(product.getCount() < 0){
				wrongFields.add("count");
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

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductReportService reportService;
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
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		if(productOptional.isEmpty()) {
			logger.error("The customer was not find");
			throw new ElementNotFound("Customer with " + id + " id doesn't exist!");
		}
		logger.info(productOptional.get().getName());
		return ResponseEntity.ok(productOptional.get());
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
    	logger.info("103");
    	if(!fieldValidator.getWrongFields().isEmpty()) {
    		throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
    	}
    	try{
			productRepository.save(product);
		} catch (Exception e){
    		e.printStackTrace();
		}

    	return new ResponseEntity<Object>(new Status("Success"), HttpStatus.ACCEPTED);
    }
	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateCustomer(@PathVariable Long id,@RequestBody Product newProduct){

		Optional<Product> productOptional = productRepository.findById(id);
		try{
			if(productOptional.isEmpty()) {
				throw new ElementNotFound("Customer with " + id + " id doesn't exist!");
			}
			FieldValidator fieldValidator = new FieldValidator(newProduct);
			if(!fieldValidator.getWrongFields().isEmpty()) {
				throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
			}
		} catch (ElementNotFound e) {
			logger.error("The customer was not find");
			e.printStackTrace();
		}
		Product product = productOptional.get();
		product.setCount(newProduct.getCount());
		product.setPrice(newProduct.getPrice());
		product.setName(newProduct.getName());

		productRepository.save(product);
		logger.info("The product was updated");
		return new ResponseEntity<Object>(new Status("Success"), HttpStatus.ACCEPTED);
	}

	@GetMapping("/product/report")
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
}

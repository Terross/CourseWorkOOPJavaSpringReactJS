package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.AsyncConfiguration;
import com.example.demo.report.CustomerReportService;
import com.example.demo.service.AsyncService;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.Exceptions.ElementNotFound;
import com.example.demo.Exceptions.ValidationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	class FieldValidator {
		
		private ArrayList<String> wrongFields;
		public FieldValidator(Customer customer) {
			ArrayList<String> wrongFields = new ArrayList<String>();
			if(customer.getAdress()=="" || customer.getAdress()==null) {
				wrongFields.add("adress");
			}
			if(customer.getFirstName()==null || customer.getFirstName().isEmpty()) {
				wrongFields.add("firstName");
			}
			if(customer.getSecondName()==null || customer.getSecondName().isEmpty()) {
				wrongFields.add("secondName");
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
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerReportService customerReportService;
	@Autowired
	private AsyncService asyncService;

	@GetMapping("/customer")
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}

	@Async
	@PostMapping("/customer")
	public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) throws ValidationException {
		logger.info("Customer was added");
		FieldValidator fieldValidator = new FieldValidator(customer);
		if(!fieldValidator.getWrongFields().isEmpty()) {
			throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
		}
		customerRepository.save(customer);
		return new ResponseEntity<Object>(new Status("Success"), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) throws ElementNotFound{
		Optional<Customer> customerOptional = customerRepository.findById(id);
		if(customerOptional.isEmpty()) {
			throw new ElementNotFound("Customer with " + id + " id doesn't exist!");
		}
		Customer customer = customerOptional.get();		
		return ResponseEntity.ok(customer);
	}
	
	@PutMapping("/customer/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable Integer id,@RequestBody Customer reCustomer){
		Optional<Customer> customerOptional = customerRepository.findById(id);
		if(customerOptional.isEmpty()) {
			throw new ElementNotFound("Customer with " + id + " id doesn't exist!");
		}
		Customer customer = customerOptional.get();	
		FieldValidator fieldValidator = new FieldValidator(reCustomer);
		if(!fieldValidator.getWrongFields().isEmpty()) {
			throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
		}
		customer.setAdress(reCustomer.getAdress());
		customer.setFirstName(reCustomer.getFirstName());
		customer.setSecondName(reCustomer.getSecondName());
		customerRepository.save(customer);
		return new ResponseEntity<Object>(new Status("Success"), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable Integer id) {
		Optional<Customer> customerOptional = customerRepository.findById(id);
		if(customerOptional.isEmpty()) {
			throw new ElementNotFound("Customer with " + id + " id doesn't exist!");
		}
		Customer customer = customerOptional.get();	
		customerRepository.delete(customer);
		Map<String, Boolean> responseMap = new HashMap<>();
		responseMap.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(responseMap);
	}


	@GetMapping("/customer/report")
	public ResponseEntity<Object> createPdfReport() throws FileNotFoundException, JRException {
		try{
			customerReportService.exportReport();
			long start = System.nanoTime();
			CompletableFuture<Double> firstPart = asyncService.getFirstPartIteration();
			CompletableFuture<Double> secondPart = asyncService.getSecondPartIteration();
			CompletableFuture<Double> thirdPart = asyncService.getThirdPartIteration();
			CompletableFuture.allOf(firstPart,secondPart,thirdPart);
			Double pi = firstPart.get() + secondPart.get() + thirdPart.get();
			long finish = System.nanoTime();
			logger.info("Time " + (finish - start));
			logger.info("Pi: " + pi/24915210);
			long start2 = System.nanoTime();
			Double pi2 = 0.0;
			Double tagline = 0.0;
			for (int i = 0; i < 3 * 8305070; i ++, tagline=0.0){
				tagline = (i + 0.5)/24915210;
				pi2 += 4/(1+tagline*tagline);
			}
			long finish2 = System.nanoTime();
			logger.info("Time " + (finish2 - start2));
			logger.info("Pi: " + pi2/24915210);
			logger.info("The report was created");
			return new ResponseEntity<Object>(new Status("Success"),HttpStatus.ACCEPTED);
		} catch (FileNotFoundException | JRException | InterruptedException | ExecutionException fileNotFoundException) {
			logger.error("The report was not created");
			fileNotFoundException.printStackTrace();
			return new ResponseEntity<Object>(new Status("Error"),HttpStatus.ACCEPTED);
		}
	}
}

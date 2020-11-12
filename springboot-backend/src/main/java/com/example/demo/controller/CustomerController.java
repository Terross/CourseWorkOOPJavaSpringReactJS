package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
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
import com.example.demo.model.Employee;
import com.example.demo.repository.CustomerRepository;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/customer")
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
	
	@PostMapping("/customer")
	public Customer addEmployee(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
		Optional<Customer> customerOptional = customerRepository.findById(id);
		Customer customer = customerOptional.get();		
		
		return ResponseEntity.ok(customer);
	}
	@PutMapping("/customer/{id}")
	public ResponseEntity<Customer> updateEmployee(@PathVariable Integer id,@RequestBody Customer reCustomer){
		Optional<Customer> customerOptional = customerRepository.findById(id);
		Customer customer = customerOptional.get();	
		customer.setAdress(reCustomer.getAdress());
		customer.setFirstName(reCustomer.getFirstName());
		customer.setSecondName(reCustomer.getSecondName());
		Customer updateCustomer = customerRepository.save(customer);
		return ResponseEntity.ok(updateCustomer);
	}
	
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable Integer id) {
		Optional<Customer> customerOptional = customerRepository.findById(id);
		Customer customer = customerOptional.get();	
		customerRepository.delete(customer);
		Map<String, Boolean> responseMap = new HashMap<>();
		responseMap.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(responseMap);
	}

}

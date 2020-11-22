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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.Exceptions.ElementNotFound;
import com.example.demo.Exceptions.ValidationException;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class CustomerController {
	
	
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
	
	@GetMapping("/customer")
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
	
	@PostMapping("/customer")
	public ResponseEntity<Object> addEmployee(@RequestBody Customer customer) throws ValidationException {

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
}

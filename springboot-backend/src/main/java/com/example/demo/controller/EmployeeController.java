package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.report.ProductReportService;
import net.sf.jasperreports.engine.JRException;
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

import com.example.demo.Exceptions.ElementNotFound;
import com.example.demo.Exceptions.ValidationException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;




@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	
	class FieldValidator {
		
		private ArrayList<String> wrongFields;
		public FieldValidator(Employee employee) {
			ArrayList<String> wrongFields = new ArrayList<String>();
			if(employee.getSalary()<=0) {
				wrongFields.add("salary");
			}
			if(employee.getFirstName()==null || employee.getFirstName().isEmpty()) {
				wrongFields.add("firstName");
			}
			if(employee.getSecondName()==null || employee.getSecondName().isEmpty()) {
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
	private EmployeeRepository employeeRepository;

	@Autowired
	private ProductReportService reportService;
	
	@GetMapping("/employee")
	public List<Employee> getAllCustomers(){
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employee")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) throws ValidationException {
		FieldValidator fieldValidator = new FieldValidator(employee);
		if(!fieldValidator.getWrongFields().isEmpty()) {
			throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
		}
		employeeRepository.save(employee);
		return new ResponseEntity<Object>(new Status("Success"), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) throws ElementNotFound {
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		if(employeeOptional.isEmpty()) {
			throw new ElementNotFound("Employee with " + id+ " id doesn't exist!");
		}
		Employee employee = employeeOptional.get();		
		return ResponseEntity.ok(employee);
	}
	@PutMapping("/employee/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable Integer id,@RequestBody Employee employeeDetail){
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		if(employeeOptional.isEmpty()) {
			throw new ElementNotFound("Employee with " + id+ " id doesn't exist!");
		}
		FieldValidator fieldValidator = new FieldValidator(employeeDetail);
		if(!fieldValidator.getWrongFields().isEmpty()) {
			throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
		}
		Employee employee = employeeOptional.get();	
		employee.setSalary(employeeDetail.getSalary());
		employee.setFirstName(employeeDetail.getFirstName());
		employee.setSecondName(employeeDetail.getSecondName());
		employeeRepository.save(employee);
		return new ResponseEntity<Object>(new Status("Success"), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Integer id) throws ElementNotFound{
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		if(employeeOptional.isEmpty()) {
			throw new ElementNotFound("Employee with " + id+ " id doesn't exist!");
		}
		Employee employee = employeeOptional.get();	
		employeeRepository.delete(employee);
		Map<String, Boolean> responseMap = new HashMap<>();
		responseMap.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(responseMap);
	}

}

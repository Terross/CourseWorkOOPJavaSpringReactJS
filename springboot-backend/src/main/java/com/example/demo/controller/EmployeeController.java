package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.report.ProductReportService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.EmployeeService;
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

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/employee")
	public List<Employee> getAllEmployees(){
		return employeeService.getAllEmployees();
	}
	
	@PostMapping("/employee")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee employee)  {
		boolean status = employeeService.addEmployee(employee);
		return new ResponseEntity<Object>(new Status(status), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id){
		return ResponseEntity.ok(employeeService.getEmployeeById(id));
	}

	@PutMapping("/employee/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable Integer id,@RequestBody Employee employee){
		boolean status = employeeService.updateEmployee(id, employee);
		return new ResponseEntity<Object>(new Status(status), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable Integer id) throws ElementNotFound{
		boolean status = employeeService.deleteEmployeeById(id);
		return new ResponseEntity<Object>(new Status(status), HttpStatus.ACCEPTED);
	}
	@GetMapping("/employee/report")
	public ResponseEntity<Object> createPdfReport() throws FileNotFoundException, JRException {
		boolean status = employeeService.createReports();
		return new ResponseEntity<Object>(new Status(status),HttpStatus.ACCEPTED);
	}
}

package com.example.demo.service;

import com.example.demo.Exceptions.ElementNotFound;
import com.example.demo.Exceptions.ValidationException;
import com.example.demo.controller.CustomerController;
import com.example.demo.model.Customer;
import com.example.demo.model.Employee;
import com.example.demo.report.CustomerReportService;
import com.example.demo.report.EmployeeReportService;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.EmployeeRepository;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeReportService employeeReportService;

    public boolean addEmployee(Employee employee) throws ValidationException {
        FieldValidator fieldValidator = new FieldValidator(employee);

        if(!fieldValidator.getWrongFields().isEmpty()) {
            throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
        }

        employeeRepository.save(employee);
        logger.info("The employee was added");
        return true;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) throws ElementNotFound {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if(employeeOptional.isEmpty()) {
            logger.error("The employee was not find");
            throw new ElementNotFound("Employee with " + id + " id doesn't exist!");
        }
        return employeeOptional.get();
    }

    public boolean updateEmployee(Integer id, Employee newEmployee) throws ElementNotFound, ValidationException {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        try{
            if(employeeOptional.isEmpty()) {
                throw new ElementNotFound("Employee with " + id + " id doesn't exist!");
            }
            FieldValidator fieldValidator = new FieldValidator(newEmployee);
            if(!fieldValidator.getWrongFields().isEmpty()) {
                throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
            }
        } catch (ElementNotFound e) {
            logger.error("The employee was not find");
            e.printStackTrace();
            return false;
        }
        Employee employee = employeeOptional.get();
        employee.setSalary(newEmployee.getSalary());
        employee.setFirstName(newEmployee.getFirstName());
        employee.setSecondName(newEmployee.getSecondName());
        employeeRepository.save(employee);
        logger.info("The employee was updated");
        return true;
    }

    public boolean deleteEmployeeById(Integer id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if(employeeOptional.isEmpty()) {
            logger.error("The employee was not find");
            throw new ElementNotFound("Employee with " + id + " id doesn't exist!");
        }
        employeeRepository.delete(employeeOptional.get());
        return true;
    }

    public boolean createReports() {
        try{
            CompletableFuture<Void> pdfReprot = employeeReportService.exportPDFReport();
            CompletableFuture<Void> htmlReport = employeeReportService.exportHTMLReport();
            CompletableFuture.allOf(pdfReprot, htmlReport);
            logger.info("The reports was created");
        } catch (FileNotFoundException | JRException e) {
            logger.error("The report was not created");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static class FieldValidator {

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
}

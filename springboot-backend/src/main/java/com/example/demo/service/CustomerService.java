package com.example.demo.service;

import com.example.demo.Exceptions.ElementNotFound;
import com.example.demo.Exceptions.ValidationException;
import com.example.demo.controller.CustomerController;
import com.example.demo.model.Customer;
import com.example.demo.report.CustomerReportService;
import com.example.demo.repository.CustomerRepository;
import net.sf.jasperreports.engine.JRException;
import org.apache.tools.ant.taskdefs.Sleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerReportService customerReportService;


    public boolean addCustomer(Customer customer) throws ValidationException {
        FieldValidator fieldValidator = new FieldValidator(customer);

        if(!fieldValidator.getWrongFields().isEmpty()) {
            throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
        }

        customerRepository.save(customer);
        logger.info("The customer was added");
        return true;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id) throws ElementNotFound {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty()) {
            logger.error("The customer was not find");
            throw new ElementNotFound("Customer with " + id + " id doesn't exist!");
        }
        return customerOptional.get();
    }

    public boolean updateCustomer(Integer id, Customer newCustomer) throws ElementNotFound, ValidationException {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        try{
            if(customerOptional.isEmpty()) {
                throw new ElementNotFound("Customer with " + id + " id doesn't exist!");
            }
            FieldValidator fieldValidator = new FieldValidator(newCustomer);
            if(!fieldValidator.getWrongFields().isEmpty()) {
                throw new ValidationException("Wrong fields", fieldValidator.getWrongFields());
            }
        } catch (ElementNotFound e) {
            logger.error("The customer was not find");
            e.printStackTrace();
            return false;
        }
        Customer customer = customerOptional.get();
        customer.setAdress(newCustomer.getAdress());
        customer.setFirstName(newCustomer.getFirstName());
        customer.setSecondName(newCustomer.getSecondName());
        customerRepository.save(customer);
        logger.info("The customer was updated");
        return true;
    }

    public boolean deleteCustomerById(Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if(customerOptional.isEmpty()) {
            logger.error("The customer was not find");
            throw new ElementNotFound("Customer with " + id + " id doesn't exist!");
        }
        customerRepository.delete(customerOptional.get());
        return true;
    }

    public boolean createReports() {
        try{
            CompletableFuture<Void> pdfReprot = customerReportService.exportPDFReport();
            CompletableFuture<Void> htmlReport = customerReportService.exportHTMLReport();
            CompletableFuture.allOf(pdfReprot, htmlReport);
            Thread.sleep(1000);
            logger.info("The reports was created");
        } catch (FileNotFoundException | JRException| InterruptedException e) {
            logger.error("The report was not created");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static class FieldValidator {

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
}

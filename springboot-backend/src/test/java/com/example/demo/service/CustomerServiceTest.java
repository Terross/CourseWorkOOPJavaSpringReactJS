package com.example.demo.service;

import com.example.demo.Exceptions.ElementNotFound;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;


    @Test
    public void addCustomer() {
        Customer customer= new Customer();
        customer.setFirstName("123");
        customer.setSecondName("12345");
        customer.setAdress("123");
        boolean status = customerService.addCustomer(customer);
        Assert.assertTrue(status);
    }

    @Test()
    public void deleteCustomerById() {
        try{
            boolean status =  customerService.deleteCustomerById(2000);
            Assert.fail("Expected Exception");
        } catch (Exception e){
            Assert.assertNotEquals("", e.getMessage());
        }

    }
}
package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(CustomerControllerTest.class);
    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void addCustomer() {
        Customer customer= new Customer();
        customer.setFirstName("123");
        customer.setSecondName("12345");
        customer.setAdress("123");

        //String status = String.valueOf(customerController.addCustomer(customer).getBody().);
        logger.info("Test123");
        //logger.info(status);
        //Assert.assertEquals("Success", status);
    }
}
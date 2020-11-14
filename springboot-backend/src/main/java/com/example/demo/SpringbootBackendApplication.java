package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

@SpringBootApplication
public class SpringbootBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}
//	@Bean
//    CommandLineRunner runner(ProductRepository productRepository) {
//        return args -> {
//            productRepository.save(new Product(1L, "TV Set", 300.00));
//            productRepository.save(new Product(2L, "Game Console", 200.00));
//            productRepository.save(new Product(3L, "Sofa", 100.00));
//            productRepository.save(new Product(4L, "Icecream", 5.00));
//            productRepository.save(new Product(5L, "Beer", 3.00));
//            productRepository.save(new Product(6L, "Phone", 500.00));
//            productRepository.save(new Product(7L, "Watch", 30.00));
//        };
//    }
}

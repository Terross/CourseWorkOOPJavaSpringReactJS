package com.example.demo.controller;

import com.example.demo.model.Shop;
import com.example.demo.repository.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ShopController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    ShopRepository shopRepository;

    @GetMapping("/shop")
    public List<Shop> getAllShops() { return shopRepository.findAll(); }

    @PostMapping("/shop")
    public ResponseEntity<Object> createShop(@RequestBody Shop shop){
        shopRepository.save(shop);
        logger.info("Shop was create");
        return new ResponseEntity<Object>(new Status("Success"), HttpStatus.ACCEPTED);
    }

    @PutMapping("/shop")
    public ResponseEntity<Object> updateShop(@RequestBody Shop newShop) {
        Shop shop = shopRepository.findAll().get(0);
        shop.setDirectorName(newShop.getDirectorName());
        shop.setName(newShop.getDirectorName());
        shop.setSpecialization(newShop.getSpecialization());
        shop.setAdress(newShop.getAdress());
        shopRepository.save(shop);
        logger.info("Shop was update");
        return new ResponseEntity<Object>(new Status("Success"), HttpStatus.ACCEPTED);
    }
}

package com.example.demo.controller;

import com.example.demo.AsyncConfiguration;
import com.example.demo.service.AsyncService;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class PiFindController {
    @Autowired
    private AsyncService asyncService;
    private static final Logger logger = LoggerFactory.getLogger(PiFindController.class);

    @GetMapping("/pi")
    public ResponseEntity<Object> createPdfReport() throws FileNotFoundException, JRException {
        try{
            long start = System.nanoTime();
            CompletableFuture<Double> firstPart = asyncService.getFirstPartIteration();
            CompletableFuture<Double> secondPart = asyncService.getSecondPartIteration();
            CompletableFuture<Double> thirdPart = asyncService.getThirdPartIteration();
            CompletableFuture.allOf(firstPart,secondPart,thirdPart);
            Double pi = firstPart.get() + secondPart.get() + thirdPart.get();
            long finish = System.nanoTime();
            logger.info("Time " + (finish - start));
            logger.info("Pi: " + pi/24915210);
            return new ResponseEntity<Object>(new Status(pi/24915210), HttpStatus.ACCEPTED);
        } catch (InterruptedException | ExecutionException fileNotFoundException) {
            logger.error("Pi was not found");
            fileNotFoundException.printStackTrace();
            return new ResponseEntity<Object>(new Status("Error"),HttpStatus.ACCEPTED);
        }
    }
}

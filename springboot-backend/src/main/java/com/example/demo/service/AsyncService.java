package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {
    private static Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Async("taskExecutor")
    public CompletableFuture<Double> getFirstPartIteration() throws InterruptedException {
        logger.info("getFirstPartIteration starts");
        Double firstPart = 0.0;
        Double tagline = 0.0;
        for (int i = 0; i < 8305070; i ++, tagline=0.0){
            tagline = (i + 0.5)/24915210;
            firstPart += 4/(1+tagline*tagline);
        }
        return CompletableFuture.completedFuture(firstPart);
    }

    @Async("taskExecutor")
    public CompletableFuture<Double> getSecondPartIteration() throws InterruptedException {
        logger.info("getSecondPartIteration starts");
        Double secondPart = 0.0;
        Double tagline = 0.0;
        for (int i = 8305070; i < 2 * 8305070; i ++, tagline=0.0){
            tagline = (i + 0.5)/24915210;
            secondPart += 4/(1+tagline*tagline);
        }
        return CompletableFuture.completedFuture(secondPart);
    }

    @Async("taskExecutor")
    public CompletableFuture<Double> getThirdPartIteration() throws InterruptedException {
        logger.info("getThirdPartIteration starts");
        Double thirdPart = 0.0;
        Double tagline = 0.0;
        for (int i = 2*8305070; i < 3 * 8305070; i ++, tagline=0.0){
            tagline = (i + 0.5)/24915210;
            thirdPart += 4/(1+tagline*tagline);
        }
        return CompletableFuture.completedFuture(thirdPart);
    }
}

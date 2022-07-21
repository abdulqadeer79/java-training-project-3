package com.example.square.service;

import com.example.square.dto.SquaredDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SquaredService {

//    ExecutorService executor = Executors.newFixedThreadPool(10);
//    public Future<SquaredDto> squareNumber(int number) {
//        log.info("Calling squareNumber method by Thread " +  Thread.currentThread().getName());
//        return executor.submit(() -> {
//            int squared = number * number;
//            return new SquaredDto(number, squared);
//        });
//    }

    public SquaredDto squareNumber(int number) throws InterruptedException {
        log.info("Calling squareNumber method by Thread " +  Thread.currentThread().getName());
        return new SquaredDto(number, number * number);
    }
}

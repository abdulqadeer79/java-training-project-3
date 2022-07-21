package com.example.number.service;

import com.example.number.dto.SquaredDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SquareService {
    public SquaredDto squareNumber(int number) throws InterruptedException {
        log.info("Calling squareNumber method by Thread " +  Thread.currentThread().getName());
        return new SquaredDto(number, number * number);
    }
}

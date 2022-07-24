package com.example.number.service;

import com.example.number.dto.SquaredDto;
import com.example.number.model.SimpleNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SquareService {
    public SimpleNumber squareNumber(int number) {
        log.info("Calling squareNumber method by Thread " +  Thread.currentThread().getName());
        return new SimpleNumber(number, number * number);
    }
}

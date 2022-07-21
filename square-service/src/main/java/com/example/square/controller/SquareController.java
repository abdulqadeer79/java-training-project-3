package com.example.square.controller;

import com.example.square.dto.SquaredDto;
import com.example.square.service.SquaredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/square")
public class SquareController {

    @Autowired
    private SquaredService service;

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/find-square/{number}")
    public SquaredDto findSquare(@PathVariable("number") int number) throws InterruptedException {
        return service.squareNumber(number);
    }
}

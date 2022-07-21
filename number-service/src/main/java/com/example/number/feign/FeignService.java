package com.example.number.feign;

import com.example.number.model.SimpleNumber;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "square-service", url = "http://localhost:8081/square/find-square")
public interface FeignService {

    @GetMapping("/{number}")
    SimpleNumber squareNumber(@PathVariable("number") int number);
}

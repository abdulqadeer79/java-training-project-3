package com.example.number.controller;

import com.example.number.model.SimpleNumber;
import com.example.number.repository.NumberRepository;
import com.example.number.service.NumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Slf4j
@RestController
@RequestMapping("/number")
public class NumberController {

    @Autowired
    private NumberService service;

    @Autowired
    private NumberRepository repository;

    // working ...
    @PostMapping(value = "/submit-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public String save(@RequestParam(value = "file") MultipartFile file ) throws Exception {
        service.saveFile(file);
        return "File saved";
    }

    // working ...
    @GetMapping(value = "/simple-numbers", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public List<SimpleNumber> getSimpleNumbers(@RequestParam(value = "file") MultipartFile file ) throws Exception {
        List<CompletableFuture<SimpleNumber>> futureNumberList = service.getSimpleNumbers(file);
        List<SimpleNumber> result = new ArrayList<>();
        for (CompletableFuture<SimpleNumber> x: futureNumberList) {
            result.add(x.get());
        }
        return result;

    }
}

package com.example.number.service;


import com.example.number.dto.SquaredDto;
import com.example.number.feign.FeignService;
import com.example.number.model.SimpleNumber;
import com.example.number.repository.NumberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

@Slf4j
@Service
public class NumberService {

    @Autowired
    private NumberRepository repository;

    @Autowired
    private FeignService feignService;

    @Autowired
    private SquareService service;

    ExecutorService executor = Executors.newFixedThreadPool(3);

//    private ExecutorService executorService;

//    // working ...
//    public void saveFile(final MultipartFile file) throws Exception {
//        InputStreamReader myStream = new InputStreamReader(file.getInputStream());
//        BufferedReader br = new BufferedReader(myStream);
//        try {
//            String line = null;
//            line = br.readLine();
//            List<Future<SimpleNumber>> futureNumberList = new ArrayList<>();
//            while(line != null) {
//                line = br.readLine();
//                if (line != null) {
//                    int value = Integer.parseInt(line);
//                    log.info("calling squareNumber() in saveFile() with Thread " + Thread.currentThread().getName());
//                    Future<SimpleNumber> mySimpleFuture = executor.submit(() -> feignService.squareNumber(value));
//                    futureNumberList.add(mySimpleFuture);
//                }
//
//            }
//            List<SimpleNumber> result = new ArrayList<>();
//            for (Future<SimpleNumber> x: futureNumberList
//            ) {
//                result.add(x.get());
//            }
//            result.get(700).getNumber();
//            List<SimpleNumber> result1 = repository.saveAll(result);
//            log.info("Size" + result1.size());
//        } catch (IOException | NumberFormatException e) {
//            throw new RuntimeException(e);
//        } finally {
//            br.close();
//        }
//    }

    // working...
    public List<CompletableFuture<SimpleNumber>> getSimpleNumbers(final MultipartFile file) throws Exception {
        InputStreamReader myStream = new InputStreamReader(file.getInputStream());
        BufferedReader br = new BufferedReader(myStream);
        Scanner sc = new Scanner(file.getInputStream());
        List<CompletableFuture<SimpleNumber>> futureNumberList = new ArrayList<>();
        try {
            String line = null;
            line = sc.nextLine();
            while(sc.hasNext()) {
                line = sc.nextLine();
                if (line != null) {
                    int value = Integer.parseInt(line);
                    log.info("calling squareNumber() with Thread" + Thread.currentThread().getName());

                }
            }
            return futureNumberList;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } finally {
            br.close();
        }
    }

    public void saveMany() {
        List<SimpleNumber> numberList = new ArrayList<>();
        for (int i = 0 ; i < 1000; i++) {
            SimpleNumber number = new SimpleNumber(i, i * i);
            numberList.add(number);
        }
        repository.saveAll(numberList);
    }

    // Testing ...
    public void saveFile(final MultipartFile file) throws Exception {
        InputStreamReader myStream = new InputStreamReader(file.getInputStream());
        BufferedReader br = new BufferedReader(myStream);
        String line = null;
        line = br.readLine();
        List<SimpleNumber> futureNumberList = new ArrayList<>();
        while(line != null) {
            line = br.readLine();
            if (line != null) {
                int value = Integer.parseInt(line);
//                log.info("calling squareNumber() in saveFile() with Thread " + Thread.currentThread().getName());
                CompletableFuture<SimpleNumber> result = CompletableFuture.supplyAsync(() -> {
                    return feignService.squareNumber(value);
                }, executor);
            }
        }
    }
}


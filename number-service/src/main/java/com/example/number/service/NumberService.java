package com.example.number.service;

import com.example.number.feign.FeignService;
import com.example.number.model.SimpleNumber;
import com.example.number.repository.NumberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Slf4j
@Service
public class NumberService {

    @Autowired
    private NumberRepository repository;

    @Autowired
    private FeignService feignService;

    @Autowired
    private SquareService service;

    ExecutorService executor = Executors.newFixedThreadPool(10);
//
//    // working...
//    public List<SimpleNumber> saveFileAsync(final MultipartFile file) throws Exception {
//        long startTime = System.currentTimeMillis();
//
//        String path = "C:\\Users\\92314\\Desktop\\java-training-project-3\\number-service\\output.csv";
//        InputStreamReader myStream = null;
//        BufferedReader br = null;
//        AtomicReference<FileWriter> fr = new AtomicReference<>(new FileWriter(path));
//        AtomicReference<BufferedWriter> bw = new AtomicReference<>(new BufferedWriter(fr.get()));
//        try {
//            myStream = new InputStreamReader(file.getInputStream());
//            br = new BufferedReader(myStream);
//            String line, result = null;
//            line = br.readLine();
//            List<SimpleNumber> numberList = new ArrayList<>();
////            List<CompletableFuture<SimpleNumber>> futureList = new ArrayList<>();
//            while (line != null) {
//                line = br.readLine();
//                if (line != null) {
//                    int value = Integer.parseInt(line);
//                    CompletableFuture<SimpleNumber> simpleNumber = getSimpleNumbers(value);
////                    futureList.add(simpleNumber);
////                    simpleNumber.thenApplyAsync(numberList::add);
//                    synchronized (simpleNumber) {
//                        CompletableFuture<SimpleNumber> myNumber = simpleNumber.thenApplyAsync( x -> {
//                            try {
//                                log.info("writing file by Thread " + Thread.currentThread().getName());
//                                bw.get().write(x.getNumber() + "," + x.getSquaredNumber() + "\n");
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                            return x;
//                        });
//                    }
//
//                }
//            }
////            for (CompletableFuture<SimpleNumber> x : futureList) {
//////                numberList.add(x.get());
////                bw.get().write(x.get().getNumber() + "," + x.get().getSquaredNumber() + "\n");
////            }
//            return numberList;
//        }
//        catch (Exception ignored){
//        }
//        finally {
//            if (br != null) {
//                br.close();
//            }
//            if (bw.get() != null) {
//                bw.get().close();
//            }
//            long endTime = System.currentTimeMillis();
//            System.out.println("code running time" + (endTime - startTime));
//        }
//        return null;
//    }

//    public List<SimpleNumber> saveFileAsync1(final MultipartFile file) throws Exception {
//        InputStreamReader is = null;
//        BufferedReader br = null;
//        BufferedWriter bw = null;
//        String path = "C:\\Users\\92314\\Desktop\\java-training-project-3\\number-service\\output.csv";
//        FileWriter fw = new FileWriter(path);
//        try {
//            is = new InputStreamReader(file.getInputStream());
//            br = new BufferedReader(is);
//            bw = new BufferedWriter(fw);
//            String line = br.readLine();
//            line = br.readLine();
//            List<SimpleNumber> numberList = new ArrayList<>();
//            List<CompletableFuture<SimpleNumber>> futureList = new ArrayList<>();
//            while (line != null) {
//                line = br.readLine();
//                if (line != null) {
//                    int value = Integer.parseInt(line);
//                    CompletableFuture<SimpleNumber> simpleNumber = getSimpleNumbers(value);
//                    futureList.add(simpleNumber);
//                }
//            }
//            for (CompletableFuture<SimpleNumber> x : futureList) {
//                numberList.add(x.get());
//                bw.write(x.get().getNumber() + "," + x.get().getSquaredNumber() + "\n");
//            }
//            return numberList;
//        }
//        catch (Exception ignored){
//        }
//        finally {
//            if (br != null) {
//                br.close();
//            }
//            if (bw != null) {
//                bw.close();
//            }
//        }
//        return null;
//    }


    // working...
    public List<SimpleNumber> saveFileAsync(final MultipartFile file) throws Exception {
        long startTime = System.currentTimeMillis();

        InputStreamReader myStream = null;
        BufferedReader br = null;
        Path path = Paths.get("C:\\Users\\92314\\Desktop\\java-training-project-3\\number-service/output.csv");
        // increase the buffer size if required
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // Write Data to buffer

        try(AsynchronousFileChannel asyncChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE)){
            // Write to async channel from buffer
            // starting from position 0

            myStream = new InputStreamReader(file.getInputStream());
            br = new BufferedReader(myStream);
            String line, result = null;
            line = br.readLine();
            long position = 0;
            while (line != null) {
                line = br.readLine();
                if (line != null) {
                    int value = Integer.parseInt(line);
                    CompletableFuture<SimpleNumber> simpleNumber = getSimpleNumbers(value);
                    long finalPosition = position;
                    simpleNumber.thenApplyAsync(x -> {
                        buffer.put((x.getNumber() + "," + x.getSquaredNumber() + "\n").getBytes());
                        buffer.flip();
                        log.info("writing file by Thread " + Thread.currentThread().getName());
                        Future<Integer> future = null;
                        try {
                            future = asyncChannel.write(buffer, asyncChannel.size());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return future;
                    });
                }
                buffer.clear();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    // working...
    public CompletableFuture<SimpleNumber> getSimpleNumbers(int number) {
        CompletableFuture<SimpleNumber> dbLookUp = new CompletableFuture<>();
        CompletableFuture<SimpleNumber> serviceCalculate = new CompletableFuture<>();

//        CompletableFuture.runAsync(() -> {
            Optional<SimpleNumber> dbData = repository.findById(number);
            if (dbData.isPresent()) {
                log.info("Data found in db by Thread " + Thread.currentThread().getName());
                dbLookUp.complete(dbData.get());
            } else {
                log.info("Data not found in db by Thread " + Thread.currentThread().getName());
                serviceCalculate.complete(null);
            }
//        });

        return serviceCalculate.thenApplyAsync(result -> {
            log.info("Calling squareNumber() method by Thread " + Thread.currentThread().getName());
            SimpleNumber simpleNumber = service.squareNumber(number);
            repository.save(simpleNumber);
            return simpleNumber;
        }, executor).applyToEither(dbLookUp, Function.identity());
    }
}


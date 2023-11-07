package com.java8.completable.future.handson.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java8.completable.future.handson.beans.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RunAsyncDemo {

    public void SaveEmployee(File fileJson) throws ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                List<Employee> employees = mapper.readValue(fileJson, new TypeReference<List<Employee>>() {
                });
                System.out.println("Thread : " + Thread.currentThread().getName());
                employees.stream().forEach(System.out::println);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //return voidCompletableFuture.get();
         voidCompletableFuture.get();
    }

    public void runAsyncWithCustomThreadPool (File fileJson) throws ExecutionException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        Executor executor = Executors.newFixedThreadPool(10);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                List<Employee> employees = mapper.readValue(fileJson, new TypeReference<List<Employee>>() {
                });
                System.out.println("Thread :" + Thread.currentThread().getName());
                System.out.println("Employees numbers" + employees.size());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, executor);

        voidCompletableFuture.get();
    }
}


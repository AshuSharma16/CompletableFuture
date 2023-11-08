package com.java8.completable.future.handson.test;

import com.java8.completable.future.handson.beans.Employee;
import com.java8.completable.future.handson.service.SupplyAsyncService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SupplySyncTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsyncService service = new SupplyAsyncService();
        List<Employee> employees = service.fetchEmployees().get();
        employees.stream().forEach(System.out::println);

    }
}

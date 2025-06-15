package com.project.completable.future.handson.main;

import com.project.completable.future.handson.beans.Employee;
import com.project.completable.future.handson.service.EmployeeService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class SendOrPrintEmailsOfAllNewJoinerWhosTrainingIsPending{
    public static void main(String[] args) throws ExecutionException, InterruptedException{
        final CompletableFuture<Void> voidCompletableFuture = sendOrPrintEmailsOfAllNewJoinerWhosTrainingIsPending();
        System.out.println(voidCompletableFuture.get());
    }

    public static CompletableFuture<Void> sendOrPrintEmailsOfAllNewJoinerWhosTrainingIsPending(){

        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        final CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("loading data from json file -" + Thread.currentThread().getName());
            return EmployeeService.getAllEmployee();
        },executorService).thenApplyAsync((employeeList) -> {
            System.out.println("get the new joiner list -" + Thread.currentThread().getName());
            return employeeList.stream().filter(emp -> emp.getNewJoiner().equalsIgnoreCase("TRUE")).collect(Collectors.toList());
        },executorService).thenApplyAsync((empList) -> {
            System.out.println("get the employee having pending training  -" + Thread.currentThread().getName());
            return empList.stream().filter(emp -> emp.getLearningPending().equalsIgnoreCase("TRUE")).collect(Collectors.toList());
        },executorService).thenApplyAsync((employeeList) -> {
            System.out.println("get all teh emails  -" + Thread.currentThread().getName());
            return employeeList.stream().map(Employee::getEmail).collect(Collectors.toList());
        },executorService).thenAcceptAsync((emailList) -> {
            System.out.println("send or populate all email  -" + Thread.currentThread().getName());
            emailList.stream().forEach(s -> acceptEmail(s));
        },executorService);

        return voidCompletableFuture;
    }

    private static void acceptEmail(String email){
        System.out.println("Employee Email : " + email);
    }
}

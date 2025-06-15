package com.completable.future.sceneriobased.handson.combiningFuture.main;

import com.completable.future.sceneriobased.handson.service.EmployeeService;
import com.project.completable.future.handson.beans.Employee;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CombingTwoInDependentFuture{
    public static void main(String[] args) throws ExecutionException, InterruptedException{
        // combine two InDependent Future
        //task -1  - Get all teh employee based on genederand its count like how many male and how many female employee 
        // task 2- get all employee emails
        // so here we are having two future oject and which are independed on each othe
        // use - thenCombine()

        final CompletableFuture<String> stringCompletableFuture = getListOfMaleAndFemale().thenCombine(getAllEmployeeEmails(),(genderMap,email) -> genderMap + "::" + email);
        System.out.println(stringCompletableFuture.get());
    }

    private static CompletableFuture<Map<String, Long>> getListOfMaleAndFemale(){

        final ExecutorService genderFuturePool = Executors.newCachedThreadPool();
        final CompletableFuture<Map<String, Long>> genderFuture = CompletableFuture.supplyAsync(() -> {
            return EmployeeService.getAllEmployee().stream().collect(Collectors.groupingBy(Employee::getGender,Collectors.counting()));
        },genderFuturePool);
        return genderFuture;
    }

    private static CompletableFuture<List<String>> getAllEmployeeEmails(){
        final ExecutorService emailsFuture = Executors.newCachedThreadPool();
        final CompletableFuture<List<String>> emailList = CompletableFuture.supplyAsync(() -> {
            return EmployeeService.getAllEmployee().stream().map(Employee::getEmail).collect(Collectors.toList());
        },emailsFuture);
        return emailList;
    }

}

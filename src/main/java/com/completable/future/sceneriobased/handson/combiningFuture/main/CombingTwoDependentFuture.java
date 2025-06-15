package com.completable.future.sceneriobased.handson.combiningFuture.main;

import com.completable.future.sceneriobased.handson.service.EmployeeService;
import com.project.completable.future.handson.beans.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CombingTwoDependentFuture{
    public static void main(String[] args) throws ExecutionException, InterruptedException{
        // combine two dependent future
        //scenrio - Increment the salary of only those employee who's having rating greater than or equals to 4
        // get all employee who is having rating greater than or equals to 4 - Create Future Object -1
        // increase teh salary by 500/- Create Future Object -2
        // so here we are having future oject and Future object 2 is dependent on 1
        // use thenCompose() method
        //final CompletableFuture<CompletableFuture<List<Employee>>> completableFutureCompletableFuture = getAllEmployeeWithExecllentrating().thenApply((employeeList) -> increamentSalaryByAmount(employeeList);
        // use

        final CompletableFuture<List<Employee>> listCompletableFuture = getAllEmployeeWithExecllentrating().thenCompose((employeeList) -> increamentSalaryByAmount(employeeList));
        System.out.println(listCompletableFuture.get());

    }

    private static CompletableFuture<List<Employee>> getAllEmployeeWithExecllentrating(){

        final ExecutorService ratingFuture = Executors.newCachedThreadPool();
        final CompletableFuture<List<Employee>> execllentRatingFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("getAllEmployeeWithExecllentrating : -" + Thread.currentThread().getName());
            return EmployeeService.getAllEmployee().stream().filter(emp -> emp.getRating() > 3).collect(Collectors.toList());
        },ratingFuture);
        return execllentRatingFuture;
    }

    private static CompletableFuture<List<Employee>> increamentSalaryByAmount(List<Employee> employeeList){
        final ExecutorService ratingFuture = Executors.newCachedThreadPool();
        final CompletableFuture<List<Employee>> increamentSalaryFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("increamentSalaryByAmount : -" + Thread.currentThread().getName());
            return employeeList.stream().map((emp) -> {
                final double updatedSal = emp.getSalary() + 500d;
                emp.setSalary(updatedSal);
                return emp;
            }).collect(Collectors.toList());
        },ratingFuture);
        return increamentSalaryFuture;

    }

}

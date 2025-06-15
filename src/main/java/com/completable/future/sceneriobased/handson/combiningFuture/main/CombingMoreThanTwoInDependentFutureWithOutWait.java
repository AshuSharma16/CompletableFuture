package com.completable.future.sceneriobased.handson.combiningFuture.main;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CombingMoreThanTwoInDependentFutureWithOutWait{
    public static void main(String[] args) throws ExecutionException, InterruptedException{
        // combine more than two InDependent Future
        //task -1  - get weather from X api
        // task 2- get weather from Y api
        // task 2- get weather from Z api

        // Here we are trying to get result from multiple consumer and return only that result which we recieved first
        // so here we are having more than two future oject and which are independed on each other
        // use - anyOf() - CompletableFuture.anyOf --> it will return the result of any of task which completed first


        // Imp Notes :
        //1. Group all task with  CompletableFuture object
        //2. CompletableFuture return Void object to you  ->  CompletableFuture<Void>
        //3. You can play with result once all task completed and process teh maipulation and all

        String city = "Bangalore";

        final CompletableFuture<String> weathersReportFromSOLR = getWeathersReportFromSOLR(city);
        final CompletableFuture<String> weathersReportFromDB = getWeathersReportFromDB(city);
        final CompletableFuture<String> weathersReportFromAccountAPI = getWeathersReportFromAccountAPI(city);

        // it will not wait for all task to complete , and return the result when any of task completed
        final CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(weathersReportFromSOLR,weathersReportFromDB,weathersReportFromAccountAPI);

        // process teh result once all task to be compeleted
        objectCompletableFuture.thenAccept((anydata) -> {
            System.out.println("respone recieved ...." + city);
        }).join();  // using join means - you are not blocking your main thread.

    }

    private static CompletableFuture<String> getWeathersReportFromSOLR(String city){

        final ExecutorService solrWeatherExecutorService = Executors.newCachedThreadPool();

        final CompletableFuture<String> solrWealthersFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("getWeathersReport : - " + Thread.currentThread().getName());
            simulateDeplay(3000);
            System.out.println("getWeathersReportFromSOLR....." + city);
            return "getWeathersReportFromSOLR".concat(city);
        },solrWeatherExecutorService);

        return solrWealthersFuture;
    }

    private static CompletableFuture<String> getWeathersReportFromDB(String city){

        final ExecutorService dbWeatherExecutorService = Executors.newCachedThreadPool();

        final CompletableFuture<String> dbWealthersFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("getWeathersReport : - " + Thread.currentThread().getName());
            simulateDeplay(2000);
            System.out.println("getWeathersReportFromDB....." + city);
            return "getWeathersReportFromDB".concat(city);
        },dbWeatherExecutorService);

        return dbWealthersFuture;
    }

    private static CompletableFuture<String> getWeathersReportFromAccountAPI(String city){

        final ExecutorService accountExecutorService = Executors.newCachedThreadPool();

        final CompletableFuture<String> accountWealthersFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("getWeathersReport : - " + Thread.currentThread().getName());
            simulateDeplay(3000);
            System.out.println("getWeathersReportFromAccountAPI....." + city);
            return "getWeathersReportFromAccountAPI".concat(city);
        },accountExecutorService);

        return accountWealthersFuture;
    }


    private static void simulateDeplay(int timePeriod){
        try {
            Thread.sleep(timePeriod);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

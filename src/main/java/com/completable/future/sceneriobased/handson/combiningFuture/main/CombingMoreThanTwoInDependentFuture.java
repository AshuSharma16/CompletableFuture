package com.completable.future.sceneriobased.handson.combiningFuture.main;

import com.completable.future.sceneriobased.handson.service.EmployeeService;
import com.project.completable.future.handson.beans.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CombingMoreThanTwoInDependentFuture{
    public static void main(String[] args) throws ExecutionException, InterruptedException{
        // combine two InDependent Future
        //task -1  - get weather reports
        // task 2- get news headlines for today
        // task 2- get stocks market updates
        // so here we are having more than two future oject and which are independed on each other
        // use - allOf() - CompletableFuture.allOf --> it will be wait to complete all task but will not block main thread


        // Imp Notes :
        //1. Group all task with  CompletableFuture object
        //2. CompletableFuture return Void object to you  ->  CompletableFuture<Void>
        //3. You can play with result once all task completed and process teh maipulation and all

        final CompletableFuture<String> weathersReport = getWeathersReport();
        final CompletableFuture<String> newsHeadLines = getNewsHeadLines();
        final CompletableFuture<String> stocksMarketNews = getStocksMarketNews();

        // wait for all task to be compeleted and  return Void
        final CompletableFuture<Void> allCompletableFuture = CompletableFuture.allOf(weathersReport,stocksMarketNews,newsHeadLines);

        // process teh result once all task to be compeleted
        allCompletableFuture.thenRun(() -> {
            try {
                final String wealther = weathersReport.get();
                final String  news = newsHeadLines.get();
                final String stocks = stocksMarketNews.get();
                System.out.println("wealther : " + wealther);
                System.out.println("news : " + news );
                System.out.println( "stocks : " + stocks);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).join();  // using join means - you are not blocking your main thread.

    }

    private static CompletableFuture<String> getWeathersReport(){

        final ExecutorService weatherExecutorService = Executors.newCachedThreadPool();

        final CompletableFuture<String> wealthersFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("getWeathersReport : - " + Thread.currentThread().getName());
            simulateDeplay(3000);
            return "Weather : Sunny , 25°C";
        },weatherExecutorService);

        return wealthersFuture;
    }

    private static CompletableFuture<String> getNewsHeadLines(){

        final ExecutorService newsExecutorService = Executors.newCachedThreadPool();

        final CompletableFuture<String> newsFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("getNewsHeadLines : - " + Thread.currentThread().getName());
            simulateDeplay(2000);
            return "java : 21 version release today ";
        },newsExecutorService);

        return newsFuture;
    }

    private static CompletableFuture<String> getStocksMarketNews(){

        final ExecutorService stocksMarketExecutorService = Executors.newCachedThreadPool();

        final CompletableFuture<String> stocksMarketFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("getStocksMarketNews : - " + Thread.currentThread().getName());
            simulateDeplay(5000);
            return "Nifty 50 : Down by 500 points";
        },stocksMarketExecutorService);

        return stocksMarketFuture;
    }


    private static void simulateDeplay(int timePeriod){
        try {
            Thread.sleep(timePeriod);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

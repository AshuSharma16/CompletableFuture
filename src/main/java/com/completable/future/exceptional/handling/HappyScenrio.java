package com.completable.future.exceptional.handling;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HappyScenrio{

    public static void main(String[] args) throws ExecutionException, InterruptedException{
        final CompletableFuture<String> responseFutue = getDatafromDownstream()
                .thenCombine(updateDataIntoDB(),(getData,updateData) -> getData + "    and : ->   " + updateData);
        System.out.println(responseFutue.get());

    }

    static CompletableFuture<List<String>> getDatafromDownstream(){
        final CompletableFuture<List<String>> getDataFuture = CompletableFuture.supplyAsync(() -> {
            return Arrays.asList("Ashu","Bittoo","Vritika");
        });
        return getDataFuture;
    }

    static CompletableFuture<String> updateDataIntoDB(){

        final CompletableFuture<String> updateData = CompletableFuture.supplyAsync(() -> {
            System.out.println("data updated into DB has been completed !!!!");
            return "data updated into DB";
        });
        return updateData;
    }

    static void graceFullyThrowException(){
        throw new RuntimeException("There is an error occured while processing your request , Please try after some time ");
    }
}

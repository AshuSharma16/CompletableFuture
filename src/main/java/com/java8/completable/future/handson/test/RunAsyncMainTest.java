package com.java8.completable.future.handson.test;

import com.java8.completable.future.handson.service.RunAsyncDemo;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class RunAsyncMainTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RunAsyncDemo demo = new RunAsyncDemo();
        demo.SaveEmployee(new File("employees.json"));
        System.out.println("-------------------");
        demo.runAsyncWithCustomThreadPool(new File("employees.json"));
    }
}

package com.project.completable.future.handson.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.completable.future.handson.beans.Employee;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class EmployeeService{
    public static List<Employee> getAllEmployee(){
        ObjectMapper mapper = new ObjectMapper();
        List<Employee> empList = new ArrayList<>();
        try {
            System.out.println("EmployeeService - Thread Name : " + Thread.currentThread().getName());
            empList = mapper.readValue(new File("employees.json"),new TypeReference<List<Employee>>(){
            });
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Emplyee data loaded from DB - " + empList);
        return empList;
    }

}

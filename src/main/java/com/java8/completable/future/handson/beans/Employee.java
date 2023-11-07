package com.java8.completable.future.handson.beans;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class Employee {
    private String employeeId;

    private String firstName;

    private String lastName;


    private String email;

    private int rating;
    private String learningPending;
    private Double salary;
    private String gender;
    private String newJoiner;


}

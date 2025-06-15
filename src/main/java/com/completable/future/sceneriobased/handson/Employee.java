package com.completable.future.sceneriobased.handson;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Employee{
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String newJoiner;
    private String learningPending;
    private Double salary;
    private int rating;
    public String toString() {
       return  ToStringBuilder.reflectionToString(this,ToStringStyle.JSON_STYLE);
    }
}

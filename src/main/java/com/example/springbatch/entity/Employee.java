package com.example.springbatch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Collate;

@Entity
@Table(name = "Employee")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    @Id
    @Column(name = "Employee_ID")
    private int employeeId;

    @Column(name = "First_Name")
    private String firstName;

    @Column(name = "Last_Name")
    private String lastName;

    @Column(name = "Age")
    private int age;

    @Column(name = "Department")
    private String department;

    @Column(name = "Salary")
    private Double salary;
}

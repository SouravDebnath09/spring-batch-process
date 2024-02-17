package com.example.springbatch.config;

import com.example.springbatch.entity.Employee;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

    public Employee process(Employee employee){
        if(employee.getSalary()>0){
            return employee;
        } else
            return null;
    }
}


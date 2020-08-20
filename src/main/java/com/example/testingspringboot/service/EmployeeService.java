package com.example.testingspringboot.service;

import com.example.testingspringboot.entity.Employee;

public interface EmployeeService {

    Employee getEmployeeByName(String name);
}

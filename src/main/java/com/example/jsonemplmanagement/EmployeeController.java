package com.example.jsonemplmanagement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


import java.util.*;



@RestController

public class EmployeeController {

    private static List<Employee> employeeList = new ArrayList<>();


    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeList);
    }

    /// Validate Employee Structure before adding it
    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        if (!employee.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee data.");
        }

        if (employeeList.stream().anyMatch(e -> e.getEmployeeID() == employee.getEmployeeID())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee with the same ID already exists.");
        }

        employeeList.add(employee);
        return ResponseEntity.ok("Employee added successfully.");
    }

    ////  One route do that requirement
    @GetMapping("/getEmployeeById/{employeeID}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int employeeID) {
        // Check if employeeList is null or empty
        if (employeeList == null || employeeList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Find and return the employee with the given ID
        for (Employee employee : employeeList) {
            if (employee.getEmployeeID() == employeeID) {
                return ResponseEntity.ok(employee);
            }
        }

        // If no employee with the specified ID is found
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getEmployeeByDesignation/{designation}")
    public ResponseEntity<List<Employee>> getEmployeeByDesignation(@PathVariable String designation) {
        List<Employee> employeesWithDesignation = new ArrayList<>();
        // Find employees with the given designation
        for (Employee employee : employeeList) {
            if (employee.getDesignation().equalsIgnoreCase(designation)) {
                employeesWithDesignation.add(employee);
            }
        }
        if (!employeesWithDesignation.isEmpty()) {
            return ResponseEntity.ok(employeesWithDesignation);
        } else {
            // If no employee with the specified designation is found
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteEmployee/{employeeID}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int employeeID) {
        Iterator<Employee> iterator = employeeList.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getEmployeeID() == employeeID) {
                iterator.remove();
                return ResponseEntity.ok("Employee deleted successfully.");
            }
        }
        // If no employee with the specified ID is found
        return ResponseEntity.status(404).body("No employee found with ID: " + employeeID);
    }
    @PutMapping("/updateEmployeeDesignation/{employeeID}")
    public ResponseEntity<String> updateEmployeeDesignation(
            @PathVariable int employeeID,
            @RequestBody Map<String, String> requestBody) {
        String newDesignation = requestBody.get("designation");

        for (Employee employee : employeeList) {
            if (employee.getEmployeeID() == employeeID) {
                employee.setDesignation(newDesignation);
                return ResponseEntity.ok("Employee designation updated successfully.");
            }
        }
        // If no employee with the specified ID is found
        return ResponseEntity.status(404).body("No employee found with ID: " + employeeID);
    }




}

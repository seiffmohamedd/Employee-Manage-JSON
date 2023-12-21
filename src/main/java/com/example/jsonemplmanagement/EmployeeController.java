package com.example.jsonemplmanagement;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//@RestController
//
//public class EmployeeController {
//
//    private static List<Employee> employeeList = new ArrayList<>();
//
//
//    @GetMapping("/getAllEmployees")
//    public ResponseEntity<List<Employee>> getAllEmployees() {
//        return ResponseEntity.ok(employeeList);
//    }
//
//    /// Validate Employee Structure before adding it
//    @PostMapping("/addEmployee")
//    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
//        if (!employee.isValid()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee data.");
//        }
//
//        if (employeeList.stream().anyMatch(e -> e.getEmployeeID() == employee.getEmployeeID())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee with the same ID already exists.");
//        }
//
//        employeeList.add(employee);
//
//        return ResponseEntity.ok("Employee added successfully.");
//    }
//
//    @GetMapping("/getEmployee")
//    public ResponseEntity<?> getEmployee(@RequestParam(required = false) Integer employeeID,
//                                         @RequestParam(required = false) String designation) {
//        // Check if both parameters are null
//        if (employeeID == null && designation == null) {
//            return ResponseEntity.badRequest().body("Please provide either employeeID or designation.");
//        }
//
//        // Search by employeeID
//        if (employeeID != null) {
//            for (Employee employee : employeeList) {
//                if (employee.getEmployeeID() == employeeID) {
//                    return ResponseEntity.ok(employee);
//                }
//            }
//            // If no employee with the specified ID is found
//            return ResponseEntity.notFound().build();
//        }
//
//        // Search by designation
//        if (designation != null) {
//            List<Employee> employeesWithDesignation = new ArrayList<>();
//            for (Employee employee : employeeList) {
//                if (employee.getDesignation().equalsIgnoreCase(designation)) {
//                    employeesWithDesignation.add(employee);
//                }
//            }
//            if (!employeesWithDesignation.isEmpty()) {
//                return ResponseEntity.ok(employeesWithDesignation);
//            } else {
//                // If no employee with the specified designation is found
//                return ResponseEntity.notFound().build();
//            }
//        }
//
//        // This should not be reached, but return a bad request if none of the conditions match
//        return ResponseEntity.badRequest().body("Invalid parameters.");
//    }
//
//    @DeleteMapping("/deleteEmployee/{employeeID}")
//    public ResponseEntity<String> deleteEmployee(@PathVariable int employeeID) {
//        Iterator<Employee> iterator = employeeList.iterator();
//        while (iterator.hasNext()) {
//            Employee employee = iterator.next();
//            if (employee.getEmployeeID() == employeeID) {
//                iterator.remove();
//                return ResponseEntity.ok("Employee deleted successfully.");
//            }
//        }
//        // If no employee with the specified ID is found
//        return ResponseEntity.status(404).body("No employee found with ID: " + employeeID);
//    }
//    @PutMapping("/updateEmployeeDesignation/{employeeID}")
//    public ResponseEntity<String> updateEmployeeDesignation(
//            @PathVariable int employeeID,
//            @RequestBody Map<String, String> requestBody) {
//        String newDesignation = requestBody.get("designation");
//
//        for (Employee employee : employeeList) {
//            if (employee.getEmployeeID() == employeeID) {
//                employee.setDesignation(newDesignation);
//                return ResponseEntity.ok("Employee designation updated successfully.");
//            }
//        }
//        // If no employee with the specified ID is found
//        return ResponseEntity.status(404).body("No employee found with ID: " + employeeID);
//    }
//
//    @GetMapping("/getEmployeesByLanguage")
//    public ResponseEntity<?> getEmployeesByLanguage(
//            @RequestParam String language,
//            @RequestParam int score,
//            @RequestParam(defaultValue = "asc") String order) {
//
//        // Filter employees by language and score
//        List<Employee> filteredEmployees = employeeList.stream()
//                .filter(employee -> employee.getKnownLanguages().stream()
//                        .anyMatch(knownLanguage -> knownLanguage.getLanguageName().equalsIgnoreCase(language)
//                                && knownLanguage.getScoreOutof100() > score))
//                .collect(Collectors.toList());
//
//        // Sort the result based on the specified order and language scores
//        if ("asc".equalsIgnoreCase(order)) {
//            filteredEmployees.sort(Comparator.comparingInt((Employee employee) ->
//                    employee.getScoreForLanguage(language)));
//        } else if ("desc".equalsIgnoreCase(order)) {
//            filteredEmployees.sort(Comparator.comparingInt((Employee employee) ->
//                    employee.getScoreForLanguage(language)).reversed());
//        } else {
//            return ResponseEntity.badRequest().body("Invalid order parameter. Use 'asc' or 'desc'.");
//        }
//
//        if (!filteredEmployees.isEmpty()) {
//            return ResponseEntity.ok(filteredEmployees);
//        } else {
//            // If no employee matches the criteria
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//
//
//
//
//
//
//
//
//}

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    private static final String JSON_FILE_PATH = "employees.json";
//    private static final String JSON_FILE_PATH = "C:\\Users\\Lenovo\\IdeaProjects\\Employee-Manage-JSON\\src\\main\\java\\com\\example\\jsonemplmanagement\\employees.json";



    private static List<Employee> employeeList = loadEmployeesFromJson();

    private static List<Employee> loadEmployeesFromJson() {
        try {
            File file = new File(JSON_FILE_PATH);
            if (file.exists()) {
                ObjectMapper objectMapper = new ObjectMapper();
                CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Employee.class);
                return objectMapper.readValue(file, listType);
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading employees from JSON file.", e);
        }
    }

    private static void saveEmployeesToJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(Paths.get(JSON_FILE_PATH).toFile(), employeeList);
        } catch (Exception e) {
            throw new RuntimeException("Error saving employees to JSON file.", e);
        }
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        if (!employee.isValid()) {
            return ResponseEntity.badRequest().body("Invalid employee data.");
        }

        if (employeeList.stream().anyMatch(e -> e.getEmployeeID() == employee.getEmployeeID())) {
            return ResponseEntity.badRequest().body("Employee with the same ID already exists.");
        }

        employeeList.add(employee);
        saveEmployeesToJson();

        return ResponseEntity.ok("Employee added successfully.");
    }

    @GetMapping("/getEmployee")
    public ResponseEntity<?> getEmployee(
            @RequestParam(required = false) Integer employeeID,
            @RequestParam(required = false) String designation) {
        // Check if both parameters are null
        if (employeeID == null && designation == null) {
            return ResponseEntity.badRequest().body("Please provide either employeeID or designation.");
        }

        // Search by employeeID
        if (employeeID != null) {
            return getEmployeeById(employeeID);
        }

        // Search by designation
        if (designation != null) {
            return getEmployeesByDesignation(designation);
        }

        // This should not be reached, but return a bad request if none of the conditions match
        return ResponseEntity.badRequest().body("Invalid parameters.");
    }

    @DeleteMapping("/deleteEmployee/{employeeID}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int employeeID) {
        Iterator<Employee> iterator = employeeList.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getEmployeeID() == employeeID) {
                iterator.remove();
                saveEmployeesToJson();
                return ResponseEntity.ok("Employee deleted successfully.");
            }
        }
        // If no employee with the specified ID is found
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateEmployeeDesignation/{employeeID}")
    public ResponseEntity<String> updateEmployeeDesignation(
            @PathVariable int employeeID,
            @RequestBody Map<String, String> requestBody) {
        String newDesignation = requestBody.get("designation");

        for (Employee employee : employeeList) {
            if (employee.getEmployeeID() == employeeID) {
                employee.setDesignation(newDesignation);
                saveEmployeesToJson();
                return ResponseEntity.ok("Employee designation updated successfully.");
            }
        }
        // If no employee with the specified ID is found
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getEmployeesByLanguage")
    public ResponseEntity<?> getEmployeesByLanguage(
            @RequestParam String language,
            @RequestParam int score,
            @RequestParam(defaultValue = "asc") String order) {

        // Filter employees by language and score
        List<Employee> filteredEmployees = employeeList.stream()
                .filter(employee -> employee.getKnownLanguages().stream()
                        .anyMatch(knownLanguage -> knownLanguage.getLanguageName().equalsIgnoreCase(language)
                                && knownLanguage.getScoreOutof100() > score))
                .collect(Collectors.toList());

        // Sort the result based on the specified order and language scores
        if ("asc".equalsIgnoreCase(order)) {
            filteredEmployees.sort(Comparator.comparingInt((Employee employee) ->
                    employee.getScoreForLanguage(language)));
        } else if ("desc".equalsIgnoreCase(order)) {
            filteredEmployees.sort(Comparator.comparingInt((Employee employee) ->
                    employee.getScoreForLanguage(language)).reversed());
        } else {
            return ResponseEntity.badRequest().body("Invalid order parameter. Use 'asc' or 'desc'.");
        }

        if (!filteredEmployees.isEmpty()) {
            return ResponseEntity.ok(filteredEmployees);
        } else {
            // If no employee matches the criteria
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getEmployeeById/{employeeID}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int employeeID) {
        // Find and return the employee with the given ID
        for (Employee employee : employeeList) {
            if (employee.getEmployeeID() == employeeID) {
                return ResponseEntity.ok(employee);
            }
        }
        // If no employee with the specified ID is found
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<List<Employee>> getEmployeesByDesignation(String designation) {
        List<Employee> employeesWithDesignation = employeeList.stream()
                .filter(employee -> employee.getDesignation().equalsIgnoreCase(designation))
                .collect(Collectors.toList());

        if (!employeesWithDesignation.isEmpty()) {
            return ResponseEntity.ok(employeesWithDesignation);
        } else {
            // If no employee with the specified designation is found
            return ResponseEntity.notFound().build();
        }
    }
}


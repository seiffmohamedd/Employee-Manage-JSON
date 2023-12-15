package com.example.jsonemplmanagement;

import java.util.List;

public class Employee {


    private String firstName;
    private String lastName;
    private int employeeID;
    private String designation;
    private List<KnownLanguage> knownLanguages;

    public Employee() {
        // Default constructor
    }

    public Employee(String firstName, String lastName, int employeeID, String designation, List<KnownLanguage> knownLanguages) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeID = employeeID;
        this.designation = designation;
        this.knownLanguages = knownLanguages;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Employee(String designation) {
        this.designation = designation;
    }

    public List<KnownLanguage> getKnownLanguages() {
        return knownLanguages;
    }

    public void setKnownLanguages(List<KnownLanguage> knownLanguages) {
        this.knownLanguages = knownLanguages;
    }



}

class KnownLanguage {
    private String languageName;
    private int scoreOutof100;

    public KnownLanguage() {
        // Default constructor
    }

    public KnownLanguage(String languageName, int scoreOutof100) {
        this.languageName = languageName;
        this.scoreOutof100 = scoreOutof100;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getScoreOutof100() {
        return scoreOutof100;
    }

    public void setScoreOutof100(int scoreOutof100) {
        this.scoreOutof100 = scoreOutof100;
    }
    // Constructors, getters, and setters
}

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

    public boolean isValid() {
        return validateName(firstName) &&
                validateName(lastName) &&
                validateEmployeeID(employeeID) &&
                validateDesignation(designation) &&
                validateKnownLanguages(knownLanguages);
    }

    private boolean validateName(String name) {
        // Add your validation logic for the name (e.g., not null or empty)
        return name != null && !name.isEmpty();
    }

    private boolean validateEmployeeID(int employeeID) {
        // Add your validation logic for the employeeID (e.g., positive value)
        return employeeID > 0;
    }

    private boolean validateDesignation(String designation) {
        // Add your validation logic for the designation (e.g., not null or empty)
        return designation != null && !designation.isEmpty();
    }

    private boolean validateKnownLanguages(List<KnownLanguage> knownLanguages) {
        // Add your validation logic for the knownLanguages
        // For example, check if the list is not null and contains valid KnownLanguage objects
        return knownLanguages != null && knownLanguages.stream()
                .allMatch(this::validateKnownLanguage);
    }

    private boolean validateKnownLanguage(KnownLanguage knownLanguage) {
        // Add your validation logic for individual KnownLanguage objects
        // For example, check if languageName is not null or empty and scoreOutof100 is within a valid range
        return knownLanguage != null &&
                validateName(knownLanguage.getLanguageName()) &&
                knownLanguage.getScoreOutof100() >= 0 && knownLanguage.getScoreOutof100() <= 100;
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

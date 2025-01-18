package com.employee.performance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.performance.model.EmployeePerformance;
import com.employee.performance.repository.EmployeePerformanceRepository;

import java.util.*;

@Service
@Transactional
public class EmployeePerformanceService {

    @Autowired
    private EmployeePerformanceRepository employeePerformanceRepository;

    // Standard percentages for each rating category
    private static final Map<String, Double> STANDARD_PERCENTAGES = Map.of(
            "A", 10.0,
            "B", 20.0,
            "C", 40.0,
            "D", 20.0,
            "E", 10.0
    );

    /**
     * Fetch all employees from the database.
     * @return List of EmployeePerformance objects.
     */
    public List<EmployeePerformance> getAllEmployees() {
        return employeePerformanceRepository.findAll();
    }

    /**
     * Calculate the actual percentage distribution of ratings.
     * @return A map of rating categories and their actual percentages.
     */
    public Map<String, Double> calculateActualPercentage() {
        List<EmployeePerformance> employees = getAllEmployees();
        Map<String, Long> ratingCounts = new HashMap<>();

        // Initialize counts for each rating category
        STANDARD_PERCENTAGES.keySet().forEach(rating -> ratingCounts.put(rating, 0L));

        // Count occurrences of each rating
        for (EmployeePerformance employee : employees) {
            ratingCounts.put(employee.getRating(), ratingCounts.get(employee.getRating()) + 1);
        }

        // Calculate actual percentages
        Map<String, Double> actualPercentages = new HashMap<>();
        int totalEmployees = employees.size();
        for (Map.Entry<String, Long> entry : ratingCounts.entrySet()) {
            actualPercentages.put(entry.getKey(), (entry.getValue() * 100.0) / totalEmployees);
        }

        return actualPercentages;
    }

    /**
     * Calculate the deviation between actual and standard percentages.
     * @return A map of rating categories and their deviations.
     */
    public Map<String, Double> calculateDeviation() {
        Map<String, Double> actualPercentages = calculateActualPercentage();
        Map<String, Double> deviations = new HashMap<>();

        for (String category : STANDARD_PERCENTAGES.keySet()) {
            double deviation = actualPercentages.getOrDefault(category, 0.0) - STANDARD_PERCENTAGES.get(category);
            deviations.put(category, deviation);
        }

        return deviations;
    }

    /**
     * Suggest adjustments for employee ratings based on deviations.
     * @return A list of suggestions for adjustments.
     */
    public List<String> suggestAdjustments() {
        Map<String, Double> deviations = calculateDeviation();
        List<String> adjustments = new ArrayList<>();

        for (Map.Entry<String, Double> entry : deviations.entrySet()) {
            if (entry.getValue() > 0) {
                adjustments.add("Reduce " + entry.getKey() + " ratings by " + entry.getValue() + "%");
            } else if (entry.getValue() < 0) {
                adjustments.add("Increase " + entry.getKey() + " ratings by " + Math.abs(entry.getValue()) + "%");
            }
        }

        return adjustments;
    }

    /**
     * Save a new employee performance record to the database.
     * @param employeePerformance The EmployeePerformance object to be saved.
     * @return The saved EmployeePerformance object.
     */
    @Transactional
    public EmployeePerformance saveEmployeePerformance(EmployeePerformance employeePerformance) {
        return employeePerformanceRepository.saveAndFlush(employeePerformance);
    }

    /**
     * Save multiple employee performance records to the database.
     * @param employeePerformances A list of EmployeePerformance objects to be saved.
     * @return The list of saved EmployeePerformance objects.
     */
    @Transactional
    public List<EmployeePerformance> saveMultipleEmployeePerformances(List<EmployeePerformance> employeePerformances) {
        return employeePerformanceRepository.saveAllAndFlush(employeePerformances);
    }
}

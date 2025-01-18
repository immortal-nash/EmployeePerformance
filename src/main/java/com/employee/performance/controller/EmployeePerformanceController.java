package com.employee.performance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.employee.performance.model.EmployeePerformance;
import com.employee.performance.service.EmployeePerformanceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
public class EmployeePerformanceController {

    @Autowired
    private EmployeePerformanceService employeePerformanceService;

    /**
     * To fetch all employee performance data.
     * @return List of EmployeePerformance objects.
     */
    @GetMapping("/all")
    public List<EmployeePerformance> getAllEmployees() {
        return employeePerformanceService.getAllEmployees();
    }

    /**
     * To fetch the percentage distribution of ratings.
     * @return A map containing the rating categories and their respective percentages.
     */
    @GetMapping("/rating-distribution")
    public Map<String, Double> getRatingDistribution() {
        return employeePerformanceService.calculateActualPercentage();
    }

    /**
     * To get the deviations between actual and standard percentages.
     * @return A map showing the deviations for each rating category.
     */
    @GetMapping("/deviation")
    public Map<String, Double> getDeviationFromStandard() {
        return employeePerformanceService.calculateDeviation();
    }

    /**
     * To suggest adjustments based on performance deviations.
     * @return A list of suggestions for employee rating adjustments.
     */
    @GetMapping("/adjustments")
    public List<String> getAdjustments() {
        return employeePerformanceService.suggestAdjustments();
    }

    /**
     * To add a new employee performance record.
     * @param employeePerformance The EmployeePerformance object to be saved.
     * @return The saved EmployeePerformance object.
     */
    @PostMapping("/add")
    public EmployeePerformance addEmployeePerformance(@RequestBody EmployeePerformance employeePerformance) {
        return employeePerformanceService.saveEmployeePerformance(employeePerformance);
    }

    /**
     * To add multiple employee performance records.
     * @param employeePerformances A list of EmployeePerformance objects to be saved.
     * @return The list of saved EmployeePerformance objects.
     */
    @PostMapping("/add-multiple")
    public List<EmployeePerformance> addMultipleEmployeePerformances(@RequestBody List<EmployeePerformance> employeePerformances) {
        return employeePerformanceService.saveMultipleEmployeePerformances(employeePerformances);
    }
}

package com.employee.performance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.employee.performance.model.EmployeePerformance;
import com.employee.performance.repository.EmployeePerformanceRepository;
import com.employee.performance.service.EmployeePerformanceService;
 
public class EmployeePerformanceServiceTest {
 
    @Mock
    private EmployeePerformanceRepository employeePerformanceRepository;
 
    @InjectMocks
    private EmployeePerformanceService employeePerformanceService;
 
    private List<EmployeePerformance> mockEmployees;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockEmployees = new ArrayList<>();
        mockEmployees.add(new EmployeePerformance("John Doe", "A"));
        mockEmployees.add(new EmployeePerformance("Jane Smith", "B"));
        mockEmployees.add(new EmployeePerformance("Tom Brown", "C"));
        mockEmployees.add(new EmployeePerformance("Lucy Green", "B"));
        mockEmployees.add(new EmployeePerformance("Jack White", "A"));
    }
 
    @Test
    void testCalculateActualPercentage() {
        when(employeePerformanceRepository.findAll()).thenReturn(mockEmployees);
        
        Map<String, Double> actualPercentages = employeePerformanceService.calculateActualPercentage();
        
        assertEquals(40.0, actualPercentages.get("A"));
        assertEquals(40.0, actualPercentages.get("B"));
        assertEquals(20.0, actualPercentages.get("C"));
        assertEquals(0.0, actualPercentages.get("D"));
        assertEquals(0.0, actualPercentages.get("E"));
    }
 
    @Test
    void testCalculateDeviation() {
        when(employeePerformanceRepository.findAll()).thenReturn(mockEmployees);
        
        Map<String, Double> deviations = employeePerformanceService.calculateDeviation();
        
        assertEquals(30.0, deviations.get("A"));
        assertEquals(20.0, deviations.get("B"));
        assertEquals(-20.0, deviations.get("C"));
      
    }
 
    @Test
    void testSuggestAdjustments() {
        when(employeePerformanceRepository.findAll()).thenReturn(mockEmployees);
        
        List<String> adjustments = employeePerformanceService.suggestAdjustments();
        
        assertTrue(adjustments.contains("Reduce A ratings by 30.0%"));
        assertTrue(adjustments.contains("Increase C ratings by 20.0%"));
        assertTrue(adjustments.contains("Increase D ratings by 20.0%"));
        assertTrue(adjustments.contains("Increase E ratings by 10.0%"));
    }
 
    @Test
    void testSaveEmployeePerformance() {
        EmployeePerformance newEmployee = new EmployeePerformance("Mary Blue", "A");
        when(employeePerformanceRepository.save(any(EmployeePerformance.class))).thenReturn(newEmployee);
        
        EmployeePerformance savedEmployee = employeePerformanceService.saveEmployeePerformance(newEmployee);
        
        assertNotNull(savedEmployee);
        assertEquals("Mary Blue", savedEmployee.getEmployeeName());
        assertEquals("A", savedEmployee.getRating());
    }
 
    @Test
    void testSaveMultipleEmployeePerformances() {
        when(employeePerformanceRepository.saveAll(anyList())).thenReturn(mockEmployees);
        
        List<EmployeePerformance> savedEmployees = employeePerformanceService.saveMultipleEmployeePerformances(mockEmployees);
        
        assertNotNull(savedEmployees);
        assertEquals(5, savedEmployees.size());
    }
}

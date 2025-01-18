package com.employee.performance;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.employee.performance.controller.EmployeePerformanceController;
import com.employee.performance.model.EmployeePerformance;
import com.employee.performance.service.EmployeePerformanceService;

@WebMvcTest(EmployeePerformanceController.class)
public class EmployeePerformanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeePerformanceService employeePerformanceService;

    // Test for GET /api/employee/all
    @Test
    public void testGetAllEmployees() throws Exception {
        EmployeePerformance employee1 = new EmployeePerformance("John Doe", "A");
        EmployeePerformance employee2 = new EmployeePerformance("Jane Doe", "B");

        // Mock the service response
        when(employeePerformanceService.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/employee/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeName").value("John Doe"))
                .andExpect(jsonPath("$[1].employeeName").value("Jane Doe"));
    }

    // Test for GET /api/employee/rating-distribution
    @Test
    public void testGetRatingDistribution() throws Exception {
        // Mock the service response
        when(employeePerformanceService.calculateActualPercentage()).thenReturn(Map.of("A", 50.0, "B", 30.0, "C", 20.0));

        // Perform the GET request and check the response
        mockMvc.perform(get("/api/employee/rating-distribution"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.A").value(50.0))
                .andExpect(jsonPath("$.B").value(30.0))
                .andExpect(jsonPath("$.C").value(20.0));
    }

    // Test for POST /api/employee/add
    @Test
    public void testAddEmployeePerformance() throws Exception {
        EmployeePerformance employee = new EmployeePerformance("John Doe", "A");

        // Mock the service response
        when(employeePerformanceService.saveEmployeePerformance(employee)).thenReturn(employee);

        // Perform the POST request and check the response
        mockMvc.perform(post("/api/employee/add")
                .contentType("application/json")
                .content("{\"employeeName\": \"John Doe\", \"rating\": \"A\"}"))
                .andExpect(status().isOk());
                
    }

    // Test for POST /api/employee/add-multiple
    @Test
    public void testAddMultipleEmployeePerformances() throws Exception {
        EmployeePerformance employee1 = new EmployeePerformance("John Doe", "A");
        EmployeePerformance employee2 = new EmployeePerformance("Jane Doe", "B");
        List<EmployeePerformance> employees = Arrays.asList(employee1, employee2);

        // Mock the service response
        when(employeePerformanceService.saveMultipleEmployeePerformances(employees)).thenReturn(employees);

        // Perform the POST request and check the response
        mockMvc.perform(post("/api/employee/add-multiple")
                .contentType("application/json")
                .content("[{\"employeeName\": \"John Doe\", \"rating\": \"A\"}, {\"employeeName\": \"Jane Doe\", \"rating\": \"B\"}]"))
                .andExpect(status().isOk());
                
    }
}

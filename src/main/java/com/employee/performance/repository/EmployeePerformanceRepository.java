package com.employee.performance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.employee.performance.model.EmployeePerformance;

@Repository
public interface EmployeePerformanceRepository extends JpaRepository<EmployeePerformance, Long> {

    // Using saveAndFlush() ensures the entity is synchronized with the database immediately
    @Transactional
    default EmployeePerformance saveEmployeePerformance(EmployeePerformance employeePerformance) {
        return saveAndFlush(employeePerformance);
    }
}

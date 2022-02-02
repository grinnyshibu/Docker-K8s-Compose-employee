package com.qburst.employeedbmanager.dao;

import com.qburst.employeedbmanager.model.entities.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employees, Long> {
}
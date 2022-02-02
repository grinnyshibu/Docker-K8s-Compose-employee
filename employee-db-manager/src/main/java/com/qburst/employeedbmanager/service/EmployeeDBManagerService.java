package com.qburst.employeedbmanager.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qburst.employeedbmanager.dao.EmployeeDao;
import com.qburst.employeedbmanager.dto.EmployeeDTO;
import com.qburst.employeedbmanager.exception.EmployeeNotFoundException;
import com.qburst.employeedbmanager.model.entities.Employees;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeDBManagerService {

    private EmployeeDao employeeDao;

    private ObjectMapper objectMapper;

    public EmployeeDBManagerService(EmployeeDao employeeDao, ObjectMapper objectMapper) {
        this.employeeDao = employeeDao;
        this.objectMapper = objectMapper;
    }

    //Method to add a new employee
    public EmployeeDTO createEmployee(Employees emp) {
        Employees employee = employeeDao.save(emp);
        return objectMapper.convertValue(employee, EmployeeDTO.class);
    }

    //Method to fetch all employees
    public List<EmployeeDTO> getAllEmployees() throws EmployeeNotFoundException {
        List<Employees> employees = employeeDao.findAll();
        if (!CollectionUtils.isEmpty(employees))return objectMapper.convertValue(employees, new TypeReference<List<EmployeeDTO>>() {
        });
        else throw new EmployeeNotFoundException("No employees present");
    }

    //Method to fetch employee by id
    public Optional<EmployeeDTO> getEmployeeByID(long id) throws EmployeeNotFoundException {
        Optional<Employees> employee = employeeDao.findById(id);
        if (employee.isPresent()) return Optional.of(objectMapper.convertValue(employee, EmployeeDTO.class));
        else throw new EmployeeNotFoundException("Employee with " + id + " is not present");
    }
}

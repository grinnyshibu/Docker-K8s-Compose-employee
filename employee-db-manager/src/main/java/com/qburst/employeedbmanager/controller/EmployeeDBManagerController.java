package com.qburst.employeedbmanager.controller;

import com.qburst.employeedbmanager.dto.EmployeeDTO;
import com.qburst.employeedbmanager.exception.EmployeeNotFoundException;
import com.qburst.employeedbmanager.model.entities.Employees;
import com.qburst.employeedbmanager.service.EmployeeDBManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("employees/v1")
public class EmployeeDBManagerController {

    private EmployeeDBManagerService employeeDBManagerService;

    public EmployeeDBManagerController(EmployeeDBManagerService employeeDBManagerService) {
        this.employeeDBManagerService = employeeDBManagerService;
    }

    //API to fetch employee by id
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeByID(@PathVariable("id") long id) throws EmployeeNotFoundException {
        EmployeeDTO employee = employeeDBManagerService.getEmployeeByID(id).get();
        return ResponseEntity.ok().body(employee);
    }

    //API to fetch all employees
    @GetMapping()
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() throws EmployeeNotFoundException {
        List<EmployeeDTO> employeeDTOS = employeeDBManagerService.getAllEmployees();
        return ResponseEntity.ok().body(employeeDTOS);
    }

    //API to add a new employee
    @PostMapping()
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody Employees emp) {
        EmployeeDTO employeeDTO = employeeDBManagerService.createEmployee(emp);
        return ResponseEntity.ok().body(employeeDTO);
    }
}

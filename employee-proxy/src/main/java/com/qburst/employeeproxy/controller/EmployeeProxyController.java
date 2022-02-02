package com.qburst.employeeproxy.controller;

import com.qburst.employeeproxy.exception.EmployeeNotFoundException;
import com.qburst.employeeproxy.model.EmployeeDTO;
import com.qburst.employeeproxy.service.EmployeeProxyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

@RestController
@RequestMapping("employees/v1")
public class EmployeeProxyController {

    private EmployeeProxyService employeeProxyService;

    public EmployeeProxyController(EmployeeProxyService employeeProxyService) {
        this.employeeProxyService = employeeProxyService;
    }

    //API to create an employee
    @PostMapping()
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeDTO emp) {
        Optional<EmployeeDTO> employee = employeeProxyService.addNewEmployee(emp);
        return employee.map(value -> ResponseEntity.ok().body("Employee added with ID " + value.getEmpID()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //API to find employee with maximum salary for given month
    @GetMapping(value = "/salary/max", params = "month")
    public ResponseEntity<EmployeeDTO> getMaxSalaryEmployeeByMonth(@RequestParam String month) throws EmployeeNotFoundException {
        Optional<EmployeeDTO> employee = employeeProxyService.getMaxSalariedEmployeeByMonth(month);
        return employee.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //API to find employee with maximum salary for given year
    @GetMapping(value = "/salary/max", params = "year")
    public ResponseEntity<EmployeeDTO> getMaxSalaryEmployeeByYear(@RequestParam int year) throws EmployeeNotFoundException {
        Optional<EmployeeDTO> employee = employeeProxyService.getMaxSalariedEmployeeByYear(year);
        return employee.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //API to count employees in given pincode
    @GetMapping("/count")
    public ResponseEntity<String> getEmployeeCountByPincode(@RequestParam int pincode) throws EmployeeNotFoundException {
        long count = employeeProxyService.getEmployeeCountInPincode(pincode);
        return ResponseEntity.ok().body("Employee count is " + count);
    }

    //API to fetch employee ids in a given age range
    @GetMapping("/ids")
    public ResponseEntity<List<Long>> getEmployeeIDsByAgeGroup(@RequestParam int minAge,
                                                               @RequestParam int maxAge) throws EmployeeNotFoundException {
        List<Long> ids = employeeProxyService.getEmployeeIDsBetweenAgeGroup(minAge, maxAge);
        return ResponseEntity.ok().body(ids);
    }

    //API to fetch employee count in each districts
    @GetMapping("/district/count")
    public ResponseEntity<TreeMap<String, Long>> getEmployeeCountByDistrict() throws EmployeeNotFoundException {
        TreeMap<String, Long> employeeByDistrict = employeeProxyService.getEmployeeCountInDistrict();
        return ResponseEntity.ok().body(employeeByDistrict);
    }

    //API to fetch employee with minimum leave in current year
    @GetMapping("/leave/min")
    public ResponseEntity<EmployeeDTO> getEmployeeWithLeastLeaves() throws EmployeeNotFoundException {
        Optional<EmployeeDTO> employee = employeeProxyService.getEmployeeWithMinimumLeaves();
        return employee.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

package com.qburst.employeeproxy.service;

import com.qburst.employeeproxy.client.EmployeeDBManagerClient;
import com.qburst.employeeproxy.exception.EmployeeNotFoundException;
import com.qburst.employeeproxy.model.Employee;
import com.qburst.employeeproxy.model.EmployeeDTO;
import com.qburst.employeeproxy.model.SalaryDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.sql.Date.valueOf;
import static java.time.LocalDate.now;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.util.Collections.max;
import static java.util.Collections.min;

@Service
public class EmployeeProxyService {

    private EmployeeDBManagerClient employeeDBManagerClient;

    public EmployeeProxyService(EmployeeDBManagerClient employeeDBManagerClient) {
        this.employeeDBManagerClient = employeeDBManagerClient;
    }

    //Helps to add a new employee
    public Optional<EmployeeDTO> addNewEmployee(EmployeeDTO emp) {
        return employeeDBManagerClient.createEmployee(emp);
    }

    //Compute employee with max salary in given month
    public Optional<EmployeeDTO> getMaxSalariedEmployeeByMonth(String month) throws EmployeeNotFoundException {
        List<EmployeeDTO> employeeDTOS = employeeDBManagerClient.getAllEmployees();
        Optional<Double> maxSalary = employeeDTOS.stream().flatMap(employeeDTO -> employeeDTO.getSalary().stream()
                .filter(salaryDTO -> salaryDTO.getMonth().equals(month))).map(SalaryDTO::getTotal).max(Double::compare);

        return employeeDTOS.stream().filter(emp -> emp.getSalary().stream()
                .anyMatch(salaryDTO -> salaryDTO.getMonth().equals(month) && salaryDTO.getTotal().equals(maxSalary.get())))
                .findFirst();
    }

    //Compute employee with max salary in given year
    public Optional<EmployeeDTO> getMaxSalariedEmployeeByYear(int year) throws EmployeeNotFoundException {
        Map<EmployeeDTO, Double> maxSalary = new HashMap<>();
        List<EmployeeDTO> employeeDTOS = employeeDBManagerClient.getAllEmployees();

        employeeDTOS.forEach(employeeDTO -> employeeDTO.getSalary().forEach(salaryDTO -> {
            if (salaryDTO.getYear().equals(year))
                maxSalary.merge(employeeDTO, salaryDTO.getTotal(), Double::sum);
        }));

        if (!maxSalary.isEmpty())
            return Optional.of(max(maxSalary.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey());
        else throw new EmployeeNotFoundException("No employee salaries present");
    }

    //Compute employee count in given pincode
    public long getEmployeeCountInPincode(int pincode) throws EmployeeNotFoundException {
        List<EmployeeDTO> employeeDTOS = employeeDBManagerClient.getAllEmployees();
        return employeeDTOS.stream().filter(employeeDTO -> employeeDTO.getPermanentAddress().getPincode().equals(pincode)).count();
    }

    //finds employee IDs within age
    public List<Long> getEmployeeIDsBetweenAgeGroup(int minAge, int maxAge) throws EmployeeNotFoundException {
        List<EmployeeDTO> employeeDTOS = employeeDBManagerClient.getAllEmployees();
        return employeeDTOS.stream().filter(employeeDTO -> employeeDTO.getAge() > minAge && employeeDTO.getAge() < maxAge)
                .map(EmployeeDTO::getEmpID).collect(Collectors.toList());
    }

    //find employees in each district
    public TreeMap<String, Long> getEmployeeCountInDistrict() throws EmployeeNotFoundException {
        List<EmployeeDTO> employeeDTOS = employeeDBManagerClient.getAllEmployees();
        return employeeDTOS.stream()
                .collect(Collectors.groupingBy(employeeDTO -> employeeDTO.getPermanentAddress().getDistrict(),
                        TreeMap::new, Collectors.counting()));
    }

    //find employee with minimum leaves in current year
    public Optional<EmployeeDTO> getEmployeeWithMinimumLeaves() throws EmployeeNotFoundException {
        Map<EmployeeDTO, Integer> minLeaves = new HashMap<>();
        List<EmployeeDTO> employeeDTOS = employeeDBManagerClient.getAllEmployees();

        employeeDTOS.forEach(employeeDTO -> employeeDTO.getLeaves().forEach(leaves -> {
            if (leaves.getFromDate().after(valueOf(now().with(firstDayOfYear())))
                    && leaves.getToDate().before(valueOf(now().with(lastDayOfYear()))))
                minLeaves.merge(employeeDTO, leaves.getNumberOfDays(), Integer::sum);
        }));
        if (!minLeaves.isEmpty())
            return Optional.of(min(minLeaves.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey());
        return Optional.empty();
    }
}

package com.qburst.employeeproxy.client;

import com.qburst.employeeproxy.exception.EmployeeNotFoundException;
import com.qburst.employeeproxy.model.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeDBManagerClient {

    @Value("${base.url}")
    private String baseURL;
    @Value("${get.employees.url}")
    private String getEmployees;
    @Value("${add.employee.url}")
    private String addEmployee;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;

    //calls DBManager to get employee by ID
    public Optional<EmployeeDTO> getEmployeeByID(long id) throws HttpStatusCodeException {
        ResponseEntity<EmployeeDTO> response = restTemplate.getForEntity(baseURL + id, EmployeeDTO.class);
        return Optional.ofNullable(response.getBody());
    }

    //calls DB Manager to get employee list
    public List<EmployeeDTO> getAllEmployees() throws HttpStatusCodeException, EmployeeNotFoundException {
        ResponseEntity<List<EmployeeDTO>> employees = restTemplate.exchange(baseURL + getEmployees, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<EmployeeDTO>>() {
                });
        if (!CollectionUtils.isEmpty(employees.getBody())) {
            return employees.getBody();
        } else throw new EmployeeNotFoundException("No employees present");
    }

    //calls DB Manager to add a new employee
    public Optional<EmployeeDTO> createEmployee(EmployeeDTO emp) throws HttpStatusCodeException {
        ResponseEntity<EmployeeDTO> response = restTemplate.postForEntity(baseURL + addEmployee, emp, EmployeeDTO.class);
        return Optional.ofNullable(response.getBody());
    }
}

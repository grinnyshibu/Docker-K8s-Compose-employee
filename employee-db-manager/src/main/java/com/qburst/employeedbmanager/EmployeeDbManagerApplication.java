package com.qburst.employeedbmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.qburst.employeedbmanager.dao")
@SpringBootApplication
public class EmployeeDbManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeDbManagerApplication.class, args);
    }
}

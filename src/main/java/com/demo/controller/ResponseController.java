package com.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/responses")
public class ResponseController {
    @GetMapping({"", "/"})
    public ResponseEntity<Employee> main() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Nikki Nicholas");
        employee.setMiddleName("Domingo");
        employee.setLastName("Romero");
        employee.setSalary(15000);
        employee.setSomeDate(LocalDate.of(2020, 7, 4));
        employee.setSomeTime(LocalTime.of(12, 2));
        employee.setSomeDatetime(LocalDateTime.of(2020, 7, 4, 12, 2));
        employee.setActive(true);

        HttpHeaders headers = new HttpHeaders();
        headers.add("some-header", "some-value");

        return new ResponseEntity<>(employee, headers, HttpStatus.OK);
    }
}

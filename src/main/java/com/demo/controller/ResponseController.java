package com.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/responses")
public class ResponseController {
    @GetMapping({"", "/"})
    public ResponseEntity<Employee> main() {
        Employee employee = new Employee();
        employee.setId(1);

        HttpHeaders headers = new HttpHeaders();
        headers.add("some-header", "some-value");

        return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);
    }

}

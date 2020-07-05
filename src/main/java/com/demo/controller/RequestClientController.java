package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/client/requests")
public class RequestClientController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/withRequestParam")
    public String withRequestParam() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/requests")
                .queryParam("name", "Nikki");

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        null,
                        String.class);

        return responseEntity.getBody();
    }

    @GetMapping("/withPathVariable")
    public String withPathVariable() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/requests/path");

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        null,
                        String.class);

        return responseEntity.getBody();
    }

    @GetMapping("/withRequestBody")
    public Employee withRequestBody() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/requests/employee");

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
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<Employee> httpEntity = new HttpEntity<>(employee, headers);

        ResponseEntity<Employee> responseEntity =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.POST,
                        httpEntity,
                        Employee.class);

        return responseEntity.getBody();
    }

    @GetMapping("/withHeader")
    public String withHeader() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/requests/withHeader");

        HttpHeaders headers = new HttpHeaders();
        headers.add("some-header", "some-header-value");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        httpEntity,
                        String.class);

        return responseEntity.getBody();
    }

    @GetMapping("/withHeaders")
    public Map<String, String> withHeaders() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/requests/withHeaders");

        HttpHeaders headers = new HttpHeaders();
        headers.add("some-header1", "some-header-value1");
        headers.add("some-header2", "some-header-value2");

        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, String>> responseEntity =
                restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<Map<String, String>>() {
                        });

        return responseEntity.getBody();
    }
}

package com.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/requests")
public class RequestController {
    @GetMapping({"", "/"})
    public void main(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        System.out.println(name);
    }

    @GetMapping({"/{path}","/{path}/"})
    public void withPathVariable(@PathVariable String path) {
        System.out.println(path);
    }

    @GetMapping({"/employee"})
    public void withRequestBody(@RequestBody Employee employee) {
        System.out.println(employee);
    }

    @GetMapping("/withHeader")
    public void withHeader(@RequestHeader(value = "some-header") String someHeader) {
        System.out.println(someHeader);
    }

    @GetMapping("/withHeaders")
    public void withHeader(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
    }
}

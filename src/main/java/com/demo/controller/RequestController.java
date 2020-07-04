package com.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/requests")
public class RequestController {
    @GetMapping({"", "/"})
    public String withRequestParam(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return name;
    }

    @GetMapping({"/{path}", "/{path}/"})
    public String withPathVariable(@PathVariable String path) {
        return path;
    }

    @GetMapping({"/employee"})
    public Employee withRequestBody(@RequestBody Employee employee) {
        return employee;
    }

    @GetMapping("/withHeader")
    public String withHeader(@RequestHeader(value = "some-header") String someHeader) {
        return someHeader;
    }

    @GetMapping("/withHeaders")
    public Map<String, String> withHeader(@RequestHeader Map<String, String> headers) {
        return headers;
    }
}

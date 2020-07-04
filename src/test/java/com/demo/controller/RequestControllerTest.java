package com.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RequestController.class)
public class RequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void main() throws Exception {
        this.mockMvc.perform(get("/requests"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(equalTo("World")));
    }

    @Test
    public void mainWithRequestParam() throws Exception {
        this.mockMvc.perform(get("/requests?name=Nikki"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(equalTo("Nikki")));
    }

    @Test
    public void withPathVariable() throws Exception {
        this.mockMvc.perform(get("/requests/Nikki"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(equalTo("Nikki")));
    }

    @Test
    public void withRequestBody() throws Exception {
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

        this.mockMvc.perform(get("/requests/employee")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(employee)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("firstName", is("Nikki Nicholas")))
                .andExpect(jsonPath("middleName", is("Domingo")))
                .andExpect(jsonPath("lastName", is("Romero")))
                .andExpect(jsonPath("salary", is(15000.0)))
                .andExpect(jsonPath("someDate", is("2020-07-04")))
                .andExpect(jsonPath("someTime", is("12:02:00")))
                .andExpect(jsonPath("someDatetime", is("2020-07-04T12:02:00")))
                .andExpect(jsonPath("active", is(true)));
    }

    @Test
    public void withHeader() throws Exception {
        this.mockMvc.perform(get("/requests/withHeader")
                .header("some-header", "Nikki"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(equalTo("Nikki")));
    }

    @Test
    public void withHeaders() throws Exception {
        this.mockMvc.perform(get("/requests/withHeaders")
                .header("some-header1", "Nikki")
                .header("some-header2", "Leslie"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("some-header1", is("Nikki")))
                .andExpect(jsonPath("some-header2", is("Leslie")));
    }
}

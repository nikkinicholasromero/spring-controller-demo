package com.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RequestClientController.class)
public class RequestClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity<String> stringResponseEntity;

    @Mock
    private ResponseEntity<Employee> employeeResponseEntity;

    @Mock
    private ResponseEntity<Map<String, String>> mapResponseEntity;

    @Captor
    private ArgumentCaptor<HttpEntity<?>> httpEntityArgumentCaptor;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void withRequestParam() throws Exception {
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<String>>any())).thenReturn(stringResponseEntity);

        when(stringResponseEntity.getBody()).thenReturn("Nikki");

        this.mockMvc.perform(get("/client/requests/withRequestParam"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(equalTo("Nikki")));

        verify(restTemplate, times(1))
                .exchange(
                        eq("http://localhost:8080/requests?name=Nikki"),
                        eq(HttpMethod.GET),
                        eq(null),
                        eq(String.class));
    }

    @Test
    public void withPathVariable() throws Exception {
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<String>>any())).thenReturn(stringResponseEntity);

        when(stringResponseEntity.getBody()).thenReturn("Nikki");

        this.mockMvc.perform(get("/client/requests/withPathVariable"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(equalTo("Nikki")));

        verify(restTemplate, times(1))
                .exchange(
                        eq("http://localhost:8080/requests/path"),
                        eq(HttpMethod.GET),
                        eq(null),
                        eq(String.class));
    }

    @Test
    public void withRequestBody() throws Exception {
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<Employee>>any())).thenReturn(employeeResponseEntity);

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

        when(employeeResponseEntity.getBody()).thenReturn(employee);

        this.mockMvc.perform(get("/client/requests/withRequestBody"))
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

        verify(restTemplate, times(1))
                .exchange(
                        eq("http://localhost:8080/requests/employee"),
                        eq(HttpMethod.POST),
                        httpEntityArgumentCaptor.capture(),
                        eq(Employee.class));

        HttpEntity<?> httpEntity = httpEntityArgumentCaptor.getValue();
        assertThat(httpEntity).isNotNull();
        assertThat(httpEntity.getHeaders()).isNotNull();
        assertThat(httpEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(httpEntity.getHeaders().getAccept()).isEqualTo(Arrays.asList(MediaType.APPLICATION_JSON));
    }

    @Test
    public void withHeader() throws Exception {
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<Class<String>>any())).thenReturn(stringResponseEntity);

        when(stringResponseEntity.getBody()).thenReturn("some-header-value");

        this.mockMvc.perform(get("/client/requests/withHeader"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(equalTo("some-header-value")));

        verify(restTemplate, times(1))
                .exchange(
                        eq("http://localhost:8080/requests/withHeader"),
                        eq(HttpMethod.GET),
                        httpEntityArgumentCaptor.capture(),
                        eq(String.class));

        HttpEntity<?> httpEntity = httpEntityArgumentCaptor.getValue();
        assertThat(httpEntity).isNotNull();
        assertThat(httpEntity.getHeaders()).isNotNull();
        assertThat(httpEntity.getHeaders().containsKey("some-header")).isTrue();
        assertThat(httpEntity.getHeaders().get("some-header").size()).isEqualTo(1);
        assertThat(httpEntity.getHeaders().get("some-header").get(0)).isEqualTo("some-header-value");
    }

    @Test
    public void withHeaders() throws Exception {
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(),
                ArgumentMatchers.<ParameterizedTypeReference<Map<String, String>>>any())).thenReturn(mapResponseEntity);

        Map<String, String> map = new HashMap<>();
        map.put("some-header1", "some-header-value1");
        map.put("some-header12", "some-header-value2");

        when(mapResponseEntity.getBody()).thenReturn(map);

        this.mockMvc.perform(get("/client/requests/withHeaders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(objectMapper.writeValueAsString(map))));

        verify(restTemplate, times(1))
                .exchange(
                        eq("http://localhost:8080/requests/withHeaders"),
                        eq(HttpMethod.GET),
                        httpEntityArgumentCaptor.capture(),
                        ArgumentMatchers.<ParameterizedTypeReference<Map<String, String>>>any());

        HttpEntity<?> httpEntity = httpEntityArgumentCaptor.getValue();
        assertThat(httpEntity).isNotNull();
        assertThat(httpEntity.getHeaders()).isNotNull();
        assertThat(httpEntity.getHeaders().containsKey("some-header1")).isTrue();
        assertThat(httpEntity.getHeaders().get("some-header1").size()).isEqualTo(1);
        assertThat(httpEntity.getHeaders().get("some-header1").get(0)).isEqualTo("some-header-value1");
        assertThat(httpEntity.getHeaders().containsKey("some-header2")).isTrue();
        assertThat(httpEntity.getHeaders().get("some-header2").size()).isEqualTo(1);
        assertThat(httpEntity.getHeaders().get("some-header2").get(0)).isEqualTo("some-header-value2");
    }
}

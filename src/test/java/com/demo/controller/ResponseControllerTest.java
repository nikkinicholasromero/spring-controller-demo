package com.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResponseController.class)
public class ResponseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void main() throws Exception {
        this.mockMvc.perform(get("/responses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("some-header", "some-value"))
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
}

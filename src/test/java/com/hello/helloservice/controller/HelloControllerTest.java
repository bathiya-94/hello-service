package com.hello.helloservice.controller;

import com.hello.helloservice.exception.InvalidNameException;
import com.hello.helloservice.service.HelloService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HelloService helloService;

    @Test
    @DisplayName("GET /hello-world?name=alice returns 200 OK and JSON body")
    void getGreeting_ValidName_Returns200AndMessage() throws Exception {
        when(helloService.generateGreeting("alice")).thenReturn("Hello Alice");

        mockMvc.perform(get("/hello-world").param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Hello Alice"));
    }

    @Test
    @DisplayName("GET /hello-world without param returns 400 Bad Request with error body")
    void getGreeting_MissingNameParam_Returns400AndError() throws Exception {
        when(helloService.generateGreeting(null))
                .thenThrow(new InvalidNameException("Invalid Input"));

        mockMvc.perform(get("/hello-world"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }
}

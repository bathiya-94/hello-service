package com.hello.helloservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HelloServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Integration: GET /hello-world?name=alice -> 200 OK")
	void testValidGreeting() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "alice"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Hello Alice"));
	}

	@Test
	@DisplayName("Integration: GET /hello-world?name=nancy -> 400 Bad Request")
	void testInvalidGreetingSecondHalf() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "nancy"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}

	@Test
	@DisplayName("Integration: GET /hello-world (missing param) -> 400 Bad Request")
	void testMissingParam() throws Exception {
		mockMvc.perform(get("/hello-world"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}

	@Test
	@DisplayName("Integration: GET /hello-world?name= -> 400 Bad Request")
	void testEmptyParam() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", ""))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}

	@Test
	@DisplayName("Integration: GET /hello-world?name=alice bob (multi-word) -> 400 Bad Request")
	void testMultiWordName() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "alice bob"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}

	@Test
	@DisplayName("Integration: GET /hello-world?name=alice-bob (special char) -> 400 Bad Request")
	void testSpecialCharactersInName() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "alice-bob"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}

	@Test
	@DisplayName("Integration: GET /hello-world?name=123 -> 400 Bad Request")
	void testNonAlphabeticChar() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "123"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}
}
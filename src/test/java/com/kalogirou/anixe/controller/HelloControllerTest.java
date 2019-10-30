package com.kalogirou.anixe.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class HelloControllerTest {

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		HelloController helloController = new HelloController();
		this.mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
	}

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/hello")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("hello anixe!")));
	}
}

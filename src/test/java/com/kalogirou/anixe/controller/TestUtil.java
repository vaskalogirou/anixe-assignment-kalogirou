package com.kalogirou.anixe.controller;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtil {
	private static final ObjectMapper mapper = createObjectMapper();

	private static ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		return mapper.writeValueAsBytes(object);
	}
}

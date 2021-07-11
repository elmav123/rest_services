/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ayo.conversion.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class ConversionControllerTests {

	//inject context
	@Autowired
	private WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;

	@Test
	public void convertLengthMetricToImperial() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult result = mockMvc.perform(get("/convert/{source_unit}/to/{target_unit}/{value}", "m", "in", 1))
				.andExpect(status().isOk())
				.andReturn();

		MockHttpServletResponse content = result.getResponse();
		JSONObject testV=new JSONObject(content.getContentAsString());

		Assertions.assertEquals(39.3700787, testV.getDouble("result"));
	}

	@Test
	public void convertLengthImperialToMetric() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult result = mockMvc.perform(get("/convert/{source_unit}/to/{target_unit}/{value}", "ft", "km", "1"))
				.andExpect(status().isOk())
				.andReturn();

		MockHttpServletResponse content = result.getResponse();
		JSONObject testV=new JSONObject(content.getContentAsString());

		Assertions.assertEquals(0.0003048, testV.getDouble("result"));
	}

	@Test
	public void testConvertWithSpaces() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult result = mockMvc.perform(get("/convert/{source_unit}/to/{target_unit}/{value}", "sq ft", "yd2", "1"))
				.andExpect(status().isOk())
				.andReturn();

		MockHttpServletResponse content = result.getResponse();
		JSONObject testV=new JSONObject(content.getContentAsString());

		Assertions.assertEquals(0.111111111, testV.getDouble("result"));
	}

	@Test
	public void convertNotFound() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		mockMvc.perform(get("/convert/{source_unit}/to/{target_unit}/{value}", "abc", "def", "1"))
				.andExpect(status().isNotFound())
				.andReturn();

	}

	@Test
	public void convertInvalidValue() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		mockMvc.perform(get("/convert/{source_unit}/to/{target_unit}/{value}", "ft", "km", "abc"))
				.andExpect(status().isBadRequest())
				.andReturn();

	}

	@Test
	public void convertTooBigValue() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		mockMvc.perform(get("/convert/{source_unit}/to/{target_unit}/{value}", "ft", "km", "0.1000000000000000055511151231257827021181583404541015625"))
				.andExpect(status().isOk())
				.andReturn();

	}
	@Test
	public void convertCelciusToFahrenheit() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult result = mockMvc.perform(get("/convert/{source_unit}/to/{target_unit}/{value}", "c", "f", "1"))
				.andExpect(status().isOk())
				.andReturn();

		MockHttpServletResponse content = result.getResponse();
		JSONObject testV=new JSONObject(content.getContentAsString());

		Assertions.assertEquals(33.8, testV.getDouble("result"));
	}

	@Test
	public void convertFahrenheitToCelcius() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult result = mockMvc.perform(get("/convert/{source_unit}/to/{target_unit}/{value}", "f", "c", "1"))
				.andExpect(status().isOk())
				.andReturn();

		MockHttpServletResponse content = result.getResponse();
		JSONObject testV=new JSONObject(content.getContentAsString());

		Assertions.assertEquals(-17.22, testV.getDouble("result"));
	}


}

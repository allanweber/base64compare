package com.waes.base64compare.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.base64compare.Application;
import com.waes.base64compare.domain.dto.JsonData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class DiffControllerIntegratedTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void should_return_status_201_for_sendLeft() throws Exception {
        JsonData data = new JsonData("123456");
        String json = mapper.writeValueAsString(data);
        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/left")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_return_status_400_for_sendLeft_with_invalid_body() throws Exception {
        JsonData data = new JsonData();
        String json = mapper.writeValueAsString(data);
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/left")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue( result.getResponse().getContentAsString().contains("Validation failed for argument"));
    }

    @Test
    public void should_return_status_201_for_sendRight() throws Exception {
        JsonData data = new JsonData("123456");
        String json = mapper.writeValueAsString(data);
        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/right")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_return_status_400_for_sendRight_with_invalid_body() throws Exception {
        JsonData data = new JsonData();
        String json = mapper.writeValueAsString(data);
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/right")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue( result.getResponse().getContentAsString().contains("Validation failed for argument"));
    }

    @Test
    public void should_return_200_for_getResult() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/v1/diff/123"))
                .andExpect(status().isOk());
    }
}
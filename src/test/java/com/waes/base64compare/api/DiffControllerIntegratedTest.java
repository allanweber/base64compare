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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

        assertTrue(result.getResponse().getContentAsString().contains("Validation failed for argument"));
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

        assertTrue(result.getResponse().getContentAsString().contains("Validation failed for argument"));
    }

    @Test
    public void should_return_400_for_getResult_and_id_does_not_exist() throws Exception {
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/v1/diff/123"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Diff 123 id does not exist."));
    }

    @Test
    public void should_return_status_400_for_invalid_base64_when_sendLeft() throws Exception {
        JsonData data = new JsonData("%123%");
        String json = mapper.writeValueAsString(data);
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/left")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("The value must be a valid base 64 string."));
    }

    @Test
    public void should_return_status_400_for_invalid_base64_when_sendRight() throws Exception {
        JsonData data = new JsonData("%123%");
        String json = mapper.writeValueAsString(data);
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/right")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("The value must be a valid base 64 string."));
    }

    @Test
    public void should_throw_exception_when_send_only_left_and_try_get_differences() throws Exception {
        String base64 = Base64.getEncoder().encodeToString("Allan Weber".getBytes());
        JsonData data = new JsonData(base64);
        String json = mapper.writeValueAsString(data);

        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/left")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/v1/diff/123"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("The entity 123 does not have the Right side."));
    }

    @Test
    public void should_throw_exception_when_send_only_right_and_try_get_differences() throws Exception {
        String base64 = Base64.getEncoder().encodeToString("Allan Weber".getBytes());
        JsonData data = new JsonData(base64);
        String json = mapper.writeValueAsString(data);

        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/right")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/v1/diff/123"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("The entity 123 does not have the Left side."));
    }

    @Test
    public void should_return_response_for_equals_base_64() throws Exception {
        String base64 = Base64.getEncoder().encodeToString("Allan Weber".getBytes());
        JsonData data = new JsonData(base64);
        String json = mapper.writeValueAsString(data);

        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/left")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/right")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mvc.perform(
                MockMvcRequestBuilders.get("/v1/diff/123"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(123))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equal").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equalSize").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.left").value(base64))
                .andExpect(MockMvcResultMatchers.jsonPath("$.right").value(base64));
    }

    @Test
    public void should_return_response_with_false_for_equal_and_equalSize() throws Exception {
        String base64Left = Base64.getEncoder().encodeToString("Allan Weber".getBytes());
        String base64Right = Base64.getEncoder().encodeToString("Allan Cassiano Weber".getBytes());

        String jsonLeft = mapper.writeValueAsString(new JsonData(base64Left));
        String jsonRight = mapper.writeValueAsString(new JsonData(base64Right));

        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/left")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLeft)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/right")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRight)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


        mvc.perform(
                MockMvcRequestBuilders.get("/v1/diff/123"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(123))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equal").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equalSize").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.left").value(base64Left))
                .andExpect(MockMvcResultMatchers.jsonPath("$.right").value(base64Right));
    }

    @Test
    public void should_return_response_with_false_differences_list() throws Exception {
        String base64Left = Base64.getEncoder().encodeToString("Allan Weber Cassiano".getBytes());
        String base64Right = Base64.getEncoder().encodeToString("Allan Cassiano Weber".getBytes());

        String jsonLeft = mapper.writeValueAsString(new JsonData(base64Left));
        String jsonRight = mapper.writeValueAsString(new JsonData(base64Right));

        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/left")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLeft)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/123/right")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRight)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


        mvc.perform(
                MockMvcRequestBuilders.get("/v1/diff/123"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(123))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equal").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equalSize").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.left").value(base64Left))
                .andExpect(MockMvcResultMatchers.jsonPath("$.right").value(base64Right))
                .andExpect(MockMvcResultMatchers.jsonPath("$.differences").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.differences", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.differences[0].position", is(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.differences[0].offSet", is(14)));
    }
}
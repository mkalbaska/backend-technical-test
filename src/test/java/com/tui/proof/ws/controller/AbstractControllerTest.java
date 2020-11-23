package com.tui.proof.ws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.proof.ws.dto.LoginRequest;
import com.tui.proof.ws.dto.LoginResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@ComponentScan("com.tui.proof.ws")
public abstract class AbstractControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;

    protected String login() throws Exception {

        final String response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .content(objectMapper.writeValueAsString(new LoginRequest("root", "root")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(response, LoginResponse.class).getToken();

    }

}

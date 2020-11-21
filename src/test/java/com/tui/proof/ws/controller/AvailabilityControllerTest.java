package com.tui.proof.ws.controller;

import com.tui.proof.ws.service.AvailabilityService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Base64Utils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AvailabilityController.class)
@ComponentScan("com.tui.proof.ws")
class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AvailabilityService availabilityService;

    @Test
    void availabilityRequestIsInvalid() throws Exception {

        String request = "{" +
                "\"airportOrigin\": \"origin\", " +
                "\"dateFrom\": \"2020-11-20\", " +
                "\"dateTo\": \"2020-11-20\", " +
                "\"paxes\": { \"infants\": 0, \"children\": 0, \"adults\": 0}" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/availability")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("root:root".getBytes()))
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.airportDestination", Is.is("must not be empty")));
    }

    @Test
    void availabilityRequestIsValid() throws Exception {

        String request = "{" +
                "\"airportOrigin\": \"origin\", " +
                "\"airportDestination\": \"destination\", " +
                "\"dateFrom\": \"2020-11-20\", " +
                "\"dateTo\": \"2020-11-20\", " +
                "\"paxes\": { \"infants\": 0, \"children\": 0, \"adults\": 0}" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/availability")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("root:root".getBytes()))
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
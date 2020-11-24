package com.tui.proof.ws.controller;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AvailabilityController.class)
class AvailabilityControllerTest extends AbstractControllerTest {

    @Test
    void availabilityRequestIsInvalid() throws Exception {

        String request = "{" +
                "\"airportOrigin\": \"origin\", " +
                "\"dateFrom\": \"2020-11-20\", " +
                "\"dateTo\": \"2020-11-20\", " +
                "\"paxes\": { \"infants\": 0, \"children\": 0, \"adults\": 0}" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/availability")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + login())
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
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + login())
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
package com.tui.proof.ws.controller;

import com.tui.proof.ws.dto.Availability;
import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.service.AvailabilityService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
@OpenAPIDefinition
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping("/availability")
    @Operation(summary = "Get list of available flights",
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Availability.class))}),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found flights",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Flight.class)))
                            }),
                    @ApiResponse(responseCode = "400", description = "Availability request is invalid",
                            content = @Content)
            },
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    List<Flight> availability(@RequestBody @Valid Availability availability) {
        return availabilityService.flight(availability);
    }

}

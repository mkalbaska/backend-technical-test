package com.tui.proof.ws.controller;

import com.tui.proof.ws.dto.Availability;
import com.tui.proof.ws.dto.Flight;
import com.tui.proof.ws.service.AvailabilityService;
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
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping("/availability")
    List<Flight> availability(@RequestBody @Valid Availability availability) {
        return availabilityService.flight(availability);
    }

}

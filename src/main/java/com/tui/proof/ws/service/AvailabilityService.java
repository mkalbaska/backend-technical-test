package com.tui.proof.ws.service;

import com.tui.proof.ws.dto.Availability;
import com.tui.proof.ws.dto.Flight;

import java.util.List;

public interface AvailabilityService {

    List<Flight> flight(Availability availabilityRequest);

}

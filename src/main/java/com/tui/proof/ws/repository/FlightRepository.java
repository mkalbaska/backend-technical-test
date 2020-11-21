package com.tui.proof.ws.repository;

import com.tui.proof.ws.dto.Flight;

import java.util.List;

public interface FlightRepository {

    List<Flight> flight();
    boolean isValid(Flight flight);
    void deleteFlight(Flight flight);

}

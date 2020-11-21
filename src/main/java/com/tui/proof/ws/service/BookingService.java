package com.tui.proof.ws.service;

import com.tui.proof.ws.dto.Booking;
import com.tui.proof.ws.dto.Flight;

public interface BookingService {

    Booking findById(Long id);
    Booking add(Booking booking);

    void confirm(Long booking);
    void addFlight(Long id, Flight flight);

    void delete(Long id);

    void deleteFlight(Long id, Flight flight);
}

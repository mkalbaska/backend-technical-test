package com.tui.proof.ws.repository;

import com.tui.proof.ws.dto.Booking;

public interface BookingRepository {

    Booking add(Booking booking);
    Booking findById(Long id);

    void delete(Long booking);
}

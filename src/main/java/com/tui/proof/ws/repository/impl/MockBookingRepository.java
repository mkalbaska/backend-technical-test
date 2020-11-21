package com.tui.proof.ws.repository.impl;

import com.tui.proof.ws.dto.Booking;
import com.tui.proof.ws.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class MockBookingRepository implements BookingRepository {

    private final static List<Booking> bookings = new ArrayList<>();

    @Override
    public Booking add(Booking booking) {
        booking.setId((long) new Random().nextInt(Integer.MAX_VALUE));
        bookings.add(booking);
        return booking;
    }

    @Override
    public Booking findById(Long id) {
        return bookings.stream().filter(it -> id.equals(it.getId())).findFirst().orElse(null);
    }

    @Override
    public void delete(Long id) {
        bookings.removeIf(it -> id.equals(it.getId()));
    }
}

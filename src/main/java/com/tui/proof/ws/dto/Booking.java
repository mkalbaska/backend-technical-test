package com.tui.proof.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tui.proof.ws.dto.validation.IsAvailableFlight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@Data
public class Booking {

    private Long id;
    @NotNull
    private final Holder holder;
    @NotEmpty
    private final List<@IsAvailableFlight Flight> flights;
}

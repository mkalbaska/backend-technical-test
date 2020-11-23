package com.tui.proof.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@Data
public class Holder {
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String lastName;
    @NotEmpty
    private final String address;
    @NotEmpty
    private final String postalCode;
    @NotEmpty
    private final String country;
    @NotEmpty
    @Email
    private final String email;

    @NotEmpty
    private final List<@NotEmpty String> telephones;
}

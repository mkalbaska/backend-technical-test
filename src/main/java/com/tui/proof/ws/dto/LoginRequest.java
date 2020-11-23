package com.tui.proof.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private final String username;
    @NotBlank
    private final String password;

}

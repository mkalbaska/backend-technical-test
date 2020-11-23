package com.tui.proof.ws.controller;

import com.tui.proof.ws.dto.LoginRequest;
import com.tui.proof.ws.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@AllArgsConstructor
public class LoginController {


    private final LoginService loginService;

    @PostMapping("auth/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequest request) {
        try {
           return ResponseEntity.ok(loginService.login(request));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

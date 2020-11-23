package com.tui.proof.ws.service;

import com.tui.proof.ws.dto.LoginRequest;
import com.tui.proof.ws.dto.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest request);

}

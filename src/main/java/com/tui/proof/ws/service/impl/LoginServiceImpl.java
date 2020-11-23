package com.tui.proof.ws.service.impl;

import com.tui.proof.ws.dto.LoginRequest;
import com.tui.proof.ws.dto.LoginResponse;
import com.tui.proof.ws.service.LoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    @Value("${jwt.issuer}")
    private String jwtIssuer;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(), request.getPassword()
                        )
                );

        User user = (User) authenticate.getPrincipal();
        return new LoginResponse(generateAccessToken(user));
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(format("%s", user.getUsername()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}

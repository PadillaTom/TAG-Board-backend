package com.padillatom.TAG_Board.controller;

import com.padillatom.TAG_Board.dto.request.AuthenticationRequest;
import com.padillatom.TAG_Board.dto.response.AuthenticationResponse;
import com.padillatom.TAG_Board.service.CustomAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final CustomAuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody String user) {
        return "Hola";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest user) {
        return authenticationService.login(user);
    }

}

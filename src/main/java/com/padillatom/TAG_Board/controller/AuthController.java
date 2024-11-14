package com.padillatom.TAG_Board.controller;

import com.padillatom.TAG_Board.config.AppConstants;
import com.padillatom.TAG_Board.dto.request.AuthenticationRequest;
import com.padillatom.TAG_Board.dto.request.RegisterRequest;
import com.padillatom.TAG_Board.dto.response.AuthenticationResponse;
import com.padillatom.TAG_Board.service.CustomAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

    private final CustomAuthenticationService authenticationService;

    @PostMapping(AppConstants.REGISTER_API_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse register(@Valid @RequestBody RegisterRequest user) {
        return authenticationService.register(user);
    }

    @PostMapping(AppConstants.LOGIN_API_URL)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest user) {
        return authenticationService.login(user);
    }
}

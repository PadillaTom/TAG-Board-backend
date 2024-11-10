package com.padillatom.TAG_Board.service;

import com.padillatom.TAG_Board.dto.request.AuthenticationRequest;
import com.padillatom.TAG_Board.dto.response.AuthenticationResponse;
import com.padillatom.TAG_Board.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse login(AuthenticationRequest loginDetails) {
        UsernamePasswordAuthenticationToken token  = new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword());
        authenticationManager.authenticate(token);
        String jwt = jwtService.generateToken(loginDetails.getUsername());

        return AuthenticationResponse.toDto(jwt);
    }

}

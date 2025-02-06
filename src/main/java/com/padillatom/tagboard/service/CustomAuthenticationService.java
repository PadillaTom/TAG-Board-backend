package com.padillatom.tagboard.service;

import com.padillatom.tagboard.dto.request.AuthenticationRequest;
import com.padillatom.tagboard.dto.request.RegisterRequest;
import com.padillatom.tagboard.dto.response.AuthenticationResponse;
import com.padillatom.tagboard.model.User;
import com.padillatom.tagboard.repository.UserRepository;
import com.padillatom.tagboard.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationService {

    private static final String REGISTER_EMAIL_IN_USE_MESSAGE = "Esta dirección de correo electrónico ya está registrada.";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthenticationResponse register(RegisterRequest registerDetails) {
        var matchingUser = userRepository.findByUsername(registerDetails.getUsername());

        if (matchingUser.isPresent()) {
            throw new BadCredentialsException(REGISTER_EMAIL_IN_USE_MESSAGE);
        } else {
            registerDetails.setPassword(passwordEncoder.encode(registerDetails.getPassword()));
            User savedUser = userRepository.save(RegisterRequest.toEntity(registerDetails));
            userRepository.flush();
            String jwt = jwtUtil.generate(savedUser.getUsername());

            return AuthenticationResponse.toDto(jwt);
        }
    }

    public AuthenticationResponse login(AuthenticationRequest loginDetails) {
        UsernamePasswordAuthenticationToken token  = new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword());
        authenticationManager.authenticate(token);
        String jwt = jwtUtil.generate((loginDetails.getUsername()));

        return AuthenticationResponse.toDto(jwt);
    }
}

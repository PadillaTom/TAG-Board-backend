package com.padillatom.TAG_Board.service;

import com.padillatom.TAG_Board.dto.request.AuthenticationRequest;
import com.padillatom.TAG_Board.dto.request.RegisterRequest;
import com.padillatom.TAG_Board.dto.response.AuthenticationResponse;
import com.padillatom.TAG_Board.model.Profile;
import com.padillatom.TAG_Board.model.UserEntity;
import com.padillatom.TAG_Board.repository.ProfileRepository;
import com.padillatom.TAG_Board.repository.UserRepository;
import com.padillatom.TAG_Board.utils.JwtUtil;
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
    private final ProfileRepository profileRepository;
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
            UserEntity savedUserEntity = userRepository.save(RegisterRequest.toEntity(registerDetails));
            userRepository.flush();

            profileRepository.save(Profile.builder().userEntity(savedUserEntity).build());

            String jwt = jwtUtil.generate(savedUserEntity.getUsername());

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

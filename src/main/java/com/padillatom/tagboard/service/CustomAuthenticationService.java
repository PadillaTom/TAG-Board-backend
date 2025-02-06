package com.padillatom.tagboard.service;

import com.padillatom.tagboard.dto.request.AuthenticationRequest;
import com.padillatom.tagboard.dto.request.RegisterRequest;
import com.padillatom.tagboard.dto.response.AuthenticationResponse;
import com.padillatom.tagboard.model.Profile;
import com.padillatom.tagboard.model.UserEntity;
import com.padillatom.tagboard.repository.ProfileRepository;
import com.padillatom.tagboard.repository.UserRepository;
import com.padillatom.tagboard.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.padillatom.TAG_Board.config.UserConstants.BAD_CREDENTIALS;
import static com.padillatom.TAG_Board.config.UserConstants.REGISTER_EMAIL_IN_USE_MESSAGE;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationService {

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
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword());
        try {
            authenticationManager.authenticate(token);
        } catch (Exception exception) {
            throw new BadCredentialsException(BAD_CREDENTIALS);
        }
        String jwt = jwtUtil.generate((loginDetails.getUsername()));
        return AuthenticationResponse.toDto(jwt);
    }
}

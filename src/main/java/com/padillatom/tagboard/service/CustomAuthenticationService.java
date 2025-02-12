package com.padillatom.tagboard.service;

import com.padillatom.tagboard.dto.request.AuthenticationRequest;
import com.padillatom.tagboard.dto.request.RegisterRequest;
import com.padillatom.tagboard.dto.response.AuthenticationResponse;
import com.padillatom.tagboard.model.Profile;
import com.padillatom.tagboard.model.Role;
import com.padillatom.tagboard.model.UserEntity;
import com.padillatom.tagboard.repository.ProfileRepository;
import com.padillatom.tagboard.repository.RoleRepository;
import com.padillatom.tagboard.repository.UserRepository;
import com.padillatom.tagboard.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

import static com.padillatom.tagboard.config.UserConstants.BAD_CREDENTIALS;
import static com.padillatom.tagboard.config.UserConstants.REGISTER_EMAIL_IN_USE_MESSAGE;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
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

            Role foundRole = roleRepository.findById(registerDetails.getRoleId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Role ID"));

            UserEntity newUser = UserEntity.builder()
                    .username(registerDetails.getUsername())
                    .password(registerDetails.getPassword())
                    .roles(Collections.singleton(foundRole))
                    .build();

            UserEntity savedUserEntity = userRepository.save(newUser);
            userRepository.flush();

            profileRepository.save(Profile.builder().userEntity(savedUserEntity).build());

            // Convert roles to a list of role names for JWT (e.g., ["ADMIN", "USER"])
            String roles = savedUserEntity.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.joining(","));

            String jwt = jwtUtil.generate(savedUserEntity.getUsername(), roles);

            return AuthenticationResponse.toDto(jwt);
        }
    }

    public AuthenticationResponse login(AuthenticationRequest loginDetails) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword());
        Authentication auth;

        try {
            auth = authenticationManager.authenticate(token);
        } catch (Exception exception) {
            throw new BadCredentialsException(BAD_CREDENTIALS);
        }

        String roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // Extract role names
                .collect(Collectors.joining(","));

        String jwt = jwtUtil.generate(loginDetails.getUsername(), roles);
        return AuthenticationResponse.toDto(jwt);
    }
}

package com.padillatom.TAG_Board.service;

import com.padillatom.TAG_Board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final String LOGIN_EXCEPTION_MESSAGE = "No hay ninguna cuenta asociada con la dirección de correo electrónico.";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsernameAndDeletedFalse(username);
        if (user.isPresent()) {
            var authUser = user.get();

            // Spring Security USER!!!
            // import org.springframework.security.core.userdetails.User;
            return new User(
                    authUser.getUsername(),
                    authUser.getPassword(),
                    authUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole())).toList()
            );
        } else {
            throw new UsernameNotFoundException(LOGIN_EXCEPTION_MESSAGE);
        }
    }
}

package com.padillatom.TAG_Board.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    private String jwt;

    public static AuthenticationResponse toDto(String token) {
        if (token == null) return null;
        return AuthenticationResponse.builder()
                .jwt(token)
                .build();
    }
}

package com.padillatom.tagboard.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;
}

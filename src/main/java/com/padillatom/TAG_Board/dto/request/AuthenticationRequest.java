package com.padillatom.TAG_Board.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
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

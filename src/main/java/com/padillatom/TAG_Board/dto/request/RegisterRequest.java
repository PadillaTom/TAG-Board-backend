package com.padillatom.TAG_Board.dto.request;

import com.padillatom.TAG_Board.model.Role;
import com.padillatom.TAG_Board.model.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RegisterRequest {

    @NotNull
    @NotEmpty
    @Size(min = 6)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 6)
    private String password;

    @NotNull
    private Integer roleId;

    public static UserEntity toEntity(RegisterRequest req) {
        return UserEntity.builder()
                .username(req.getUsername())
                .password(req.getPassword())
                .roles(List.of(Role.builder().id(req.getRoleId()).build()))
                .build();
    }
}

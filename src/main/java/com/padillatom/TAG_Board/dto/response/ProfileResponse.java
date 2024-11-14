package com.padillatom.TAG_Board.dto.response;

import com.padillatom.TAG_Board.model.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileResponse {

    private String name;

    private String lastName;

    private String phone;

    private String bio;

    private String imageUrl;

    private String email;

    public static ProfileResponse toDto(Profile entity) {
        return ProfileResponse.builder()
                .name(entity.getName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .bio(entity.getBio())
                .imageUrl("http://localhost:8080/api/v1/profile/" + entity.getId() + "/image")
                .email(entity.getUserEntity().getUsername())
                .build();
    }
}
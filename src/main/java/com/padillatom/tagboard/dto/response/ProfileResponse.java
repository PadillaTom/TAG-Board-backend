package com.padillatom.tagboard.dto.response;

import com.padillatom.tagboard.model.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileResponse {

    private String email;

    private String name;

    private String lastName;

    private String phone;

    private String bio;

    private String nickname;

    private String imageUrl;


    public static ProfileResponse toDto(Profile entity) {
        String imageUrl = "http://localhost:8080/api/v1/profile/" + entity.getId() + "/image";
        return ProfileResponse.builder()
                .email(entity.getUserEntity().getUsername())
                .nickname(entity.getNickname())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .bio(entity.getBio())
                .imageUrl(entity.getImageData() == null ? null : imageUrl)
                .build();
    }
}
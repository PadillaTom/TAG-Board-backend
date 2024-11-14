package com.padillatom.TAG_Board.dto.request;

import com.padillatom.TAG_Board.model.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@Setter
@Builder
public class ProfileRequest {

    private String name;

    private String lastName;

    private String phone;

    private String bio;

    private MultipartFile imageFile;

    public static Profile toEntity(ProfileRequest req) throws IOException {
        return Profile.builder()
                .name(req.getName())
                .lastName(req.getLastName())
                .phone(req.getPhone())
                .bio(req.getBio())
                .imageData(req.getImageFile() != null ? req.getImageFile().getBytes() : null)
                .build();
    }
}

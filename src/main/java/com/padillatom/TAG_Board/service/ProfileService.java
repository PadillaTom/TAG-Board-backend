package com.padillatom.TAG_Board.service;

import com.padillatom.TAG_Board.dto.request.ProfileRequest;
import com.padillatom.TAG_Board.dto.response.ImageResponse;
import com.padillatom.TAG_Board.dto.response.ProfileResponse;
import com.padillatom.TAG_Board.model.Profile;
import com.padillatom.TAG_Board.repository.ProfileRepository;
import com.padillatom.TAG_Board.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final String PROFILE_NOT_FOUND = "No se encuentra el perfil.";
    private static final String PROFILE_IMAGE_NOT_FOUND = "No se encuentra la imagen.";

    private final JwtUtil jwtUtil;
    private final ProfileRepository profileRepository;

    @Transactional
    private Profile findOrElse(Optional<Profile> optionalProfile) {
        return optionalProfile.orElseThrow(() -> new NoSuchElementException(PROFILE_NOT_FOUND));
    }

    @Transactional
    public ProfileResponse findMy() {
        return ProfileResponse
                .toDto(findOrElse(profileRepository.findFirstByUserEntityUsername(jwtUtil.getContextUsernameWithJWT())));
    }

    @Transactional
    public ProfileResponse update(ProfileRequest request) throws IOException {
        Profile profile = findOrElse(profileRepository.findFirstByUserEntityUsername(jwtUtil.getContextUsernameWithJWT()));

        profile.setName(request.getName());
        profile.setLastName(request.getLastName());
        profile.setPhone(request.getPhone());
        profile.setBio(request.getBio());
        profile.setImageData(request.getImageFile() != null ? request.getImageFile().getBytes() : null);

        return ProfileResponse.toDto(profileRepository.save(profile));
    }

    public ProfileResponse findById(Long id) {
        return ProfileResponse.toDto(findOrElse(profileRepository.findById(id)));
    }

    public byte[] findProfileImage(Long id) {
        Profile profile = findOrElse(profileRepository.findById(id));

        if (profile.getImageData() == null) {
            throw new NoSuchElementException(PROFILE_IMAGE_NOT_FOUND);
        }

        return ImageResponse.toDto(profile.getImageData()).getImageData();
    }
}

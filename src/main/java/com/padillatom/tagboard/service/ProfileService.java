package com.padillatom.tagboard.service;

import com.padillatom.tagboard.dto.request.ProfileRequest;
import com.padillatom.tagboard.dto.response.ImageResponse;
import com.padillatom.tagboard.dto.response.ProfileResponse;
import com.padillatom.tagboard.model.Profile;
import com.padillatom.tagboard.repository.ProfileRepository;
import com.padillatom.tagboard.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final String PROFILE_NOT_FOUND = "No se encuentra el perfil.";
    private static final String PROFILE_IMAGE_NOT_FOUND = "No se encuentra la imagen.";

    private final JwtUtil jwtUtil;
    private final ProfileRepository profileRepository;

    @Transactional
    public ProfileResponse findMy() {
        return ProfileResponse
                .toDto(findByUsernameOrThrow(jwtUtil.getContextUsernameWithJWT()));
    }

    @Transactional
    public ProfileResponse update(ProfileRequest request) throws IOException {
        Profile profile = findByUsernameOrThrow(jwtUtil.getContextUsernameWithJWT());

        profile.setName(request.getName());
        profile.setLastName(request.getLastName());
        profile.setPhone(request.getPhone());
        profile.setBio(request.getBio());
        profile.setNickname(request.getNickname());
        profile.setImageData(request.getImageFile() != null ? request.getImageFile().getBytes() : null);

        return ProfileResponse.toDto(profileRepository.save(profile));
    }

    public ProfileResponse findById(Long id) {
        return ProfileResponse.toDto(findByIdOrThrow(id));
    }

    public byte[] findProfileImage(Long id) {
        Profile profile = findByIdOrThrow(id);

        if (profile.getImageData() == null) {
            throw new NoSuchElementException(PROFILE_IMAGE_NOT_FOUND);
        }

        return ImageResponse.toDto(profile.getImageData()).getImageData();
    }

    /**
     * @param username username in JWT
     * @return finds Profile or throws PROFILE_NOT_FOUND
     */
    private Profile findByUsernameOrThrow(String username) {
        return profileRepository
                .findFirstByUserEntityUsername(username)
                .orElseThrow(() -> new NoSuchElementException(PROFILE_NOT_FOUND));
    }

    /**
     * @param id profile id
     * @return finds Profile or throws PROFILE_NOT_FOUND
     */
    private Profile findByIdOrThrow(Long id) {
        return profileRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(PROFILE_NOT_FOUND));
    }
}

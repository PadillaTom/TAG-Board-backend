package com.padillatom.TAG_Board.service;

import com.padillatom.TAG_Board.dto.request.ProfileRequest;
import com.padillatom.TAG_Board.dto.response.ImageResponse;
import com.padillatom.TAG_Board.dto.response.ProfileResponse;
import com.padillatom.TAG_Board.model.Profile;
import com.padillatom.TAG_Board.model.UserEntity;
import com.padillatom.TAG_Board.repository.ProfileRepository;
import com.padillatom.TAG_Board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final String PROFILE_NOT_FOUND = "No se encuentra el perfil.";

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProfileResponse update(ProfileRequest request) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername())
                .orElseThrow(() -> new NoSuchElementException("No se encuentra el usuario"));

        Profile profile = profileRepository.findFirstByUserEntityUsername(userEntity.getUsername())
                .orElseThrow(() -> new NoSuchElementException(PROFILE_NOT_FOUND));
        profile.setName(request.getName());
        profile.setLastName(request.getLastName());
        profile.setPhone(request.getPhone());
        profile.setBio(request.getBio());
        profile.setImageData(request.getImageFile() != null ? request.getImageFile().getBytes() : null);

        return ProfileResponse.toDto(profileRepository.save(profile));
    }

    public ProfileResponse findById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(PROFILE_NOT_FOUND));

        return ProfileResponse.toDto(profile);
    }

    public byte[] findProfileImage(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encuentra el profile"));

        if (profile.getImageData() == null) {
            throw new NoSuchElementException("La imagen no existe.");
        }

        return ImageResponse.toDto(profile.getImageData()).getImageData();
    }
}

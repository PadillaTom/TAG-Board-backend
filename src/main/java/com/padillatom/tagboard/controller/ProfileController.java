package com.padillatom.tagboard.controller;

import com.padillatom.tagboard.config.AppConstants;
import com.padillatom.tagboard.dto.request.ProfileRequest;
import com.padillatom.tagboard.dto.response.ProfileResponse;
import com.padillatom.tagboard.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.PROFILE_API_URL)
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ProfileResponse get() {
        return profileService.findMy();
    }

    @PutMapping
    public ProfileResponse update(@Valid @ModelAttribute ProfileRequest request) throws IOException {
        return profileService.update(request);
    }

    @GetMapping(AppConstants.BY_ID_PARAM)
    public ProfileResponse getById(@PathVariable Long id) {
        return profileService.findById(id);
    }

    @GetMapping(AppConstants.IMAGE_BY_PROFILE_ID)
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(profileService.findProfileImage(id));
    }
}

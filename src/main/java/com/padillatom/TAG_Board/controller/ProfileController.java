package com.padillatom.TAG_Board.controller;

import com.padillatom.TAG_Board.config.AppConstants;
import com.padillatom.TAG_Board.dto.request.ProfileRequest;
import com.padillatom.TAG_Board.dto.response.ProfileResponse;
import com.padillatom.TAG_Board.service.ProfileService;
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

    @PutMapping
    public ProfileResponse update(@Valid @ModelAttribute ProfileRequest request) throws IOException {
        return profileService.update(request);
    }

    @GetMapping(AppConstants.BY_ID_PARAM)
    public ProfileResponse get(@PathVariable Long id) {
        return profileService.findById(id);
    }

    @GetMapping(AppConstants.IMAGE_BY_PROFILE_ID)
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(profileService.findProfileImage(id));
    }
}
